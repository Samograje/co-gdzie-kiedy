import React from 'react';
import {
  Button,
  ScrollView,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import {mainColor} from '../../constValues';

const AffiliationDetailsComponent = (props) => {
  const {
    onSubmit,
    onReject,
    mode,
  } = props;

  const header = (
    <View style={styles.header}>
      <Text style={styles.headerText}>
        {mode === 'create' && 'Dodawanie osoby / miejsca'}
        {mode === 'edit' && 'Edycja osoby / miejsca'}
      </Text>
    </View>
  );

  const main = (
    <View style={styles.main}>
      Główna część formularza
    </View>
  );

  const footer = (
    <View style={styles.footer}>
      <Button
        title="Zapisz"
        onPress={onSubmit}
        color={mainColor}
      />
      <Button
        title="Wróć"
        onPress={onReject}
        color={mainColor}
      />
    </View>
  );

  return (
    <ScrollView>
      <View style={styles.container}>
        {header}
        {main}
        {footer}
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {

  },
  header: {
    flex: 1,
  },
  headerText: {
    fontSize: 20,
  },
  main: {
    flex: 1,
  },
  footer: {
    flex: 1,
  },
});

export default AffiliationDetailsComponent;

