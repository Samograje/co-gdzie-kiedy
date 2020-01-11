'use strict';

import React, {Component} from 'react';
import {
  StyleSheet,
  View,
  Text,
  Linking,
} from 'react-native';
import QRCodeScanner from 'react-native-qrcode-scanner';


class ScanScreenComponent extends Component {
  onSuccess = (e) => {
    Linking
        .openURL(e.data)
        .catch(err => console.error('An error occured', err));
  };

  render() {
    return (
        <View style={styles.container}>
          <QRCodeScanner
              containerStyle={styles.scanner}
              onRead={this.onSuccess}
              bottomContent={
                <Text style={styles.textInfo}>
                  Nakieruj kamerę na kod QR
                </Text>
              }
          />
        </View>
    );
  }
}

const styles = StyleSheet.create({
  textInfo: {
    fontWeight: '500',
    fontSize: 18,
    marginTop: 30,
    backgroundColor: 'white',
    width: '100%',
    textAlign: 'center',
  },
  scanner: {
    marginTop: 10,
  },
  container: {
    flex: 1,
  },
});

export default ScanScreenComponent;