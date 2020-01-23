import React from 'react';
import {Platform, ScrollView, StyleSheet, View} from 'react-native';
import ResponsiveTable from "../ui/responsivetable/ResponsiveTable";
import ErrorElement from "../ui/ErrorElement";
import ScreenHeader from '../ui/ScreenHeader';


const HardwareHistoryComponent = (props) => {

    const {
        loading,
        error,
        items,
        totalElements,
        onFetchData,
        columns,
    } = props;

    return (
        <ScrollView>
            <View style={styles.container}>
                {Platform.OS === 'web' && (
                  <ScreenHeader title="Historia powiązań wybranego sprzętu"/>
                )}
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
                    />
                )}
            </View>
        </ScrollView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
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

export default HardwareHistoryComponent;
