import React, {Component} from 'react';
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
      isInvalid: true
    };
  }

  componentDidMount() {
    this.fetchDataHardwareDictionary();
    this.fetchDataAffiliations();
    this.fetchDataComputerSets();

    if (this.props.mode === 'edit')
      this.getDataForEditCall();
  }

  fetchDataAffiliations = (query) => {
    const options = {
      filters: {
        name: query,
      },
    };

    request('/api/affiliations', options)
        .then((response) => response.json())
        .then((response) => {
          this.setState({
            loadingAffiliations: false,
            dataSourceAffiliations: response
          });
        })
        .catch(() => {
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
          this.setState({
            loadingComputerSets: false,
            dataSourceComputerSets: response
          });
        })
        .catch(() => {
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
          this.setState({
            loadingDictionary: false,
            dataSourceDictionary: response
          });
        })
        .catch(() => {
          this.setState({
            loadingDictionary: false,
            error: true,
          });
        })
  };

  addOrEditCallCall = (method, path) => {
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
        .then((responseJson) => {
          console.log(responseJson);
          return responseJson;
        })
        .catch((error) => {
          console.error(error);
        });
  };

  getDataForEditCall() {
    request(`/api/hardware/${this.props.id}`)
        .then(response => response.json())
        .then(responseJson => {
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
  setName = (value) => this.setState({name: value});
  setDictionaryID = (value) => this.setState({dictionaryID: value});
  setAffiliationID = (value) => this.setState({affiliationID: value});
  setComputerSetID = (value) => this.setState({computerSetID: value});

  render() {
    return (
        <HardwareDetailsComponent
            onSubmit={this.onSubmit}
            onReject={this.onReject}
            setName={this.setName}
            setDictionaryID={this.setDictionaryID}
            setAffiliationID={this.setAffiliationID}
            setComputerSetID={this.setComputerSetID}
            mode={this.props.mode}
            name={this.state.name}
            loadingDictionary={this.state.loadingDictionary}
            loadingAffiliations={this.state.loadingAffiliations}
            loadingComputerSets={this.state.loadingComputerSets}
            dictionaryID={this.state.dictionaryID}
            affiliationID={this.state.affiliationID}
            computerSetID={this.state.computerSetID}
            dataSourceAffiliations={this.state.dataSourceAffiliations}
            dataSourceComputerSets={this.state.dataSourceComputerSets}
            dataSourceDictionary={this.state.dataSourceDictionary}
            isInvalid={this.state.name === '' || this.state.dictionaryID === '' || this.state.affiliationID === ''}
            updateAffiliations={this.fetchDataAffiliations}
            updateComputerSets={this.fetchDataComputerSets}
        />
    );
  }
}

export default HardwareDetailsContainer;
