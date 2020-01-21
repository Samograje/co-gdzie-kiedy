import React from 'react';
import {
  ActivityIndicator,
  ScrollView,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import AutoComplete from '../ui/form/AutoComplete';
import PickerWithItems from '../ui/form/PickerWithItems';
import CgkFormFooter from '../ui/form/CgkFormFooter';
import CgkLabelAndValidation from '../ui/form/CgkLabelAndValidation';
import CgkTextInput from '../ui/form/CgkTextInput';

const HardwareDetailsComponent = (props) => {
  let modeInfo;
  if (props.mode === 'edit')
    modeInfo = "edycji";
  else if (props.mode === 'create')
    modeInfo = "dodawania nowego";

  return (
      <ScrollView>
        <View style={styles.addForm}>
          <Text style={styles.header}>Formularz {modeInfo} sprzętu.</Text>
          <Text>Pola z * są obowiązkowe.</Text>

          {(props.loadingAffiliations || props.loadingDictionary || props.loadingComputerSets) && (
              <View style={styles.indicator}>
                <ActivityIndicator size="large"/>
              </View>
          )}

          {!(props.loadingAffiliations || props.loadingDictionary || props.loadingComputerSets) && (
              <>

                <CgkLabelAndValidation
                  label="* Nazwa sprzętu:"
                >
                  <CgkTextInput
                    placeholder="Wprowadź nazwę sprzętu"
                    text={props.name}
                    onChangeText={(name) => props.setName(name)}
                  />
                </CgkLabelAndValidation>

                <View>
                  <Text style={styles.labelText}>* Typ:</Text>
                  <PickerWithItems
                    value={props.dictionaryID}
                    updateValue={props.setDictionaryID}
                    options={props.dataSourceDictionary}
                  />
                </View>

                <View>
                  <Text style={styles.labelText}>* Przynależność:</Text>
                  <AutoComplete
                      value={props.affiliationID}
                      updateValue={props.setAffiliationID}
                      options={props.dataSourceAffiliations.items}
                      updateOptions={props.updateAffiliations}
                  />
                </View>

                <View>
                  <Text style={styles.labelText}>W zestawie komputerowym:</Text>
                  <AutoComplete
                      value={props.computerSetID}
                      updateValue={props.setComputerSetID}
                      options={props.dataSourceComputerSets.items}
                      updateOptions={props.updateComputerSets}
                  />
                </View>
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
  header: {
    fontSize: 24,
    paddingBottom: 10,
    marginBottom: 25,
    borderBottomColor: '#199187',
    borderBottomWidth: 1,
  },
  oneLineElement: {
    flexDirection: 'row',
    marginBottom: 15,
  },
  indicator: {
    flex: 1,
    paddingTop: 20,
    paddingBottom: 20,
  },
});

export default HardwareDetailsComponent;

