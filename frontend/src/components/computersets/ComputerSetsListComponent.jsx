import React from 'react';
import {Platform, ScrollView, StyleSheet, View,} from 'react-native';
import ResponsiveTable from '../ui/responsivetable/ResponsiveTable';
import ErrorElement from '../ui/ErrorElement';
import ScreenHeader from '../ui/ScreenHeader';

const ComputerSetsListComponent = (props) => {

  const {
    loading,
    error,
    items,
    totalElements,
    onFilterChange,
    columns,
    itemActions,
    groupActions,
  } = props;

  return (
    <ScrollView>
      <View style={styles.container}>
        {Platform.OS === 'web' && (
            <ScreenHeader title="Lista zestawów komputerowych" gActions={groupActions}/>
        )}
        {/* {groupActions && (
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

export default ComputerSetsListComponent;
