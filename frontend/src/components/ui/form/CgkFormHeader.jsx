import React from 'react';
import {StyleSheet, Text, View} from 'react-native';
import {mainColor} from "../../../constValues";

const CgkFormHeader =  (props) => {
  const {
    text, // tekst do wyświetlenia w nagłówku
  } = props;

  return (
    <View style={styles.header}>
      <Text style={styles.headerText}>
        {text}
      </Text>
    </View>
  );
};

const styles = StyleSheet.create({
  header: {
    margin: 5,
    paddingBottom: 10,
    marginBottom: 15,
    borderBottomWidth: 1,
    borderBottomColor: mainColor,
  },
  headerText: {
    fontSize: 24,
  },

  // TODO: usunąć poniższe stare style

  // header: {
  //   fontSize: 24,
  //   paddingBottom: 10,
  //   marginBottom: 25,
  //   borderBottomColor: '#199187',
  //   borderBottomWidth: 1,
  // },
});

export default CgkFormHeader;
