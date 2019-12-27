import React from 'react';
import {Button,
        StyleSheet,
        Text,
        View,
        TextInput,
        } from 'react-native';

const SoftwareDetailsComponent = (props) => {
  return (
    <View style={styles.addform}>
      <Text style={styles.header}>Formularz dodawania lub edycji oprogramowania</Text>
        <View style={styles.onelineelement}>
            <Text style={styles.labeltext}>Nazwa oprogramowania:</Text>
            <TextInput style={styles.textinput}
                       placeholder={"np. Mathematica"}
                       onChangeText={(name) => props.setName(name)}
            />
        </View>
        <View style={styles.onelineelement}>
            <Text style={styles.labeltext}>Klucz produktu:</Text>
            <TextInput style={styles.textinput}
                       placeholder={"np. T847-54GF-7845-FSF5"}
                       onChangeText={(key) => props.setKey(key)}
            />
        </View>
        <View style={styles.onelineelement}>
            <Text style={styles.labeltext}>Ilość dostępnych kluczy:</Text>
            <TextInput style={styles.textinput}
                       placeholder={"np. 5"}
                       onChangeText={(availableKeys) => props.setAvailableKeys(availableKeys)}
            />
        </View>
        <View style={styles.onelineelement}>
            <Text style={styles.labeltext}>Ważna do:</Text>
            <TextInput style={styles.textinput}
                       placeholder={"Data końca licencji. "}
                       onChangeText={(duration) => props.setDuration(duration)}
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
        backgroundColor: '#f4f4f4',
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
    textinput: {

    },
    onelineelement: {
        flexDirection: 'row',
        marginBottom: 15,
    }
});

export default SoftwareDetailsComponent;

