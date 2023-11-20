import firebase from "firebase";
import "firebase/storage"
const config = {
    apiKey: "AIzaSyD-dyDXwc3-KSesEi6Jouf_t0jUeoQTvSg",
    authDomain: "my-job-react-with-node.firebaseapp.com",
    databaseURL: "https://my-job-react-with-node-default-rtdb.firebaseio.com",
    projectId: "my-job-react-with-node",
    storageBucket: "my-job-react-with-node.appspot.com",
    messagingSenderId: "774668644719",
    appId: "1:774668644719:web:6ea0ad3ca69777caf5b89a",
    measurementId: "G-J0GC6P88Z4"
};

firebase.initializeApp(config)

const storage = firebase.storage()
export { storage, firebase as default }