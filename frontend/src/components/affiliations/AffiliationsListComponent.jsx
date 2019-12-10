import React from 'react';
import {Button, StyleSheet, Text, View} from 'react-native';
import ResponsiveTable from "../ui/ResponsiveTable";

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
      <Text>Tutaj znajdzie się lista przynależności</Text>

      <Text>Dane:</Text>
      {items.map(item => (
        <Text>{item.name}</Text>
      ))}

      <Text>Tabela:</Text>
      <ResponsiveTable
        items={items}
        totalElements={totalElements}
        onFetchData={onFetchData}
        columns={columns}
        itemActions={itemActions}
        footerActions={footerActions}
      />

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
