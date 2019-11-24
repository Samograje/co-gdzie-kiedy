import React from 'react';
import {Button, StyleSheet, Text} from 'react-native';

const AffiliationDetailsComponent = (props) => {
  return (
    <>
      <Text>Tutaj znajdzie się formularz przynależności</Text>
      <Button
        title="Zapisz"
        onPress={props.onSubmit}
      />
      <Button
        title="Wróć"
        onPress={props.onReject}
      />
    </>
  );
};

const styles = StyleSheet.create({

});

export default AffiliationDetailsComponent;

