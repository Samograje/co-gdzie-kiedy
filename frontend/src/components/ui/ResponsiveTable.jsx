import React from 'react';
import {Button, FlatList, ScrollView, StyleSheet, Text, View} from "react-native";
import {mainColor} from "../../constValues";

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

        {/* nagłówki kolumn */}
        <View style={styles.tr}>
          {/* zadeklarowane kolumny */}
          {columns.map((column, key) => (
            <View style={styles.th} key={key}>
              <Text style={[styles.thText, styles.text]}>{column.label}</Text>
            </View>
          ))}

          {/* kolumna akcji */}
          {itemActions && (
            <View style={styles.th}>
              <Text style={[styles.thText, styles.text]}>Akcje</Text>
            </View>
          )}
        </View>

        {/* rekordy tabeli */}
        {items.map((item, rowId) => (
          <View style={[styles.tr, rowId % 2 !== 0 && styles.greyRow]} key={rowId}>

            {/* dane do komórek */}
            {columns.map((column, key) => (
              <View style={styles.td} key={key}>
                <Text style={[styles.tdText, styles.text]}>{item[column.name]}</Text>
              </View>
            ))}

            {/* komórka z akcjami */}
            {itemActions && (
              <View style={styles.td}>
                {itemActions.map((action, idx) => (
                  <View style={styles.buttonContainer} key={idx}>
                    <Button
                      title={action.label}
                      onPress={() => action.onClick(item)}
                      color={mainColor}
                    />
                  </View>
                ))}
              </View>
            )}
          </View>
        ))}
      </View>
    );

    return (
        <View style={styles.container} onLayout={this.handleLayout}>
          {this.state.isWide ? wideLayout : mobileLayout}
        </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    margin: 5,
  },

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
    flexDirection: 'row',
    justifyContent: 'center',
    backgroundColor: 'darkgrey',
  },
  thText: {
    fontWeight: 'bold',
  },
  td: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    flexWrap: 'wrap',
  },
  tdText: {},

  // dotyczy obu widoków
  text: {
    fontSize: 16,
  },
  greyRow: {
    backgroundColor: 'lightgrey',
  },
  buttonContainer: {
    width: 75,
    margin: 2,
  },
});

export default ResponsiveTable;
