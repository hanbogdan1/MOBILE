import React from 'react';
import { Text, Alert, Linking, StyleSheet, TextInput, Image} from 'react-native';
import {Button, Container, Content, Form, Header, Input, Item, Label, List, ListItem, View} from "native-base";
import firebase from 'firebase';

export default class Register extends React.Component {
    email: string = "";
    subject: string = "";

    constructor(props) {
        super(props);

        this.state = {
            user_lgn: 'false',
            username: null,
            email: null,
            password: null,
        }

        firebase.initializeApp({
            apiKey: "AIzaSyB6uxaz29wHf601BcbDTOGqPbiURfB2hJs",
            authDomain: "projName-d0c3e.firebaseapp.com",
            databaseURL: "https://mobilefacultate.firebaseio.com",
            storageBucket: "mobilefacultate.appspot.com"
        });
    }

    static navigationOptions = {
        header: null
    }

    send_email(){
        // alert("Email:" + this.email + "\n Subject:" + this.subject);
        if(this.email.trim() == "" || this.subject.trim() == "")
        {
            alert("Fields cannot be empty!!!");
            return;
        }
        Linking.openURL('mailto:?to=' + this.email + '&subject=' + this.subject + '&body=Hi im using Air Dates');
        App.list.push(this.email);
        // alert(App.list.toString());
    }

    check_register() {
        console.log("check_register");
        if (this.state.username == "" || this.state.username == null ||
            this.state.password == "" || this.state.password == null ||
            this.state.email == "" || this.state.email == null) {
            console.log("empty user !")
            Alert.alert(
                'Error',
                "Please fill all fields !"
            );
            return false;
        }

        console.log("once");
        firebase.database().ref("Users/" + this.state.username).once("value").then(function (snapshot) {
            if (snapshot.exists()) {
                console.log("exista");
                Alert.alert(
                    'ERROR',
                    "User already exists!"
                );
                console.log("return");
                return false;
            }
            console.log("ajunge la final");
            return true;
        })
    }

    render() {

        const {navigate} = this.props.navigation;

        return (
            <View>
                <View style={styles.viewUpp}>
                    <Form>
                        <Item floatingLabel>
                            <Label>Username</Label>
                            <Input onChangeText={(text) => this.setState({username: text})}
                                   value={this.state.username}/>
                        </Item>
                        <Item floatingLabel>
                            <Label>Password</Label>
                            <Input secureTextEntry onChangeText={(text) => this.setState({password: text})}
                                   value={this.state.password}/>
                        </Item>
                        <Item floatingLabel>
                            <Label>Email</Label>
                            <Input onChangeText={(text) => this.setState({email: text})}
                                   value={this.state.email}/>
                        </Item>
                        <Item>
                            <Button block onPress={() => this.register()}>
                                <Text style={styles.textLgn}>Register</Text>
                            </Button>
                        </Item>
                    </Form>
                </View>
                <View style={styles.viewDown}>
                    <Button small primary rounded style={styles.ButnLog} onPress={() => navigate('Login')}>
                        <Text style={styles.textLgn}>Login</Text>
                    </Button>
                        <View style={styles.container}>
                            <Text style={styles.text}>Email address</Text>
                            <TextInput
                                style={styles.input}
                                underlineColorAndroid="transparent"
                                placeholder="Email"
                                onChangeText={(text) => this.email = text}
                            />
                            <Text style={styles.text}>Subject</Text>
                            <TextInput
                                style={styles.input}
                                underlineColorAndroid="transparent"
                                placeholder="Subject"
                                onChangeText={(text) => this.subject = text}
                            />

                            <Button

                                title="Send Email"
                                onPress={() => {

                                }
                                }
                            >
                            </Button>

                            <Button         style={{
                                            marginTop: 20,
                                            margin:50,
                                            }}

                                    onPress={() => this.send_email().bind(this)}>
                                <Text style={styles.textLgn}>Send Email</Text>
                            </Button>
                    </View>
                </View>
            </View>
        );
    }

    register() {

        if (this.state.username == "" || this.state.username == null ||
            this.state.password == "" || this.state.password == null ||
            this.state.email == "" || this.state.email == null) {
            console.log("empty user !")
            Alert.alert(
                'Error',
                "Please fill all fields !"
            );
            return;
        }

        // if (!this.check_register()){
        //     console.log("merg in return dupa check");
        //     // return;
        // }

        try {
            console.log("next create");
            return firebase.database().ref("Users/" + this.state.username).set({
                email: this.state.email,
                password: this.state.password,
                username: this.state.username
            })
        } catch (error) {
            console.log("Error:" + error);
        } finally {
            console.log("clear fields after register")
            this.setState({username: ''});
            this.setState({email: ''});
            this.setState({password: ''});
        }
        Alert.alert(
            'Succes',
            "User created!"
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
        // height:"50%"
    },
    viewUpp:{
        height:"50%"
    }
    ,ButnLog: {
        backgroundColor:"green"
    },container: {
        flexGrow: 1,
        backgroundColor: '#CACFD2',
        alignItems: 'center',
    },
    text: {
        textAlign: 'center',
        fontSize: 20,
        fontWeight: 'bold',
        marginTop: 20,
    },
    input: {
        height: 40,
        borderColor: 'gray',
        borderWidth: 1,
        width: 200,
        backgroundColor: 'white',
        margin: 2,
        borderRadius: 10,
        textAlign: 'center',
    },
});
