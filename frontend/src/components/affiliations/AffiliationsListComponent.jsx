import React from 'react';
import {Button, StyleSheet, Text} from 'react-native';

const AffiliationsListComponent = (props) => {
  return (
    <>
      <Text>Tutaj znajdzie się lista przynależności</Text>
      <Button
        title="Dodaj przynależność"
        onPress={props.onCreate}
      />
      <Button
        title="Edytuj przynależność"
        onPress={props.onEdit}
      />
    </>
  );
};

const styles = StyleSheet.create({

});

export default AffiliationsListComponent;
