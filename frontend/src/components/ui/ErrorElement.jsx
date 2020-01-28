import React from 'react';
import {StyleSheet, Text, View} from "react-native";

const ErrorElement = ({message}) => {
  // TODO: warianty: error, warn, info - z różnymi kolorami
  return (
    <View style={styles.container}>
      <Text style={styles.text}>{message}</Text>
    </View>
  )
};

const styles = StyleSheet.create({
  // TODO: większa wysokość + pozycjonowanie na środku
  container: {
    backgroundColor: 'darkred',
    width: '80%',
    alignSelf: 'center',
    marginTop: '1%',
    marginBottom: '1%',
    borderRadius: 5,
  },
  text: {
    alignSelf: 'center',
    paddingTop: '1%',
    paddingBottom: '1%',
    color: 'white',
    fontSize: 20,
    fontWeight: 'bold',
    textAlign: 'center',
  },
});

export default ErrorElement;
