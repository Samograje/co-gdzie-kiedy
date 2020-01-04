import React, {Component} from 'react';
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
    };
  }
  componentDidMount() {
    this.fetchData();
  }

  fetchData = () => {
    request('/api/software')
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

    const itemActions = [
      {
        label: 'Edytuj',
        onClick: (itemData) => this.props.push('SoftwareDetails', {
          mode: 'edit',
          id: itemData.id,
        }),
      },
      // TODO: akcja usuwania oprogramowania
    ];
    const footerActions = [
      {
        label: 'Dodaj oprogramowanie',
        onClick: () => this.props.push('SoftwareDetails', {
          mode: 'create',
        }),
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
          itemActions={itemActions}
          footerActions={footerActions}
      />
    );
  }
}

export default SoftwareListContainer;
