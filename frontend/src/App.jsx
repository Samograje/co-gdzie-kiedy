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
import withTopbar from "./components/ui/Topbar";

const App = () => {
  return (
    <View style={styles.container}>
      <Router>
        <Switch>
          <Route exact path="/" component={withTopbar(HomepageContainer, 'Strona główna', true)}/>
          <Route exact path="/affiliations" component={withTopbar(AffiliationsListContainer, 'Osoby / miejsca')}/>
          <Route path="/affiliations/:mode/:id?" component={withTopbar(AffiliationDetailsContainer, 'Osoba / miejsce')}/>
          <Route exact path="/computer-sets" component={withTopbar(ComputerSetsListContainer, 'Zestawy komputerowe')}/>
          <Route path="/computer-sets/:mode/:id?" component={withTopbar(ComputerSetDetailsContainer, 'Zestaw komputerowy')}/>
          <Route exact path="/hardware" component={withTopbar(HardwareListContainer, 'Hardware')}/>
          <Route path="/hardware/:mode/:id?" component={withTopbar(HardwareDetailsContainer, 'Hardware')}/>
          <Route exact path="/software" component={withTopbar(SoftwareListContainer, 'Software')}/>
          <Route path="/software/:mode/:id?" component={withTopbar(SoftwareDetailsContainer, 'Software')}/>
        </Switch>
      </Router>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default App;
