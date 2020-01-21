import React from 'react';
import {
  ActivityIndicator,
  Button,
  ScrollView,
  StyleSheet,
  Text,
  View,
  TextInput,
} from 'react-native';
import FormFooter from '../ui/form/FormFooter';

const SoftwareDetailsComponent = (props) => {
  let mode;
  if (props.mode === 'edit')
    mode = "edycji";
  else if (props.mode === 'create')
    mode = "dodawania nowego";
  else
    return "";
  return (
    <ScrollView>
      <View style={styles.addForm}>
        <Text style={styles.header}>Formularz {mode} oprogramowania.</Text>
      {(props.loading) && (
        <View style={styles.indicator}>
          <ActivityIndicator size="large"/>
        </View>
      )}
      {(!props.loading) && (
        <>
          <Text>Pola z * są obowiązkowe.</Text>
          <Text style={styles.labeltext}>* Nazwa oprogramowania:</Text>
          <TextInput style={styles.textinput}
                     placeholder={"Wprowadź nazwe nowego oprogramowania"}
                     value={props.name}
                     onChangeText={(name) => props.setName(name)}
          />
          <Text style={styles.labeltext}>* Klucz produktu:</Text>
          <TextInput style={styles.textinput}
                     placeholder={"Wprowadź klucz produktu"}
                     onChangeText={(key) => props.setKey(key)}
                     value={props.keY}
          />
          <Text style={styles.labeltext}>* Ilość dostępnych kluczy:</Text>
          <TextInput style={styles.textinput}
                     placeholder={'Wprowadź ilość dostępnych kluczy'}
                     onChangeText={(availableKeys) => props.setAvailableKeys(availableKeys)}
                     value={props.availableKeys.toString()}
          />
          <Text
              style={styles.validationError}>{props.validationAvailableKeysIsNumberStatus ? "Wartość musi być liczbą" : ""}</Text>
          <Text
              style={styles.validationError}>{!props.validationAvailableKeysIsBiggerThan0NumberStatus ? "Wartość musi być liczbą większą od 0" : ""}</Text>
          <Text style={styles.labeltext}>* Czas trwania (w miesiącach):</Text>
          <TextInput style={styles.textinput}
                     placeholder={"Wprowadź okres trwania licencji, w miesiącach "}
                     onChangeText={(duration) => props.setDuration(duration)}
                     value={props.duration.toString()}
                     disabled={props.validationDisableDuration}
          />
          <Text
              style={styles.validationError}>{props.validationDurationIsNumberStatus ? "Wartość musi być liczbą" : ""}</Text>
          <Text
              style={styles.validationError}>{!props.validationDurationIsBiggerThan0NumberStatus ? "Wartość musi być liczbą większą od 0" : ""}</Text>
        </>
      )}

        <FormFooter
          isSubmitDisabled={
            props.validationEmptyStatus ||
            props.validationAvailableKeysIsNumberStatus ||
            !props.validationAvailableKeysIsBiggerThan0NumberStatus ||
            props.validationDurationIsNumberStatus ||
            !props.validationDurationIsBiggerThan0NumberStatus ||
            props.validationDisableDuration
          }
          onSubmit={props.onSubmit}
          onReject={props.onReject}
        />
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  addForm: {
    alignSelf: 'center',
    padding: 15,
    width: '75%',
  },
  labeltext: {
    marginTop: 10,
    fontSize: 20,
    marginBottom: 5,
    fontWeight: '500',
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
    width: '100%',
    height: 35,
    borderColor: '#009000',
    borderWidth: 1.2,
    padding: 2,
    borderRadius: 7,
    fontWeight: '500',
  },

  validationError: {
    color: '#ff0000',
    fontSize: 10,
  },

  indicator: {
    flex: 1,
    paddingTop: 20,
    paddingBottom: 20,
  },
});

export default SoftwareDetailsComponent;

