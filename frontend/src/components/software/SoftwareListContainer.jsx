import React, {Component} from 'react';
import SoftwareListComponent from './SoftwareListComponent';

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
        inventoryNumber: 'inventoryNumber',
        label: 'Numer inwentażowy',
      },
      {
        key: 'key',
        label: 'Klucz produktu',
      },
      {
        availableKeys: 'availableKeys',
        label: 'Ilość dostępnych kluczy',
      },
      {
        duration: 'duration',
        label: 'Ważna do',
      },
      {
        validTo: 'validTo',
        label: 'Usunięta dnia',
      },
    ];

    return (
      <SoftwareListComponent
          onFetchData={this.fetchData}
          columns={columns}
          {...this.state}
      />
    );
  }
}

export default SoftwareListContainer;
