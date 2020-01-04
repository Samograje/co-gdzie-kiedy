import React, {Component} from 'react';
import HardwareListComponent from './HardwareListComponent';
import request from "../../APIClient";

class HardwareListContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      error: false,
      items: [],
      totalElements: null,
    };
  }

  componentDidMount() {
    this.fetchData();
  }

  fetchData = () => {
    request('/api/hardware')
      .then((response) => response.json())
      .then((response) => {
        this.setState({
          loading: false,
          error: false,
          ...response
        })
      })
      .catch(() => {
        this.setState({
          loading: false,
          error: true
        });
      })
  };

  render() {

    const columns = [
      {
        name: 'name',
        label: 'Nazwa',
      },
      {
        name: 'type',
        label: 'Typ',
      },
      {
        name: 'inventoryNumber',
        label: 'Numer inwentarzowy',
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
        onFetchData={this.fetchData}
        error={this.state.error}
        loading={this.state.loading}
        items={this.state.items}
        totalElements={this.state.totalElements}
        columns={columns}
        itemActions={itemActions}
        groupActions={groupActions}
      />
    );
  }
}

export default HardwareListContainer;
