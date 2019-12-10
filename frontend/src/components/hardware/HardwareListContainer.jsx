import React, {Component} from 'react';
import HardwareListComponent from './HardwareListComponent';

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
    // TODO: po zaktualizowaniu backendu tutaj należy zaktualizować url
    fetch('/api/hardware?solo-only=false')
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
        onClick: (itemData) => this.props.history.push(`/hardware/edit/${itemData.id}`),
      },
      // TODO: akcja usuwania hardware'u
    ];

    const footerActions = [
      {
        label: 'Dodaj sprzęt',
        onClick: () => this.props.history.push('/hardware/create'),
      },
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
        footerActions={footerActions}
      />
    );
  }
}

export default HardwareListContainer;
