import React, {Component} from 'react';
import SoftwareDetailsComponent from './SoftwareDetailsComponent';

class SoftwareDetailsContainer extends Component {
  constructor(props) {
    super(props);
    this.registerCall = this.registerCall.bind(this);
    this.state = {
      name: '',
      key: '',
      availableKeys: '',
      duration: '',
      baseUrl: 'http://localhost:8080/api/software'
    };
  }
  registerCall(){
  console.log(this.state.name);
    var that = this;
    var url = that.state.baseUrl;
    fetch(url,{
      method: 'POST',
      mode: 'cors',
      body: JSON.stringify({
        "name": this.state.name,
        "key": this.state.key,
        "availableKeys": this.state.availableKeys,
        "duration": this.state.duration
      }),
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Cache-Control': 'no-cache',
        'Access-Control-Allow-Methods': 'DELETE, POST, GET, OPTIONS',
        'Access-Control-Allow-Headers': 'Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With',
      }
    }).then(function (response) {
      return response.json();
    }).then(function (result) {
      if(!result.error){
        that.setState({
          status: result.error,
          wholeResult: result,
        });
        console.log(that.state.wholeResult);
      }else{
        console.log(result);
      }
    }).catch(function (error) {
      console.log("-------- error ------- "+error);
    });
  }

  onSubmit = () => this.registerCall();

  onReject = () => this.props.history.goBack();

  setName = (value) => this.setState({name: value});
  setKey = (value) => this.setState({key: value});
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
