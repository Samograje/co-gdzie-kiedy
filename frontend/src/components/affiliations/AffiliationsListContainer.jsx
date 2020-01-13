import React, {Component} from 'react';
import AffiliationsListComponent from './AffiliationsListComponent';
import request from '../../APIClient';

class AffiliationsListContainer extends Component {
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
    request('/api/affiliations', options)
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

  deleteItem = (id) => {
    request(`/api/affiliations/${id}`,{
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }
    })
      .then((response) => response.json())
      .then((response) => {
        if (response.success) {
          this.fetchData();
        } else {
          this.setState({
            error: true,
          });
        }
      })
      .catch(() => {
        this.setState({
          error: true,
        });
      });
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
    ];

    const itemActions = [
      {
        label: 'Edytuj',
        onClick: (itemData) => this.props.push('AffiliationDetails', {
          mode: 'edit',
          id: itemData.id,
        }),
      },
      {
        label: 'Usuń',
        onClick: (itemData) => this.deleteItem(itemData.id),
      },
      // TODO: akcje wyświetlania historii powiązań
    ];

    const groupActions = [
      {
        label: 'Dodaj osobę / miejsce',
        onClick: () => this.props.push('AffiliationDetails', {
          mode: 'create',
        }),
      },
    ];

    return (
      <AffiliationsListComponent
        columns={columns}
        itemActions={itemActions}
        groupActions={groupActions}
        filters={this.state.filters}
        onFilterChange={this.handleFilterChange}
        loading={this.state.loading}
        error={this.state.error}
        items={this.state.items}
        totalElements={this.state.totalElements}
      />
    );
  }
}

export default AffiliationsListContainer;
