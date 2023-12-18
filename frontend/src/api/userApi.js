import { message } from "antd";
import axiosClient from "./axiosClient";

class UserApi {
    getAll = (params) => {
        const url = '/v1/user/list';
        return axiosClient.get(url, { params });
    };
    getAllId = (params) => {
        const url = '/v1/user/list';
        return axiosClient.get(url, { params });
    };
    getOne = async (params) => {
        const url = `/v1/user/select/${params}`;
        return await axiosClient.get(url);
    };
    // getUserSaveWork = (params) => {
    //     const url = `/getUserSaveWork/${params}`;
    //     return axiosClient.get(url).then(data => {
    //         return data.data
    //     });
    // };
    postuser = (params) => {
        const url = '/users';
        return axiosClient.post(url, params).then(data => {
            message.success("Thêm công việc thành công!");
        }).catch(err => {
            message.error("Có lỗi xảy ra!");
        });
    };
    deleteuser = (id) => {
        const url = `/v1/user/delete/${id}`;
        return axiosClient.delete(url)
    };
    edituser = (params) => {
        const url = `/v1/user/update/${params.id}`;
        return axiosClient.patch(url, params).then(data => {
            message.success("Sửa thành công!");
        }).catch(err => {
            message.error("Có lỗi xảy ra!");
        });
    }
    getProfileUser = async () => {
        const url = `/v1/user/profile`;
        return (await axiosClient.get(url)).user
    }
}
const userApi = new UserApi();
export default userApi;