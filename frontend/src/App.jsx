import {Route, Router, Switch} from './routing';
import React from "react";
import {StyleSheet, View} from "react-native";
import HomepageContainer from "./components/homepage/HomepageContainer";
import AffiliationsListContainer from "./components/affiliations/AffiliationsListContainer";
import AffiliationDetailsContainer from "./components/affiliations/AffiliationDetailsContainer";
import ComputerSetsListContainer from "./components/computersets/ComputerSetsListContainer";
import ComputerSetDetailsContainer from "./components/computersets/ComputerSetDetailsContainer";
import HardwareListContainer from "./components/hardware/HardwareListContainer";
import HardwareDetailsContainer from "./components/hardware/HardwareDetailsContainer";
import SoftwareListContainer from "./components/software/SoftwareListContainer";
import SoftwareDetailsContainer from "./components/software/SoftwareDetailsContainer";

const App = () => {
  return (
    <View style={styles.container}>
      <Router>
        <Switch>
          <Route exact path="/" component={HomepageContainer}/>
          <Route exact path="/affiliations" component={AffiliationsListContainer}/>
          <Route path="/affiliations/:mode/:id?" component={AffiliationDetailsContainer}/>
          <Route exact path="/computer-sets" component={ComputerSetsListContainer}/>
          <Route path="/computer-sets/:mode/:id?" component={ComputerSetDetailsContainer}/>
          <Route exact path="/hardware" component={HardwareListContainer}/>
          <Route path="/hardware/:mode/:id?" component={HardwareDetailsContainer}/>
          <Route exact path="/software" component={SoftwareListContainer}/>
          <Route path="/software/:mode/:id?" component={SoftwareDetailsContainer}/>
        </Switch>
      </Router>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    // flex: 1,
    // alignItems: 'center',
    // justifyContent: 'center',
    // marginTop: 50,
    // padding: 50
  }
});

export default App;
