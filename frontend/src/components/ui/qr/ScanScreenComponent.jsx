'use strict';

import React, {Component} from 'react';
import {
  StyleSheet,
  View,
  Text,
  Alert,
} from 'react-native';
import QRCodeScanner from 'react-native-qrcode-scanner';

class ScanScreenComponent extends Component {

  processTheQRCode = (qrCode) => {
    if (qrCode.charAt(0) === 'H') {
      this.props.push('HardwareDetails', {
        mode: 'edit',
        id: 3,
      });
    } else if (qrCode.charAt(0) === 'C') {
      this.props.push('ComputerSetDetails', {
        mode: 'edit',
        id: 3,
      });
    } else if (qrCode.charAt(0) === 'S') {
      this.props.push('SoftwareDetails', {
        mode: 'edit',
        id: 3,
      });
      //TODO: hardcoded ID
    }
  };

  onSuccess = (e) => {
    Alert.alert(
        'Wykryto kod',
        e.data + ' kliknij OK, aby wyświetlić dane.',
        [
          {
            text: 'Cancel',
            onPress: () => console.log('Cancel Pressed'),
            style: 'cancel',
          },
          {text: 'OK', onPress: () => this.processTheQRCode(e.data)},
        ],
        {cancelable: false},
    );
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