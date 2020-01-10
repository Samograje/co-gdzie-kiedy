import React from 'react';
import {
  ActivityIndicator,
  Button,
  ScrollView,
  StyleSheet,
  Text,
  TextInput,
  View,
} from 'react-native';
import {mainColor} from '../../constValues';
import ErrorElement from '../ui/ErrorElement';

const AffiliationDetailsComponent = (props) => {
  const {
    mode,
    error,
    errors,
    isLoading,
    isSubmitting,
    isWide,
    onSubmit,
    onReject,
    onChange,
    onLayout,
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
    <View style={[styles.main, isWide && styles.row]}>
      <View style={styles.inputField}>
        <Text style={styles.label}>Imię</Text>
        <TextInput
          style={styles.input}
          placeholder="Wprowadź imię"
          onChangeText={(text) => onChange('firstName', text)}
          editable={!isSubmitting}
        />
        {errors.firstName && (
          <Text style={styles.validationError}>{errors.firstName}</Text>
        )}
      </View>
      <View style={styles.inputField}>
        <Text style={styles.label}>Nazwisko</Text>
        <TextInput
          style={styles.input}
          placeholder="Wprowadź nazwisko"
          onChangeText={(text) => onChange('lastName', text)}
          editable={!isSubmitting}
        />
        {errors.lastName && (
          <Text style={styles.validationError}>{errors.lastName}</Text>
        )}
      </View>
      <View style={styles.inputField}>
        <Text style={styles.label}>Lokalizacja</Text>
        <TextInput
          style={styles.input}
          placeholder="Wprowadź lokalizację"
          onChangeText={(text) => onChange('location', text)}
          editable={!isSubmitting}
        />
        {errors.location && (
          <Text style={styles.validationError}>{errors.location}</Text>
        )}
      </View>

    </View>
  );

  const footer = (
    <View style={styles.footer}>
      <View style={styles.buttonContainer}>
        <Button
          title="Zapisz"
          onPress={onSubmit}
          color={mainColor}
          disabled={isLoading || isSubmitting}
        />
      </View>
      <View style={styles.buttonContainer}>
        <Button
          title="Wróć"
          onPress={onReject}
          color="darkgrey"
          disabled={isSubmitting}
        />
      </View>
    </View>
  );

  const spinner = (
    <ActivityIndicator
      size="large"
      color={mainColor}
    />
  );

  return (
    <ScrollView>
      <View style={styles.container} onLayout={onLayout}>
        {header}
        {!isLoading && main}
        {isLoading && spinner}
        {error && (
          <ErrorElement
            message="Nie udało się pobrać danych z serwera"
            type="error"
          />
        )}
        {footer}
        {isSubmitting && spinner}
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: 5,
  },
  header: {
    margin: 5,
    paddingBottom: 10,
    marginBottom: 15,
    borderBottomWidth: 1,
    borderBottomColor: mainColor,
  },
  headerText: {
    fontSize: 24,
  },
  main: {
    marginBottom: 15,
  },
  inputField: {
    flex: 1,
    margin: 5,
  },
  label: {
    fontSize: 16,
    fontWeight: '500',
    marginBottom: 2,
  },
  input: {
    fontSize: 16,
    padding: 2,
    borderWidth: 1,
    borderRadius: 2,
  },
  validationError: {
    color: 'darkred',
  },
  footer: {
    flexDirection: 'row',
  },
  buttonContainer: {
    margin: 2,
  },
  row: {
    flexDirection: 'row',
  },
});

export default AffiliationDetailsComponent;

