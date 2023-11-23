import { message } from "antd";
import axiosClient from "./axiosClient";

class WorkTypeOfWorkApi {
    getAll = (params) => {
        const url = "/WorkTypeOfWorks";
        return axiosClient.get(url, { params });
    };
    getOne = (params) => {
        const url = `/WorkTypeOfWorks/${params}`;
        return axiosClient.get(url).then((data) => {
            return data.data;
        });
    };
    postWorkTypeOfWork = (params) => {
        const url = "/WorkTypeOfWorks";
        return axiosClient.post(url, params);
    };
    deleteWorkTypeOfWork = (id) => {
        const url = `/WorkTypeOfWorks/${id}`;
        return axiosClient.delete(url);
    };
    editWorkTypeOfWork = (params) => {
        const url = `/WorkTypeOfWorks/${params.id}`;
        return axiosClient
            .patch(url, params)
            .then((data) => {
                message.success("Sửa thành công!");
            })
            .catch((err) => {
                message.error("Có lỗi xảy ra!");
            });
    };
}
const workTypeOfWorkApi = new WorkTypeOfWorkApi();
export default workTypeOfWorkApi;
