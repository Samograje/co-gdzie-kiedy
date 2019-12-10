import React from 'react';
import {ActivityIndicator, Button, StyleSheet, View} from 'react-native';
import ResponsiveTable from "../ui/ResponsiveTable";
import ErrorElement from "../ui/ErrorElement";

const AffiliationsListComponent = (props) => {
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
      <Button
        title="Dodaj przynależność"
        onPress={footerActions[0].onClick}
      />
      <Button
        title="Testuj edycję przynależności"
        onPress={() => itemActions[0].onClick({id: 5})}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {},
});

export default AffiliationsListComponent;
