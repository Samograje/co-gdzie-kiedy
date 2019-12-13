import React, {Component} from 'react';
import AffiliationDetailsComponent from './AffiliationDetailsComponent';

class AffiliationDetailsContainer extends Component {

  onSubmit = () => this.props.navigation.goBack();

  onReject = () => this.props.navigation.goBack();

  render() {
    return (
      <AffiliationDetailsComponent
        onSubmit={this.onSubmit}
        onReject={this.onReject}
      />
    );
  }
}

export default AffiliationDetailsContainer;
