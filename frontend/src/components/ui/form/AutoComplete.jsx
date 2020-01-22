import React from 'react';
import {StyleSheet, Text, TextInput, View} from 'react-native';
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
    <View style={styles.container}>
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
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
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
