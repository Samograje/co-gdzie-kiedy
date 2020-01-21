import React from 'react';
import {
  StyleSheet,
  Text,
  TextInput,
  View,
} from 'react-native';

const FormField = (props) => {
  const {
    label, // string - nazwa pola do wyświetlenia nad polem tekstowym
    placeholder, // string - placeholder wyświetlany w polu tekstowym
    disabled, // bool informujący o tym, czy pole tekstowe ma być niedostępne
    text, // string - wartość do wpisania w pole tekstowe
    onChangeText, // funkcja wykonywana przy zmianie tekstu w polu tekstowym; argumentem jest string z nowym tekstem
    errors, // string lub tablica stingów - komunikat(y) błędów walidacji
  } = props;

  return <View style={styles.inputField}>
    <Text style={styles.label}>{label}</Text>
    <TextInput
      style={styles.input}
      placeholder={placeholder || "Wprowadź tekst"}
      onChangeText={onChangeText}
      editable={!disabled}
      value={text}
    />
    {[].concat(errors).map((errorText, idx) => (
      <Text style={styles.validationError} key={idx}>{errorText}</Text>
    ))}
  </View>;
};

const styles = StyleSheet.create({
  inputField: {
    flex: 1,
    margin: 5,
  },
  label: {
    fontSize: 16,
    fontWeight: '500',
    marginBottom: 2,
  },
  input: {
    fontSize: 16,
    padding: 2,
    borderWidth: 1,
    borderRadius: 2,
  },
  validationError: {
    color: 'darkred',
  },

  // TODO: usunąć poniższe stare style

  // labeltext: {
  //   marginTop: 10,
  //   fontSize: 20,
  //   marginBottom: 5,
  //   fontWeight: '500',
  // },

  // textinput: {
  //   marginBottom: 10,
  //   width: '100%',
  //   height: 35,
  //   borderColor: '#009000',
  //   borderWidth: 1.2,
  //   padding: 2,
  //   borderRadius: 7,
  //   fontWeight: '500',
  // },

  // validationError: {
  //   color: '#ff0000',
  //   fontSize: 10,
  // },
});

export default FormField;
