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
import CgkFormFooter from '../ui/form/CgkFormFooter';
import CgkLabelAndValidation from '../ui/form/CgkLabelAndValidation';
import CgkTextInput from '../ui/form/CgkTextInput';

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

      <CgkLabelAndValidation
        label="Imię"
        errors={errors.firstName}
      >
        <CgkTextInput
          placeholder="Wprowadź imię"
          disabled={isSubmitting}
          text={data.firstName}
          onChangeText={(text) => onChange('firstName', text)}
        />
      </CgkLabelAndValidation>

      <CgkLabelAndValidation
        label="Nazwisko"
        errors={errors.lastName}
      >
        <CgkTextInput
          placeholder="Wprowadź nazwisko"
          disabled={isSubmitting}
          text={data.lastName}
          onChangeText={(text) => onChange('lastName', text)}
        />
      </CgkLabelAndValidation>

      <CgkLabelAndValidation
        label="Lokalizacja"
        errors={errors.location}
      >
        <CgkTextInput
          placeholder="Wprowadź lokalizację"
          disabled={isSubmitting}
          text={data.location}
          onChangeText={(text) => onChange('location', text)}
        />
      </CgkLabelAndValidation>
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
        <CgkFormFooter
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

