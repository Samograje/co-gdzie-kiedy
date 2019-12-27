import React, {Component} from 'react';
import SoftwareDetailsComponent from './SoftwareDetailsComponent';

class SoftwareDetailsContainer extends Component {
  constructor(props) {
    super(props);
    this.addCall = this.addCall.bind(this);
    this.state = {
      name: '',
      key: '',
      availableKeys: '',
      duration: '',
    };
  }
  addCall(){
    fetch('http://localhost:8080/api/software',{
      method: 'POST',
      mode: 'cors',
      body: JSON.stringify(this.state),
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

  }

  onSubmit = () => this.addCall();

  onReject = () => this.props.history.goBack();

  setName = (value) => this.setState({name: value});
  setKey = (value) => this.setState( {key: value});
  setAvailableKeys = (value) => this.setState({availableKeys: value});
  setDuration = (value) => this.setState({duration: value});

  render() {
    return (
      <SoftwareDetailsComponent
          onSubmit={this.onSubmit}
          onReject={this.onReject}
          setName={this.setName}
          setKey={this.setKey}
          setAvailableKeys={this.setAvailableKeys}
          setDuration={this.setDuration}
      />
    );
  }
}

export default SoftwareDetailsContainer;
