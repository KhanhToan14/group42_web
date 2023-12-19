import axiosClient from "./axiosClient";

class StatisticalApi {
    getAll = (params) => {
        const url = "/statisticals";
        return axiosClient.get(url, { params });
    };
}
const statisticalApi = new StatisticalApi();
export default statisticalApi;
