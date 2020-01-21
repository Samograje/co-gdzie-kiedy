import React, {Component} from 'react';
import SoftwareListComponent from './SoftwareListComponent';
import request from "../../APIClient";
import moment from "moment";
import {Platform} from "react-native";

class SoftwareListContainer extends Component {
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
    request('/api/software', options)
        .then((response) => response.json())
        .then((response) => {
          if (!this._isMounted) {
            return;
          }
          for(let i = 0; i < response.items.length; i++)
          {
            let duration = response.items[i].duration;
            let months = moment(duration).month() +  12 * (moment(duration).year() - moment(0).year());
            if(months <= 0)
              response.items[i].duration = "Licencja utraciła ważność";
            else
              response.items[i].duration = months;
          }
          this.setState({
            loading: false,
            ...response,
          });
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

  deleteCall = (id) => {
    request(`/api/software/${id}`,{
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

  render() {
    const columns = [
      {
        name: 'name',
        label: 'Nazwa',
        filter: true,
      },
      {
        name: 'inventoryNumber',
        label: 'Numer inwentarzowy',
        filter: true,
      },
      {
        name: 'key',
        label: 'Klucz produktu',
        filter: true,
      },
      {
        name: 'availableKeys',
        label: 'Ilość dostępnych kluczy',
      },
      {
        name: 'duration',
        label: 'Ważna przez (msc)',
      },
      {
        name: 'computerSetInventoryNumbers',
        label: 'Powiązane zestawy komputerowe',
      },
    ];

    const itemActions = [
      {
        label: 'Edytuj',
        icon: require('./../../images/ic_action_edit.png'),
        onClick: (itemData) => this.props.push('SoftwareDetails', {
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
        label: 'Historia zestawów komputerowych',
        icon: require('./../../images/ic_action_devices.png'),
        onClick: (itemData) => this.props.push('SoftwareHistory', {
          id: itemData.id,
        }),
      },
    ];

    const groupActions = [
      {
        disabled: false,
        label: 'Dodaj oprogramowanie',
        onClick: () => this.props.push('SoftwareDetails', {
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
      <SoftwareListComponent
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

export default SoftwareListContainer;
