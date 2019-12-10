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
      <View style={styles.table}>
        <View style={styles.tr}>
          {columns.map((column) => (
            <View style={styles.th}>
              <Text style={styles.thText}>{column.label}</Text>
            </View>
          ))}
        </View>
        {items.map((item, rowId) => (
          <View style={[styles.tr, rowId % 2 !== 0 && styles.greyRow]}>
            {columns.map((column) => (
              <View style={styles.td}>
                <Text style={styles.tdText}>{item[column.name]}</Text>
              </View>
            ))}
          </View>
        ))}
      </View>
    );

    return (
      <View style={styles.container} onLayout={this.handleLayout}>
        {this.state.isWide ? largeLayout : smallLayout}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {},
  table: {},
  tr: {
    flexDirection: 'row',
  },
  greyRow: {
    backgroundColor: 'lightgrey',
  },
  th: {
    flex: 1,
    backgroundColor: 'darkgrey',
  },
  thText: {
    fontSize: 16,
  },
  td: {
    flex: 1,
  },
  tdText: {},
});

export default ResponsiveTable;
