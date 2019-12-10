import React from 'react';
import {StyleSheet, Text, View} from "react-native";

class ResponsiveTable extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isWide: null,
    }
  }

  handleLayout = ({nativeEvent}) => {
    const {width} = nativeEvent.layout;
    this.setState({
      isWide: width > 650,
    });
  };

  render() {

    const {
      items,
      totalElements,
      onFetchData,
      columns,
      itemActions,
      footerActions,
    } = this.props;

    const smallLayout = (
      <View>
        <Text>Small layout</Text>
      </View>
    );

    const largeLayout = (
      <View>
        <Text>Large layout</Text>
      </View>
    );

    return (
      <View style={styles.container} onLayout={this.handleLayout}>
        {this.state.isWide ? largeLayout : smallLayout}

        {/* TODO: poniższe jest tymczasowe */}
        {items.map(item => (
          <Text>{item.name}</Text>
        ))}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {},
});

export default ResponsiveTable;
