import React from 'react';
import {StyleSheet, Text, TextInput} from 'react-native';
import PickerWithItems from "./PickerWithItems";

const AutoComplete = (props) => {
  const {
    value, // id opcji wybranej w pickerze
    updateValue, // funkcja aktualizująca wartość po wyborze z pickera
    options, // opcje do pickera - obiekt o kluczach id, name
    updateOptions, // funkcja aktualizująca opcje na podstawie tekstu z pola tekstowego
  } = props;

  if (!options || !options.length) {
    return (
      <Text>Brak opcji do wyboru</Text>
    );
  }

  return (
    <>
      <TextInput
        style={styles.textInput}
        placeholder="Wyszukaj"
        onChangeText={updateOptions}
      />
      <PickerWithItems
        value={value}
        updateValue={updateValue}
        options={options}
      />
    </>
  );
};

const styles = StyleSheet.create({
  textInput: {
    fontSize: 16,
    padding: 2,
    borderWidth: 1,
    borderRadius: 2,
    marginBottom: 5,
  },

  // TODO: usunąć poniższe stare style

  // textInput: {
  //   marginBottom: 10,
  //   width: '100%',
  //   height: 35,
  //   borderColor: '#009000',
  //   borderWidth: 1.2,
  //   padding: 2,
  //   borderRadius: 7,
  //   fontWeight: '500',
  // },
});

export default AutoComplete;
