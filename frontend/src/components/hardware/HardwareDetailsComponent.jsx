import React from 'react';
import {
  ScrollView,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import AutoComplete from '../ui/form/AutoComplete';
import CgkActivityIndicator from '../ui/CgkActivityIndicator';
import CgkFormFooter from '../ui/form/CgkFormFooter';
import CgkFormHeader from '../ui/form/CgkFormHeader';
import CgkLabelAndValidation from '../ui/form/CgkLabelAndValidation';
import CgkTextInput from '../ui/form/CgkTextInput';
import PickerWithItems from '../ui/form/PickerWithItems';
import ErrorElement from '../ui/ErrorElement';
import SuccessElement from '../ui/SuccessElement';
import DecisionDialog from "../ui/dialogs/DecisionDialog";

const HardwareDetailsComponent = (props) => {

  let modeInfo;
  if (props.mode === 'edit')
    modeInfo = "edycji";
  else if (props.mode === 'create')
    modeInfo = "dodawania nowego";

  return (
    <ScrollView>
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
      <View style={props.isWide ? styles.contentWide : styles.contentMobile}>
        <CgkFormHeader text={`Formularz ${modeInfo} sprzętu.`}/>
        <Text>Pola z * są obowiązkowe.</Text>

        {props.isLoading && (
          <CgkActivityIndicator/>
        )}

        {!props.isLoading && (
          <View style={styles.main}>

            <CgkLabelAndValidation label="* Nazwa sprzętu:">
              <CgkTextInput
                placeholder="Wprowadź nazwę sprzętu"
                text={props.name}
                disabled={props.isSubmitting}
                onChangeText={(name) => props.setName(name)}
              />
            </CgkLabelAndValidation>

            <CgkLabelAndValidation label="* Typ:">
              <PickerWithItems
                value={props.dictionaryID}
                editable={!props.isSubmitting}
                updateValue={props.setDictionaryID}
                options={props.dataSourceDictionary}
              />
            </CgkLabelAndValidation>

            <CgkLabelAndValidation label="* Przynależność:">
              <AutoComplete
                value={props.affiliationID}
                disabled={props.isSubmitting}
                updateValue={props.setAffiliationID}
                options={props.dataSourceAffiliations.items}
                updateOptions={props.updateAffiliations}
              />
            </CgkLabelAndValidation>

            <CgkLabelAndValidation label="W zestawie komputerowym:">
              <AutoComplete
                value={props.computerSetID}
                disabled={props.isSubmitting}
                updateValue={props.setComputerSetID}
                options={props.dataSourceComputerSets.items}
                updateOptions={props.updateComputerSets}
              />
            </CgkLabelAndValidation>
          </View>
        )}
        {props.error && (
          <ErrorElement
            message="Nie udało się pobrać danych z serwera"
            type="error"
          />
        )}
        <CgkFormFooter
          isSubmitDisabled={props.isInvalid || props.isSubmitting || props.isLoading}
          isRejectDisabled={props.isSubmitting}
          onSubmit={props.onSubmit}
          onReject={props.onReject}
        />
        {props.isSubmitting && (
          <CgkActivityIndicator/>
        )}
        {props.isGrowlVisible && (
          <SuccessElement text="Zapisano sprzęt"/>
        )}
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  contentWide: {
    alignSelf: 'center',
    width: 400,
    margin: 10,
  },
  contentMobile: {
    flex: 1,
    margin: 10,
  },
  main: {
    marginBottom: 15,
  },
});

export default HardwareDetailsComponent;

