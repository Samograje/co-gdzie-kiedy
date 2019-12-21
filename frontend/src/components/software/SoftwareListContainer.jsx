import React, {Component} from 'react';
import SoftwareListComponent from './SoftwareListComponent';
import HardwareListComponent from "../hardware/HardwareListComponent";

class SoftwareListContainer extends Component {
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
    fetch('/api/software')
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

  render() {
    const columns = [
      {
        name: 'name',
        label: 'Nazwa',
      },
      {
        name: 'inventoryNumber',
        label: 'Numer inwentarzowy',
      },
      {
        name: 'key',
        label: 'Klucz produktu',
      },
      {
        name: 'availableKeys',
        label: 'Ilość dostępnych kluczy',
      },
      {
        name: 'duration',
        label: 'Ważna do',
      },
    ];

    return (
      <SoftwareListComponent
          onFetchData={this.fetchData}
          error={this.state.error}
          loading={this.state.loading}
          items={this.state.items}
          totalElements={this.state.totalElements}
          columns={columns}
      />
    );
  }
}

export default SoftwareListContainer;
