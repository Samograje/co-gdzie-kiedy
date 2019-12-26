import React, {Component} from 'react';
import SoftwareDetailsComponent from './SoftwareDetailsComponent';
import AffiliationDetailsComponent from "../affiliations/AffiliationDetailsComponent";

class SoftwareDetailsContainer extends Component {
  onSubmit = () => this.props.history.goBack();

  onReject = () => this.props.history.goBack();
  render() {
    return (
      <SoftwareDetailsComponent
          onSubmit={this.onSubmit}
          onReject={this.onReject}
      />
    );
  }
}

export default SoftwareDetailsContainer;
