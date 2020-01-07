import React, {Component} from 'react';
import ComputerSetsListComponent from './ComputerSetsListComponent';
import request from "../../APIClient";

class ComputerSetsListContainer extends Component {

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
    request('/api/computer-sets')
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
        name: 'computerSetInventoryNumber',
        label: 'Numer inwentarzowy',
      },
      {
        name: 'affiliationName',
        label: 'Przynależy do',
      },
      {
        name: 'softwareInventoryNumbers',
        label: 'Numery inwentarzowe oprogramowania',
      },
      {
        name: 'hardwareInventoryNumbers',
        label: 'Numery inwentarzowe sprzętów',
      },
    ];

    const itemActions = [
      {
        label: 'Edytuj',
        onClick: (itemData) => this.props.push('ComputerSetDetails', {
          mode: 'edit',
          id: itemData.id,
        }),
      },
      {
        label: 'Usuń',
        onClick: (itemData) => {
          // TODO: usuwanie zestawu komputerowego
        },
      },
      {
        label: 'HA',
        onClick: (itemData) => this.props.push('ComputerSetHistory', {
          mode: 'affiliations',
          id: itemData.id,
        }),
      },
      {
        label: 'HH',
        onClick: (itemData) => this.props.push('ComputerSetHistory', {
          mode: 'hardware',
          id: itemData.id,
        }),
      },
      {
        label: 'HS',
        onClick: (itemData) => this.props.push('ComputerSetHistory', {
          mode: 'software',
          id: itemData.id,
        }),
      },
    ];

    const groupActions = [
      {
        label: 'Dodaj zestaw komputerowy',
        onClick: () => this.props.push('ComputerSetDetails', {
          mode: 'create',
        }),
      },
    ];

    return (
        <ComputerSetsListComponent
            onFetchData={this.fetchData}
            columns={columns}
            itemActions={itemActions}
            groupActions={groupActions}
            {...this.state}
        />
    );
  }
}

export default ComputerSetsListContainer;
