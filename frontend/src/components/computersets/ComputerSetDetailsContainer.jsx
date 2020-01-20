import React, {Component} from 'react';
import ComputerSetDetailsComponent from './ComputerSetDetailsComponent';
import request from "../../APIClient";

class ComputerSetDetailsContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            affiliationID: '',
            hardwareIDs: [],
            softwareIDs: [],
            loadingAffiliations: true,
            loadingHardware: true,
            loadingSoftware: true,
            error: false,
            dataSourceAffiliations: {"items": []},
            dataSourceHardware: {"items": []},
            dataSourceSoftware: {"items": []},
            isInvalid: true
        };
    }

    componentDidMount() {
        this._isMounted = true;
        this.fetchDataAffiliations();
        this.fetchDataHardware();
        this.fetchDataSoftware();

        if (this.props.mode === 'edit')
            this.getDataForEditCall();
    }

    componentWillUnmount() {
        this._isMounted = false;
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
                if (!this._isMounted) {
                    return;
                }
                this.setState({
                    loadingAffiliations: false,
                    dataSourceAffiliations: response,
                });
            })
            .catch(() => {
                if (!this._isMounted) {
                    return;
                }
                this.setState({
                    loadingAffiliations: false,
                    error: true,
                });
            })
    };

    fetchDataHardware = (query) => {
        const options = {
            filters: {
                name: query,
            },
        };

        request('/api/hardware', options)
            .then((response) => response.json())
            .then((response) => {
                if (!this._isMounted) {
                    return;
                }
                this.setState({
                    loadingHardware: false,
                    dataSourceHardware: response,

                });
            })
            .catch(() => {
                if (!this._isMounted) {
                    return;
                }
                this.setState({
                    loadingHardware: false,
                    error: true,
                });
            })
    };

    fetchDataSoftware = (query) => {
        const options = {
            filters: {
                name: query,
            },
        };

        request('/api/software', options)
            .then((response) => response.json())
            .then((response) => {
                if (!this._isMounted) {
                    return;
                }
                this.setState({
                    loadingSoftware: false,
                    dataSourceSoftware: response
                });
            })
            .catch(() => {
                if (!this._isMounted) {
                    return;
                }
                this.setState({
                    loadingSoftware: false,
                    error: true,
                });
            })
    };

    addOrEditCallCall = (method, path) => {
        request(path, {
            method: method,
            body: JSON.stringify({
                "name": this.state.name,
                "affiliationId": this.state.affiliationID,
                "softwareIds": this.state.softwareIDs,
                "hardwareIds": this.state.hardwareIDs,
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

    getDataForEditCall() {

    };

    onSubmit = () => {
        if (this.props.mode === 'create')
            this.addOrEditCallCall('POST', '/api/ComputerSet');
        else if (this.props.mode === 'edit')
            this.addOrEditCallCall('PUT', `/api/ComputerSet/${this.props.id}`);
    };
    onReject = () => this.props.goBack();
    setName = (value) => this.setState({name: value});
    setAffiliationID = (value) => this.setState({affiliationID: value});
    setSoftwareIDs = (value) => this.setState({softwareIDs: value});
    setHardwareIDs = (value) => this.setState({hardwareIDs: value});

    render() {
        return (
            <ComputerSetDetailsComponent
                onSubmit={this.onSubmit}
                onReject={this.onReject}
                setName={this.setName}
                setSoftwareIDs={this.setSoftwareIDs}
                setAffiliationID={this.setAffiliationID}
                setHardwareIDs={this.setHardwareIDs}
                mode={this.props.mode}
                name={this.state.name}
                loadingSoftware={this.state.loadingSoftware}
                loadingAffiliations={this.state.loadingAffiliations}
                loadingHardware={this.state.loadingHardware}
                softwareIDs={this.state.softwareIDs}
                affiliationID={this.state.affiliationID}
                hardwareIDs={this.state.hardwareIDs}
                dataSourceAffiliations={this.state.dataSourceAffiliations}
                dataSourceHardware={this.state.dataSourceHardware}
                dataSourceSoftware={this.state.dataSourceSoftware}
                isInvalid={this.state.name === '' || this.state.affiliationID === ''}
                updateAffiliations={this.fetchDataAffiliations}
                updateHardware={this.fetchDataHardware}
                updateSoftware={this.fetchDataSoftware}
            />
        );
    }
}

export default ComputerSetDetailsContainer;