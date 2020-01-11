import HomepageContainer from "./components/homepage/HomepageContainer";
import AffiliationsListContainer from "./components/affiliations/AffiliationsListContainer";
import AffiliationDetailsContainer from "./components/affiliations/AffiliationDetailsContainer";
import ComputerSetsListContainer from "./components/computersets/ComputerSetsListContainer";
import ComputerSetDetailsContainer from "./components/computersets/ComputerSetDetailsContainer";
import ComputerSetHistoryContainer from "./components/computersets/ComputerSetHistoryContainer";
import HardwareListContainer from "./components/hardware/HardwareListContainer";
import HardwareDetailsContainer from "./components/hardware/HardwareDetailsContainer";
import SoftwareListContainer from "./components/software/SoftwareListContainer";
import SoftwareDetailsContainer from "./components/software/SoftwareDetailsContainer";
import HardwareHistoryContainer from "./components/hardware/HardwareHistoryContainer";
import SoftwareHistoryContainer from "./components/software/SoftwareHistoryContainer";

const routes = {
  Home: {
    component: HomepageContainer,
    path: '/',
    exact: true,
    title: 'Strona główna',
  },
  AffiliationsList: {
    component: AffiliationsListContainer,
    path: '/affiliations',
    exact: true,
    title: 'Osoby / miejsca',
  },
  AffiliationDetails: {
    component: AffiliationDetailsContainer,
    path: '/affiliations/:mode/:id?',
    exact: true,
    title: {
      create: 'Dodawanie osoby / miejsca',
      edit: 'Edycja osoby / miejsca',
    },
  },
  ComputerSetsList: {
    component: ComputerSetsListContainer,
    path: '/computer-sets',
    exact: true,
    title: 'Zestawy komputerowe',
  },
  ComputerSetDetails: {
    component: ComputerSetDetailsContainer,
    path: '/computer-sets/:mode/:id?',
    exact: true,
    title: {
      create: 'Dodawanie zestawu komputerowego',
      edit: 'Edycja zestawu komputerowego',
    },
  },
  ComputerSetHistory: {
    component: ComputerSetHistoryContainer,
    path: '/computer-sets/:id/history/:mode',
    exact: false,
    title: {
      affiliations: 'Historia osób / miejsc zestawu komputerowego',
      hardware: 'Historia sprzętów zestawu komputerowego',
      software: 'Historia oprogramowania zestawu komputerowego'
    },
  },
  HardwareList: {
    component: HardwareListContainer,
    path: '/hardware',
    exact: true,
    title: 'Sprzęty',
  },
  HardwareDetails: {
    component: HardwareDetailsContainer,
    path: '/hardware/:mode/:id?',
    exact: true,
    title: {
      create: 'Dodawanie sprzętu',
      edit: 'Edycja sprzętu',
    },
  },
  HardwareHistory: {
    component: HardwareHistoryContainer,
    path: '/hardware/:id/history/:mode',
    exact: false,
    title: {
      affiliations: 'Historia osób / miejsc sprzętu',
      'computer-sets': 'Historia zestawów komputerowych sprzętu',
    },
  },
  SoftwareList: {
    component: SoftwareListContainer,
    path: '/software',
    exact: true,
    title: 'Oprogramowanie',
  },
  SoftwareDetails: {
    component: SoftwareDetailsContainer,
    path: '/software/:mode/:id?',
    exact: true,
    title: {
      create: 'Dodawanie oprogramowania',
      edit: 'Edycja oprogramowania',
    },
  },
  SoftwareHistory: {
    component: SoftwareHistoryContainer,
    path: '/software/:id/history/computer-sets-history',
    exact: false,
    title: 'Historia zestawów komputerowych',
  },
};


export default routes;
