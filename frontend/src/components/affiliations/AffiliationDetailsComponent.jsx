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
import DecisionDialog from "../ui/dialogs/DecisionDialog";

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
    dialogOpened,
    dialogHandleConfirm,
    dialogHandleReject,
  } = props;

  let headerText;
    if (mode === 'create' || props.mode === 'copy') {
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
          disabled={isSubmitting}
          text={data.firstName}
          onChangeText={(text) => onChange('firstName', text)}
        />
      </CgkLabelAndValidation>

      <CgkLabelAndValidation label="Nazwisko">
        <CgkTextInput
          placeholder="Wprowadź nazwisko"
          disabled={isSubmitting}
          text={data.lastName}
          onChangeText={(text) => onChange('lastName', text)}
        />
      </CgkLabelAndValidation>

      <CgkLabelAndValidation label="Lokalizacja">
        <CgkTextInput
          placeholder="Wprowadź lokalizację"
          disabled={isSubmitting}
          text={data.location}
          onChangeText={(text) => onChange('location', text)}
        />
      </CgkLabelAndValidation>
    </View>
  );

  return (
    <ScrollView>
      {dialogOpened && (
          <DecisionDialog
              headerText="Uwaga!"
              text="Zmiany nie zostaną zapisane, czy chcesz kontynuować?"
              onConfirmText="Tak"
              onConfirm={dialogHandleConfirm}
              onRejectText="Nie"
              onReject={dialogHandleReject}
          />
      )}
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
          isSubmitDisabled={isLoading || isSubmitting || validationError}
          isRejectDisabled={isSubmitting}
          onSubmit={onSubmit}
          onReject={onReject}
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

