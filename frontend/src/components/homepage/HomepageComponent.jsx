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
    isWide,
    goToAffiliations,
    goToComputerSets,
    goToHardware,
    goToSoftware,
    handleLayout,
  }
) => {

  const layoutStyle = isWide ? styles.wide : styles.small;

  return (
    <View style={styles.container}>
      <View
        style={[styles.links, layoutStyle]}
        onLayout={handleLayout}
      >
        <LinkElement
          label="Osoby i miejsca"
          onClick={goToAffiliations}
          isWide={isWide}
        />
        <LinkElement
          label="Zestawy komputerowe"
          onClick={goToComputerSets}
          isWide={isWide}
        />
        <LinkElement
          label="Hardware'y"
          onClick={goToHardware}
          isWide={isWide}
        />
        <LinkElement
          label="Oprogramowanie"
          onClick={goToSoftware}
          isWide={isWide}
        />
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
  links: {},
  small: {
    flexDirection: 'column',
  },
  wide: {
    flexDirection: 'row',
  },
});

export default HomepageComponent;

