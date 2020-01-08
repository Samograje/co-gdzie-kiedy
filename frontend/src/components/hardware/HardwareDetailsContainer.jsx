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
      loading: true,
      error: false,
      dataSourceAffiliations: { "items": []},
      dataSourceComputerSets: { "items": []},
      dataSourceDictionary: []
    };
  }

  componentDidMount() {
    if (this.props.mode === 'edit')
      this.getDataForEditCall();

    this.fetchDataAffiliations();
    this.fetchDataComputerSets();
    this.fetchDataHardwareDictionary();

    console.log("works");
  }

  fetchDataAffiliations = () => {
    request('/api/affiliations')
        .then((response) => response.json())
        .then((response) => {
          this.setState({
            loading: false,
            dataSourceAffiliations: response
          });
        })
        .catch(() => {
          this.setState({
            loading: false,
            error: true,
          });
        })
  };

  fetchDataComputerSets = () => {
    request('/api/computer-sets')
        .then((response) => response.json())
        .then((response) => {
          this.setState({
            loading: false,
            dataSourceComputerSets: response
          });
        })
        .catch(() => {
          this.setState({
            loading: false,
            error: true,
          });
        })
  };

  fetchDataHardwareDictionary = () => {
    request('/api/hardware-dictionaries')
        .then((response) => response.json())
        .then((response) => {
          this.setState({
            loading: false,
            dataSourceDictionary: response
          });
        })
        .catch(() => {
          this.setState({
            loading: false,
            error: true,
          });
        })
  };

  addCall = () => {
    console.log(this.state.name+","+this.state.dictionaryID+","+this.state.affiliationID+","+this.state.computerSetID)
    request('/api/hardware', {
      method: 'POST',
      body: JSON.stringify({
        "name": this.state.name,
        "dictionaryId": Number(this.state.dictionaryID),
        "affiliationId": Number(this.state.dictionaryID),
        "computerSetId": Number(this.state.computerSetID)
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

  getDataForEditCall(){
    request(`/api/hardware/${this.props.id}`)
        .then(response => response.json())
        .then(responseJson => {
          this.setState({
            name: responseJson.name,
            dictionaryID: responseJson.dictionaryId,
            affiliationID: responseJson.affiliationId,
            computerSetID: responseJson.computerSetId
          })})
  };

  onSubmit = () => this.addCall();
  onReject = () => this.props.goBack();
  setName = (value) => this.setState({name: value});
  setDictionaryID = (value) => this.setState( {dictionaryID: value});
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
            loading={this.state.loading}
            dictionaryID={this.state.dictionaryID}
            affiliationID={this.state.affiliationID}
            computerSetID={this.state.computerSetID}
            dataSourceAffiliations={this.state.dataSourceAffiliations}
            dataSourceComputerSets={this.state.dataSourceComputerSets}
            dataSourceDictionary={this.state.dataSourceDictionary}
        />
    );
  }
}

export default HardwareDetailsContainer;
