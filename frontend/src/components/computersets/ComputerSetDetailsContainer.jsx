import React, {Component} from 'react';
import ComputerSetDetailsComponent from './ComputerSetDetailsComponent';
import request from "../../APIClient";
import {Dimensions} from "react-native";

class ComputerSetDetailsContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      affiliationID: '',
      hardwareIDs: [],
      softwareIDs: [],
      loadingAffiliations: true,
      loadingHardware: true,
      loadingSoftware: true,
      error: false,
      dataSourceAffiliations: {"items": []},
      dataSourceHardware: {"items": []},
      dataSourceSoftware: [],
      isInvalid: true,
      isSubmitting: false,
    };
  }

  componentDidMount() {
    this._isMounted = true;
    this.fetchDataAffiliations();
    this.fetchDataHardware();
    this.fetchDataSoftware();

    if (this.props.mode === 'edit')
      this.getDataForEditCall();
  }

  componentWillUnmount() {
    this._isMounted = false;
  }

  fetchDataAffiliations = (query) => {
    const options = {
      filters: {
        firstName: query,
        lastName: query,
        location: query,
      },
      searchType: 'OR',
    };

    request('/api/affiliations', options)
      .then((response) => response.json())
      .then((response) => {
        if (!this._isMounted) {
          return;
        }
        response.items = response.items.map((item) => ({
          id: item.id,
          name: `${item.firstName}${item.firstName && item.lastName && ' '}${item.lastName}${item.location && (item.firstName || item.lastName) && ' - '}${item.location}`,
        }));
        this.setState({
          loadingAffiliations: false,
          dataSourceAffiliations: response
        });
      })
      .catch(() => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          loadingAffiliations: false,
          error: true,
        });
      })
  };

  fetchDataHardware = (query) => {
    const options = {
      filters: {
        name: query,
      },
    };

    request('/api/hardware', options)
      .then((response) => response.json())
      .then((response) => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          loadingHardware: false,
          dataSourceHardware: response
        });
      })
      .catch(() => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          loadingHardware: false,
          error: true,
        });
      })
  };

  fetchDataSoftware = (query) => {
    const options = {
      filters: {
        name: query,
      },
    };

    request('/api/software', options)
      .then((response) => response.json())
      .then((response) => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          loadingSoftware: false,
          dataSourceSoftware: response
        });
      })
      .catch(() => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          loadingSoftware: false,
          error: true,
        });
      })
  };

  addOrEditCallCall = (method, path) => {
    this.setState({
      isSubmitting: true,
    });
    request(path, {
      method: method,
      body: JSON.stringify({
        "name": this.state.name,
        "affiliationId": this.state.affiliationID,
        "hardwareIds": this.state.hardwareIDs,
        "softwareIds": this.state.softwareIDs,
      }),
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }
    }).then((response) => response.json())
      .then((response) => {
        if (response.success) {
          this.props.goBack();
        } else {
          if (!this._isMounted) {
            return;
          }
          this.setState({
            error: true,
            isSubmitting: false,
          });
        }
      })
      .catch(() => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          error: true,
          isSubmitting: false,
        });
      });
  };

  getDataForEditCall() {
    request(`/api/computer-sets/${this.props.id}`)
      .then(response => response.json())
      .then(responseJson => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          name: responseJson.name,
          affiliationID: responseJson.affiliationId,
          hardwareIDs: responseJson.hardwareIds,
          softwareIDs: responseJson.softwareIds,
        });
      })
  };

  onSubmit = () => {
    if (this.props.mode === 'create')
      this.addOrEditCallCall('POST', '/api/computer-sets');
    else if (this.props.mode === 'edit')
      this.addOrEditCallCall('PUT', `/api/computer-sets/${this.props.id}`);
  };
  onReject = () => this.setState({dialogOpened: true});
  closeDialog = () => this.setState({dialogOpened: false});
  confirmDialog = () => this.props.goBack();
  setName = (value) => this.setState({name: value});
  setAffiliationID = (value) => this.setState({affiliationID: value});
  setHardwareIDs = (values) => this.setState({hardwareIDs: values});
  setSoftwareIDs = (values) => this.setState({SoftwareIDs: values});

  onAddHardwareValues = (selectedId) => {
    if (!selectedId) {
      return;
    }
    if (this.state.hardwareIDs.includes(selectedId)) {
      return;
    }
    const prevIds = [...this.state.hardwareIDs];
    prevIds.push(selectedId);
    this.setState({
      hardwareIDs: prevIds,
    });
  };

  onRemoveHardwareValues = (selectedId) => {
    const index = this.state.hardwareIDs.findIndex((id) => id === selectedId);
    const prevIds = [...this.state.hardwareIDs];
    prevIds.splice(index, 1);
    this.setState({
      hardwareIDs: prevIds,
    });
  };

  onAddSoftwareValues = (selectedId) => {
    if (!selectedId) {
      return;
    }
    if (this.state.softwareIDs.includes(selectedId)) {
      return;
    }
    const prevIds = [...this.state.softwareIDs];
    prevIds.push(selectedId);
    this.setState({
      softwareIDs: prevIds,
    });
  };

  onRemoveSoftwareValues = (selectedId) => {
    const index = this.state.softwareIDs.findIndex((id) => id === selectedId);
    const prevIds = [...this.state.softwareIDs];
    prevIds.splice(index, 1);
    this.setState({
      softwareIDs: prevIds,
    });
  };

  render() {
    const isWide = Dimensions.get('window').width > 450;
    return (
      <ComputerSetDetailsComponent
        onSubmit={this.onSubmit}
        onReject={this.onReject}
        setName={this.setName}
        setAffiliationID={this.setAffiliationID}
        setHardwareIDs={this.setHardwareIDs}
        setSoftwareIDs={this.setSoftwareIDs}
        mode={this.props.mode}
        name={this.state.name}
        isLoading={this.state.loadingAffiliations || this.state.loadingHardware || this.state.loadingSoftware}
        affiliationID={this.state.affiliationID}
        hardwareIDs={this.state.hardwareIDs}
        softwareIDs={this.state.softwareIDs}
        dataSourceAffiliations={this.state.dataSourceAffiliations}
        dataSourceHardware={this.state.dataSourceHardware}
        dataSourceSoftware={this.state.dataSourceSoftware}
        isInvalid={this.state.name === '' || this.state.affiliationID === ''}
        updateAffiliations={this.fetchDataAffiliations}
        updateHardware={this.fetchDataHardware}
        updateSoftware={this.fetchDataSoftware}
        onAddHardwareValues={this.onAddHardwareValues}
        onRemoveHardwareValues={this.onRemoveHardwareValues}
        onAddSoftwareValues={this.onAddSoftwareValues}
        onRemoveSoftwareValues={this.onRemoveSoftwareValues}
        isWide={isWide}
        dialogOpened={this.state.dialogOpened}
        dialogHandleReject={this.closeDialog}
        dialogHandleConfirm={this.confirmDialog}
      />
    );
  }
}

export default ComputerSetDetailsContainer;
