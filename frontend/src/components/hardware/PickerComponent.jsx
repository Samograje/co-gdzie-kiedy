import React from 'react';
import {Picker, StyleSheet} from 'react-native';

const PickerComponent = (props) => {
  return (
        <Picker
            style={styles.picker}
            onValueChange={(itemValue, itemIndex) => props.setValue(itemValue)}>
          <Picker.Item label="--------" value={''}/>
          {
            props.dataSource.items.map((item, key) => (
                    <Picker.Item label={item.name} value={item.id} key={key}/>
                )
            )
          }
        </Picker>
  );
};

const styles = StyleSheet.create({
  picker: {
    marginBottom: 10,
    width: '100%',
    height: 35,
    borderColor: '#009000',
    borderWidth: 1.2,
    padding: 2,
    borderRadius: 7,
    fontWeight: '500',
  }
});

export default PickerComponent;