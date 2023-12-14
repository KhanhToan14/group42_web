import axiosClient from "./axiosClient";

class LoginApi {
    // loginCompany = (params) => {
    //     const url = '/loginCompany';
    //     return axiosClient.post(url, params);
    // };
    loginUser = (params) => {
        const url = '/v1/auth/login';
        return axiosClient.post(url, params);
    };
    // loginAdmin = (params) => {
    //     const url = '/loginAdmin';
    //     return axiosClient.post(url, params);
    // };
}
const loginApi = new LoginApi();
export default loginApi;