import { message } from "antd";
import axiosClient from "./axiosClient";

class WorkApi {
    getAll = (params) => {
        const url = '/v1/job/list';
        return axiosClient.get(url, { params });
    };
    getAllCensorship = (params) => {
        const url = '/v1/job/list';
        return axiosClient.get(url, { params });
    };
    search = (params) => {
        const url = '/v1/job/list';
        return axiosClient.get(url, { params });
    };
    // getAllId = (params) => {
    //     const url = '/workId';
    //     return axiosClient.get(url, { params });
    // };
    // getAllRejectId = (params) => {
    //     const url = '/workId/reject';
    //     return axiosClient.get(url, { params });
    // };
    getOne = (params) => {
        const url = `/v1/job/select/${params}`;
        return axiosClient.get(url).then(data => {
            return data.data
        });
    };
    postwork = (params) => {
        const url = '/v1/job/insert';
        return axiosClient.post(url, params).then(data => {
            message.success("Thêm công việc thành công!");
        }).catch(err => {
            message.error("Có lỗi xảy ra!");
        });
    };
    deletework = (id) => {
        const url = `/v1/job/delete/${id}`;
        return axiosClient.delete(url)
    };
    editwork = (params) => {
        const url = `/v1/job/update/${params.id}`;
        return axiosClient.patch(url, params).then(data => {
            message.success("Sửa thành công!");
        }).catch(err => {
            message.error("Có lỗi xảy ra!");
        });
    }
}
const workApi = new WorkApi();
export default workApi;