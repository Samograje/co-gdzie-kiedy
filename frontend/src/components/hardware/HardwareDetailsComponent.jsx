import React from 'react';
import {Button, StyleSheet, Text, View, TextInput, Picker, ActivityIndicator} from 'react-native';

const HardwareDetailsComponent = (props) => {
  let modeInfo;
  if (props.mode === 'edit')
    modeInfo = "edycji";
  else if (props.mode === 'create')
    modeInfo = "dodawania nowego";

  return (
      <View style={styles.addForm}>
        <Text style={styles.header}>Formularz {modeInfo} sprzętu.</Text>

        {props.loading && (
            <View style={{flex: 1, paddingTop: 20}}>
              <ActivityIndicator/>
            </View>
        )}

        {!props.loading && (
            <>
              <View style={styles.oneLineElement}>
                <Text style={styles.labelText}>Nazwa sprzętu:</Text>
                <TextInput style={styles.textInput}
                           placeholder={"np. HD 7780"}
                           onChangeText={(name) => props.setName(name)}
                           value={props.name}
                />
              </View>
              <View style={styles.oneLineElement}>
                <Text style={styles.labelText}>Typ:</Text>
                <TextInput style={styles.textInput}
                           placeholder={"np. Karta sieciowa"}
                    //TODO: dropdown na typ z Dictionary
                           onChangeText={(dictionaryID) => props.setDictionaryID(dictionaryID)}
                           value={props.dictionaryID}
                />
              </View>

              <View style={styles.oneLineElement}>
                <Text style={styles.labelText}>Przynależność:</Text>
                <Picker
                    onValueChange={(itemValue, itemIndex) => props.setAffiliationID(itemValue)}>
                  <Picker.Item label="--------" value={null}/>
                  {
                    props.dataSourceAffiliations.items.map((item, key) => (
                            <Picker.Item label={item.name} value={item.id} key={key}/>
                        )
                    )
                  }
                </Picker>
              </View>

              <View style={styles.oneLineElement}>
                <Text style={styles.labelText}>W zestawie komputerowym:</Text>
                <Picker
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
        />
        <Button
            title="Wróć"
            onPress={props.onReject}
        />
      </View>
  );
};

const styles = StyleSheet.create({
  addForm: {
    alignSelf: 'center',
    padding: 15,
  },
  labelText: {
    marginRight: 10,
  },
  header: {
    fontSize: 24,
    paddingBottom: 10,
    marginBottom: 40,
    borderBottomColor: '#199187',
    borderBottomWidth: 1,
  },
  textInput: {},
  oneLineElement: {
    flexDirection: 'row',
    marginBottom: 15,
  }
});

export default HardwareDetailsComponent;

