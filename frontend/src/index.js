import React from 'react';
import ReactDom from 'react-dom';
import {StyleSheet, View} from "react-native";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import routes from "./routes";
import withCustomRouting from "./withCustomRouting";
import withTopbar from "./components/ui/Topbar";
import './index.css';

// Punkt startowy dla strony internetowej

const App = () => {
  return (
    <View style={styles.container}>
      <BrowserRouter>
        <Switch>
          {Object.values(routes).map((routeObject, idx) => (
            <Route
              key={idx}
              exact={routeObject.exact}
              path={routeObject.path}
              render={withCustomRouting(withTopbar(routeObject.component))}
            />
          ))}
        </Switch>
      </BrowserRouter>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

// ignorowanie warningów pochodzących z bibliotek
const warnedComponents = ['ScrollView', 'TouchableOpacity'];
const defineNewConsole = (oldConsole => ({
  log: text => oldConsole.log(text),
  info: text => oldConsole.info(text),
  warn: text => {
    let shouldIgnore = false;
    warnedComponents.forEach((name) => {
      if (text.includes(name) && text.includes('Please update the following components:')) {
        shouldIgnore = true;
      }
    });
    if (!shouldIgnore) {
      oldConsole.warn(text);
    }
  },
  error: text => oldConsole.error(text)
}));
window.console = defineNewConsole(window.console);

// renderowanie aplikacji do drzewa DOM
ReactDom.render(<App/>, document.getElementById('root'));
