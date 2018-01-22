import React from 'react';
import {Navigator, StyleSheet} from 'react-native';
import {StackNavigator} from 'react-navigation';
import Final from './src/FinalPage/Final';
import Login from './src/LoginPage/Login';
import Register from './src/RegisterPage/Register';

export default class App extends React.Component {

    constructor() {
        super();
    }

    render() {
       return <RootApp/>;
    }
}

const RootApp = StackNavigator({
    Register:{screen:Register},
    Login:{screen:Login},
    Final:{screen:Final}
});
