import React from 'react';
import {Platform, ScrollView, StyleSheet, View,} from 'react-native';
import ErrorElement from '../ui/ErrorElement';
import DecisionDialog from '../ui/dialogs/DecisionDialog';
import ResponsiveTable from '../ui/responsivetable/ResponsiveTable';
import ScreenHeader from '../ui/ScreenHeader';

const SoftwareListComponent = (props) => {
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
          text="Czy na pewno chcesz usunąć oprogramowanie?"
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
              <ScreenHeader title="Lista oprogramowania" gActions={groupActions}/>
          )}
          {/*{groupActions && (
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
          )}*/}
          {error && (
            <ErrorElement
              message="Nie udało się pobrać danych z serwera"
              type="error"
            />
          )}
          {!error && (
            <ResponsiveTable
              items={items}
              totalElements={totalElements}
              loading={loading}
              onFilterChange={onFilterChange}
              columns={columns}
              itemActions={itemActions}
            />
          )}
        </View>
      </ScrollView>
    </>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  responseText: {
    flex: 1,
    flexDirection: 'row',
  },
  groupActions: {
    flex: 1,
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
  buttonContainer: {
    margin: 5,
  },
});

export default SoftwareListComponent;
