import React, {Component} from 'react';
import SoftwareDetailsComponent from './SoftwareDetailsComponent';
import request from "../../APIClient";

class SoftwareDetailsContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      key: '',
      availableKeys: '',
      duration: '',
      loading: true,
      error: false,
    };
  }

  componentDidMount() {
    if(this.props.mode === 'edit')
      this.getDataForEditCall();
  }

  addCall = () => {
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
        console.log(responseJson);
        return responseJson;
      })
      .catch((error) => {
        console.error(error);
      });
  };

  getDataForEditCall(){
    fetch(`http://localhost:8080/api/software/${this.props.id}`)
        .then(response => response.json())
        .then(responseJson => {
          this.setState({ name: responseJson.name });
          this.setState({ key: responseJson.key});
          this.setState({ availableKeys: responseJson.availableKeys });
          this.setState({ duration: responseJson.duration });
        })
  };

  onSubmit = () => this.addCall();
  onReject = () => this.props.goBack();
  setName = (value) => this.setState({name: value});
  setKey = (value) => this.setState( {key: value});
  setAvailableKeys = (value) => this.setState({availableKeys: value});
  setDuration = (value) => this.setState({duration: value});

  render() {
    return (
      <SoftwareDetailsComponent
        setText={this.setText}
        onSubmit={this.onSubmit}
        onReject={this.onReject}
        setName={this.setName}
        setKey={this.setKey}
        setAvailableKeys={this.setAvailableKeys}
        setDuration={this.setDuration}
        mode={this.props.mode}
        name={this.state.name}
        keY={this.state.key}
        availableKeys={this.state.availableKeys}
        duration={this.state.duration}
      />
    );
  }
}

export default SoftwareDetailsContainer;
