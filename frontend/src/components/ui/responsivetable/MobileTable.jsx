import React from 'react';
import {ActivityIndicator, Button, Image, StyleSheet, Text, TextInput, TouchableOpacity, View} from 'react-native';
import {mainColor} from '../../../constValues';

const MobileTable = (props) => {
  const {
    items,
    totalElements,
    loading,
    onFilterChange,
    columns,
    itemActions,
  } = props;

  const filtersEnabled = columns.filter((column) => column.filter).length > 0;

  return (
    <View style={styles.list}>

      {/* nagłówek listy */}
      {filtersEnabled && (
        <View style={[styles.head, styles.item]}>
          <Text style={styles.filterHeader}>Filtry</Text>
          {columns.map((column, key) => {
            if (!column.filter) {
              return null;
            }
            return (
              <View style={styles.row} key={key}>
                <Text style={[styles.text, styles.label]}>{column.label}</Text>
                <TextInput
                  style={styles.input}
                  placeholder="Wyszukaj"
                  onChangeText={(text) => {
                    onFilterChange(column.name, text);
                  }}
                />
              </View>
            );
          })}
        </View>
      )}

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
        <View style={styles.item}>
          <Text style={styles.text}>Brak elementów do wyświetlenia</Text>
        </View>
      )}

      {/* rekordy */}
      {!loading && items.map((item, idx) => (
        <View style={styles.item} key={idx}>

          {/* wiersze z danymi */}
          {columns.map((column, key) => (
            <View style={styles.row} key={key}>
              <Text style={[styles.text, styles.label]}>{column.label}</Text>
              <Text style={[styles.text, styles.value]}>{item[column.name]}</Text>
            </View>
          ))}

          {/* przyciski akcji */}
          {itemActions && (
            <View style={styles.buttons}>
              {itemActions.map((action, idx) => (
                <View style={styles.buttonContainer} key={idx}>
                  {action.icon && (
                    <TouchableOpacity onPress={() => action.onClick(item)}>
                      <Image source={require(`./../../../images/${action.icon}`)} style={styles.image}/>
                    </TouchableOpacity>
                  )}
                  {!action.icon && (
                    <Button
                      title={action.label}
                      onPress={() => action.onClick(item)}
                      color={mainColor}
                    />
                  )}
                </View>
              ))}
            </View>
          )}
        </View>
      ))}

      {/* stopka */}
      <View style={[styles.item, styles.footer]}>
        <Text style={styles.text}>
          Wyświetla {items.length || 0} z {totalElements || 0} elementów
        </Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  image: {
    padding: 30,
    width: '20%',
    flexDirection: 'row',
    justifyContent: 'center',
  },
  list: {
    borderWidth: 1,
    borderTopWidth: 0,
    borderRadius: 2,
    overflow: 'hidden',
  },
  head: {
    backgroundColor: 'lightgrey',
  },
  filterHeader: {
    fontSize: 16,
    fontWeight: 'bold',
    textAlign: 'center',
    margin: 5,
    marginBottom: 20,
  },
  input: {
    flex: 1,
    backgroundColor: 'white',
    borderWidth: 1,
    borderRadius: 2,
    padding: 2,
    margin: 2,
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
  buttonContainer: {
    flex: 1,
    margin: 5,
  },
  text: {
    fontSize: 16,
    textAlign: 'center',
  },
  footer: {
    justifyContent: 'center',
    backgroundColor: 'lightgrey',
  },
  loading: {
    flex: 1,
    borderTopWidth: 1,
    padding: 5,
  },
});

export default MobileTable;
