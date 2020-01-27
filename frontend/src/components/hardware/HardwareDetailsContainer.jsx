import React, {Component} from 'react';
import {Dimensions} from 'react-native';
import HardwareDetailsComponent from './HardwareDetailsComponent';
import request from "../../APIClient";

class HardwareDetailsContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      dictionaryID: '',
      affiliationID: '',
      computerSetID: '',
      loadingDictionary: true,
      loadingAffiliations: true,
      loadingComputerSets: true,
      error: false,
      dataSourceAffiliations: {"items": []},
      dataSourceComputerSets: {"items": []},
      dataSourceDictionary: [],
      isInvalid: true,
      isSubmitting: false,
      isPreviewed: false,
    };
  }

  componentDidMount() {
    this._isMounted = true;
    this.fetchDataHardwareDictionary();
    this.fetchDataAffiliations();
    this.fetchDataComputerSets();

    if (this.props.mode === 'edit') {
      this.getDataForEditCall();
      this.setState({
        isPreviewed: true,
      });
    }
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

  fetchDataComputerSets = (query) => {
    const options = {
      filters: {
        name: query,
      },
    };

    request('/api/computer-sets', options)
      .then((response) => response.json())
      .then((response) => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          loadingComputerSets: false,
          dataSourceComputerSets: response
        });
      })
      .catch(() => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          loadingComputerSets: false,
          error: true,
        });
      })
  };

  fetchDataHardwareDictionary = () => {
    request('/api/hardware-dictionaries')
      .then((response) => response.json())
      .then((response) => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          loadingDictionary: false,
          dataSourceDictionary: response.map((item) => ({
            id: item.id,
            name: item.value,
          }))
        });
      })
      .catch(() => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          loadingDictionary: false,
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
        "dictionaryId": this.state.dictionaryID,
        "affiliationId": this.state.affiliationID,
        "computerSetId": this.state.computerSetID
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
    request(`/api/hardware/${this.props.id}`)
      .then(response => response.json())
      .then(responseJson => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          name: responseJson.name,
          dictionaryID: responseJson.dictionaryId,
          affiliationID: responseJson.affiliationId,
          computerSetID: responseJson.computerSetId
        })
      })
  };

  onSubmit = () => {
    if (this.props.mode === 'create')
      this.addOrEditCallCall('POST', '/api/hardware');
    else if (this.props.mode === 'edit')
      this.addOrEditCallCall('PUT', `/api/hardware/${this.props.id}`);
  };
  onReject = () => this.props.goBack();
  onEdit = () => this.setState({
    isPreviewed: false,
  });
  setName = (value) => this.setState({name: value});
  setDictionaryID = (value) => this.setState({dictionaryID: value});
  setAffiliationID = (value) => this.setState({affiliationID: value});
  setComputerSetID = (value) => this.setState({computerSetID: value});

  render() {
    const isWide = Dimensions.get('window').width > 450;

    return (
      <HardwareDetailsComponent
        onSubmit={this.onSubmit}
        onReject={this.onReject}
        onEdit={this.onEdit}
        setName={this.setName}
        setDictionaryID={this.setDictionaryID}
        setAffiliationID={this.setAffiliationID}
        setComputerSetID={this.setComputerSetID}
        mode={this.props.mode}
        name={this.state.name}
        dictionaryID={this.state.dictionaryID}
        affiliationID={this.state.affiliationID}
        computerSetID={this.state.computerSetID}
        dataSourceAffiliations={this.state.dataSourceAffiliations}
        dataSourceComputerSets={this.state.dataSourceComputerSets}
        dataSourceDictionary={this.state.dataSourceDictionary}
        isInvalid={this.state.name === '' || this.state.dictionaryID === '' || this.state.affiliationID === ''}
        isSubmitting={this.state.isSubmitting}
        isLoading={this.state.loadingDictionary || this.state.loadingAffiliations || this.state.loadingComputerSets}
        error={this.state.error}
        isWide={isWide}
        isPreviewed={this.state.isPreviewed}
        updateAffiliations={this.fetchDataAffiliations}
        updateComputerSets={this.fetchDataComputerSets}
      />
    );
  }
}

export default HardwareDetailsContainer;
