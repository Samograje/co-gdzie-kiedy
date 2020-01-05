import React from 'react';
import {ActivityIndicator, Button, ScrollView, StyleSheet, View} from 'react-native';
import ErrorElement from "../ui/ErrorElement";
import ResponsiveTable from "../ui/responsivetable/ResponsiveTable";

const SoftwareListComponent = (props) => {
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
            onFetchData={onFetchData}
            columns={columns}
            itemActions={itemActions}
          />
        )}
        <Button
            title="Dodaj oprogramowanie"
            onPress={footerActions[0].onClick}
        />
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

export default SoftwareListComponent;
