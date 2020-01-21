import React, {Component} from 'react';
import {Platform} from 'react-native';
import HardwareListComponent from './HardwareListComponent';
import request from '../../APIClient';

class HardwareListContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      error: false,
      items: [],
      totalElements: null,
      filters: {},
    };
  }

  componentDidMount() {
    this._isMounted = true;
    this.fetchData();
  }

  componentWillUnmount() {
    this._isMounted = false;
  }

  fetchData = (options) => {
    this.setState({
      loading: true,
      error: false,
    });
    request('/api/hardware', options)
      .then((response) => response.json())
      .then((response) => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          loading: false,
          ...response,
        })
      })
      .catch(() => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          loading: false,
          error: true,
        });
      })
  };

  deleteCall = (id) => {
    request(`/api/hardware/${id}`,{
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }
    }).then((response) => response.json())
        .then(() => {
          if (!this._isMounted) {
            return;
          }
          this.fetchData();
        })
        .catch((error) => {
          if (!this._isMounted) {
            return;
          }
          console.error(error);
        });
  };

  handleFilterChange = (fieldName, text) => {
    const newFilters = {
      ...this.state.filters,
      [fieldName]: text,
    };
    this.setState({
      filters: newFilters,
    });
    this.fetchData({
      filters: newFilters,
    });
  };

  render() {

    const columns = [
      {
        name: 'name',
        label: 'Nazwa',
        filter: true,
      },
      {
        name: 'type',
        label: 'Typ',
        filter: true,
      },
      {
        name: 'inventoryNumber',
        label: 'Numer inwentarzowy',
        filter: true,
      },
      {
        name: 'affiliationName',
        label: 'Przynależy do',
        filter: true,
      },
      {
        name: 'computerSetInventoryNumber',
        label: 'Numer inwentarzowy powiązanego zestawu komputerowego',
        filter: true,
      },
    ];

    const itemActions = [
      {
        label: 'Edytuj',
        icon: require('./../../images/ic_action_edit.png'),
        onClick: (itemData) => this.props.push('HardwareDetails', {
          mode: 'edit',
          id: itemData.id,
        }),
      },
      {
        label: 'Usuń',
        icon: require('./../../images/ic_action_delete.png'),
        onClick: (itemData) => {
          this.deleteCall(itemData.id)
        },
      },
      {
        label: 'Historia osób / miejsc',
        icon: require('./../../images/ic_action_person_pin.png'),
        onClick: (itemData) => this.props.push('HardwareHistory', {
          mode: 'affiliations',
          id: itemData.id,
        }),
      },
      {
        label: 'Historia zestawów komputerowych',
        icon: require('./../../images/ic_action_devices.png'),
        onClick: (itemData) => this.props.push('HardwareHistory', {
          mode: 'computer-sets',
          id: itemData.id,
        }),
      },
    ];

    const groupActions = [
      {
        disabled: false,
        label: 'Dodaj sprzęt',
        onClick: () => this.props.push('HardwareDetails', {
          mode: 'create',
        }),
      },
      {
        disabled: Platform.OS !== 'android',
        label: 'Wyszukaj za pomocą kodu QR',
        onClick: () => {
          this.props.push('ScanScreen')
        },
      },
    ];

    return (
      <HardwareListComponent
        error={this.state.error}
        loading={this.state.loading}
        items={this.state.items}
        totalElements={this.state.totalElements}
        filters={this.state.filters}
        onFilterChange={this.handleFilterChange}
        columns={columns}
        itemActions={itemActions}
        groupActions={groupActions}
      />
    );
  }
}

export default HardwareListContainer;
