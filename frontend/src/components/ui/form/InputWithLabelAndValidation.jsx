import React from 'react';
import {
  StyleSheet,
  Text,
  View,
} from 'react-native';

const InputWithLabelAndValidation = (props) => {
  const {
    label, // string - nazwa pola do wyświetlenia nad polem tekstowym
    errors, // string lub tablica stingów - komunikat(y) błędów walidacji
    children, // node - komponent do wprowadzania danych
  } = props;

  return (
    <View style={styles.inputField}>
      <Text style={styles.label}>{label}</Text>
      {children}
      {[].concat(errors).map((errorText, idx) => (
        <Text style={styles.validationError} key={idx}>{errorText}</Text>
      ))}
    </View>
  );
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

  // validationError: {
  //   color: '#ff0000',
  //   fontSize: 10,
  // },
});

export default InputWithLabelAndValidation;
