import React from 'react';
import {Button, StyleSheet, Text, View, TextInput, Picker} from 'react-native';

const HardwareDetailsComponent = (props) => {
  let modeInfo;
  if (props.mode === 'edit')
    modeInfo = "edycji";
  else if (props.mode === 'create')
    modeInfo = "dodawania nowego";
  else
    return "";

  return (
      <View style={styles.addform}>
        <Text style={styles.header}>Formularz {modeInfo} sprzętu.</Text>
        <View style={styles.onelineelement}>
          <Text style={styles.labeltext}>Nazwa sprzętu:</Text>
          <TextInput style={styles.textinput}
                     placeholder={"np. HD 7780"}
                     onChangeText={(name) => props.setName(name)}
                     value={props.name}
          />
        </View>
        <View style={styles.onelineelement}>
          <Text style={styles.labeltext}>Typ:</Text>
          <TextInput style={styles.textinput}
                     placeholder={"np. Karta sieciowa"}
              //TODO: dropdown na typ z Dictionary
                     onChangeText={(dictionaryID) => props.setDictionaryID(dictionaryID)}
                     value={props.dictionaryID}
          />
        </View>
        <Text style={styles.labeltext}>Przynależność:</Text>
        <Picker
            onValueChange={(itemValue, itemIndex) => props.setAffiliationID({affiliationID: itemValue})}>
          {
            //TODO: jakby ten dataSource był nullem?
            props.dataSourceAffiliations.items.map((item, key) => (
                <Picker.Item label={item.name} value={item.name} key={key}/>)
            )
          }
        </Picker>

        <View style={styles.onelineelement}>
          <Text style={styles.labeltext}>W zestawie komputerowym:</Text>
          <TextInput style={styles.textinput}
                     placeholder={"Nazwa zestawu komputerowego. "}
              //TODO: dropdown na zestaw komputerowy
                     onChangeText={(computerSetID) => props.setComputerSetID(computerSetID)}
                     value={props.computerSetID}
          />
        </View>
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
  addform: {
    alignSelf: 'center',
    padding: 15,
  },
  labeltext: {
    marginRight: 10,
  },
  header: {
    fontSize: 24,
    paddingBottom: 10,
    marginBottom: 40,
    borderBottomColor: '#199187',
    borderBottomWidth: 1,
  },
  textinput: {},
  onelineelement: {
    flexDirection: 'row',
    marginBottom: 15,
  }
});

export default HardwareDetailsComponent;

