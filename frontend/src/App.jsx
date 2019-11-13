import React from 'react';
import {Image, TouchableOpacity, View} from 'react-native';
import {createAppContainer} from 'react-navigation';
import {createDrawerNavigator} from 'react-navigation-drawer';
import {createStackNavigator} from 'react-navigation-stack';
import HomepageContainer from './components/homepage/HomepageContainer';
import AffiliationsListContainer from './components/affiliations/AffiliationsListContainer';
import ComputerSetsListContainer from './components/computersets/ComputerSetsListContainer';
import HardwareListContainer from './components/hardware/HardwareListContainer';
import SoftwareListContainer from './components/software/SoftwareListContainer';
import AffiliationDetailsContainer from './components/affiliations/AffiliationDetailsContainer';
import ComputerSetDetailsContainer from './components/computersets/ComputerSetDetailsContainer';
import HardwareDetailsContainer from './components/hardware/HardwareDetailsContainer';
import SoftwareDetailsContainer from './components/software/SoftwareDetailsContainer';
import DrawerImage from './../public/images/drawer.png';

const getNavigationOptions = (navigation) => ({
  headerLeft: (
    <View
      style={{
        flexDirection: 'row',
      }}
    >
      <TouchableOpacity
        onPress={() => navigation.toggleDrawer()}
      >
        <Image
          source={DrawerImage}
          style={{
            width: 25,
            height: 25,
            marginLeft: 15,
            marginRight: 15,
          }}
        />
      </TouchableOpacity>
    </View>
  ),
  headerStyle: {
    backgroundColor: '#477d18',
  },
  headerTintColor: '#fff',
  headerTitleStyle: {
    fontWeight: 'bold',
  },
});

const routeConfigs = {
  'Home': {
    screen: createStackNavigator({
      Home: {
        screen: HomepageContainer,
        path: 'homepage',
        navigationOptions: ({navigation}) => ({
          title: 'Strona główna',
          ...getNavigationOptions(navigation),
        }),
      },
    }),
    navigationOptions: {
      drawerLabel: 'Strona główna',
    },
  },
  'Affiliations': {
    screen: createStackNavigator({
      AffiliationsList: {
        screen: AffiliationsListContainer,
        path: 'affiliations',
        navigationOptions: ({navigation}) => ({
          title: 'Przeznaczenia',
          ...getNavigationOptions(navigation),
        })
      },
      AffiliationDetails: {
        screen: AffiliationDetailsContainer,
        path: 'affiliations/:mode/:id?',
        navigationOptions: ({navigation}) => ({
          title: navigation.getParam('mode', null) === 'edit' ? 'Edycja przynależności' : 'Dodawanie przynależności',
          ...getNavigationOptions(navigation),
        }),
      },
    }, {initialRouteName: 'AffiliationsList'}),
    navigationOptions: {
      drawerLabel: 'Przynależności',
    },
  },
  'ComputerSets': {
    screen: createStackNavigator({
      ComputerSetsList: {
        screen: ComputerSetsListContainer,
        path: 'computer-sets',
        navigationOptions: ({navigation}) => ({
          title: 'Zestawy komputerowe',
          ...getNavigationOptions(navigation),
        }),
      },
      ComputerSetDetails: {
        screen: ComputerSetDetailsContainer,
        path: 'computer-sets/:mode/:id?',
        navigationOptions: ({navigation}) => ({
          title: navigation.getParam('mode', null) === 'edit' ? 'Edycja zestawu komputerowego' : 'Dodawanie zestawu komputerowego',
          ...getNavigationOptions(navigation),
        }),
      },
    }, {initialRouteName: 'ComputerSetsList'}),
    navigationOptions: {
      drawerLabel: 'Zestawy komputerowe',
    },
  },
  'Hardware': {
    screen: createStackNavigator({
      HardwareList: {
        screen: HardwareListContainer,
        path: 'hardware',
        navigationOptions: ({navigation}) => ({
          title: 'Hardware',
          ...getNavigationOptions(navigation),
        })
      },
      HardwareDetails: {
        screen: HardwareDetailsContainer,
        path: 'hardware/:mode/:id?',
        navigationOptions: ({navigation}) => ({
          title: navigation.getParam('mode', null) === 'edit' ? "Edycja hardware'u" : "Dodawanie hardware'u",
          ...getNavigationOptions(navigation),
        }),
      },
    }, {initialRouteName: 'HardwareList'}),
    navigationOptions: {
      drawerLabel: 'Hardware',
    },
  },
  'Software': {
    screen: createStackNavigator({
      SoftwareList: {
        screen: SoftwareListContainer,
        path: 'software',
        navigationOptions: ({navigation}) => ({
          title: 'Oprogramowanie',
          ...getNavigationOptions(navigation),
        })
      },
      SoftwareDetails: {
        screen: SoftwareDetailsContainer,
        path: 'software/:mode/:id?',
        navigationOptions: ({navigation}) => ({
          title: navigation.getParam('mode', null) === 'edit' ? 'Edycja oprogramowania' : 'Dodawanie oprogramowania',
          ...getNavigationOptions(navigation),
        }),
      },
    }, {initialRouteName: 'SoftwareList'}),
    navigationOptions: {
      drawerLabel: 'Oprogramowanie',
    },
  },
};

const drawerNavigatorConfig = {
  // drawerBackgroundColor: 'black',
  drawerType: 'front', // front, back or slide
  unmountInactiveRoutes: true,
  // defaultNavigationOptions: {
  //   title: 'Co gdzie kiedy',
  // },
  initialRouteName: 'Home',
};

const AppNavigator = createDrawerNavigator(routeConfigs, drawerNavigatorConfig);

// const AppNavigator = createStackNavigator({
//   Home: HomepageContainer,
//   AffiliationsList: AffiliationsListContainer,
//   AffiliationDetails: AffiliationDetailsContainer,
//   ComputerSetsList: ComputerSetsListContainer,
//   ComputerSetDetails: ComputerSetDetailsContainer,
//   HardwareList: HardwareListContainer,
//   HardwareDetails: HardwareDetailsContainer,
//   SoftwareList: SoftwareListContainer,
//   SoftwareDetails: SoftwareDetailsContainer,
// }, {
//   initialRouteName: 'Home',
//   defaultNavigationOptions: {
//     headerStyle: {
//       backgroundColor: '#477d18',
//     },
//     headerTintColor: '#fff',
//     headerTitleStyle: {
//       fontWeight: 'bold',
//     }
//   },
// });

const AppContainer = createAppContainer(AppNavigator);

const App = () => {
  return (
    <AppContainer/>
  );
};

export default App;
