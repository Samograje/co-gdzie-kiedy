import React from 'react';
import {ActivityIndicator, ScrollView, StyleSheet, View} from 'react-native';
import StatisticsElement from "./StatisticsElement";
import LinkElement from "./LinkElement";
import ErrorElement from "../ui/ErrorElement";

const HomepageComponent = (props) => {

  const {
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
  } = props;

  const layoutStyle = isWide ? styles.wide : styles.small;

  return (
    <ScrollView>
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
            label="Sprzęty"
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
        {!loading && !error && (
          <View style={[styles.stats, layoutStyle]}>
            <StatisticsElement
              label="Osób i miejsc"
              value={affiliationsCount}
              isWide={isWide}
            />
            <StatisticsElement
              label="Zestawów komputerowych"
              value={computerSetsCount}
              isWide={isWide}
            />
            <StatisticsElement
              label="Sprzętów"
              value={hardwareCount}
              isWide={isWide}
            />
            <StatisticsElement
              label="Oprogramowania"
              value={softwareCount}
              isWide={isWide}
            />
          </View>
        )}
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  links: {},
  stats: {
    flexWrap: 'wrap',
    justifyContent: 'center',
  },
  small: {
    flexDirection: 'column',
  },
  wide: {
    flexDirection: 'row',
  },
});

export default HomepageComponent;

