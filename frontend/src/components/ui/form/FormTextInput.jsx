import React from 'react';
import {StyleSheet, TextInput} from 'react-native';

const FormTextInput = (props) => {
  const {
    placeholder, // string - placeholder wyświetlany w polu tekstowym
    disabled, // bool informujący o tym, czy pole tekstowe ma być niedostępne
    text, // string - wartość do wpisania w pole tekstowe
    onChangeText, // funkcja wykonywana przy zmianie tekstu w polu tekstowym; argumentem jest string z nowym tekstem
  } = props;

  return (
    <TextInput
      style={styles.input}
      placeholder={placeholder || "Wprowadź tekst"}
      onChangeText={onChangeText}
      editable={!disabled}
      value={text}
    />
  );
};

const styles = StyleSheet.create({
  input: {
    fontSize: 16,
    padding: 2,
    borderWidth: 1,
    borderRadius: 2,
  },

  // TODO: usunąć poniższe stare style

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
});

export default FormTextInput;
