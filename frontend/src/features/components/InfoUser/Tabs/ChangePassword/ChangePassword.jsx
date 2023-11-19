import React from 'react'
import { useForm } from "react-hook-form";
import { message } from "antd";
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { updateuser } from '../../../../admin/Slice/userSlice';

export default function ChangePassword({ id }) {
    const { register, handleSubmit } = useForm();

    const dispatch = useDispatch()

    const onSubmit = async (data) => {
        console.log('data', data)
        if (
            data.oldPassword.trim() === "" ||
            data.newPassword.trim() === "" ||
            data.rePassword.trim() === ""
        ) {
            message.warning("Bạn chưa nhập đầy đủ thông tin!");
        } else {
            if (data.newPassword !== data.rePassword) {
                message.warning("Nhập lại mật khẩu mới chưa chính xác!");
            } else {
                axios.get(`http://localhost:777/changePassword/user?id=${id}&password=${data.oldPassword}`).then(ok => {
                    if (ok.data.data === "wrong") {
                        message.warning("Mật khẩu của bạn chưa chính xác!");
                    } else {
                        dispatch(
                            updateuser({
                                password: data.newPassword,
                                id: id,
                            }),
                        )
                    }
                }).catch(Err => {
                    console.log(Err);
                })
            }
        }
    }

    return (
        <div className="ListJob">
            <div className="heading">
                <div className="heading__title">
                    <h3>Đổi mật khẩu</h3>
                </div>
                <div className="heading__hr"></div>
            </div>

            <div className="content">
                <form action="" onSubmit={handleSubmit(onSubmit)}>
                    <div style={{
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        gap: 20
                    }}>
                        <div className="form-group w-45">
                            <label htmlFor="">Mật khẩu cũ</label>
                            <input
                                type="password"
                                className="form-control"
                                {...register("oldPassword")}
                                id=""
                                aria-describedby="helpId"
                                placeholder=""
                            />
                        </div>
                        <div className="form-group w-45">
                            <label htmlFor="">Mật khẩu mới</label>
                            <input
                                type="password"
                                className="form-control"
                                {...register("newPassword")}
                                id=""
                                aria-describedby="helpId"
                                placeholder=""
                            />
                        </div>
                        <div className="form-group w-45">
                            <label htmlFor="">Nhập lại mật khẩu mới</label>
                            <input
                                type="password"
                                className="form-control"
                                {...register("rePassword")}
                                id=""
                                aria-describedby="helpId"
                                placeholder=""
                            />
                        </div>
                        <div className="infor">
                            <div className="text-center mtb">
                                <input type="submit" value="Cập nhật" />
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div >
    )
}
