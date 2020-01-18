import React from 'react';
import {StyleSheet, Text, View, Button, Dimensions} from 'react-native';
import {mainColor} from '../../../constValues';


const InfoDialog = (props) => {
  const {
    opened,
    headerText,
    text,
    onConfirm,
  } = props;

  return (
  <>
    {opened && (
      <View style={styles.background}>
        <View style={styles.modal}>
          <View style={styles.headerContainer}>
            <Text style={styles.headerText}>
              {headerText}
            </Text>
          </View>
          <View style={styles.textContainer}>
            <Text style={styles.text}>
              {text}
            </Text>
          </View>
          <View style={styles.buttonContainer}>
            <Button
                title="Ok"
                onPress={onConfirm}
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
  headerContainer:{
    width: '100%',
    margin: 1,
    backgroundColor: mainColor,
    alignContent: 'center',
    paddingTop: 5,
    paddingBottom: 5,
  },
  headerText:{
    width: "100%",
    fontSize: 25,
    fontWeight: '600',
    textAlign: 'center',
    color: '#ffffff',
  },
  text:{
    fontSize: 18,
  },
  buttonContainer: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingLeft: '30%',
    paddingRight: '30%',
  },
  textContainer:{
    justifyContent: 'center',
    paddingTop: 15,
    paddingBottom: 15,
    textAlign: 'center',
  }
});

export default InfoDialog;
