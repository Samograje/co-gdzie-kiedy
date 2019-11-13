import React, {Component} from 'react';
import {StyleSheet} from 'react-native';
import AffiliationsListComponent from './AffiliationsListComponent';

class AffiliationsListContainer extends Component {

  onCreate = () => this.props.navigation.navigate('AffiliationDetails', {
    mode: 'create'
  });

  onEdit = () => this.props.navigation.navigate('AffiliationDetails', {
    id: 1,
    mode: 'edit'
  });

  render() {
    return (
      <AffiliationsListComponent
        onCreate={this.onCreate}
        onEdit={this.onEdit}
      />
    );
  }
}

const styles = StyleSheet.create({

});

export default AffiliationsListContainer;
