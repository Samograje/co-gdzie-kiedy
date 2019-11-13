import React, {Component} from 'react';
import {StyleSheet} from 'react-native';
import AffiliationDetailsComponent from './AffiliationDetailsComponent';

class AffiliationDetailsContainer extends Component {

  exampleGetAffiliationId = () => {
    return this.props.navigation.getParam('id', null);
  };

  onSubmit = () => this.props.navigation.push('AffiliationsList');

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

const styles = StyleSheet.create({

});

export default AffiliationDetailsContainer;
