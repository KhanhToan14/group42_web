import { Spin } from "antd";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import userApi from "../../../api/userApi";
import moment from "moment";
import renderHTML from "react-render-html";

function CandidateInfor() {
    let { id } = useParams();
    const [data, setData] = useState();
    console.log("data", data);

    const getApi = async () => {
        return await userApi.getOne(id).then((data) => {
            setData(data);
        });
    };

    useEffect(() => {
        getApi();
        window.scrollTo(0, 0);
    }, []);

    return (
        <div id="admin">
            <div className="heading">
                <div className="heading__title">
                    <h3>Hồ sơ ứng viên</h3>
                </div>
                <div className="heading__hr"></div>
            </div>
            <div className="content">
                {!data ? (
                    <div className="spin">
                        <Spin className="mt-5" />
                    </div>
                ) : (
                    <div className="infor-detail-admin">
                        <div className="content_img">
                            <img src={data.avatar} alt="Applicant's profile" width="500px" />
                        </div>
                        <div className="content-text">
                            <div className="box">
                                <div className="title">Họ và tên:</div>
                                <div className="detail">{data.name}</div>
                            </div>
                            <div className="box">
                                <div className="title">Kỹ năng:</div>
                                <div className="detail">
                                    <div className="tag">
                                        {data.Tags.length !== 0
                                            ? data.Tags.map((ok) => (
                                                <div className="item">{ok.name}</div>
                                            ))
                                            : "Chưa cập nhật!"}
                                    </div>
                                </div>
                            </div>
                            <div className="box">
                                <div className="title">Địa chỉ:</div>
                                <div className="detail">{data.address}</div>
                            </div>
                            <div className="box">
                                <div className="title">Giới tính:</div>
                                <div className="detail">{data.male}</div>
                            </div>
                            <div className="box">
                                <div className="title">Email:</div>
                                <div className="detail">{data.email}</div>
                            </div>
                            <div className="box">
                                <div className="title">Số điện thoại:</div>
                                <div className="detail">{data.phone}</div>
                            </div>
                            <div className="box">
                                <div className="title">Ngày tham gia:</div>
                                <div className="detail">
                                    {moment(data.createdAt).format("DD/MM/yyyy")}
                                </div>
                            </div>
                            <div className="box">
                                <div className="title">Giới thiệu:</div>
                                <div className="detail">
                                    {renderHTML(data.introduce ?? "Chưa cập nhật!")}
                                </div>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

export default CandidateInfor;