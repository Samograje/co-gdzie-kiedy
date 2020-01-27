import React from 'react';
import {StyleSheet, Text, View} from 'react-native';
import {mainColor} from '../../../constValues';

const Growl = (props) => {
  const {
    summary,
    detail,
  } = props;

  return (
    <View style={styles.background}>
      <View style={styles.modal}>
        <View style={styles.headerContainer}>
          <Text style={styles.headerText}>
            {summary}
          </Text>
        </View>
        <View style={styles.textContainer}>
          <Text style={styles.text}>
            {detail}
          </Text>
        </View>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  background: {
    zIndex: 1000,
    position: 'absolute',
    left: 0,
    right: 0,
    top: 200,
    alignItems: 'center'
  },
  modal: {
    backgroundColor: "#f4f4f4",
    margin: "auto",
    flex: 1,
    maxWidth: '80%',
    borderWidth: 2,
    borderColor: mainColor,
  },
  headerContainer: {
    backgroundColor: mainColor,
    alignContent: 'center',
    paddingTop: 5,
    paddingBottom: 5,
    alignItems: 'center'
  },
  headerText: {
    fontSize: 25,
    fontWeight: '600',
    color: '#ffffff',
    alignSelf: 'center',
  },
  text: {
    fontSize: 18,
  },
});

export default Growl;
