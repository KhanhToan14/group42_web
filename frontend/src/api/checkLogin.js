import axiosClient from "./axiosClient";
import userApi from "./userApi";
class CheckLoginApi {
    checkLogin = async (params) => {
        if (localStorage.getItem("token")) {
            // return axiosClient.get(url).then((data) => {
            //     return data;
            // });
            // return {
            //     // data: {
            //     //     id: "123",
            //     //     name: "dkhanhtoan14",
            //     //     avatar: "https://vpokedex.com/wp-content/uploads/2022/12/Pikachu-anime.png",
            //     //     type: "user",
            //     // }

            // }
            return await userApi.getProfileUser()
        } else {
            return ""
        }
    };
    checkLoginUser = (params) => {
        // const url = '/checkUserLogin';
        if (localStorage.getItem("token")) {
            return true
            // return axiosClient.get(url).then((data) => {
            //     return data;
            // });
        } else {
            return ""
        }
    };
}
const checkLoginApi = new CheckLoginApi();
export default checkLoginApi;