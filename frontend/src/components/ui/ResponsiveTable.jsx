import React from 'react';
import {Button, Dimensions, StyleSheet, Text, View} from "react-native";
import {mainColor} from "../../constValues";

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

        {/* rekordy */}
        {items.map((item, idx) => (
          <View style={styles.item} key={idx}>

            {/* wiersze z danymi */}
            {columns.map((column, key) => (
              <View style={styles.row} key={key}>
                <Text style={[styles.label, styles.text]}>{column.label}</Text>
                <Text style={[styles.value, styles.text]}>{item[column.name]}</Text>
              </View>
            ))}

            {/* przyciski akcji */}
            {itemActions && (
              <View style={styles.buttons}>
                {itemActions.map((action, idx) => (
                  <View style={styles.buttonContainerMobile} key={idx}>
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

        {/* informacja o braku rekordów */}
        {!items.length && (
          <View style={styles.item}>
            <Text style={styles.text}>Brak elementów do wyświetlenia</Text>
          </View>
        )}
      </View>
    );

    const wideLayout = (
      <View style={styles.table}>

        {/* nagłówki kolumn */}
        <View style={styles.trHead}>
          {/* zadeklarowane kolumny */}
          {columns.map((column, key) => (
            <View style={styles.cell} key={key}>
              <Text style={[styles.thText, styles.text]}>{column.label}</Text>
            </View>
          ))}

          {/* kolumna akcji */}
          {itemActions && (
            <View style={styles.cell}>
              <Text style={[styles.thText, styles.text]}>Akcje</Text>
            </View>
          )}
        </View>

        {/* rekordy tabeli */}
        {items.map((item, rowId) => (
          <View style={styles.tr} key={rowId}>

            {/* dane do komórek */}
            {columns.map((column, key) => (
              <View style={styles.cell} key={key}>
                <Text style={[styles.tdText, styles.text]}>{item[column.name]}</Text>
              </View>
            ))}

            {/* komórka z akcjami */}
            {itemActions && (
              <View style={styles.cell}>
                {itemActions.map((action, idx) => (
                  <View style={styles.buttonContainerDesktop} key={idx}>
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

        {/* informacja o braku rekordów */}
        {!items.length && (
          <View style={styles.tr}>
            <Text style={[styles.tdText, styles.text]}>Brak elementów do wyświetlenia</Text>
          </View>
        )}
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
  list: {
    borderWidth: 1,
    borderTopWidth: 0,
    borderRadius: 2,
    overflow: 'hidden',
  },
  item: {
    borderTopWidth: 1,
    borderBottomWidth: 0,
    padding: 5,
  },
  row: {
    flexDirection: 'row',
    margin: 2,
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
  buttons: {
    flexDirection: 'row',
    justifyContent: 'center',
    flexWrap: 'wrap',
  },
  buttonContainerMobile: {
    flex: 1,
    margin: 5,
  },

  // dotyczy widoku tabeli na szerokie ekrany
  table: {
    borderWidth: 1,
    borderRadius: 2,
    overflow: 'hidden',
  },
  tr: {
    flexDirection: 'row',
    borderTopWidth: 1,
    padding: 2,
  },
  trHead: {
    flexDirection: 'row',
    backgroundColor: 'lightgrey',
  },
  cell: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    flexWrap: 'wrap',
    alignItems: 'center',
  },
  thText: {
    fontWeight: 'bold',
    textAlign: 'center',
  },
  tdText: {},

  // dotyczy obu widoków
  text: {
    fontSize: 16,
  },
  buttonContainerDesktop: {
    width: 75,
    margin: 2,
  },
});

export default ResponsiveTable;
