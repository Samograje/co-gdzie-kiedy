import React from 'react';
import {ScrollView, StyleSheet, Text, View,} from 'react-native';
import AutoComplete from '../ui/form/AutoComplete';
import CgkActivityIndicator from '../ui/CgkActivityIndicator';
import CgkFormFooter from '../ui/form/CgkFormFooter';
import CgkFormHeader from '../ui/form/CgkFormHeader';
import CgkLabelAndValidation from '../ui/form/CgkLabelAndValidation';
import CgkTextInput from '../ui/form/CgkTextInput';
import MultiSelect from "../ui/form/MultiSelect";

const ComputerSetDetailsComponent = (props) => {

  const {
    isLoading,
  } = props;

  let modeInfo;
  if (props.mode === 'edit')
    modeInfo = "edycji";
  else if (props.mode === 'create')
    modeInfo = "dodawania nowego";

  return (
      <ScrollView>
        <View style={styles.addForm}>
          <CgkFormHeader text={`Formularz ${modeInfo} zestawu komputerowego.`}/>
          <Text>Pola z * są obowiązkowe.</Text>
          {!isLoading}
          {isLoading && (
            <CgkActivityIndicator/>
          )}
          {(props.loadingAffiliations || props.loadingHardware || props.loadingSoftware) && (
              <View style={styles.indicator}>
                <CgkActivityIndicator/>
              </View>
          )}

          {!(props.loadingAffiliations || props.loadingHardware || props.loadingSoftware) && (
              <>

                <CgkLabelAndValidation label="* Nazwa zestawu komputerowego:">
                  <CgkTextInput
                      placeholder="Wprowadź nazwę zestawu komputerowego"
                      text={props.name}
                      onChangeText={(name) => props.setName(name)}
                  />
                </CgkLabelAndValidation>

                <CgkLabelAndValidation label="* Przynależność:">
                  <AutoComplete
                      value={props.affiliationID}
                      updateValue={props.setAffiliationID}
                      options={props.dataSourceAffiliations.items}
                      updateOptions={props.updateAffiliations}
                  />
                </CgkLabelAndValidation>

                <CgkLabelAndValidation label="* Sprzęty:">
                  <MultiSelect
                      values={props.hardwareIDs}
                      onAddValue={props.onAddHardwareValues}
                      onRemoveValue={props.onRemoveHardwareValues}
                      options={props.dataSourceHardware.items}
                      onUpdateOptions={props.updateHardware}
                  />
                </CgkLabelAndValidation>

                <CgkLabelAndValidation label="* Oprogramowanie:">
                  <MultiSelect
                      values={props.softwareIDs}
                      onAddValue={props.onAddSoftwareValues}
                      onRemoveValue={props.onRemoveSoftwareValues}
                      options={props.dataSourceSoftware.items}
                      onUpdateOptions={props.updateSoftware}
                  />
                </CgkLabelAndValidation>

              </>
          )}

          <CgkFormFooter
              isSubmitDisabled={props.isInvalid}
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
  indicator: {
    flex: 1,
    paddingTop: 20,
    paddingBottom: 20,
  },
});

export default ComputerSetDetailsComponent;

