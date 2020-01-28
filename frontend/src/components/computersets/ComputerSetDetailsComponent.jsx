import React from 'react';
import {ScrollView, StyleSheet, Text, View,} from 'react-native';
import AutoComplete from '../ui/form/AutoComplete';
import CgkActivityIndicator from '../ui/CgkActivityIndicator';
import CgkFormFooter from '../ui/form/CgkFormFooter';
import CgkFormHeader from '../ui/form/CgkFormHeader';
import CgkLabelAndValidation from '../ui/form/CgkLabelAndValidation';
import CgkTextInput from '../ui/form/CgkTextInput';
import MultiSelect from '../ui/form/MultiSelect';
import SuccessElement from '../ui/SuccessElement';

const ComputerSetDetailsComponent = (props) => {

  const {
    isLoading,
    isWide,
    isGrowlVisible,
  } = props;

  let modeInfo;
  if (props.mode === 'edit')
    modeInfo = "edycji";
  else if (props.mode === 'create')
    modeInfo = "dodawania nowego";

  return (
    <ScrollView>
      <View style={isWide ? styles.contentWide : styles.contentMobile}>
        <CgkFormHeader text={`Formularz ${modeInfo} zestawu komputerowego.`}/>
        <Text>Pola z * są obowiązkowe.</Text>

        {isLoading && (
          <View style={styles.indicator}>
            <CgkActivityIndicator/>
          </View>
        )}
        {!isLoading && (
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

            <CgkLabelAndValidation label="Sprzęty:">
              <MultiSelect
                values={props.hardwareIDs}
                onAddValue={props.onAddHardwareValues}
                onRemoveValue={props.onRemoveHardwareValues}
                options={props.dataSourceHardware.items}
                onUpdateOptions={props.updateHardware}
              />
            </CgkLabelAndValidation>

            <CgkLabelAndValidation label="Oprogramowanie:">
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

        {isGrowlVisible && (
          <SuccessElement text="Zapisano osobę / miejsce"/>
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
  indicator: {
    flex: 1,
    paddingTop: 20,
    paddingBottom: 20,
  },
});

export default ComputerSetDetailsComponent;

