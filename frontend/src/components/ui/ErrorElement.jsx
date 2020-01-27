import React from 'react';
import {Button, StyleSheet, Text, View} from "react-native";
import {mainColor} from "../../constValues";

const ErrorElement = (props) => {

  const {
    message,
    OnFetchData,
  } = props;

  // TODO: warianty: error, warn, info - z różnymi kolorami


  return (
    <View style={styles.container}>
      <Text style={styles.text}>{message}</Text>
        <Button
            onPress={OnFetchData}
            style={styles.b}
            title={"Odśwież"}
            color={mainColor}
        />
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
    b: {
        padding: 10
    }

});

export default ErrorElement;
