import React from 'react';
import {ActivityIndicator, ScrollView, StyleSheet, View} from 'react-native';
import ErrorElement from "../ui/ErrorElement";
import ResponsiveTable from "../ui/ResponsiveTable";

const HardwareListComponent = (props) => {

  const {
    loading,
    error,
    items,
    totalElements,
    onFetchData,
    columns,
    itemActions,
    footerActions,
  } = props;

  return (
    <ScrollView>
      <View style={styles.container}>
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
            footerActions={footerActions}
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
  }
});

export default HardwareListComponent;
