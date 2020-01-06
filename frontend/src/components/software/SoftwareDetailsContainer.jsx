import React, {Component} from 'react';
import SoftwareDetailsComponent from './SoftwareDetailsComponent';
import isEmpty from "react-native-web/dist/vendor/react-native/isEmpty";
import moment from "moment";
import request from "../../APIClient";

class SoftwareDetailsContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      key: '',
      availableKeys: '',
      duration: '',
      validationStatus: false,
      loading: true,
      error: false,
    };
  }

  componentDidMount() {
    if(this.props.mode === 'edit')
      this.getDataForEditCall();
  }

  addOrEditCallCall = (method, path) => {
    let currentDate = new Date();
    let endDate = moment(currentDate).add(this.state.duration, 'month');
    let duration = endDate - currentDate; //to poleci jsonem

    request(path,{
      method: method,
      body: JSON.stringify({
        "name": this.state.name,
        "key": this.state.key,
        "availableKeys": this.state.availableKeys,
        "duration": duration
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
    request(`http://localhost:8080/api/software/${this.props.id}`)
        .then(response => response.json())
        .then(response => {
          let duration = response.duration;
          let months = moment(duration).month() +  12 * (moment(duration).year() - moment(0).year());
          months <= 0 ? response.duration = "Licencja utraciła ważność" : response.duration = months;
          this.setState({
            name: response.name,
            key: response.key,
            availableKeys: response.availableKeys,
            duration: response.duration,
        });
        })
  };

  onSubmit = () => {
    if(this.props.mode === 'create')
      this.addOrEditCallCall('POST', 'http://localhost:8080/api/software');
    else if (this.props.mode === 'edit')
      this.addOrEditCallCall('PUT', `http://localhost:8080/api/software/${this.props.id}`);
  };
  onReject = () => this.props.goBack();
  setName = (value) => {this.setState({name: value});};
  setKey = (value) => this.setState( {key: value});
  setAvailableKeys = (value) => this.setState({availableKeys: value});
  setDuration = (value) => this.setState({duration: value});;

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
        validationEmptyStatus={isEmpty(this.state.name) || isEmpty(this.state.key) ||
                          isEmpty(this.state.availableKeys) || isEmpty(this.state.duration)}
        validationAvailableKeysIsNumberStatus={isNaN(this.state.availableKeys)}
        validationAvailableKeysIsBiggerThan0NumberStatus={this.state.availableKeys === '' ? true : Number(this.state.availableKeys) > 0}
        validationDurationIsNumberStatus={this.state.duration === 'Licencja utraciła ważność' ? false : isNaN(this.state.duration)}
        validationDurationIsBiggerThan0NumberStatus={(this.state.duration === '' || this.state.duration === 'Licencja utraciła ważność') ? true : Number(this.state.duration) > 0}
        validationDisableDuration={this.state.duration === 'Licencja utraciła ważność'}
      />
    );
  }
}

export default SoftwareDetailsContainer;
