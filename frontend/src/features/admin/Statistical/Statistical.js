import React, { useEffect, useState } from "react";
import statisticalApi from "../../../api/statisticalApi";

export default function Statistical() {
    const [data, setData] = useState();
    console.log("data", data);
    useEffect(() => {
        statisticalApi.getAll().then((data) => {
            setData(data.data);
        });
    }, []);
    return (
        <div id="admin">
            <div className="heading">
                <div className="heading__title">
                    <h3>Thống kê</h3>
                </div>
                <div className="heading__hr"></div>
            </div>
            <div className="content_statistical">
                <div className="layout_one">
                    <div className="box">
                        <div className="box_title">Công ty</div>
                        <div className="box_number">{data?.numCompany}</div>
                    </div>
                    <div className="box">
                        <div className="box_title">Ứng viên</div>
                        <div className="box_number">{data?.numUser}</div>
                    </div>
                    <div className="box_long">
                        <div className="box_title">Công việc đã đăng</div>
                        <div className="box_number">{data?.numWork}</div>
                    </div>
                </div>
                <div className="layout_two">
                    <div className="box_long">
                        <div className="box_title">Loại công việc hiện có</div>
                        <div className="box_number">{data?.numTypeOfWork}</div>
                    </div>
                    <div className="box">
                        <div className="box_title">Công việc đang bật</div>
                        <div className="box_number">{data?.numWorkActive}</div>
                    </div>
                    <div className="box">
                        <div className="box_title">Công việc đang ẩn</div>
                        <div className="box_number ">{data?.numWorkUnActive}</div>
                    </div>
                </div>
            </div>
        </div>
    );
}
