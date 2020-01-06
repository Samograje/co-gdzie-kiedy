import React, {Component} from 'react';
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
    this.fetchData();
  }

  fetchData = (options) => {
    this.setState({
      loading: true,
      error: false,
    });
    request('/api/hardware', options)
      .then((response) => response.json())
      .then((response) => {
        this.setState({
          loading: false,
          ...response,
        })
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
        onClick: (itemData) => this.props.push('HardwareDetails', {
          mode: 'edit',
          id: itemData.id,
        }),
      },
      {
        label: 'Usuń',
        onClick: (itemData) => {
          // TODO: usuwanie hardware'u
        },
      },
      // TODO: akcje wyświetlania historii powiązań
    ];

    const groupActions = [
      {
        label: 'Dodaj sprzęt',
        onClick: () => this.props.push('HardwareDetails', {
          mode: 'create',
        }),
      },
      {
        label: 'Wyszukaj za pomocą kodu QR',
        onClick: () => {
          // TODO: wyszukiwanie po kodzie QR
        },
      }
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
