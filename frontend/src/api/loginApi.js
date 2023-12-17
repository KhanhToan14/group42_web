import axiosClient from "./axiosClient";

class LoginApi {
    loginUser = (params) => {
        const url = '/v1/auth/login';
        return axiosClient.post(url, params);
    };
}
const loginApi = new LoginApi();
export default loginApi;