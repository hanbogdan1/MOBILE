import React from 'react';
import { StyleSheet, Text, View, Alert} from 'react-native';
import {Button, Container, Content, Form, Header, Input, Item, Label, List, ListItem} from "native-base";
import * as firebase from "firebase";

export default class Login extends React.Component {

    constructor() {
        super();
        this.state = {
            user_lgn: 'false',
            username: null,
            password: null,
        }

        firebase.initializeApp({
            apiKey: "AIzaSyB6uxaz29wHf601BcbDTOGqPbiURfB2hJs",
            authDomain: "projName-d0c3e.firebaseapp.com",
            databaseURL: "https://mobilefacultate.firebaseio.com",
            storageBucket: "mobilefacultate.appspot.com"
        });
    }
    render() {
        return (
                <View style={styles.viewUpp}>
                    <Form>
                        <Item floatingLabel>
                            <Label>Username</Label>
                            <Input  onChangeText={(text) => this.setState({username: text})}
                                    value={this.state.username}/>
                        </Item>
                        <Item floatingLabel >
                            <Label>Password</Label>
                            <Input secureTextEntry  onChangeText={(text) => this.setState({password: text})}
                                   value={this.state.password}/>
                        </Item>
                        <Item>
                            <Button block  onPress={()=>this.register()}>
                                <Text  style={styles.textLgn}>Login</Text>
                            </Button>
                        </Item>
                    </Form>
                </View>
        );
    }

    register(){
        console.log("am intrat in functia de register!")

        if (this.state.username == "" || this.state.username == null ||
            this.state.password == "" || this.state.password == null ||
            this.state.email == "" || this.state.email == null )
        {
            console.log("empty user !")
            Alert.alert(
                'Error',
                "Please fill all fields !"
            );
            return ;
        }


        try{
            return firebase.database().ref("Users/" + this.state.username).set({
                email: this.state.email,
                password: this.state.password,
                username: this.state.username
            })
        }catch(error) {
            console.log("Error:" + error);
        } finally{
            console.log("clear fields after register")
            this.setState({username: ''});
            this.setState({email: ''});
            this.setState({password: ''});
            Alert.alert(
                'Succes',
                "User created!"
            );

        }
    }
}

const styles = StyleSheet.create({
    textLgn: {
        alignContent: 'center',
        width: "100%",
        color:"white",
        textAlign:"center"
    },
    viewDown:{
        height:"50%"
    },
    viewUpp:{
        height:"50%"
    }
    ,ButnLog: {
        backgroundColor:"red"
    }

});
