import React from 'react';
import {ActivityIndicator, StyleSheet, View} from 'react-native';
import StatisticsElement from "./StatisticsElement";
import LinkElement from "./LinkElement";
import ErrorElement from "../ui/ErrorElement";

const HomepageComponent = (
  {
    loading,
    error,
    affiliationsCount,
    hardwareCount,
    softwareCount,
    computerSetsCount,
    goToAffiliations,
    goToComputerSets,
    goToHardware,
    goToSoftware,
  }
) => {
  return (
    <View style={styles.container}>
      {/* TODO: responsywność elementów */}
      <View style={styles.links}>
        <LinkElement label="Osoby i miejsca" onClick={goToAffiliations}/>
        <LinkElement label="Zestawy komputerowe" onClick={goToComputerSets}/>
        <LinkElement label="Hardware'y" onClick={goToHardware}/>
        <LinkElement label="Oprogramowanie" onClick={goToSoftware}/>
      </View>
      {loading && (
        <ActivityIndicator size="large"/>
      )}
      {error && (
        <ErrorElement
          message="Nie udało się pobrać danych z serwera"
          type="error"
        />
      )}
      {/* TODO: kafelki statystyk */}
      {!loading && !error && (
        <>
          <StatisticsElement label="Osób i miejsc" value={affiliationsCount}/>
          <StatisticsElement label="Zestawów komputerowych" value={computerSetsCount}/>
          <StatisticsElement label="Hardware'u" value={hardwareCount}/>
          <StatisticsElement label="Oprogramowania" value={softwareCount}/>
        </>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  links: {
    flex: 1,
    flexDirection: 'row',
  }
});

export default HomepageComponent;

