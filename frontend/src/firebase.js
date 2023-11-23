import firebase from "./firebase";
import "firebase/storage"

const firebaseConfig = {
    apiKey: "AIzaSyD_d33kwP_J0yGggnNKa_vHku_7qT7VVtk",
    authDomain: "my-job-react-with-node-1d763.firebaseapp.com",
    databaseURL: "https://my-job-react-with-node-1d763-default-rtdb.firebaseio.com",
    projectId: "my-job-react-with-node-1d763",
    storageBucket: "my-job-react-with-node-1d763.appspot.com",
    messagingSenderId: "812630800877",
    appId: "1:812630800877:web:568c335964ac3d1abe4fe4"
};

// Initialize Firebase
firebase.initializeApp(firebaseConfig);

const storage = firebase.storage()
export { storage, firebase as default }