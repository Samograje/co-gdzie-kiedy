import React, {Component} from 'react';
import {Platform} from 'react-native';
import ComputerSetsListComponent from './ComputerSetsListComponent';
import request from '../../APIClient';

class ComputerSetsListContainer extends Component {

  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      error: false,
      items: [],
      totalElements: null,
      filters: {},
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
    request('/api/computer-sets', options)
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
    request('/api/computer-sets/export')
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
        name: 'computerSetInventoryNumber',
        label: 'Numer inwentarzowy',
        filter: true,
      },
      {
        name: 'affiliationName',
        label: 'Przynależy do',
        filter: false,
      },
      {
        name: 'softwareInventoryNumbers',
        label: 'Numery inwentarzowe oprogramowania',
      },
      {
        name: 'hardwareInventoryNumbers',
        label: 'Numery inwentarzowe sprzętów',
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
        onClick: (itemData) => this.props.push('ComputerSetDetails', {
          mode: 'edit',
          id: itemData.id,
        }),
      },
      {
        label: 'Usuń',
        icon: require('./../../images/ic_action_delete.png'),
        onClick: (itemData) => {
          // TODO: usuwanie zestawu komputerowego
        },
      },
      {
        label: 'Historia osób / miejsc',
        icon: require('./../../images/ic_action_person_pin.png'),
        onClick: (itemData) => this.props.push('ComputerSetHistory', {
          mode: 'affiliations',
          id: itemData.id,
        }),
      },
      {
        label: 'Historia sprzętu',
        icon: require('./../../images/ic_action_mouse.png'),
        onClick: (itemData) => this.props.push('ComputerSetHistory', {
          mode: 'hardware',
          id: itemData.id,
        }),
      },
      {
        label: 'Historia oprogramowania',
        icon: require('./../../images/ic_action_web.png'),
        onClick: (itemData) => this.props.push('ComputerSetHistory', {
          mode: 'software',
          id: itemData.id,
        }),
      },
    ];

    const groupActions = [
      {
        disabled: false,
        label: 'Dodaj zestaw komputerowy',
        onClick: () => this.props.push('ComputerSetDetails', {
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
      <ComputerSetsListComponent
        onFilterChange={this.handleFilterChange}
        columns={columns}
        itemActions={itemActions}
        groupActions={groupActions}
        {...this.state}
      />
    );
  }
}

export default ComputerSetsListContainer;
