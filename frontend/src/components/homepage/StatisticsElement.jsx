import React from 'react';
import {StyleSheet, Text, View} from "react-native";

const StatisticsElement = ({label, value}) => {
  return (
    <View style={styles.container}>
      <Text>{label}</Text>
      <Text>{value}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {

  },
});

export default StatisticsElement;
