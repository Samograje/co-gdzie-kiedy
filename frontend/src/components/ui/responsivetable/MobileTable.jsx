import React from 'react';
import {
  Image,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from 'react-native';
import CgkActivityIndicator from '../CgkActivityIndicator';

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
          <CgkActivityIndicator/>
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
            <View style={styles.icons}>
              {itemActions.map((action, idx) => (
                <TouchableOpacity
                  style={styles.opacity}
                  onPress={() => action.onClick(item)}
                  key={idx}
                >
                  <Image
                    source={action.icon}
                    resizeMode="contain"
                    style={styles.icon}
                  />
                </TouchableOpacity>
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
    textAlign: 'left',
    marginLeft: 5,
    flex: 1,
  },
  icons: {
    marginTop: 5,
    marginBottom: 5,
    flexDirection: 'row',
  },
  opacity: {
    flex: 1,
    alignItems: 'center',
  },
  icon: {
    height: 50,
    width: 50,
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
