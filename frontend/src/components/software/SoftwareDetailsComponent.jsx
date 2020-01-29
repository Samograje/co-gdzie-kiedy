import React from 'react';
import {ScrollView, StyleSheet, Text, View,} from 'react-native';
import CgkActivityIndicator from '../ui/CgkActivityIndicator';
import CgkFormFooter from '../ui/form/CgkFormFooter';
import CgkFormHeader from '../ui/form/CgkFormHeader';
import CgkLabelAndValidation from '../ui/form/CgkLabelAndValidation';
import CgkTextInput from '../ui/form/CgkTextInput';
import MultiSelect from "../ui/form/MultiSelect";
import SuccessElement from '../ui/SuccessElement';
import DecisionDialog from "../ui/dialogs/DecisionDialog";

const SoftwareDetailsComponent = (props) => {
  let mode;
  if (props.mode === 'edit')
    mode = "edycji";
  else if (props.mode === 'create' || props.mode === 'copy')
    mode = "dodawania nowego";
  else
    return "";
  return (
    <>
      {props.dialogOpened && (
          <DecisionDialog
              headerText="Uwaga!"
              text="Zmiany nie zostaną zapisane, czy chcesz kontynuować?"
              onConfirmText="Tak"
              onConfirm={props.dialogHandleConfirm}
              onRejectText="Nie"
              onReject={props.dialogHandleReject}
          />
      )}
    <ScrollView>
      <View style={props.isWide ? styles.contentWide : styles.contentMobile}>
        <CgkFormHeader text={`Formularz ${mode} oprogramowania.`}/>
      {(props.loading || props.loadingComputerSets) && (
          <CgkActivityIndicator/>
      )}
      {!(props.loading || props.loadingComputerSets) && (
        <>
          <Text>Pola z * są obowiązkowe.</Text>

          <CgkLabelAndValidation label="* Nazwa oprogramowania:">
            <CgkTextInput
              placeholder="Wprowadź nazwe nowego oprogramowania"
              text={props.name}
              onChangeText={(name) => props.setName(name)}
            />
          </CgkLabelAndValidation>

          <CgkLabelAndValidation label="* Klucz produktu:">
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

          <CgkLabelAndValidation label="Zestaw komputerowy:">
            <MultiSelect
                values={props.computerSetIds}
                onAddValue={props.onAddComputerSetValues}
                onRemoveValue={props.onRemoveComputerSetValues}
                options={props.dataSourceComputerSets.items}
                onUpdateOptions={props.updateComputerSets}
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
        {props.isGrowlVisible && (
          <SuccessElement text="Zapisano oprogramowanie"/>
        )}
      </View>
    </ScrollView>
  </>
  );
};

const styles = StyleSheet.create({
  contentWide: {
    alignSelf: 'center',
    width: 400,
    margin: 10,
  },
  contentMobile: {
  },

});

export default SoftwareDetailsComponent;

