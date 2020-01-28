import React, {Component} from 'react';
import SoftwareDetailsComponent from './SoftwareDetailsComponent';
import moment from "moment";
import request from "../../APIClient";
import {Dimensions} from "react-native";

class SoftwareDetailsContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      key: '',
      availableKeys: '',
      duration: '',
      validationStatus: false,
      loading: false,
      error: false,
      computerSetIds: [],
      dataSourceComputerSets: {"items": []},
      loadingComputerSets: true,
    };
  }

  componentDidMount() {
    this._isMounted = true;
    this.fetchDataComputerSet();
    if(this.props.mode === 'edit')
      this.getDataForEditCall();
  }

  componentWillUnmount() {
    this._isMounted = false;
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
        "duration": duration,
        "computerSetIds": this.state.computerSetIds
      }),
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }
    }).then((response) => response.json())
      .then((responseJson) => {
        if (!this._isMounted) {
          return;
        }
        console.log(responseJson);
        return responseJson;
      })
      .catch((error) => {
        if (!this._isMounted) {
          return;
        }
        console.error(error);
      });
  };

  getDataForEditCall(){
    this.setState({"loading": true});
    request(`/api/software/${this.props.id}`)
      .then(response => response.json())
      .then(response => {
        if (!this._isMounted) {
          return;
        }
        let duration = response.duration;
        let months = moment(duration).month() +  12 * (moment(duration).year() - moment(0).year());
        months <= 0 ? response.duration = "Licencja utraciła ważność" : response.duration = months;
        this.setState({
          name: response.name,
          key: response.key,
          availableKeys: response.availableKeys,
          duration: response.duration,
          computerSetIds: response.computerSetIds,
          loading: false,
      });
      })
  };

  fetchDataComputerSet = (query) => {
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

  onAddComputerSetValues = (selectedId) => {
    if(!selectedId){
      return;
    }
    if(this.state.computerSetIds.includes(selectedId)){
      return;
    }
    const prevIds = [...this.state.computerSetIds];
    prevIds.push(selectedId);
    this.setState({
      computerSetIds: prevIds,
    });
  };

  onRemoveComputerSetValues = (selectedId) => {
    const index = this.state.computerSetIds.findIndex((id) => id === selectedId);
    const prevIds = [...this.state.computerSetIds];
    prevIds.splice(index, 1);
    this.setState({
      computerSetIds: prevIds,
    });
  };

  onSubmit = () => {
    if(this.props.mode === 'create')
      this.addOrEditCallCall('POST', '/api/software');
    else if (this.props.mode === 'edit')
      this.addOrEditCallCall('PUT', `/api/software/${this.props.id}`);
  };
  onReject = () => this.props.goBack();
  setName = (value) => {this.setState({name: value});};
  setKey = (value) => this.setState( {key: value});
  setAvailableKeys = (value) => this.setState({availableKeys: value});
  setDuration = (value) => this.setState({duration: value});
  // setComputerSetIds = (values) => this.setState({computerSetIds: values});
  render() {
    const isWide = Dimensions.get('window').width > 450;
    return (
      <SoftwareDetailsComponent
        setText={this.setText}
        onSubmit={this.onSubmit}
        onReject={this.onReject}
        setName={this.setName}
        setKey={this.setKey}
        setAvailableKeys={this.setAvailableKeys}
        setDuration={this.setDuration}
        isWide={isWide}
        mode={this.props.mode}
        name={this.state.name}
        keY={this.state.key}
        availableKeys={this.state.availableKeys}
        duration={this.state.duration}
        loading={this.state.loading}
        validationEmptyStatus={this.state.name === '' || this.state.key === '' ||
                          this.state.availableKeys === '' || this.state.duration === ''}
        validationAvailableKeysIsNumberStatus={isNaN(this.state.availableKeys)}
        validationAvailableKeysIsBiggerThan0NumberStatus={this.state.availableKeys === '' ? true : Number.parseInt(this.state.availableKeys) > 0}
        validationDurationIsNumberStatus={this.state.duration === 'Licencja utraciła ważność' ? false : isNaN(this.state.duration)}
        validationDurationIsBiggerThan0NumberStatus={(this.state.duration === '' || this.state.duration === 'Licencja utraciła ważność') ? true : Number.parseInt(this.state.duration) > 0}
        validationDisableDuration={this.state.duration === 'Licencja utraciła ważność'}
        computerSetIds={this.state.computerSetIds}
        updateComputerSets={this.fetchDataComputerSet}
        onAddComputerSetValues={this.onAddComputerSetValues}
        onRemoveComputerSetValues={this.onRemoveComputerSetValues}
        dataSourceComputerSets={this.state.dataSourceComputerSets}
        loadingComputerSets={this.state.loadingComputerSets}
        // setComputerSetIds={this.setComputerSetIds}
      />
    );
  }
}

export default SoftwareDetailsContainer;
