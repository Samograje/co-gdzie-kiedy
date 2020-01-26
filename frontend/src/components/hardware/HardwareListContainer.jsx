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
      dialogOpened: false,
      itemToDeleteId: null,
    };
  }

  componentDidMount() {
    this._isMounted = true;

    if (Platform.OS === 'android') {
      this.focusListener = this.props.addListener('didFocus', () => {
        this.fetchData();
      });
    } else {
      this.fetchData();
    }
  }

  componentWillUnmount() {
    this._isMounted = false;
    if (Platform.OS === 'android') {
      this.focusListener.remove();
    }
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

  deleteCall = () => {
    request(`/api/hardware/${this.state.itemToDeleteId}`,{
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
          this.closeDialog();
          this.fetchData();
        })
        .catch((error) => {
          if (!this._isMounted) {
            return;
          }
          console.error(error);
        });
  };

  closeDialog = () => this.setState({
    dialogOpened: false,
    itemToDeleteId: null,
  });

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

  getPdf = () => {
    request('/api/hardware/export')
      .then((response) => response.blob())
      .then((blob) => {
        const fileURL = URL.createObjectURL(blob);
        window.open(fileURL);
        // TODO: obsługa tego na komórze
      })
      .catch(() => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          error: true,
        });
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
      },
      {
        name: 'computerSetInventoryNumber',
        label: 'Numer inwentarzowy powiązanego zestawu komputerowego',
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
        onClick: (itemData) => this.setState({
          dialogOpened: true,
          itemToDeleteId: itemData.id,
        }),
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
    ];
    if (Platform.OS === 'web') {
      groupActions.push({
        label: 'Eksportuj do pdf',
        onClick: this.getPdf,
      });
    }
    if (Platform.OS === 'android') {
      groupActions.push({
        label: 'Wyszukaj za pomocą kodu QR',
        onClick: () => this.props.push('ScanScreen'),
      });
    }

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
        dialogOpened={this.state.dialogOpened}
        dialogHandleConfirm={this.deleteCall}
        dialogHandleReject={this.closeDialog}
      />
    );
  }
}

export default HardwareListContainer;
