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

const WideTable = (props) => {
  const {
    items,
    totalElements,
    loading,
    onFilterChange,
    columns,
    itemActions,
  } = props;

  return (
    <View style={styles.table}>

      {/* nagłówki kolumn */}
      <View style={styles.headRow}>
        {/* zadeklarowane kolumny */}
        {columns.map((column, key) => (
          <View style={styles.headCell} key={key}>
            <View style={styles.headTextContainer}>
              <Text style={styles.headText}>{column.label}</Text>
            </View>
            {column.filter && (
              <TextInput
                style={styles.input}
                placeholder="Wyszukaj"
                onChangeText={(text) => {
                  onFilterChange(column.name, text);
                }}
              />
            )}
          </View>
        ))}

        {/* kolumna akcji */}
        {itemActions && (
          <View style={styles.cell}>
            <Text style={styles.headText}>Akcje</Text>
          </View>
        )}
      </View>

      {/* spinner ładowania danych */}
      {loading && (
        <View style={styles.loading}>
          <CgkActivityIndicator/>
        </View>
      )}

      {/* informacja o braku rekordów */}
      {!loading && !items.length && (
        <View style={styles.row}>
          <Text style={styles.text}>Brak elementów do wyświetlenia</Text>
        </View>
      )}

      {/* rekordy tabeli */}
      {!loading && items.map((item, rowId) => (
        <View style={styles.row} key={rowId}>

          {/* dane do komórek */}
          {columns.map((column, key) => {
            let value = item[column.name];

            if (typeof value === 'boolean') { // ładne wyświetlanie wartości logicznych
              value = value ? 'TAK' : 'NIE';
            }

            const array = [].concat(value); // opakowanie pojedynczej wartości w tablicę

            return (
              <View style={styles.cell} key={key}>

                {/* elementy tablicy wartości */}
                {array.map((text, key) => (
                  <Text key={key} style={styles.text}>{text}</Text>
                ))}
              </View>
            );
          })}

          {/* komórka z akcjami */}
          {itemActions && (
            <View style={styles.cell}>
              {itemActions.filter((action) => !action.disabledIfDeleted || !item.deleted).map((action, idx) => (
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
      <View style={[styles.row, styles.footer]}>
        <Text style={styles.text}>
          Wyświetla {items.length || 0} z {totalElements || 0} elementów
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
  headRow: {
    flexDirection: 'row',
    backgroundColor: 'lightgrey',
    padding: 2,
  },
  row: {
    flexDirection: 'row',
    borderTopWidth: 1,
    padding: 2,
  },
  headCell: {
    flex: 1,
  },
  cell: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    flexWrap: 'wrap',
    alignItems: 'center',
  },
  headTextContainer: {
    flex: 1,
    justifyContent: 'center',
  },
  headText: {
    fontSize: 16,
    fontWeight: 'bold',
    textAlign: 'center',
    marginTop: 5,
    marginBottom: 5,
  },
  text: {
    width: '100%',
    fontSize: 16,
    textAlign: 'center',
  },
  input: {
    backgroundColor: 'white',
    borderWidth: 1,
    borderRadius: 2,
    padding: 2,
    margin: 2,
  },
  opacity: {
    alignItems: 'center',
  },
  icon: {
    height: 30,
    width: 30,
  },
  loading: {
    flex: 1,
    borderTopWidth: 1,
    padding: 5,
  },
  footer: {
    justifyContent: 'center',
    backgroundColor: 'lightgrey',
    padding: 5,
  },
});

export default WideTable;
