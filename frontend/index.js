import {AppRegistry} from 'react-native';
import {createAppContainer} from "react-navigation";
import {createStackNavigator} from "react-navigation-stack";
import routes from "./src/routes";
import withCustomRouting from "./src/withCustomRouting";
import {mainColor} from "./src/constValues";

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
  routeConfigs[key] = ({
    screen: withCustomRouting(routes[key].component),
    navigationOptions: getTitleFunc(routes[key].title),
  });
});

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

const App = createAppContainer(navigator);

AppRegistry.registerComponent('frontend', () => App);
