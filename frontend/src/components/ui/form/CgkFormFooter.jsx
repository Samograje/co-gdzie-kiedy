import React from 'react';
import {Button, StyleSheet, View} from 'react-native';
import {mainColor} from '../../../constValues';

const CgkFormFooter = (props) => {
  const {
    isSubmitDisabled, // bool informujący o tym, czy przycisk 'Zapisz' ma być niedostępny
    isRejectDisabled, // bool informujący o tym, czy przycisk 'Wróć' ma być niedostępny
    isDeleteDisabled, // bool informujący o tym, czy przycisk 'Usuń' ma być niedostępny
    onSubmit, // funkcja bez argumentów, wykonywana po naciśnięciu przycisku 'Zapisz'
    onReject, // funkcja bez argumentów, wykonywana po naciśnięciu przycisku 'Wróć'
    onDelete, // funkcja bez argumentów, wykonywana po naciśnięciu przycisku 'Usuń'
  } = props;

  return (
    <View style={styles.footer}>
      <View style={styles.buttonContainer}>
        <Button
          title="Zapisz"
          onPress={onSubmit}
          color={mainColor}
          disabled={isSubmitDisabled}
        />
      </View>
      <View style={styles.buttonContainer}>
        <Button
          title="Wróć"
          onPress={onReject}
          color={mainColor}
          disabled={isRejectDisabled}
        />
      </View>
      <View style={styles.buttonContainer}>
        <Button
          title="Usuń"
          onPress={onDelete}
          color={mainColor}
          disabled={isDeleteDisabled}
        />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  footer: {
    flexDirection: 'row',
  },
  buttonContainer: {
    margin: 2,
  },
});

export default CgkFormFooter;
