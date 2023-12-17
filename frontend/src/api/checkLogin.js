import axiosClient from "./axiosClient";
import userApi from "./userApi";

class CheckLoginApi {
    checkLogin = async (params) => {
        // const url = "/checkLogin";
        if (localStorage.getItem("token")) {
            return await userApi.getProfileUser();
        } else {
            return '';
        }
    };
    checkLoginUser = (params) => {
        // const url = '/checkUserLogin';
        if (localStorage.getItem("token")) {
            return true;
        } else {
            return '';
        }
    };
}
const checkLoginApi = new CheckLoginApi();
export default checkLoginApi;
