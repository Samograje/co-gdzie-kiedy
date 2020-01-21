import React from 'react';
import {
  ActivityIndicator,
  ScrollView,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import CgkFormFooter from '../ui/form/CgkFormFooter';
import CgkLabelAndValidation from '../ui/form/CgkLabelAndValidation';
import CgkTextInput from '../ui/form/CgkTextInput';

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

          <CgkLabelAndValidation
            label="* Nazwa oprogramowania:"
          >
            <CgkTextInput
              placeholder="Wprowadź nazwe nowego oprogramowania"
              text={props.name}
              onChangeText={(name) => props.setName(name)}
            />
          </CgkLabelAndValidation>
          <CgkLabelAndValidation
            label="* Klucz produktu:"
          >
            <CgkTextInput
              placeholder="Wprowadź klucz produktu"
              text={props.keY}
              onChangeText={(key) => props.setKey(key)}
            />
          </CgkLabelAndValidation>
          <CgkLabelAndValidation
            label="* Ilość dostępnych kluczy:"
            errors={[
              props.validationAvailableKeysIsNumberStatus ? "Wartość musi być liczbą" : "",
              !props.validationAvailableKeysIsBiggerThan0NumberStatus ? "Wartość musi być liczbą większą od 0" : ""
            ]}
          >
            <CgkTextInput
              placeholder="Wprowadź ilość dostępnych kluczy"
              text={props.availableKeys.toString()}
              onChangeText={(availableKeys) => props.setAvailableKeys(availableKeys)}
            />
          </CgkLabelAndValidation>
          <CgkLabelAndValidation
            label="* Czas trwania (w miesiącach):"
            errors={[
              props.validationDurationIsNumberStatus ? "Wartość musi być liczbą" : "",
              !props.validationDurationIsBiggerThan0NumberStatus ? "Wartość musi być liczbą większą od 0" : ""
            ]}
          >
            <CgkTextInput
              placeholder="Wprowadź okres trwania licencji, w miesiącach "
              disabled={props.validationDisableDuration}
              text={props.duration.toString()}
              onChangeText={(duration) => props.setDuration(duration)}
            />
          </CgkLabelAndValidation>
        </>
      )}

        <CgkFormFooter
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
  header: {
    fontSize: 24,
    paddingBottom: 10,
    marginBottom: 25,
    borderBottomColor: '#199187',
    borderBottomWidth: 1,
  },
  indicator: {
    flex: 1,
    paddingTop: 20,
    paddingBottom: 20,
  },
});

export default SoftwareDetailsComponent;

