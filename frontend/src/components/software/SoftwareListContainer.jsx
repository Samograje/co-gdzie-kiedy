import React, {Component} from 'react';
import {Platform} from 'react-native';
import moment from 'moment';
import SoftwareListComponent from './SoftwareListComponent';
import request from "../../APIClient";

class SoftwareListContainer extends Component {
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
      withHistory: false,
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
    request('/api/software', options)
      .then((response) => response.json())
      .then((response) => {
        if (!this._isMounted) {
          return;
        }
        for (let i = 0; i < response.items.length; i++) {
          let duration = response.items[i].duration;
          let months = moment(duration).month() + 12 * (moment(duration).year() - moment(0).year());
          if (months <= 0)
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
      withHistory: this.state.withHistory,
    });
  };

  deleteCall = () => {
    request(`/api/software/${this.state.itemToDeleteId}`, {
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

  getPdf = () => {
    request('/api/software/export')
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
    if (this.state.withHistory) {
      columns.push({
        name: 'deleted',
        label: 'Usunięty',
      })
    }

    const itemActions = [
      {
        label: 'Kopiuj',
        icon: require('./../../images/ic_action_content_copy.png'),
        onClick: (itemData) => this.props.push('SoftwareDetails', {
          mode: 'copy',
          id: itemData.id,
        }),
      },
      {
        label: 'Edytuj',
        icon: require('./../../images/ic_action_edit.png'),
        onClick: (itemData) => this.props.push('SoftwareDetails', {
          mode: 'edit',
          id: itemData.id,
        }),
        disabledIfDeleted: true,
      },
      {
        label: 'Usuń',
        icon: require('./../../images/ic_action_delete.png'),
        onClick: (itemData) => this.setState({
          dialogOpened: true,
          itemToDeleteId: itemData.id,
        }),
        disabledIfDeleted: true,
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
        label: this.state.withHistory ? 'Nie wyświetlaj archiwum' : 'Wyświetl archiwum',
        onClick: () => {
          const withHistory = !this.state.withHistory;
          this.fetchData({
            filters: this.state.filters,
            withHistory,
          });
          this.setState({
            withHistory,
          });
        },
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
        dialogOpened={this.state.dialogOpened}
        dialogHandleConfirm={this.deleteCall}
        dialogHandleReject={this.closeDialog}
      />
    );
  }
}

export default SoftwareListContainer;
