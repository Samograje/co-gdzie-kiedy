import React, {Component} from 'react';
import SoftwareListComponent from './SoftwareListComponent';
import request from "../../APIClient";
import moment from "moment";
import {Platform} from "react-native";

class SoftwareListContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      error: false,
      items: [],
      totalElements: null,
      filters: {},
      dialogOpened: false,
      itemToDeleteId: null,
      currentPositionScrollViewY: 50,
    };
  }
  componentDidMount() {
    this._isMounted = true;
    this.fetchData();
  }

  componentWillUnmount() {
    this._isMounted = false;
  }

  fetchData = (options) => {
    this.setState({
      loading: true,
      error: false,
    });
    request('/api/software', options)
      .then((response) => response.json())
      .then((response) => {
        if (!this._isMounted) {
          return;
        }
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
        if (!this._isMounted) {
          return;
        }
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
    });
  };

  deleteCall = () => {
    request(`/api/software/${this.state.itemToDeleteId}`,{
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }
    }).then((response) => response.json())
      .then(() => {
        if (!this._isMounted) {
          return;
        }
        this.closeDialog();
        this.fetchData();
      })
      .catch((error) => {
        if (!this._isMounted) {
          return;
        }
        console.error(error);
      });
  };

  closeDialog = () => this.setState({
    dialogOpened: false,
    itemToDeleteId: null,
  });

  handleScroll = (event) => {this.setState({currentPositionScrollViewY: event.nativeEvent.contentOffset.y})};

  render() {
    const columns = [
      {
        name: 'name',
        label: 'Nazwa',
        filter: true,
      },
      {
        name: 'inventoryNumber',
        label: 'Numer inwentarzowy',
        filter: true,
      },
      {
        name: 'key',
        label: 'Klucz produktu',
        filter: true,
      },
      {
        name: 'availableKeys',
        label: 'Ilość dostępnych kluczy',
      },
      {
        name: 'duration',
        label: 'Ważna przez (msc)',
      },
      {
        name: 'computerSetInventoryNumbers',
        label: 'Powiązane zestawy komputerowe',
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
      {
        label: 'Usuń',
        onClick: (itemData) => this.setState({
          dialogOpened: true,
          itemToDeleteId: itemData.id,
        }),
      },
      {
        label: 'HC',
        onClick: (itemData) => this.props.push('SoftwareHistory', {
          id: itemData.id,
        }),
      },
    ];

    const groupActions = [
      {
        disabled: false,
        label: 'Dodaj oprogramowanie',
        onClick: () => this.props.push('SoftwareDetails', {
          mode: 'create',
        }),
      },
      {
        disabled: Platform.OS !== 'android',
        label: 'Wyszukaj za pomocą kodu QR',
        onClick: () => {
          this.props.push('ScanScreen')
        },
      },
    ];

    return (
      <SoftwareListComponent
        error={this.state.error}
        loading={this.state.loading}
        items={this.state.items}
        totalElements={this.state.totalElements}
        filters={this.state.filters}
        currentPositionScrollViewY={this.state.currentPositionScrollViewY}
        onFilterChange={this.handleFilterChange}
        columns={columns}
        itemActions={itemActions}
        groupActions={groupActions}
        dialogOpened={this.state.dialogOpened}
        dialogHandleConfirm={this.deleteCall}
        dialogHandleReject={this.closeDialog}
        handleScroll={this.handleScroll}
      />
    );
  }
}

export default SoftwareListContainer;
