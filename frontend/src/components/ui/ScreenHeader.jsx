import React from 'react';
import {StyleSheet, Text, View} from 'react-native';

const ScreenHeader = (props) => {
  const {title} = props;

  return (
    <View style={styles.card}>
      <Text style={styles.text}>{title}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  card: {
    flex: 1,
    backgroundColor: 'lightgrey',
    padding: 5,
    borderWidth: 1,
    borderRadius: 2,
    margin: 5,
  },
  text: {
    fontSize: 30,
  },
});

export default ScreenHeader;
