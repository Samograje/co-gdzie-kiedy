import React from 'react';
import {StyleSheet, Text, View} from 'react-native';
import {mainColor} from '../../constValues';

const SuccessElement = (props) => {
  const {
    text,
  } = props;

  return (
    <Text style={styles.text}>{text}</Text>
  );
};

const styles = StyleSheet.create({
  text: {
    fontSize: 20,
    backgroundColor: mainColor,
    padding: 5,
    borderRadius: 2,
    margin: 2,
  },
});

export default SuccessElement;
