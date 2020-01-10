import React from 'react';
import {Picker, StyleSheet, Text} from 'react-native';

const PickerWithItems = (props) => {
  const {
    value, // id opcji wybranej w pickerze
    updateValue, // funkcja aktualizująca wartość po wyborze z pickera
    options, // opcje do pickera - obiekt o kluczach id, name
  } = props;

  if (!options || !options.length) {
    return (
      <Text>Brak opcji do wyboru</Text>
    );
  }

  return (
    <Picker
      style={styles.picker}
      onValueChange={updateValue}
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
    marginBottom: 10,
    width: '100%',
    height: 35,
    borderColor: '#009000',
    borderWidth: 1.2,
    padding: 2,
    borderRadius: 7,
  },
});

export default PickerWithItems;