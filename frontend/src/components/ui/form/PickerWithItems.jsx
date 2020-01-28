import React from 'react';
import {Picker, StyleSheet, Text} from 'react-native';

const PickerWithItems = (props) => {
  const {
    value, // id opcji wybranej w pickerze
    updateValue, // funkcja aktualizująca wartość po wyborze z pickera
    editable, // bool informujący czy picker ma być edytowalny
    options, // opcje do pickera - tablica obiektów o kluczach id, name
  } = props;

  if (!options || !options.length) {
    return (
      <Text>Brak opcji do wyboru</Text>
    );
  }

  return (
    <Picker
      style={styles.picker}
      enabled={editable}
      onValueChange={(value) => updateValue(value !== 'Brak' ? parseInt(value) : null)}
      selectedValue={value}
      mode="dropdown"
    >
      <Picker.Item
        label="Brak"
        value={null}
      />
      {options.map((item, key) => (
        <Picker.Item
          key={key}
          label={item.name}
          value={item.id}
        />
      ))}
    </Picker>
  );
};

const styles = StyleSheet.create({
  picker: {
    fontSize: 16,
    padding: 2,
    borderWidth: 1,
    borderRadius: 2,
  },

  // TODO: usunąć poniższe stare style

  // picker: {
  //   marginBottom: 10,
  //   width: '100%',
  //   height: 35,
  //   borderColor: '#009000',
  //   borderWidth: 1.2,
  //   padding: 2,
  //   borderRadius: 7,
  // },
});

export default PickerWithItems;
