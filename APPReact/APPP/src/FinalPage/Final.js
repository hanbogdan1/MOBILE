import React, { Component } from 'react';
import {
    View,
    Text,
    StyleSheet,
    ListView,
    Image,
    TouchableOpacity
} from 'react-native';
import {Container, Content, Header, Spinner} from "native-base";
import TimerMixin from 'react-timer-mixin';

export default class Final extends Component {

    static navigationOptions = {
        header: null,
    }

    constructor(props) {
        super(props);
        const ds = new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 });
        this.data = [];
        this.state = {
            dataSource: ds.cloneWithRows(this.data),
            page: 1, app_loaded:false
        };
        this.getContentAPi();
    }

    getContentAPi() {
        return fetch("https://www.reddit.com/r/popular/.json?")
            .then((response) => response.json())
            .then((responseJson) => {
                // console.log(responseJson);
                this.data = responseJson.data.children;
                this.setState({
                    dataSource: this.state.dataSource.cloneWithRows(this.data)
                });
            })
            .catch((error) => {
                console.error(error);
            });
    }



    renderRow = (rowData) => {
        return (
            <View style={{ flexDirection: 'row' }}>
                    <Image
                        source={{
                            uri: rowData.data.url,
                        }}
                        style={{ flexWrap: 'wrap', width: 100, height: 150, marginTop: 2, marginBottom: 2, marginLeft: 5 }}
                    />
                    <Text style={{ alignItems: 'center', width: 160, justifyContent: 'center', fontSize: 15, marginLeft: 30, marginTop: 50, fontWeight: 'bold' }} >
                        {rowData.data.title}
                    </Text>
            </View>
        )
    }

    componentDidMount() {
        setTimeout(() => {
            this.setState({app_loaded: true});
        }, 5000);
    }

    render() {

        if (this.state.app_loaded == false){
            return (
                <Container>
                    <Header />
                    <Content>
                        <Spinner color='blue' />
                        <Text>Loading...</Text>
                    </Content>
                </Container>
            );
        }
        else
            return (
                <View style={styles.container}>
                    <ListView style={styles.listView}
                              dataSource={this.state.dataSource}
                              renderRow={this.renderRow.bind(this)}
                    />
                </View >
            );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
    listView: {
        flex: 1,
    },

});