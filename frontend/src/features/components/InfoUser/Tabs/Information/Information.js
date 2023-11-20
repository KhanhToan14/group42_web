import React from "react";
import { useState } from "react";
import { useForm } from "react-hook-form";
import JoditEditor from "jodit-react";
import { message, Select } from "antd";
import { Option } from "antd/lib/mentions";
import { useEffect } from "react";
import { storage } from "../../../../../firebase";
import { useDispatch, useSelector } from "react-redux";
import SpinLoad from "../../../Spin/Spin";
import { useNavigate } from "react-router-dom";
import userApi from "../../../../../api/userApi";
import { updateuser, userData } from "../../../../admin/Slice/userSlice";
// import { typeWorkData } from "../../../../admin/Slice/typeWorkSlice";
// import { checkArrayEquar } from "../../../../container/Functionjs";
export default function Information({ id }) {
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
    const dispatch = useDispatch();

    const [male, setMale] = useState("");
    const [date, setDate] = useState("");

    const getApi = async () => {
        return await userApi.getOne(id).then((data) => {
            return data;
        });
    };

    useEffect(() => {
        if (id) {
            Promise.all([getApi()]).then(function (data) {
                setContent(data[0].introduce);
                setMale(data[0].male);
                setDate(data[0].date)
                reset(data[0]);
                setState({
                    ...state,
                    anh: data[0].avatar,
                    anhBanner: data[0].banner,
                });
            });
        }
    }, []);

    const actionResult = (page) => {
        dispatch(userData(page));
    };

    const edit = async (data) => {
        if (data.anh && data.anhBanner === undefined) {
            dispatch(
                updateuser({
                    status: 1,
                    avatar: data.anh,
                    name: data.name,
                    address: data.address,
                    male,
                    phone: data.phone,
                    email: data.email,
                    date,
                    introduce: content,
                    id: id,
                }),
            );
        } else if (data.anhBanner && data.anh === undefined) {
            dispatch(
                updateuser({
                    status: 1,
                    banner: data.anhBanner,
                    name: data.name,
                    address: data.address,
                    male,
                    phone: data.phone,
                    email: data.email,
                    date,
                    introduce: content,
                    id: id,
                }),
            );
        } else if (data.anhBanner && data.anh) {
            dispatch(
                updateuser({
                    status: 1,
                    avatar: data.anh,
                    banner: data.anhBanner,
                    name: data.name,
                    male,
                    address: data.address,
                    phone: data.phone,
                    email: data.email,
                    date,
                    introduce: content,
                    id: id,
                }),
            );
        } else {
            dispatch(
                updateuser({
                    status: 1,
                    name: data.name,
                    address: data.address,
                    phone: data.phone,
                    male,
                    email: data.email,
                    date,
                    introduce: content,
                    id: id,
                }),
            );
        }
    };

    const navigate = useNavigate();

    const onSubmit = async (data) => {
        if (
            data.phone === "" ||
            data.email === "" ||
            date === "" ||
            content === ""
        ) {
            message.warning("Bạn cần nhập đầy đủ thông tin!");
        } else {
            setState({
                ...state,
                loading: true,
            });
            if (img !== "" || imgBanner !== "") {
                if (img !== "" && imgBanner === "") {
                    await storage.ref(`imagesuser/${img.name}`).put(img);
                    const anh = await storage
                        .ref("imagesuser")
                        .child(img.name)
                        .getDownloadURL();
                    edit({ data, anh });
                } else if (imgBanner !== "" && img === "") {
                    await storage.ref(`imagesuser/${imgBanner.name}`).put(imgBanner);
                    const anhBanner = await storage
                        .ref("imagesuser")
                        .child(imgBanner.name)
                        .getDownloadURL();
                    edit({ data, anhBanner });
                } else {
                    await storage.ref(`imagesuser/${img.name}`).put(img);
                    const anh = await storage
                        .ref("imagesuser")
                        .child(img.name)
                        .getDownloadURL();
                    await storage.ref(`imagesuser/${imgBanner.name}`).put(imgBanner);
                    const anhBanner = await storage
                        .ref("imagesuser")
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
            navigate(`/candidates/${id}`);
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

    const handleChangeDate = (e) => {
        setDate(e.target.value)
    }

    return (
        <div className="information">
            <div className="heading">
                <div className="heading__title">
                    <h3>Thông tin cá nhân</h3>
                </div>
                <div className="heading__hr"></div>
            </div>
            <div className="content">
                <form action="" onSubmit={handleSubmit(onSubmit)}>
                    <div className="form-group ">
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
                        <div className="form-group w-45 ">
                            <label htmlFor="">Tên ứng viên</label>
                            <input
                                type="text"
                                className="form-control "
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
                            <label htmlFor="">Giới tính</label>
                            <Select
                                value={male}
                                onChange={(e) => setMale(e)}
                                className="form-control w-100"
                            >
                                <Option value="Nam">Nam</Option>
                                <Option value="Nữ">Nữ</Option>
                            </Select>
                        </div>
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
                    </div>

                    <div className="d-flex">
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
                        <div className="form-group w-45">
                            <label htmlFor="">Ngày sinh</label>
                            <input className="form-control" type="date" onChange={handleChangeDate} value={date.split("T")[0]} />
                        </div>

                    </div>
                    <div className="form-group">
                        <label htmlFor="">Giới thiệu bản thân</label>
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
