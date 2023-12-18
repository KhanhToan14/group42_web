import { message } from "antd";
import axiosClient from "./axiosClient";

class UserApi {
    getAll = (params) => {
        const url = "/v1/user/list";
        return axiosClient.get(url, { params });
    };
    getAllId = (params) => {
        const url = "/v1/user/list";
        return axiosClient.get(url, { params });
    };
    getOne = (params) => {
        const url = `/v1/user/select/${params}`;
        return axiosClient.get(url).then((data) => {
            return data.data;
        });
    };
    getUserSaveWork = (params) => {
        const url = `/v1/user/list/${params}`;
        return axiosClient.get(url).then((data) => {
            return data.data
        });
    };
    postuser = (params) => {
        const url = "/v1/user/list";
        return axiosClient
            .post(url, params)
            .then((data) => {
                return data.data;
                // message.success("Thêm công việc thành công!");
            }).catch((err) => {
                message.error("Có lỗi xảy ra!");
            });
    };
    deleteuser = (id) => {
        const url = `/v1/user/delete/${id}`;
        return axiosClient
            .delete(url)
            .then((data) => {
                message.success("Xoá thành công!");
            })
            .catch((err) => {
                message.error("Có lỗi xảy ra!");
            });
    };
    edituser = (params) => {
        const url = `/v1/user/update/${params.id}`;
        console.log('UserApi ~ params', params)
        return axiosClient
            .patch(url, params)
            .then((data) => {
                message.success("Sửa thành công!");
            }).catch((err) => {
                message.error("Có lỗi xảy ra!");
            });
    };
    getProfileUser = async () => {
        const url = `/v1/user/profile`;
        return (await axiosClient.get(url)).user
    };
}
const userApi = new UserApi();
export default userApi;