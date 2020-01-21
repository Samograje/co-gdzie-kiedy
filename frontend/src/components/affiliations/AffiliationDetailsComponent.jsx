import React from 'react';
import {
  ActivityIndicator,
  ScrollView,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import {mainColor} from '../../constValues';
import ErrorElement from '../ui/ErrorElement';
import FormFooter from '../ui/form/FormFooter';
import FormField from '../ui/form/FormField';

const AffiliationDetailsComponent = (props) => {
  const {
    data,
    mode,
    error,
    errors,
    isLoading,
    isSubmitting,
    isWide,
    onSubmit,
    onReject,
    onChange,
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
      <FormField
        label="Imię"
        placeholder="Wprowadź imię"
        disabled={isSubmitting}
        text={data.firstName}
        onChangeText={(text) => onChange('firstName', text)}
        errors={errors.firstName}
      />
      <FormField
        label="Nazwisko"
        placeholder="Wprowadź nazwisko"
        disabled={isSubmitting}
        text={data.lastName}
        onChangeText={(text) => onChange('lastName', text)}
        errors={errors.lastName}
      />
      <FormField
        label="Lokalizacja"
        placeholder="Wprowadź lokalizację"
        disabled={isSubmitting}
        text={data.location}
        onChangeText={(text) => onChange('location', text)}
        errors={errors.location}
      />
    </View>
  );

  const spinner = (
    <ActivityIndicator
      size="large"
      color={mainColor}
    />
  );

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <View style={isWide ? styles.contentWide : styles.contentMobile}>
        {header}
        {!isLoading && main}
        {isLoading && spinner}
        {error && (
          <ErrorElement
            message="Nie udało się pobrać danych z serwera"
            type="error"
          />
        )}
        <FormFooter
          isSubmitDisabled={isLoading || isSubmitting}
          isRejectDisabled={isSubmitting}
          onSubmit={onSubmit}
          onReject={onReject}
        />
        {isSubmitting && spinner}
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    justifyContent: 'center',
  },
  contentWide: {
    width: 400,
    margin: 10,
  },
  contentMobile: {
    flex: 1,
    margin: 10,
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
  row: {
    flexDirection: 'row',
  },
});

export default AffiliationDetailsComponent;

