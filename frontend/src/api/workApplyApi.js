import { message } from "antd";
import axiosClient from "./axiosClient";

class WorkApplyApi {
    getAll = (params) => {
        const url = "/workApplys";
        return axiosClient.get(url, { params });
    };
    getOne = (params) => {
        const url = `/workApplys/${params}`;
        return axiosClient.get(url).then((data) => {
            return data.data;
        });
    };
    checkWorkApply = (params) => {
        const url = `/checkWorkApply/${params.id}?userId=` + params.userId;
        return axiosClient.get(url).then((data) => {
            return data.data;
        });
    };
    checkUserApply = (params) => {
        const url = `/checkUserApply/${params}`;
        return axiosClient.get(url).then((data) => {
            return data.data;
        });
    };
    postworkApply = (params) => {
        const url = "/workApplys";
        return axiosClient
            .post(url, params)
            .then((data) => {
                message.success("Ứng tuyển thành công!");
            })
            .catch((err) => {
                message.error("Có lỗi xảy ra!");
            });
    };
    deleteworkApply = (params) => {
        const url = `/workApplys/reject`;
        return axiosClient
            .delete(url, { params })
            .then((data) => {
                message.success("Từ chối thành công!");
            })
            .catch((err) => {
                message.error("Có lỗi xảy ra!");
            });
    };
    editworkApply = (params) => {
        const url = `/workApplys`;
        return axiosClient
            .patch(url, params)
            .then((data) => {
                return data;
            })
            .catch((err) => {
                message.error("Có lỗi xảy ra!");
            });
    };
}
const workApplyApi = new WorkApplyApi();
export default workApplyApi;
