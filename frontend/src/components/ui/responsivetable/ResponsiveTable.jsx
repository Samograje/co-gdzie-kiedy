import React from 'react';
import {Dimensions, StyleSheet, View} from "react-native";
import WideTable from "./WideTable";
import MobileTable from "./MobileTable";

class ResponsiveTable extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isWide: Dimensions.get('window').width > 650,
    }
  }

  handleLayout = () => {
    this.setState({
      isWide: Dimensions.get('window').width > 650,
    });
  };

  render() {
    return (
      <View style={styles.container} onLayout={this.handleLayout}>
        {this.state.isWide && (
          <WideTable {...this.props}/>
        )}
        {!this.state.isWide && (
          <MobileTable {...this.props}/>
        )}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    margin: 5,
  },
});

export default ResponsiveTable;
