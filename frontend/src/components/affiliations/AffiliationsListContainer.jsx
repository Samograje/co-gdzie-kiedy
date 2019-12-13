import React, {Component} from 'react';
import AffiliationsListComponent from './AffiliationsListComponent';

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
    fetch('/api/affiliations')
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
        onClick: (itemData) => this.props.navigation.navigate('AffiliationDetails', {
          mode: 'edit',
          id: itemData.id,
        }),
      },
      // TODO: akcja usuwania afiliacji
    ];

    const footerActions = [
      {
        label: 'Dodaj osobę / miejsce',
        onClick: () => this.props.navigation.navigate('AffiliationDetails', {
          mode: 'create',
        }),
      },
    ];

    return (
      <AffiliationsListComponent
        onFetchData={this.fetchData}
        columns={columns}
        itemActions={itemActions}
        footerActions={footerActions}
        {...this.state}
      />
    );
  }
}

export default AffiliationsListContainer;
