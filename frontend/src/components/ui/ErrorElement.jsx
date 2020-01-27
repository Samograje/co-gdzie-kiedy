import React from 'react';
import {Button, StyleSheet, Text, View} from "react-native";
import {mainColor} from "../../constValues";

const ErrorElement = ({message}) => {
  // TODO: warianty: error, warn, info - z różnymi kolorami
  return (
    <View style={styles.container}>
      <Text style={styles.text}>{message}</Text>
        <Button
            style={styles.b}
            title={"Odśwież"}
            color={mainColor}
        />
    </View>

  )
};

function refresh() {

}

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
