import React, {Component} from 'react';
import AffiliationsListComponent from './AffiliationsListComponent';
import request from "../../APIClient";

class AffiliationsListContainer extends Component {

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
    request('/api/affiliations')
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
        onClick: (itemData) => {
          // TODO: usuwanie afiliacji
        },
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
        onFetchData={this.fetchData}
        columns={columns}
        itemActions={itemActions}
        groupActions={groupActions}
        {...this.state}
      />
    );
  }
}

export default AffiliationsListContainer;
