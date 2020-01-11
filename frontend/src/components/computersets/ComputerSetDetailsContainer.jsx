import React, {Component} from 'react';
import ComputerSetDetailsComponent from './ComputerSetDetailsComponent';
import moment from "moment";
import request from "../../APIClient";

class ComputerSetDetailsContainer extends Component {
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
        if (this.props.mode === 'edit')
            this.getDataForEditCall();
    }

    addOrEditCallCall = (method, path) => {
        let currentDate = new Date();
        let endDate = moment(currentDate).add(this.state.duration, 'month');
        let duration = endDate - currentDate; //to poleci jsonem

        request(path, {
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

    getDataForEditCall() {
        request(`/api/computer-sets/${this.props.id}`)
            .then(response => response.json())
            .then(response => {
                let name = response.name;
                let affiliationId = response.affiliationId;
                let hardwareIds = response.hardwareIds;
                let softwareIds = response.softwareIds;
                this.setState({
                    name: response.name,
                    //TODO: Warning - zamiana na stringa, ale i tak będą nazwy, nie id
                    affiliationId: response.affiliationId.toString(),
                    hardwareIds: response.hardwareIds,
                    softwareIds: response.softwareIds,
                });
            })
    };

    onSubmit = () => {
        if (this.props.mode === 'create')
            this.addOrEditCallCall('POST', '/api/computer-sets');
        else if (this.props.mode === 'edit')
            this.addOrEditCallCall('PUT', `/api/computer-sets/${this.props.id}`);
    };
    onReject = () => this.props.goBack();
    setName = (value) => {
        this.setState({name: value});
    };
    setAffiliationId = (value) => this.setState({affiliationId: value});

    /*setAvailableKeys = (value) => this.setState({availableKeys: value});
    setDuration = (value) => this.setState({duration: value});;*/

  render() {

    return (
        <ComputerSetDetailsComponent
            setText={this.setText}
            onSubmit={this.onSubmit}
            onReject={this.onReject}
            setName={this.setName}
            setAffiliationId={this.setAffiliationId}
            /*setAvailableKeys={this.setAvailableKeys}
            setDuration={this.setDuration}*/
            mode={this.props.mode}
            name={this.state.name}
            affiliationId={this.state.affiliationId}
            /*availableKeys={this.state.availableKeys}
            duration={this.state.duration}*/
            validationEmptyStatus={this.state.name === '' || this.state.affiliationId === '' /*||
            this.state.availableKeys === '' || this.state.duration === ''*/}
            /*  validationAvailableKeysIsNumberStatus={isNaN(this.state.availableKeys)}
              validationAvailableKeysIsBiggerThan0NumberStatus={this.state.availableKeys === '' ? true : Number.parseInt(this.state.availableKeys) > 0}
              validationDurationIsNumberStatus={this.state.duration === 'Licencja utraciła ważność' ? false : isNaN(this.state.duration)}
              validationDurationIsBiggerThan0NumberStatus={(this.state.duration === '' || this.state.duration === 'Licencja utraciła ważność') ? true : Number.parseInt(this.state.duration) > 0}
              validationDisableDuration={this.state.duration === 'Licencja utraciła ważność'}*/
        />
    );
  }
}

export default ComputerSetDetailsContainer;
