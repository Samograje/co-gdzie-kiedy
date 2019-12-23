import React from 'react';
import {Button, StyleSheet, Text} from 'react-native';

const HardwareDetailsComponent = (props) => {
    return (
        <>
            <Text>Tutaj znajdzie się formularz hardware'u</Text>
            <Button
                title="Zapisz"
                onPress={props.onSubmit}
            />
            <Button
                title="Wróć"
                onPress={props.onReject}
            />
        </>
    );
};

const styles = StyleSheet.create({
    container: {
        justifyContent: 'center',
    },
});

export default HardwareDetailsComponent;

