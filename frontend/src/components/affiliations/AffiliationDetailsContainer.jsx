import React, {Component} from 'react';
import AffiliationDetailsComponent from './AffiliationDetailsComponent';

class AffiliationDetailsContainer extends Component {

  onSubmit = () => this.props.goBack();

  onReject = () => this.props.goBack();

  render() {
    console.log(`Przykład uzyskania id elementu: ${this.props.id}`);
    console.log(`Przykład uzyskania trybu formularza: ${this.props.mode}`);
    return (
      <AffiliationDetailsComponent
        onSubmit={this.onSubmit}
        onReject={this.onReject}
      />
    );
  }
}

export default AffiliationDetailsContainer;
