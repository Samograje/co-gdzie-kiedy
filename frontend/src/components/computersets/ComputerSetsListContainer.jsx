import React, {Component} from 'react';
import ComputerSetsListComponent from './ComputerSetsListComponent';
import request from '../../APIClient';
import {Platform} from "react-native";

class ComputerSetsListContainer extends Component {

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

    if (Platform.OS === 'android') {
      const {navigation} = this.props;
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
        filter: true,
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

    const itemActions = [
      {
        label: 'Edytuj',
        onClick: (itemData) => this.props.push('ComputerSetDetails', {
          mode: 'edit',
          id: itemData.id,
        }),
      },
      {
        label: 'Usuń',
        onClick: (itemData) => {
          // TODO: usuwanie zestawu komputerowego
        },
      },
      {
        label: 'HA',
        onClick: (itemData) => this.props.push('ComputerSetHistory', {
          mode: 'affiliations',
          id: itemData.id,
        }),
      },
      {
        label: 'HH',
        onClick: (itemData) => this.props.push('ComputerSetHistory', {
          mode: 'hardware',
          id: itemData.id,
        }),
      },
      {
        label: 'HS',
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
        disabled: Platform.OS !== 'android',
        label: 'Wyszukaj za pomocą kodu QR',
        onClick: () => {
          this.props.push('ScanScreen')
        },
      },
    ];

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
