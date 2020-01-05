import React, {Component} from 'react';
import HardwareDetailsComponent from './HardwareDetailsComponent';
import {ActivityIndicator, View} from 'react-native';
import request from "../../APIClient";
import * as Alert from "react-native-web";

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
      dataSourceAffiliations: { "items": []}
    };
  }

  componentDidMount() {
    if (this.props.mode === 'edit')
      this.getDataForEditCall();

    this.fetchDataAffiliations();
    console.log("works");
  }

  fetchDataAffiliations = () => {
    request('http://localhost:8080/api/affiliations')
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

  addCall = () => {
    fetch('http://localhost:8080/api/hardware', {
      method: 'POST',
      body: JSON.stringify({
        "name": this.state.name,
        "dictionaryID": this.state.dictionaryID,
        "affiliationID": this.state.affiliationID,
        "computerSetID": this.state.computerSetID
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
    fetch(`http://localhost:8080/api/hardware/${this.props.id}`)
        .then(response => response.json())
        .then(responseJson => {
          this.setState({
            name: responseJson.name,
            dictionaryID: responseJson.key,
            affiliationID: responseJson.affiliationID,
            computerSetID: responseJson.computerSetID
          })})
  };

  onSubmit = () => this.addCall();
  onReject = () => this.props.goBack();
  setName = (value) => this.setState({name: value});
  setDictionaryID = (value) => this.setState( {dictionaryID: value});
  setAffiliationID = (value) => this.setState({affiliationID: value});
  setComputerSetID = (value) => this.setState({computerSetID: value});

  render() {
    if (this.state.Loading) {
      return (
          <View style={{flex: 1, paddingTop: 20}}>
            <ActivityIndicator />
          </View>
      );
    }

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
            dictionaryID={this.state.dictionaryID}
            affiliationID={this.state.affiliationID}
            computerSetID={this.state.computerSetID}
            dataSourceAffiliations={this.state.dataSourceAffiliations}
        />
    );
  }
}

export default HardwareDetailsContainer;
