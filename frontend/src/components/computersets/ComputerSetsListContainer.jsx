import React, {Component} from 'react';
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
    };
  }

  componentDidMount() {
    this.fetchData();
  }

  fetchData = (options) => {
    this.setState({
      loading: true,
      error: false,
    });
    request('/api/computer-sets', options)
      .then((response) => response.json())
      .then((response) => {
        this.setState({
          loading: false,
          ...response,
        });
      })
      .catch(() => {
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
      // TODO: akcje wyświetlania historii
    ];

    const groupActions = [
      {
        label: 'Dodaj zestaw komputerowy',
        onClick: () => this.props.push('ComputerSetDetails', {
          mode: 'create',
        }),
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
