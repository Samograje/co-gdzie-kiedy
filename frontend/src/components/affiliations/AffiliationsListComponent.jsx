import React from 'react';
import {Button, StyleSheet, Text, View} from 'react-native';

const AffiliationsListComponent = ({loading, error, items, totalElements, onFetchData, columns, itemActions, footerActions}) => {
  return (
    <View style={styles.container}>
      <Text>Tutaj znajdzie się lista przynależności</Text>
      <Text>Loading - informacja o tym, czy dane są właśnie ładowane</Text>
      <Text>Error - komunikat ewentualnego błędu</Text>
      <Text>items - dane do wyświetlenia w tabeli</Text>
      <Text>totalElements - ilość wszystkich rekordów w bazie</Text>
      <Text>onFetchData - funkcja aktualizująca dane w kontenerze</Text>
      <Text>
        columns - tablica informacji o kolumnach do wyświetlenia - łączy nazwę kolumny z kluczem w obiekcie json
      </Text>
      <Text>itemActions - tablica akcji dostępnych dla pojedynczego elementu</Text>
      <Text>footerActions - tablica akcji dostępnych dla całej tabeli</Text>

      <Text>Dane:</Text>
      {items.map(item => (
        <Text>{item.name}</Text>
      ))}

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
