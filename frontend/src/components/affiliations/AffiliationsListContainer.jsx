import React, {Component} from 'react';
import {Platform} from 'react-native';
import AffiliationsListComponent from './AffiliationsListComponent';
import request from '../../APIClient';

class AffiliationsListContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      error: false,
      items: [],
      totalElements: null,
      filters: {},
      isDialogOpened: false,
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
    request('/api/affiliations', options)
      .then((response) => response.json())
      .then((response) => {
        if (!this._isMounted) {
          return;
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

  deleteItem = () => {
    this.closeDialog();
    request(`/api/affiliations/${this.state.itemToDeleteId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }
    })
      .then((response) => response.json())
      .then((response) => {
        if (!this._isMounted) {
          return;
        }
        if (!response.success) {
          this.setState({
            error: true,
          });
          return;
        }
        this.fetchData();
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

  closeDialog = () => this.setState({
    isDialogOpened: false,
    itemToDeleteId: null,
  });

  getPdf = () => {
    request('/api/affiliations/export')
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
        name: 'firstName',
        label: 'Imię',
        filter: true,
      },
      {
        name: 'lastName',
        label: 'Nazwisko',
        filter: true,
      },
      {
        name: 'location',
        label: 'Lokalizacja',
        filter: true,
      },
      {
        name: 'computerSetsInventoryNumbers',
        label: 'Numery inwentarzowe powiązanych zestawów komputerowych',
      },
      {
        name: 'hardwareInventoryNumbers',
        label: 'Numery inwentarzowe powiązanych sprzętów',
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
        label: 'Edytuj',
        icon: require('./../../images/ic_action_edit.png'),
        onClick: (itemData) => this.props.push('AffiliationDetails', {
          mode: 'edit',
          id: itemData.id,
        }),
        disabledIfDeleted: true,
      },
      {
        label: 'Usuń',
        icon: require('./../../images/ic_action_delete.png'),
        onClick: (itemData) => this.setState({
          isDialogOpened: true,
          itemToDeleteId: itemData.id,
        }),
        disabledIfDeleted: true,
      },
    ];

    const groupActions = [
      {
        label: 'Dodaj osobę / miejsce',
        onClick: () => this.props.push('AffiliationDetails', {
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

    return (
      <AffiliationsListComponent
        columns={columns}
        itemActions={itemActions}
        groupActions={groupActions}
        filters={this.state.filters}
        onFilterChange={this.handleFilterChange}
        loading={this.state.loading}
        error={this.state.error}
        items={this.state.items}
        totalElements={this.state.totalElements}
        isDialogOpened={this.state.isDialogOpened}
        dialogHandleConfirm={this.deleteItem}
        dialogHandleReject={this.closeDialog}
      />
    );
  }
}

export default AffiliationsListContainer;
