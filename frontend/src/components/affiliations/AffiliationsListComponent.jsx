import React from 'react';
import {ActivityIndicator, Button, ScrollView, StyleSheet, View} from 'react-native';
import ResponsiveTable from "../ui/responsivetable/ResponsiveTable";
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
        <Button
          title="Dodaj przynależność"
          onPress={footerActions[0].onClick}
        />
        <Button
          title="Testuj edycję przynależności"
          onPress={() => itemActions[0].onClick({id: 5})}
        />
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {},
});

export default AffiliationsListComponent;
