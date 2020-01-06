import React from 'react';
import {Button,
        StyleSheet,
        Text,
        View,
        TextInput,
        } from 'react-native';

const SoftwareDetailsComponent = (props) => {
  let mode;
  if(props.mode === 'edit')
    mode = "edycji";
  else if (props.mode === 'create')
    mode = "dodawania nowego";
  else
    return "";

  console.log(props.validationEmptyStatus);
  console.log(props.validationDurationIsNumberStatus);
  console.log(props.validationDurationIsBiggerThan0NumberStatus);
  console.log(props.validationAvailableKeysIsNumberStatus);
  console.log(props.validationAvailableKeysIsBiggerThan0NumberStatus);
  console.log("*****");
  return (
    <View style={styles.addform}>

      <Text style={styles.header}>Formularz {mode} oprogramowania.</Text>
      <Text>Pola z * są obowiązkowe.</Text>
      <Text style={styles.labeltext}>* Nazwa oprogramowania:</Text>
      <TextInput style={styles.textinput}
                 placeholder={"np. Mathematica"}
                 value={props.name}
                 onChangeText={(name) => props.setName(name)}
      />
      <Text style={styles.labeltext}>* Klucz produktu:</Text>
      <TextInput style={styles.textinput}
                 placeholder={"np. T847-54GF-7845-FSF5"}
                 onChangeText={(key) => props.setKey(key)}
                 value={props.keY}
      />
      <Text style={styles.labeltext}>* Ilość dostępnych kluczy:</Text>
      <TextInput style={styles.textinput}
                 placeholder={"np. 5"}
                 onChangeText={(availableKeys) => props.setAvailableKeys(availableKeys)}
                 value={props.availableKeys}
      />
      <Text  style={styles.validationError}>{props.validationAvailableKeysIsNumberStatus ? "Wartość musi być liczbą" : ""}</Text>
      <Text  style={styles.validationError}>{!props.validationAvailableKeysIsBiggerThan0NumberStatus ? "Wartość musi być liczbą większą od 0" : ""}</Text>
      <Text style={styles.labeltext}>* Czas trwania (w miesiącach):</Text>
      <TextInput style={styles.textinput}
                 placeholder={"np. 4 "}
                 onChangeText={(duration) => props.setDuration(duration)}
                 value={props.duration}
      />
      <Text  style={styles.validationError}>{props.validationDurationIsNumberStatus ? "Wartość musi być liczbą" : ""}</Text>
      <Text  style={styles.validationError}>{!props.validationDurationIsBiggerThan0NumberStatus ? "Wartość musi być liczbą większą od 0" : ""}</Text>
      <Button
        title="Zapisz"
        onPress={props.onSubmit}
        disabled={props.validationEmptyStatus ||
                  props.validationAvailableKeysIsNumberStatus ||
                  !props.validationAvailableKeysIsBiggerThan0NumberStatus ||
                  props.validationDurationIsNumberStatus ||
                  !props.validationDurationIsBiggerThan0NumberStatus}
      />
      <Button
        title="Wróć"
        onPress={props.onReject}
      />
    </View>
  );
};

const styles = StyleSheet.create({
    addform: {
      alignSelf: 'center',
      padding: 15,
    },
    labeltext: {
      marginTop: 10,
      fontSize: 20,
      marginBottom: 5,
      fontWeight: 500,
    },
    header: {
      fontSize: 24,
      paddingBottom: 10,
      marginBottom: 25,
      borderBottomColor: '#199187',
      borderBottomWidth: 1,
    },
    textinput: {
      marginBottom: 10,
      width: 500,
      height: 35,
      borderColor: '#009000',
      borderWidth: 1.2,
      padding: 2,
      borderRadius: 7,
      fontWeight: 500,
    },

    validationError:{
      color: '#ff0000',
      fontSize: 10,
    }
});

export default SoftwareDetailsComponent;

