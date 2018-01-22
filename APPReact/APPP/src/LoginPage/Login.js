import React from 'react';
import { StyleSheet, Text, View, Alert} from 'react-native';
import {Button, Container, Content, Form, Header, Input, Item, Label, List, ListItem} from "native-base";
import firebase from 'firebase';

export default class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
        }

    }
    static navigationOptions = {
        header: null
    }



     check_login(){
        console.log("am intrat in functia de login!")

        if (this.state.username == "" || this.state.username == null ||
            this.state.password == "" || this.state.password == null )
        {
            console.log("empty user !")
            Alert.alert(
                'Error',
                "Please fill all fields !"
            );
            return ;
        }

         firebase.database().ref("Users/" + this.state.username).once("value").then( snapshot => {
             let password = snapshot.child("username").val();
             let username = snapshot.child("password").val();
             console.log("username:", username, "   password:", password);
             if (password != null && username != null && password.localeCompare(this.state.password) === 0 && username.localeCompare(this.state.username)===0){
                 console.log("this.state.username : ", this.state.username, "  this.state.password=" , this.state.password);
                 this.props.navigation.navigate('Final');
             }
             else {
                 Alert.alert(
                     'Error',
                     "DATA INCORRECT!"
                 );
             }
         });
    }

    render() {
        const {navigate} = this.props.navigation;

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
                        <Button block  onPress={this.check_login.bind(this)}>
                            <Text  style={styles.textLgn}>Login</Text>
                        </Button>
                    </Item>
                </Form>
            </View>
        );
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
