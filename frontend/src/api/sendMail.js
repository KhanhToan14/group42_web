import { message } from "antd";
import axios from "axios";
import axiosClient from "./axiosClient";

class SendMail {
    testTimeOut = (params, timeout = 1000) => {
        const url = "/sendMail";
        const CancelToken = axios.CancelToken;
        const source = CancelToken.source();
        if (timeout) {
            setTimeout(() => {
                source.cancel("ok");
            }, timeout * 1000);
        }
        return axiosClient.post(url, {
            cancelToken: source.token,
            ...params,
        });
    };

    Send = (params) => {
        const url = "/sendMail";
        return axiosClient
            .post(url, params)
            .then((data) => {
                message.success("Gửi mail thành công!");
            })
            .catch((err) => {
                message.error("Có lỗi xảy ra!");
            });
    };
}
const sendMailApi = new SendMail();
export default sendMailApi;
