import { yupResolver } from "@hookform/resolvers/yup";
import { message } from "antd";
import axios from "axios";
import React from "react";
import { useForm } from "react-hook-form";
import { useDispatch } from "react-redux";
import { useHistory } from "react-router-dom";
import * as yup from "yup";
import { companyData } from "../../admin/Slice/companySlice";
export default function RegisterCompany() {
    const schema = yup.object().shape({
        userName: yup.string().email().required(),
        name: yup.string().required(),
        web: yup.string().required(),
        quantity: yup.number().required(),
        address: yup.string().required(),
        phone: yup.number().required(),
        password: yup.string().min(4).max(20).required(),
        rePassword: yup.string().oneOf([yup.ref("password"), null]),
    });
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm({
        resolver: yupResolver(schema),
    });
    const banner =
        "https://phuoc-associates.com/wp-content/uploads/2019/10/5-Things-To-Keep-In-Mind-When-Opening-A-Company-In-Vietnam.jpg";
    const avatar =
        "https://www.mintformations.co.uk/blog/wp-content/uploads/2020/05/shutterstock_583717939.jpg";
    const dispatch = useDispatch();
    const history = useHistory();
    const actionResult = () => {
        dispatch(companyData());
    };

    const onSubmit = (data) => {
        const dataCompany = {
            address: data.address,
            banner,
            avatar,
            phone: data.phone,
            website: data.web,
            name: data.name,
            email: data.userName,
            password: data.password,
            quantity: data.quantity,
            status: 0,
        };
        const link = "http://localhost:777/companys";
        axios
            .post(link, dataCompany)
            .then((ok) => {
                if (ok.data.data === "email đã tồn tại!") {
                    message.info("Email đã được đăng ký!");
                } else {
                    message.success("Đăng ký tài khoản thành công!");
                    setTimeout(() => {
                        actionResult();
                    }, 700);
                    history.push("/login");
                }
            })
            .catch((er) => {
                console.log(er);
            });
    };
    return (
        <>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="register__box__left__title">Email công ty</div>
                <input type="text" {...register("userName")} placeholder="Email" />
                <p className="text-danger">
                    {errors.userName ? "Email không đúng định dạng" : ""}
                </p>
                <div className="register__box__left__title">Tên công ty</div>
                <input type="text" {...register("name")} placeholder="Tên công ty" />
                <p className="text-danger">
                    {errors.name ? "Tên công ty không phù hợp" : ""}
                </p>
                <div className="register__box__left__title">Địa chỉ</div>
                <input type="text" {...register("address")} placeholder="Địa chỉ" />
                <p className="text-danger">
                    {errors.address ? "Địa chỉ công ty không được bỏ trống" : ""}
                </p>
                <div className="register__box__left__title">Điện thoại</div>
                <input type="text" {...register("phone")} placeholder="Điện thoại" />
                <p className="text-danger">
                    {errors.phone ? "Địa chỉ công ty không được bỏ trống" : ""}
                </p>
                <div className="register__box__left__title">Website công ty</div>
                <input type="text" {...register("web")} placeholder="Website công ty" />
                <p className="text-danger">
                    {errors.web ? "Website công ty không được bỏ trống" : ""}
                </p>
                <div className="register__box__left__title">Số lượng nhân viên</div>
                <input type="text" {...register("quantity")} placeholder="Số lượng nhân viên" />
                <p className="text-danger">
                    {errors.web ? "Số lượng nhân viên không được bỏ trống" : ""}
                </p>
                <div className="register__box__left__title">Mật khẩu</div>
                <input
                    type="password"
                    {...register("password")}
                    placeholder="Mật khẩu"
                />
                <p className="text-danger">
                    {errors.password ? "Mật khẩu ít nhất 4 ký tự và không quá 20 ký tự" : ""}
                </p>
                <div className="register__box__left__title">Nhập lại mật khẩu</div>
                <input
                    type="password"
                    {...register("rePassword")}
                    placeholder="Mật khẩu"
                />
                <p className="text-danger">
                    {errors.rePassword ? "Mật khẩu không trùng khớp" : ""}
                </p>
                <div className="register__box__left__button">
                    <input type="submit" value="Đăng ký" />
                </div>
            </form>
        </>
    );
}
