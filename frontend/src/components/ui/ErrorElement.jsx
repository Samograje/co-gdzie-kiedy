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
  },
  text: {

  },
});

export default ErrorElement;
