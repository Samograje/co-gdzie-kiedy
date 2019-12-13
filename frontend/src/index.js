// Punkt startowy dla strony internetowej

import React from 'react';
import ReactDom from 'react-dom';
import {createBrowserApp} from '@react-navigation/web'
import AppNavigator from './AppNavigator';
import './index.css';

const App = createBrowserApp(AppNavigator);

ReactDom.render(<App/>, document.getElementById('root'));
