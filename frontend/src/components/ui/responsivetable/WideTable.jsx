import React from 'react';
import {ActivityIndicator, Button, StyleSheet, Text, View} from 'react-native';
import {mainColor} from '../../../constValues';

const WideTable = (props) => {
  const {
    items,
    totalElements,
    loading,
    onFetchData,
    columns,
    itemActions,
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

      {/* spinner ładowania danych */}
      {loading && (
        <View style={styles.loading}>
          <ActivityIndicator
            size="large"
            color={mainColor}
          />
        </View>
      )}

      {/* informacja o braku rekordów */}
      {!loading && !items.length && (
        <View style={styles.tr}>
          <Text style={[styles.tdText, styles.text]}>Brak elementów do wyświetlenia</Text>
        </View>
      )}

      {/* rekordy tabeli */}
      {!loading && items.map((item, rowId) => (
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

      {/* stopka */}
      <View style={[styles.tr, styles.footer]}>
        <Text style={styles.text}>
          Wyświetla {items.length || 0} z {totalElements} elementów
        </Text>
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
    padding: 2,
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
    textAlign: 'center',
  },
  footer: {
    justifyContent: 'center',
    backgroundColor: 'lightgrey',
    padding: 5,
  },
  loading: {
    flex: 1,
    borderTopWidth: 1,
    padding: 5,
  },
});

export default WideTable;
