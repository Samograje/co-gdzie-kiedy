import React from 'react';
import {StyleSheet, Text, TouchableOpacity, View} from 'react-native';
import AutoComplete from './AutoComplete';
import {mainColor} from '../../../constValues';

const MultiSelect = (props) => {
  const {
    values, // tablica idków wybranych opcji
    onAddValue, // funkcja dodająca wartość po wyborze z pickera, argument to id dodawanego obiektu
    onRemoveValue, // funkcja usuwająca wybraną wartość, argument to id usuwanego elementu
    options, // opcje do pickera - tablica obiektów o kluczach id, name
    onUpdateOptions, // funkcja aktualizująca opcje na podstawie tekstu z pola tekstowego, argument to wpisany tekst
  } = props;

  return (
    <View style={styles.container}>
      <AutoComplete
        updateValue={onAddValue}
        options={options}
        updateOptions={onUpdateOptions}
      />
      {values && values.length > 0 && (
        <View style={styles.values}>
          {values.map((value, key) => {
            const label = options.filter((option) => option.id === value)[0].name;
            return (
              <View style={styles.value} key={key}>
                <Text style={styles.label}>{label}</Text>
                <TouchableOpacity
                  style={styles.deleteOpacity}
                  onPress={() => onRemoveValue(value)}
                >
                  <Text style={styles.deleteText}>X</Text>
                </TouchableOpacity>
              </View>
            );
          })}
        </View>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {},
  vaules: {
    flexDirection: 'row',
  },
  value: {
    flexDirection: 'row',
    alignItems: 'center',
    alignSelf: 'baseline',
    padding: 2,
    borderRadius: 2,
    margin: 2,
    backgroundColor: 'lightgrey',
  },
  label: {
    flex: 1,
    marginLeft: 5,
    marginRight: 5,
  },
  deleteOpacity: {
    backgroundColor: mainColor,
    borderRadius: 2,
    width: 30,
    height: 30,
    margin: 2,
    justifyContent: 'center',
  },
  deleteText: {
    textAlign: 'center',
  },
});

export default MultiSelect;
