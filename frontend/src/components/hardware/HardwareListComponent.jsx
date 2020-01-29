import React from 'react';
import {Button, Platform, ScrollView, StyleSheet, View,} from 'react-native';
import ErrorElement from '../ui/ErrorElement';
import ResponsiveTable from '../ui/responsivetable/ResponsiveTable';
import {mainColor} from '../../constValues';
import DecisionDialog from "../ui/dialogs/DecisionDialog";
import ScreenHeader from '../ui/ScreenHeader';

const HardwareListComponent = (props) => {

  const {
    loading,
    error,
    items,
    totalElements,
    onFilterChange,
    columns,
    itemActions,
    groupActions,
    dialogOpened,
    dialogHandleConfirm,
    dialogHandleReject,
  } = props;

  return (
    <>
      {dialogOpened && (
        <DecisionDialog
          headerText="Uwaga!"
          text="Czy na pewno chcesz usunąć ten sprzęt?"
          onConfirmText="Tak"
          onConfirm={dialogHandleConfirm}
          onRejectText="Nie"
          onReject={dialogHandleReject}
        />
      )}

      <ScrollView scrollEnabled={!dialogOpened}>
        <View
          style={styles.container}
          pointerEvents={dialogOpened ? 'none' : null}
        >
          {Platform.OS === 'web' && (
            <ScreenHeader title="Lista sprzętów"/>
          )}
          {groupActions && (
            <View style={styles.groupActions}>
              {groupActions.map((action, idx) => (
                <View style={styles.buttonContainer} key={idx}>
                  <Button
                    disabled={action.disabled}
                    title={action.label}
                    onPress={action.onClick}
                    color={mainColor}
                  />
                </View>
              ))}
            </View>
          )}
          {error && (
            <ErrorElement
              message="Nie udało się pobrać danych z serwera"
              type="error"
            />
          )}
          <ResponsiveTable
            items={items}
            totalElements={totalElements}
            loading={loading}
            onFilterChange={onFilterChange}
            columns={columns}
            itemActions={itemActions}
          />
        </View>
      </ScrollView>
    </>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1
  },
  responseText: {
    flex: 1,
    flexDirection: 'row'
  },
  groupActions: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    // alignSelf: 'stretch',
  },
  buttonContainer: {
    margin: 5,
  },
});

export default HardwareListComponent;
