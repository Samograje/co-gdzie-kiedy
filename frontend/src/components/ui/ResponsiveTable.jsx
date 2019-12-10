import React from 'react';
import {FlatList, ScrollView, StyleSheet, Text, View} from "react-native";

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

    const mobileLayout = (
      <View style={styles.list}>
        <FlatList
          data={items}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({item, index}) => (
            <View style={[styles.item, index % 2 === 0 && styles.greyRow]}>
              {columns.map((column, key) => (
                <View style={styles.row} key={key}>
                  <Text style={[styles.label, styles.text]}>{column.label}</Text>
                  <Text style={[styles.value, styles.text]}>{item[column.name]}</Text>
                </View>
              ))}
            </View>
          )}
        />
      </View>
    );

    const wideLayout = (
      <View style={styles.table}>
        <View style={styles.tr}>
          {columns.map((column, key) => (
            <View style={styles.th} key={key}>
              <Text style={[styles.thText, styles.text]}>{column.label}</Text>
            </View>
          ))}
        </View>
        {items.map((item, rowId) => (
          <View style={[styles.tr, rowId % 2 !== 0 && styles.greyRow]} key={rowId}>
            {columns.map((column, key) => (
              <View style={styles.td} key={key}>
                <Text style={[styles.tdText, styles.text]}>{item[column.name]}</Text>
              </View>
            ))}
          </View>
        ))}
      </View>
    );

    return (
      <ScrollView>
        <View style={styles.container} onLayout={this.handleLayout}>
          {this.state.isWide ? wideLayout : mobileLayout}
        </View>
      </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  container: {},

  // dotyczy widoku mobilnego
  list: {},
  item: {},
  row: {
    flexDirection: 'row',
  },
  label: {
    flex: 1,
    textAlign: 'right',
    marginRight: 5,
    fontWeight: 'bold',
  },
  value: {
    marginLeft: 5,
    flex: 1,
  },

  // dotyczy widoku tabeli na szerokie ekrany
  table: {},
  tr: {
    flexDirection: 'row',
  },
  th: {
    flex: 1,
    backgroundColor: 'darkgrey',
  },
  thText: {
    fontWeight: 'bold',
  },
  td: {
    flex: 1,
  },
  tdText: {},

  // dotyczy obu widoków
  text: {
    fontSize: 16,
  },
  greyRow: {
    backgroundColor: 'lightgrey',
  },
});

export default ResponsiveTable;
