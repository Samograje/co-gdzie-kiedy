import React from 'react';
import {Button, StyleSheet, View} from 'react-native';
import {mainColor} from "../../constValues";

const LinkElement = ({label, onClick}) => {
  return (
    <View style={styles.container}>
      <Button
        title={label}
        onPress={onClick}
        color={mainColor}
      />
    </View>
  )
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    margin: 5,
  },
});

export default LinkElement;
