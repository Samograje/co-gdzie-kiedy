import React from 'react';
import {createStackNavigator} from './stack-navigator';
import HomepageContainer from './components/homepage/HomepageContainer';
import AffiliationsListContainer from './components/affiliations/AffiliationsListContainer';
import ComputerSetsListContainer from './components/computersets/ComputerSetsListContainer';
import HardwareListContainer from './components/hardware/HardwareListContainer';
import SoftwareListContainer from './components/software/SoftwareListContainer';
import AffiliationDetailsContainer from './components/affiliations/AffiliationDetailsContainer';
import ComputerSetDetailsContainer from './components/computersets/ComputerSetDetailsContainer';
import HardwareDetailsContainer from './components/hardware/HardwareDetailsContainer';
import SoftwareDetailsContainer from './components/software/SoftwareDetailsContainer';
import {mainColor} from "./constValues";

const routeConfigs = {
  Home: {
    screen: HomepageContainer,
    path: 'homepage',
    navigationOptions: () => ({
      title: 'Strona główna',
    }),
  },
  AffiliationsList: {
    screen: AffiliationsListContainer,
    path: 'affiliations',
    navigationOptions: () => ({
      title: 'Osoby / miejsca',
    })
  },
  AffiliationDetails: {
    screen: AffiliationDetailsContainer,
    path: 'affiliations/:mode/:id?',
    navigationOptions: ({navigation}) => ({
      title: navigation.getParam('mode', null) === 'edit' ? 'Edycja osoby / miejsca' : 'Dodawanie osoby / miejsca',
    }),
  },
  ComputerSetsList: {
    screen: ComputerSetsListContainer,
    path: 'computer-sets',
    navigationOptions: () => ({
      title: 'Zestawy komputerowe',
    }),
  },
  ComputerSetDetails: {
    screen: ComputerSetDetailsContainer,
    path: 'computer-sets/:mode/:id?',
    navigationOptions: ({navigation}) => ({
      title: navigation.getParam('mode', null) === 'edit' ? 'Edycja zestawu komputerowego' : 'Dodawanie zestawu komputerowego',
    }),
  },
  HardwareList: {
    screen: HardwareListContainer,
    path: 'hardware',
    navigationOptions: () => ({
      title: 'Sprzęty',
    })
  },
  HardwareDetails: {
    screen: HardwareDetailsContainer,
    path: 'hardware/:mode/:id?',
    navigationOptions: ({navigation}) => ({
      title: navigation.getParam('mode', null) === 'edit' ? "Edycja sprzętu" : "Dodawanie sprzętu",
    }),
  },
  SoftwareList: {
    screen: SoftwareListContainer,
    path: 'software',
    navigationOptions: () => ({
      title: 'Oprogramowanie',
    }),
  },
  SoftwareDetails: {
    screen: SoftwareDetailsContainer,
    path: 'software/:mode/:id?',
    navigationOptions: ({navigation}) => ({
      title: navigation.getParam('mode', null) === 'edit' ? 'Edycja oprogramowania' : 'Dodawanie oprogramowania',
    }),
  },
};

const stackNavigatorConfig = {
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
};

const AppNavigator = createStackNavigator(routeConfigs, stackNavigatorConfig);

export default AppNavigator;
