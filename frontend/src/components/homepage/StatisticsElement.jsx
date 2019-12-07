import React from 'react';
import {StyleSheet, Text, View} from "react-native";

const StatisticsElement = ({label, value, isWide}) => {
  const layoutStyle = isWide ? styles.wide : styles.small;

  return (
    <View style={[styles.container, layoutStyle]}>
      <Text style={styles.value}>{value}</Text>
      <Text style={styles.label}>{label}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: 10,
    backgroundColor: 'lightgrey',
    margin: 5,
    borderRadius: 2,
  },
  small: {
    width: '98%',
  },
  wide: {
    width: '48%',
  },
  value: {
    fontSize: 100,
  },
  label: {
    fontSize: 20,
  },
});

export default StatisticsElement;
