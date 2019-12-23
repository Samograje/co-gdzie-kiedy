import React, {Component} from 'react';
import HardwareDetailsComponent from './HardwareDetailsComponent';
import AffiliationDetailsComponent from "../affiliations/AffiliationDetailsComponent";

class HardwareDetailsContainer extends Component {

    onSubmit = () => this.props.history.goBack();

    onReject = () => this.props.history.goBack();

    render() {
        return (
            <HardwareDetailsComponent
                onSubmit={this.onSubmit}
                onReject={this.onReject}
            />
        );
    }
}

export default HardwareDetailsContainer;
