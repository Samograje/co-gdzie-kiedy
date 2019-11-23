import React, {Component} from 'react';
import {StyleSheet} from 'react-native';
import AffiliationDetailsComponent from './AffiliationDetailsComponent';

class AffiliationDetailsContainer extends Component {

  onSubmit = () => this.props.history.goBack();

  onReject = () => this.props.history.goBack();

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
