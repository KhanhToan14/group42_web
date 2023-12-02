import axiosClient from "./axiosClient";

class CheckLoginApi {
    checkLogin = async (params) => {
        const url = "/checkLogin";
        if (localStorage.getItem("token")) {
            return axiosClient.get(url).then((data) => {
                return data;
            });
        } else {
            return await "";
        }
    };
    checkLoginUser = (params) => {
        const url = '/checkUserLogin';
        if (localStorage.getItem("token")) {
            return axiosClient.get(url);
        } else {
            return '';
        }
    };
}
const checkLoginApi = new CheckLoginApi();
export default checkLoginApi;
