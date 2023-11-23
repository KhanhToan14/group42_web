import axiosClient from "./axiosClient";

class CheckLoginApi {
    checkLogin = async (params) => {
        const url = "/checkLogin";
        return axiosClient.get(url).then((data) => {
            return data;
        });
    };
}
const checkLoginApi = new CheckLoginApi();
export default checkLoginApi;
