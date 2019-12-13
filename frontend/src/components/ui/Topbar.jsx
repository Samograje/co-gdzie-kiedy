import React from 'react';
import {StyleSheet, Text, TouchableOpacity, View} from 'react-native';
import {mainColor} from '../../constValues';

const Topbar = ({label, isHomePage, onGoBack}) => {
  return (
    <View style={styles.topbar}>
      {!isHomePage && (
        // TODO: zamiast tego można by renderować ikonę powrotu
        <TouchableOpacity
          style={styles.button}
          onPress={onGoBack}
        >
          <Text style={styles.buttonLabel}>Wróć</Text>
        </TouchableOpacity>
      )}
      <Text style={styles.label}>{label}</Text>
    </View>
  )
};

const withTopbar = (MainComponent, label, isHomePage) => {
  return (props) => (
    <>
      <Topbar
        label={label}
        isHomePage={isHomePage}
        onGoBack={props.navigation.goBack}
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
