import React, {Component} from 'react';
import SoftwareDetailsComponent from './SoftwareDetailsComponent';
import request from "../../APIClient";

class SoftwareDetailsContainer extends Component {
  constructor(props) {
    super(props);
    this.addCall = this.addCall.bind(this);
    this.state = {
      name: '',
      key: '',
      availableKeys: '',
      duration: '',
      mode: '',
      id: '',
      loading: true,
      error: false,
    };
  }

  addCall(){
    fetch('http://localhost:8080/api/software',{
      method: 'POST',
      body: JSON.stringify({
        "name": this.state.name,
        "key": this.state.key,
        "availableKeys": this.state.availableKeys,
        "duration": this.state.duration
      }),
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }
    }).then((response) => response.json())
      .then((responseJson) => {
        return responseJson;
      })
      .catch((error) => {
        console.error(error);
      });
  }

  getDataForEditCall(){
    console.log("XD");
    fetch(`http://localhost:8080/api/software/${this.state.id}`,{
      method: 'GET',
    }).then((response) => response.json())
        .then(responseJson => {
          console.log(responseJson);
          // this.setState({name: responseJson.name});
          // this.setState( {key: responseJson.key});
          // this.setState({availableKeys: responseJson.availableKeys});
          // this.setState({duration: responseJson.duration});
          return responseJson;
        })
        .catch((error) => {
          console.error(error);
        });
  };

  onSubmit = () => this.addCall();
  onReject = () => this.props.goBack();
  setName = (value) => this.setState({name: value});
  setKey = (value) => this.setState( {key: value});
  setAvailableKeys = (value) => this.setState({availableKeys: value});
  setDuration = (value) => this.setState({duration: value});
  getState() {return this.state.mode};

  render() {
    this.state.mode = this.props.mode;
    this.state.id = this.props.id;
    return (
      <SoftwareDetailsComponent
        setText={this.setText}
        onSubmit={this.onSubmit}
        onReject={this.onReject}
        setName={this.setName}
        setKey={this.setKey}
        setAvailableKeys={this.setAvailableKeys}
        setDuration={this.setDuration}
        mode = {this.getState()}
        dataForEditCall={this.getDataForEditCall()}
      />
    );
  }
}

export default SoftwareDetailsContainer;
