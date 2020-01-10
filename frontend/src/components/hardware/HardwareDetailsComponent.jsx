import React from 'react';
import {Button, ScrollView, StyleSheet, Text, View, TextInput, Picker, ActivityIndicator} from 'react-native';

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
                <View>
                  <Text style={styles.labelText}>* Nazwa sprzętu:</Text>
                  <TextInput style={styles.textInput}
                             placeholder={"Wprowadź nazwę sprzętu"}
                             value={props.name}
                             onChangeText={(name) => props.setName(name)}
                  />
                </View>

                <View>
                  <Text style={styles.labelText}>* Typ:</Text>
                  <Picker
                      style={styles.picker}
                      onValueChange={(itemValue, itemIndex) => props.setDictionaryID(itemValue)}>
                    <Picker.Item label="Wybierz" value={''}/>
                    {
                      props.dataSourceDictionary.map((item, key) => (
                              <Picker.Item label={item.value} value={item.id} key={key}/>
                          )
                      )
                    }
                  </Picker>
                </View>

                <View>
                  <Text style={styles.labelText}>* Przynależność:</Text>
                  <TextInput style={styles.textInput}
                             placeholder={"Filtr do wyszukiwania"}
                      // onChangeText={/*TODO: onChangeText funkcja do dropdown*/}
                  />
                  <Picker
                      style={styles.picker}
                      onValueChange={(itemValue, itemIndex) => props.setAffiliationID(itemValue)}>
                    <Picker.Item label="Wybierz" value={''}/>
                    {
                      props.dataSourceAffiliations.items.map((item, key) => (
                              <Picker.Item label={item.name} value={item.id} key={key}/>
                          )
                      )
                    }
                  </Picker>
                </View>

                <View>
                  <Text style={styles.labelText}>W zestawie komputerowym:</Text>
                  <TextInput style={styles.textInput}
                             placeholder={"Filtr do wyszukiwania"}
                      // onChangeText={/*TODO: onChangeText funkcja do dropdown*/}
                  />
                  <Picker
                      style={styles.picker}
                      onValueChange={(itemValue, itemIndex) => props.setComputerSetID(itemValue)}>
                    <Picker.Item label="--------" value={null}/>
                    {
                      props.dataSourceComputerSets.items.map((item, key) => (
                              <Picker.Item label={item.name} value={item.id} key={key}/>
                          )
                      )
                    }
                  </Picker>
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
  picker: {
    marginBottom: 10,
    width: '100%',
    height: 35,
    borderColor: '#009000',
    borderWidth: 1.2,
    padding: 2,
    borderRadius: 7,
  }
});

export default HardwareDetailsComponent;

