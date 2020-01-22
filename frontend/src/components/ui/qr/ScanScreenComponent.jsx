import React, {Component} from 'react';
import { NavigationEvents, NavigationScreenProps } from 'react-navigation';

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
      reactivate: false,
    };
  }

  onDidFocus = payload => {
    this.setState({ isFocused: true });
  };

  onDidBlur = payload => {
    this.setState({ isFocused: false });
  };

  componentDidMount() {
    this.focusListener = this.props.addListener('didFocus', () => {
      this.state.reactivate = true;
    });
  }

  componentWillUnmount() {
    this.focusListener.remove();
    this.state.reactivate = false;
  }

  extractId = (text) => {
    const regex = /[0-9]+/;
    let found;

    if ((found = regex.exec(text))) {
      return found;
    }
  };

  checkIfProperCode = (scan) => {
    const regex = /[SHC][1-9][0-9]*\/20[0-9]{2}/;

    return regex.test(scan)
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

    this.props.push(screenName, {
      mode: 'edit',
      id: parseInt(this.extractId(qrCode)),
    });
  };

  showInvalidCodeAlert = () => {
    Alert.alert(
      'Skan',
      'Niepoprawny kod QR.',
      [
        {
          text: 'OK', onPress: () => this.setState({
            isDialogOpened: false,
          })
        },
      ],
      {cancelable: false},
    );
  };

  showValidCodeAlert = (e) => {
    Alert.alert(
      'Wykryto kod',
      e.data + ' kliknij OK, aby wyświetlić dane.',
      [
        {
          text: 'Anuluj',
          onPress: () => this.setState({
            isDialogOpened: false,
          }),
          style: 'cancel',
        },
        {
          text: 'OK', onPress: () => {
            this.setState({
              isDialogOpened: false,
            });
            this.processTheQRCode(e.data);
          }
        },
      ],
      {cancelable: false},
    );
  };

  onSuccess = (e) => {
    if (!this.state.isDialogOpened) {
      if (!this.checkIfProperCode(e.data)) {
        this.showInvalidCodeAlert();
      } else {
        this.showValidCodeAlert(e);
      }

      this.setState({
        isDialogOpened: true,
      });
    }
  };

  render() {
    const { isFocused } = this.state;

    return (
      <View style={styles.container}>
        <NavigationEvents
          onDidFocus={this.onDidFocus}
          onDidBlur={this.onDidBlur}
        />
        {isFocused && (
        <QRCodeScanner
          reactivate={this.state.reactivate}
          reactivateTimeout={3000}
          vibrate={!this.state.isDialogOpened}
          containerStyle={styles.scanner}
          onRead={this.onSuccess}
          bottomContent={
            <Text style={styles.textInfo}>
              Nakieruj kamerę na kod QR
            </Text>
          }
        />
        )}
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
