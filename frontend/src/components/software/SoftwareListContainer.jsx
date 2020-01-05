import React, {Component} from 'react';
import SoftwareListComponent from './SoftwareListComponent';
import request from "../../APIClient";
import moment from "moment";

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
          for(let i = 0; i < response.items.length; i++)
          {
            let duration = response.items[i].duration;
            let months = moment(duration).month() +  12 * (moment(duration).year() - moment(0).year());
            if(months <= 0)
              response.items[i].duration = "Licencja utraciła ważność";
            else
              response.items[i].duration = months;
          }
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
        label: 'Ważna przez (msc)',
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
