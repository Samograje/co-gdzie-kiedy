import React from 'react';
import {ActivityIndicator, Button, ScrollView, StyleSheet, Text, TextInput, View,} from 'react-native';
import MultiSelect from "../ui/form/MultiSelect";
import AutoComplete from "../ui/form/AutoComplete";

const ComputerSetDetailsComponent = (props) => {
  let modeInfo;
  if (props.mode === 'edit')
    modeInfo = "edycji";
  else if (props.mode === 'create')
    modeInfo = "dodawania";

  return (
      <ScrollView>
        <View style={styles.addForm}>
          <Text style={styles.header}>Formularz {modeInfo} zestawu komputerowego</Text>
          <Text>Pola z * są obowiązkowe.</Text>

          {(props.loadingAffiliations || props.loadingHardware || props.loadingSoftware) && (
              <View style={styles.indicator}>
                <ActivityIndicator size="large"/>
              </View>
          )}

          {!(props.loadingAffiliations || props.loadingHardware || props.loadingSoftware) && (
              <>
                <View>
                  <Text style={styles.labelText}>Nazwa zestawu komputerowego:</Text>
                  <TextInput style={styles.textInput}
                             placeholder={"Wprowadź nazwę zestawu"}
                             value={props.name}
                             onChangeText={(name) => props.setName(name)}
                  />
                </View>

                <View>
                  <Text style={styles.labelText}>Przynależność:</Text>
                  <AutoComplete
                      value={props.affiliationID}
                      updateValue={props.setAffiliationID}
                      options={props.dataSourceAffiliations.items}
                      updateOptions={props.updateAffiliations}
                  />
                </View>

                <View>
                  <Text style={styles.labelText}>Sprzęty:</Text>
                  <MultiSelect
                    values = {props.hardwareIDs}
                    onAddValue = {props.setHardwareIDs}
                    onRemoveValue = {props.hardwareIDs}
                    options={props.dataSourceHardware.items}
                    onUpdateOptions = {props.updateHardware}
                  />

                </View>

              </>
          )}

          <Button
              title="Zapisz"
              onPress={props.onSubmit}
              disabled={props.isInvalid}
          />
          <Button
              title="Wróć"
              onPress={props.onReject}
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
  labelText: {
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
  textInput: {
    marginBottom: 10,
    width: '100%',
    height: 35,
    borderColor: '#009000',
    borderWidth: 1.2,
    padding: 2,
    borderRadius: 7,
    fontWeight: '500',
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

export default ComputerSetDetailsComponent;
