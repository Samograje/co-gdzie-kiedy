import React from 'react';
import {
  ScrollView,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import CgkActivityIndicator from '../ui/CgkActivityIndicator';
import CgkFormFooter from '../ui/form/CgkFormFooter';
import CgkFormHeader from '../ui/form/CgkFormHeader';
import CgkLabelAndValidation from '../ui/form/CgkLabelAndValidation';
import CgkTextInput from '../ui/form/CgkTextInput';
import ErrorElement from '../ui/ErrorElement';
import SuccessElement from '../ui/SuccessElement';

const AffiliationDetailsComponent = (props) => {
  const {
    data,
    mode,
    error,
    validationError,
    isLoading,
    isSubmitting,
    isGrowlVisible,
    isWide,
    onSubmit,
    onReject,
    onChange,
  } = props;

  let headerText;
  if (mode === 'create') {
    headerText = 'Dodawanie osoby / miejsca';
  }
  if (mode === 'edit') {
    headerText = 'Edycja osoby / miejsca';
  }

  const main = (
    <View style={styles.main}>
      <Text>Należy wpisać wartość w co najmniej jedno pole tekstowe</Text>

      <CgkLabelAndValidation label="Imię">
        <CgkTextInput
          placeholder="Wprowadź imię"
          disabled={isSubmitting || props.isPreviewed}
          text={data.firstName}
          onChangeText={(text) => onChange('firstName', text)}
        />
      </CgkLabelAndValidation>

      <CgkLabelAndValidation label="Nazwisko">
        <CgkTextInput
          placeholder="Wprowadź nazwisko"
          disabled={isSubmitting || props.isPreviewed}
          text={data.lastName}
          onChangeText={(text) => onChange('lastName', text)}
        />
      </CgkLabelAndValidation>

      <CgkLabelAndValidation label="Lokalizacja">
        <CgkTextInput
          placeholder="Wprowadź lokalizację"
          disabled={isSubmitting || props.isPreviewed}
          text={data.location}
          onChangeText={(text) => onChange('location', text)}
        />
      </CgkLabelAndValidation>
    </View>
  );

  return (
    <ScrollView>
      <View style={isWide ? styles.contentWide : styles.contentMobile}>
        <CgkFormHeader text={headerText}/>
        {!isLoading && main}
        {isLoading && (
          <CgkActivityIndicator/>
        )}
        {error && (
          <ErrorElement
            message="Nie udało się pobrać danych z serwera"
            type="error"
          />
        )}
        <CgkFormFooter
          isSubmitDisabled={isLoading || isSubmitting || validationError || props.isPreviewed}
          isRejectDisabled={isSubmitting}
          isEditDisabled={!props.isPreviewed}
          onSubmit={onSubmit}
          onReject={onReject}
          onEdit={props.onEdit}
        />
        {isGrowlVisible && (
          <SuccessElement text="Zapisano osobę / miejsce"/>
        )}
        {isSubmitting && (
          <CgkActivityIndicator/>
        )}
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  contentWide: {
    alignSelf: 'center',
    width: 400,
    margin: 10,
  },
  contentMobile: {
    flex: 1,
    margin: 10,
  },
  main: {
    marginBottom: 15,
  },
});

export default AffiliationDetailsComponent;

