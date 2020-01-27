import React from 'react';
import {AppRegistry, View} from 'react-native';
import {createAppContainer} from 'react-navigation';
import {createStackNavigator} from 'react-navigation-stack';
import {GrowlProvider} from './src/components/ui/growl/GrowlProvider';
import {mainColor} from './src/constValues';
import routes from './src/routes';
import ScanScreenComponent from './src/components/ui/qr/ScanScreenComponent';
import withCustomRouting from './src/withCustomRouting';

// Punkt startowy dla aplikacji mobilnej

const getTitleFunc = (title) => {
  if (typeof title === 'string') {
    return () => ({
      title,
    });
  }

  if (typeof title === 'object') {
    return ({navigation}) => {
      const activeMode = navigation.state.params.mode;
      return ({
        title: title[activeMode],
      });
    };
  }
};

const routeConfigs = {};
Object.keys(routes).forEach((key) => {
  routeConfigs[key] = {
    screen: withCustomRouting(routes[key].component),
    navigationOptions: getTitleFunc(routes[key].title),
  };
});
routeConfigs.ScanScreen = {
  screen: withCustomRouting(ScanScreenComponent),
  navigationOptions: getTitleFunc('Skaner kodów QR'),
};

const navigatorConfig = {
  initialRouteName: 'Home',
  defaultNavigationOptions: {
    headerStyle: {
      backgroundColor: mainColor,
    },
    headerTintColor: '#fff',
    headerTitleStyle: {
      fontWeight: 'bold',
    }
  },
  backBehavior: 'order',
};

const navigator = createStackNavigator(routeConfigs, navigatorConfig);

const AppContainer = createAppContainer(navigator);

const App = () => (
  <View style={{flex: 1}}>
    <GrowlProvider>
      <AppContainer/>
    </GrowlProvider>
  </View>
);

AppRegistry.registerComponent('frontend', () => App);
