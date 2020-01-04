import React from 'react';
import {ActivityIndicator, Button, ScrollView, StyleSheet, View} from 'react-native';
import ErrorElement from "../ui/ErrorElement";
import ResponsiveTable from "../ui/responsivetable/ResponsiveTable";
import {mainColor} from "../../constValues";

const HardwareListComponent = (props) => {

  const {
    loading,
    error,
    items,
    totalElements,
    onFetchData,
    columns,
    itemActions,
    groupActions,
  } = props;

  return (
    <ScrollView>
      <View style={styles.container}>
        {groupActions && (
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
        )}
        {loading && (
          <ActivityIndicator size="large"/>
        )}
        {error && (
          <ErrorElement
            message="Nie udało się pobrać danych z serwera"
            type="error"
          />
        )}
        {!loading && !error && (
          <ResponsiveTable
            items={items}
            totalElements={totalElements}
            onFetchData={onFetchData}
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
    flex: 1
  },
  responseText: {
    flex: 1,
    flexDirection: 'row'
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

export default HardwareListComponent;
