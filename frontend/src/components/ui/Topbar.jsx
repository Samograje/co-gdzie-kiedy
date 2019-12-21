import React from 'react';
import {StyleSheet, Text, TouchableOpacity, View} from 'react-native';
import {mainColor} from '../../constValues';

// komponent nagłówka wykrzystywany w stronie internetowej

const Topbar = ({onPress}) => {
  return (
    <View style={styles.topbar}>
      <TouchableOpacity onPress={onPress}>
        <Text style={styles.label}>
          Co gdzie kiedy
        </Text>
      </TouchableOpacity>
    </View>
  )
};

const withTopbar = (MainComponent) => {
  return (props) => (
    <>
      <Topbar
        onPress={() => {
          props.push('Home');
        }}
      />
      <MainComponent {...props}/>
    </>
  )
};

const styles = StyleSheet.create({
  topbar: {
    backgroundColor: mainColor,
    height: 50,
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 10,
  },
  label: {
    color: 'white',
    fontSize: 20,
    fontWeight: 'bold',
    marginLeft: 20,
    textDecorationLine: 'underline',
  },
  button: {
    backgroundColor: 'white',
    marginLeft: 20,
    padding: 5,
    borderRadius: 5,
  },
  buttonLabel: {
    color: 'black',
    fontSize: 20,
    fontWeight: 'bold',
  },
});

export default withTopbar;
