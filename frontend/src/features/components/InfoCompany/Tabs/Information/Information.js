import React from "react";
import { useState } from "react";
import { useForm } from "react-hook-form";
import JoditEditor from "jodit-react";
import { useEffect } from "react";
import companyApi from "../../../../../api/companyApi";
import { storage } from "../../../../../firebase";
import {
    companyData,
    updatecompany,
} from "../../../../admin/Slice/companySlice";
import { useDispatch } from "react-redux";
import SpinLoad from "../../../Spin/Spin";
import { useHistory } from "react-router-dom";
import { message } from "antd";
export default function Infor({ id }) {
    const [state, setState] = useState({
        loading: false,
        linkImg: "",
        tenanh: "",
        img: "",
        anh: "",
        linkImgBanner: "",
        tenanhBanner: "",
        imgBanner: "",
        anhBanner: "",
    });
    const {
        loading,
        linkImg,
        tenanh,
        img,
        anh,
        linkImgBanner,
        tenanhBanner,
        imgBanner,
        anhBanner,
    } = state;
    const { register, handleSubmit, reset } = useForm();
    const [content, setContent] = useState();
    const getApi = async () => {
        return await companyApi.getOne(id).then((data) => {
            return data;
        });
    };
    useEffect(() => {
        if (id) {
            Promise.all([getApi()]).then(function (data) {
                setContent(data[0].introduce);
                reset(data[0]);
                setState({
                    ...state,
                    anh: data[0].avatar,
                    anhBanner: data[0].banner,
                });
            });
        }
    }, []);
    const dispatch = useDispatch();
    const actionResult = (page) => {
        dispatch(companyData(page));
    };
    const edit = async (data) => {
        if (data.anh && data.anhBanner === undefined) {
            await dispatch(
                updatecompany({
                    avatar: data.anh,
                    name: data.name,
                    address: data.address,
                    quantity: data.quantity,
                    website: data.website,
                    phone: data.phone,
                    email: data.email,
                    // //password: data.newPassword,
                    introduce: content,
                    id: id,
                }),
            );
        } else if (data.anhBanner && data.anh === undefined) {
            await dispatch(
                updatecompany({
                    banner: data.anhBanner,
                    name: data.name,
                    address: data.address,
                    quantity: data.quantity,
                    website: data.website,
                    phone: data.phone,
                    email: data.email,
                    //password: data.newPassword,
                    introduce: content,
                    id: id,
                }),
            );
        } else if (data.anhBanner && data.anh) {
            await dispatch(
                updatecompany({
                    avatar: data.anh,
                    banner: data.anhBanner,
                    name: data.name,
                    address: data.address,
                    quantity: data.quantity,
                    website: data.website,
                    phone: data.phone,
                    email: data.email,
                    //password: data.newPassword,
                    introduce: content,
                    id: id,
                }),
            );
        } else {
            await dispatch(
                updatecompany({
                    name: data.name,
                    address: data.address,
                    quantity: data.quantity,
                    website: data.website,
                    phone: data.phone,
                    email: data.email,
                    //password: data.newPassword,
                    introduce: content,
                    id: id,
                }),
            );
        }
    };
    const history = useHistory();
    const onSubmit = async (data) => {

        if (
            data.name === "" ||
            data.quantity === "" ||
            data.address === "" ||
            data.phone === "" ||
            content === "" ||
            data.website === "" ||
            data.email === "" ||
            data.newPassword === ""
        ) {
            message.warning("Bạn chưa nhập đầy đủ thông tin!");
        } else {
            setState({
                ...state,
                loading: true,
            });
            if (img !== "" || imgBanner !== "") {
                if (img !== "" && imgBanner === "") {
                    await storage.ref(`imagescompany/${img.name}`).put(img);
                    const anh = await storage
                        .ref("imagescompany")
                        .child(img.name)
                        .getDownloadURL();
                    edit({ data, anh });
                } else if (imgBanner !== "" && img === "") {
                    await storage.ref(`imagescompany/${imgBanner.name}`).put(imgBanner);
                    const anhBanner = await storage
                        .ref("imagescompany")
                        .child(imgBanner.name)
                        .getDownloadURL();
                    edit({ data, anhBanner });
                } else {
                    await storage.ref(`imagescompany/${img.name}`).put(img);
                    const anh = await storage
                        .ref("imagescompany")
                        .child(img.name)
                        .getDownloadURL();
                    await storage.ref(`imagescompany/${imgBanner.name}`).put(imgBanner);
                    const anhBanner = await storage
                        .ref("imagescompany")
                        .child(imgBanner.name)
                        .getDownloadURL();

                    edit({ data, anh, anhBanner });
                }
            } else {
                edit(data);
            }
            setTimeout(() => {
                actionResult({ page: 1 });
            }, 800);
            history.push(`/companys/${id}`);
        }
    };
    const hangdelimage = (e) => {
        setState({
            ...state,
            linkImg: URL.createObjectURL(e.target.files[0]),
            tenanh: e.target.files[0].name,
            img: e.target.files[0],
        });
    };
    const hangdelimageBanner = (e) => {
        setState({
            ...state,
            linkImgBanner: URL.createObjectURL(e.target.files[0]),
            tenanhBanner: e.target.files[0].name,
            imgBanner: e.target.files[0],
        });
    };
    return (
        <div className="infor">
            <div className="heading">
                <div className="heading__title">
                    <h3>Thông tin công ty</h3>
                </div>
                <div className="heading__hr"></div>
            </div>
            <div className="content">
                <form action="" onSubmit={handleSubmit(onSubmit)}>
                    <div className="form-group">
                        <label htmlFor="">Ảnh đại diện</label>
                        <label htmlFor="img">
                            <div className="btn_camera">
                                <i className="fas fa-camera-retro"></i>
                            </div>
                        </label>
                        <input
                            type="file"
                            hidden
                            name=""
                            id="img"
                            onChange={hangdelimage}
                        />
                        {linkImg ? (
                            <img src={linkImg} className="ml-3" height="150px" alt="" />
                        ) : anh ? (
                            <img src={anh} className="ml-5" height="150px" alt="" />
                        ) : (
                            ""
                        )}
                        <br />
                        {tenanh ? (
                            <span>
                                <span className="text-danger">Tên ảnh</span>: {tenanh}
                            </span>
                        ) : (
                            ""
                        )}
                    </div>
                    <div className="form-group">
                        <label htmlFor="">Ảnh banner</label>
                        <label htmlFor="imgBanner">
                            <div className="btn_camera">
                                <i className="far fa-images"></i>
                            </div>
                        </label>
                        <input
                            type="file"
                            hidden
                            name=""
                            id="imgBanner"
                            onChange={hangdelimageBanner}
                        />
                        {linkImgBanner ? (
                            <img src={linkImgBanner} className="ml-3" height="150px" alt="" />
                        ) : anhBanner ? (
                            <img
                                src={anhBanner}
                                className="ml-5"
                                height="150px"
                                width="250px"
                                alt=""
                            />
                        ) : (
                            ""
                        )}
                        <br />
                        {tenanhBanner ? (
                            <span>
                                <span className="text-danger">Tên ảnh</span>: {tenanhBanner}
                            </span>
                        ) : (
                            ""
                        )}
                    </div>
                    <div className="d-flex">
                        <div className="form-group w-45">
                            <label htmlFor="">Tên công ty</label>
                            <input
                                type="text"
                                className="form-control"
                                {...register("name")}
                                id=""
                                aria-describedby="helpId"
                                placeholder=""
                            />
                        </div>
                        <div className="form-group w-45">
                            <label htmlFor="">Địa chỉ</label>
                            <input
                                type="text"
                                className="form-control"
                                {...register("address")}
                                id=""
                                aria-describedby="helpId"
                                placeholder=""
                            />
                        </div>
                    </div>
                    <div className="d-flex">
                        <div className="form-group w-45">
                            <label htmlFor="">Số lượng nhân viên</label>
                            <input
                                type="text"
                                className="form-control"
                                {...register("quantity")}
                                id=""
                                aria-describedby="helpId"
                                placeholder=""
                            />
                        </div>
                        <div className="form-group w-45">
                            <label htmlFor="">Website</label>
                            <input
                                type="text"
                                className="form-control"
                                {...register("website")}
                                id=""
                                aria-describedby="helpId"
                                placeholder=""
                            />
                        </div>
                    </div>
                    <div className="d-flex">
                        <div className="form-group w-45">
                            <label htmlFor="">Số điện thoại</label>
                            <input
                                type="text"
                                className="form-control"
                                {...register("phone")}
                                id=""
                                aria-describedby="helpId"
                                placeholder=""
                            />
                        </div>
                        <div className="form-group w-45">
                            <label htmlFor="">Email</label>
                            <input
                                type="text"
                                className="form-control"
                                {...register("email")}
                                id=""
                                aria-describedby="helpId"
                                placeholder=""
                            />
                        </div>
                    </div>
                    {/* <div className="form-group w-45">
                        <label htmlFor="">Mật khẩu</label>
                        <input
                            type="password"
                            className="form-control"
                            {...register("newPassword")}
                            id=""
                            aria-describedby="helpId"
                            placeholder=""
                        />
                    </div> */}
                    <div className="form-group">
                        <label htmlFor="">Nội dung</label>
                        <JoditEditor
                            value={content}
                            tabIndex={1}
                            onChange={(e) => setContent(e)}
                        />
                    </div>
                    {loading ? (
                        <SpinLoad />
                    ) : (
                        <div className="text-center mtb">
                            <input type="submit" value="Cập nhật" />
                        </div>
                    )}
                </form>
            </div>
        </div>
    );
}
