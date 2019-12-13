// Punkt startowy dla aplikacji mobilnej

import {AppRegistry} from 'react-native';
import {createAppContainer} from "react-navigation";
import AppNavigator from "./src/AppNavigator";

const App = createAppContainer(AppNavigator);

AppRegistry.registerComponent('frontend', () => App);
