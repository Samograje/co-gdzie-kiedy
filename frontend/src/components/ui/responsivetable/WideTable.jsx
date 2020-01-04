import React from 'react';
import {Button, StyleSheet, Text, View} from 'react-native';
import {mainColor} from '../../../constValues';

const WideTable = (props) => {
  const {
    items,
    totalElements,
    onFetchData,
    columns,
    itemActions,
    footerActions,
  } = props;

  return (
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

      {/* informacja o braku rekordów */}
      {!items.length && (
        <View style={styles.tr}>
          <Text style={[styles.tdText, styles.text]}>Brak elementów do wyświetlenia</Text>
        </View>
      )}

      {/* stopka */}
      <View style={[styles.tr, styles.footer]}>
        {footerActions && footerActions.map((action, idx) => (
          <View style={styles.buttonContainer} key={idx}>
            <Button
              title={action.label}
              onPress={() => action.onClick()}
              color={mainColor}
            />
          </View>
        ))}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
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
  buttonContainer: {
    margin: 2,
  },
  text: {
    fontSize: 16,
  },
  footer: {
    backgroundColor: 'lightgrey',
    padding: 5,
  },
});

export default WideTable;
