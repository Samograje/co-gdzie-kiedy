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
      withHistory: false,
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
          items: response.items.map((item) => ({
            ...item,
            isDeletedLabel: item.deleted ? 'TAK' : 'NIE',
          })),
          totalElements: response.totalElements,
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
      withHistory: this.state.withHistory,
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
    if (this.state.withHistory) {
      columns.push({
        name: 'isDeleted',
        label: 'Usunięty',
      })
    }

    const itemActions = [
      {
        label: 'Edytuj',
        onClick: (itemData) => this.props.push('AffiliationDetails', {
          mode: 'edit',
          id: itemData.id,
        }),
        disabled: (itemData) => itemData.deleted,
      },
      {
        label: 'Usuń',
        onClick: (itemData) => {
          // TODO: usuwanie afiliacji
        },
        disabled: (itemData) => itemData.deleted,
      },
    ];

    const groupActions = [
      {
        label: 'Dodaj osobę / miejsce',
        onClick: () => this.props.push('AffiliationDetails', {
          mode: 'create',
        }),
      },
      {
        label: this.state.withHistory ? 'Wyświetl tylko nieusunięte rekordy' : 'Wyświetl również usunięte rekordy',
        onClick: () => {
          const withHistory = !this.state.withHistory;
          this.fetchData({
            filters: this.state.filters,
            withHistory,
          });
          this.setState({
            withHistory,
          });
        },
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
