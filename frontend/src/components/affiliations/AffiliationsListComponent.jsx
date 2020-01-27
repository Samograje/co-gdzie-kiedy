import React from 'react';
import {Platform, ScrollView, StyleSheet, View,} from 'react-native';
import ResponsiveTable from '../ui/responsivetable/ResponsiveTable';
import ErrorElement from '../ui/ErrorElement';
import DecisionDialog from "../ui/dialogs/DecisionDialog";
import ScreenHeader from '../ui/ScreenHeader';

const AffiliationsListComponent = (props) => {
  const {
    loading,
    error,
    items,
    totalElements,
    onFilterChange,
    columns,
    itemActions,
    groupActions,
    isDialogOpened,
    dialogHandleConfirm,
    dialogHandleReject,
  } = props;

  return (
    <>
      {isDialogOpened && (
        <DecisionDialog
          headerText="Uwaga!"
          text="Czy na pewno chcesz usunąć osobę / miejsce?"
          onConfirmText="Tak"
          onConfirm={dialogHandleConfirm}
          onRejectText="Nie"
          onReject={dialogHandleReject}
        />
      )}
      <ScrollView scrollEnabled={!isDialogOpened}>
        <View
          style={styles.container}
          pointerEvents={isDialogOpened ? 'none' : null}
        >
          {Platform.OS === 'web' && (
              <ScreenHeader title="Lista osób / miejsc" gActions={groupActions}/>
          )}
            {/*{groupActions && (
            <View style={styles.groupActions}>
              {groupActions.map((action, idx) => (
                <View style={styles.buttonContainer} key={idx}>
                  <Button
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
  groupActions: {
    flex: 1,
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
  buttonContainer: {
    margin: 5,
  },
});

export default AffiliationsListComponent;
