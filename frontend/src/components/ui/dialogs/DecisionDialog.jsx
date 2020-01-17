import React from 'react';
import {StyleSheet, Text, View, Button, Dimensions} from 'react-native';

const DecisionDialog = (props) => {
  const {
    opened,
    headerText,
    text,
    onConfirmText,
    onConfirm,
    onRejectText,
    onReject,
  } = props;

  return (
  <>
    {opened && (
      <View style={styles.background}>
        <View style={styles.modal}>
          <View style={styles.header}>
            <Text style={styles.headerText}>
              {headerText}
            </Text>
          </View>
          <View style={styles.textContainer}>
            <Text style={styles.text}>
              {text}
            </Text>
          </View>
          <View style={styles.fixToText}>
            <Button
                title={onConfirmText}
                onPress={onConfirm}
            />
            <Button
                title={onRejectText}
                onPress={onReject}
            />
          </View>
        </View>
      </View>
    )}
    </>
  )
};

const styles = StyleSheet.create({
  background:{
    zIndex: 1000,
    position: 'absolute',
    left: 0,
    right: 0,
    top: 200,
  },
  modal:{
    minWidth: 400,
    backgroundColor: "#f4f4f4",
    margin: "auto",
  },
  header:{
    width: '100%',
    margin: 1,
    backgroundColor: "#ff00ff",
  },
  headerText:{
    width: "100%",
    fontSize: 25,
    fontWeight: '600',
  },
  text:{

  },
  fixToText: {
    flexDirection: 'row',
  },
  textContainer:{
    justifyContent: 'center',
    paddingTop: 15,
    paddingBottom: 15,
  }
});

export default DecisionDialog;
