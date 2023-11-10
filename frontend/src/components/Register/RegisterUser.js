import React, { useState } from "react";
import { useForm } from "react-hook-form";
import * as yup from "yup";
import { message, Radio, Select } from "antd";
import axios from "axios";
import { useHistory } from "react-router-dom";
export default function RegisterUser() {
    const schema = yup.object().shape({
        userName: yup.string().email().required(),
        name: yup.string().required(),
        address: yup.string().required(),
        date: yup.date().required(),
        phone: yup.number().required(),
        password: yup.string().min(4).max(20).required(),
        rePassword: yup.string().oneOf([yup.ref("password"), null]),
    });

    const [male, setMale] = useState("Nam");
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm({
        resolver: yupResolver(schema),
    });
    const avatar = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png";
    const banner = "https://zeru.com/blog/wp-content/uploads/How-Do-You-Have-No-Profile-Picture-on-Facebook_25900";
    const history = useHistory();

    const onSubmit = (data) => {
        const asUserRole = [{ roleId: 2 }];
        const dataUser = {
            banner,
            avatar,
            address: data.address,
            phone: data.phone,
            name: data.name,
            date: data.date,
            email: data.userName,
            password: data.password,
            asUserRole,
            male,
            status: 1,
        };
        const link = "http://localhost:777/users";
        axios
            .post(link, dataUser)
            .then((ok) => {
                if (ok.data.data === "email đã tồn tại!") {
                    message.info("Email đã được đăng ký!");
                } else {
                    message.success("Đăng ký tài khoản thành công!");
                    history.push("/login");
                }
            })
            .catch((er) => {
                console.log(er);
            });
    };

    const onChange = (e) => {
        setMale(e.target.value);
    }

    return (
        <>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="register__box__left__title">Email đăng nhập</div>
                <input type="text" {...register("userName")} placeholder="Email" />
                <p className="text-danger">
                    {errors.userName ? "Email không đúng định dạng" : ""}
                </p>
                <div className="register__box__left__title">Tên người dùng</div>
                <input type="text" {...register("name")} placeholder="Tên người dùng" />
                <p className="text-danger">
                    {errors.name ? "Tên người dùng khống phù hơp" : ""}
                </p>
                <div className="register__box__left__title">Địa điểm</div>
                <input type="text" {...register("address")} placeholder="Địa điểm" />
                <p className="text-danger">
                    {errors.address ? "Không được bỏ trống" : ""}
                </p>
                <div className="register__box__left__title">Số điện thoại</div>
                <input type="text" {...register("phone")} placeholder="Số điện thoại" />
                <p className="text-danger">
                    {errors.phone ? "Số điện thoại không hợp lệ" : ""}
                </p>
                <div className="register__box__left__title">Ngày sinh</div>
                <input id="date" type="date" {...register("date")} placeholder="Ngày sinh" />
                <p className="text-danger">
                    {errors.date ? "Ngày sinh không hợp lệ" : ""}
                </p>
                <div className="register__box__left__title">Giới tính</div>
                <Radio.Group onChange={onChange} value={male} style={{ marginBottom: 10 }}>
                    <Radio value="Nam">Nam</Radio>
                    <Radio value="Nữ">Nữ</Radio>
                </Radio.Group>
                <div className="register__box__left__title">Mật khẩu</div>
                <input
                    type="password"
                    {...register("password")}
                    placeholder="Mật khẩu"
                />
                <p className="text-danger">
                    {errors.password
                        ? "Mật khẩu ít nhất 4 ký tự và không quá 20 ký tự"
                        : ""}
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
