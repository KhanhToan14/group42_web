import { message } from "antd";
import axiosClient from "./axiosClient";

class CompanyApi {
    getAll = (params) => {
        const url = "/v1/company/list";
        return axiosClient.get(url, { params });
    };
    search = (params) => {
        const url = "v1/company/list";
        return axiosClient.get(url, { params });
    };
    getOne = (params) => {
        const url = `/v1/company/select/${params}`;
        return axiosClient.get(url).then((data) => {
            return data.data;
        });
    };
    getCompanySaveUser = (params) => {
        const url = `/getCompanySaveUser/${params}`;
        return axiosClient.get(url).then((data) => {
            return data.data;
        });
    };
    getCheck = (params) => {
        const url = "/checkCompanys";
        return axiosClient.get(url, { params });
    };
    postcompany = (params) => {
        const url = "/companys";
        return axiosClient
            .post(url, params)
            .then((data) => {
                return data.data;
            })
            .catch((err) => {
                message.error("Có lỗi xảy ra!");
            });
    };
    deletecompany = (id) => {
        const url = `/v1/company/delete/${id}`;
        return axiosClient
            .delete(url)
            .then((data) => {
                message.success("Xoá thành công!");
            })
            .catch((err) => {
                message.error("Có lỗi xảy ra!");
            });
    };
    editcompany = (params) => {
        const url = `/v1/company/update/${params.id}`;
        console.log('CompanyApi ~ params', params)
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
const companyApi = new CompanyApi();
export default companyApi;
