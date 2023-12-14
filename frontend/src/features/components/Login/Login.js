import { Checkbox, message } from "antd";
import React from "react";
import { useForm } from "react-hook-form";
import { Link, useHistory } from "react-router-dom";
import "../../styles/Login/Login.scss";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import loginApi from "../../../api/loginApi";
function Login({ onLogin }) {
    const schema = yup.object().shape({
        userName: yup.string().email().required(),
        password: yup.string().min(4).max(20).required(),
    });
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm({
        resolver: yupResolver(schema),
    });
    const history = useHistory();
    const onSumit = async (data) => {
        console.log("onSubmit");
        await loginApi
            .loginUser({
                username: data.userName,
                password: data.password,
            })
            .then((ok) => {
                if (ok === "err") {
                    message.error("Sai tên đăng nhập hoặc mật khẩu!");
                } else {
                    console.log(ok);
                    localStorage.setItem("token", ok.accessToken);
                    message.success("Đăng nhập thành công!");
                    onLogin();
                    history.push("/");
                }
            });
    };
    return (
        <div className="login">
            <div className="login__title">Việc làm tốt</div>
            <div className="login__box">
                <div className="line__login"></div>
                <div className="login__box__left">
                    <form onSubmit={handleSubmit(onSumit)}>
                        <div className="login__box__left__title">Tài khoản</div>
                        <input type="text" {...register("userName")} placeholder="Email" />
                        <p className="text-danger">
                            {errors.userName ? "Email không đúng định dạng" : ""}
                        </p>
                        <div className="login__box__left__title">Mật khẩu</div>
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
                        <Checkbox>Nhớ mật khẩu</Checkbox>
                        <div className="login__box__left__button">
                            <input type="submit" value="Đăng nhập" />
                        </div>
                    </form>
                </div>
                <div className="login__box__right">
                    <div className="right">
                        <div className="login__box__right__text">Hoặc đăng nhập với</div>
                        <button className="fb">Đăng nhập với facebook</button>
                        <button className="in">Đăng nhập với instagram</button>
                        <div className="login__box__right__text">
                            Chưa có tài khoản? <Link to="/register">Đăng ký</Link> ở đây
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;