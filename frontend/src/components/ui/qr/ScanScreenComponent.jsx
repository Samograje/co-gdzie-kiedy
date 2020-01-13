import React, {Component} from 'react';
import {
  StyleSheet,
  View,
  Text,
  Alert,
} from 'react-native';
import QRCodeScanner from 'react-native-qrcode-scanner';

class ScanScreenComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isDialogOpened: false,
    };
  }

  extractId = (text) => {
    const regex = /[0-9]+/;
    let found;

    if ((found = regex.exec(text))) {
      return found;
    }
  };

  processTheQRCode = (qrCode) => {
    let screenName;
    switch (qrCode.charAt(0)) {
      case 'H':
        screenName = 'HardwareDetails';
        break;
      case 'C':
        screenName = 'ComputerSetDetails';
        break;
      case 'S':
        screenName = 'SoftwareDetails';
        break;
    }

    //TODO: zabezpeiczenie na niewłaściwe kody qr
    const regex = /[SHC][1-9][0-9]*\/20[0-9]{2}/;

    this.props.push(screenName, {
      mode: 'edit',
      id: parseInt(this.extractId(qrCode)),
    });
  };

  onSuccess = (e) => {
    this.setState({
      isDialogOpened: true,
    });
    Alert.alert(
      'Wykryto kod',
      e.data + ' kliknij OK, aby wyświetlić dane.',
      [
        {
          text: 'Anuluj',
          // TODO: skanowanie po naciśnięciu 'Anuluj'
          onPress: () => this.setState({
            isDialogOpened: false,
          }),
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
          reactivate={!this.state.isDialogOpened}
          showMarker={true}
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
