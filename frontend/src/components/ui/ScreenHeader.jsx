import React from 'react';
import {Button, StyleSheet, Text, View} from 'react-native';
import {mainColor} from "../../constValues";

const ScreenHeader = (props) => {
  const {
    title,
    gActions,
  } = props;

  return (
    <View style={styles.card}>
      <Text style={styles.text}>{title}</Text>
      {gActions.map((action, idx) => (
          <View style={styles.buttonContainer} key={idx}>
            <Button
                disabled={action.disabled}
                title={action.label}
                onPress={action.onClick}
                color={mainColor}
            />
          </View>
      ))}
    </View>

  );
};

const styles = StyleSheet.create({
  card: {
    flex: 1,
    backgroundColor: 'lightgrey',
    padding: 5,
    borderWidth: 1,
    borderRadius: 2,
    margin: 5,
  },
  text: {
    fontSize: 30,
  },
});

export default ScreenHeader;
