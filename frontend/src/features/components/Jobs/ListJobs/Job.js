import { Pagination } from "antd";
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { workData } from "../../../admin/Slice/workSlice";
import { Radio } from "antd";
import { typeWorkData } from "../../../admin/Slice/typeWorkSlice";
import { formatDateWork } from "../../../container/Functionjs";
import "../../../styles/SearchJobs/ListJob.scss";
import SpinLoad from "../../Spin/Spin";
import KeyTag from "./KeyTag";
export default function Job({
    searchData,
    onTime,
    onAmout,
    amount,
    time,
    typeWorkValue,
    onTypeWork,
}) {
    const work = useSelector((state) => state.works.work.data);
    const loading = useSelector((state) => state.works.loading);
    const typework = useSelector((state) => state.typeWorks.typeWork.data);
    const dispatch = useDispatch();
    const [state, setState] = useState({
        page: localStorage.getItem("pageWorkHome") || 1,
    });
    const { page } = state;
    const onChangePage = (page) => {
        setState({
            page: page,
        });
    };

    const actionResult = (page) => {
        dispatch(workData(page));
        dispatch(typeWorkData({ status: 1 }));
    };
    useEffect(() => {
        localStorage.setItem("pageWorkHome", page);
        actionResult({ page: page, status: 1 });
    }, [page]);
    const onChangeTime = (e) => {
        onTime(e.target.value);
    };
    const onChangeTypeWork = (e) => {
        onTypeWork(+e.target.value !== 0 ? e.target.value : "");
    };
    const onChangeAmount = (e) => {
        onAmout(e.target.value);
    };
    return (
        <div className="ListJobSearch">
            <div className="container">
                <div className="row">
                    <div className="col-md-8">
                        {searchData === "" ? (
                            loading ? (
                                <SpinLoad />
                            ) : (
                                work?.map((data, index) => (
                                    <div className="job__box" key={index}>
                                        {/* <div className="job__tag">hot</div> */}
                                        <div className="job__logo">
                                            {/* <img src={data.Company.avatar} alt="" /> */}
                                        </div>
                                        <div className="job__content">
                                            <div className="job__title">
                                                <Link to={`/jobs/work/${data.id}`}>
                                                    <h4 className="jobTitle" title={data.name}>
                                                        {data.name}
                                                    </h4>
                                                </Link>
                                            </div>
                                            <div className="job__nameCompany">
                                                <Link to={`/jobs/work/${data.id}`}>
                                                    {" "}
                                                    {/* <span>{data.Company.name}</span> */}
                                                </Link>
                                            </div>
                                            <div className="job__detail">
                                                <div className="job__detail--address">
                                                    <div className="job__icon">
                                                        <i className="fas fa-map-marker-alt"></i>
                                                    </div>
                                                    <span>{data.location}</span>
                                                </div>
                                                <div className="job__detail--deadline outSize outSize">
                                                    <div className="job__icon">
                                                        <i className="far fa-clock"></i>
                                                    </div>
                                                    {/* <span>{formatDateWork(data.dealtime)}</span> */}
                                                </div>
                                                <div className="job__detail--salary">
                                                    <div className="job__icon">
                                                        <i className="fas fa-dollar-sign"></i>
                                                    </div>
                                                    <span>
                                                        {(data.salaryFrom && data.salaryTo) ?
                                                            data.salaryFrom + " - " + data.salaryTo + " " + data.currency
                                                            : "Thương lượng"
                                                        }
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                ))
                            )
                        ) : (
                            searchData?.map((data, index) => (
                                <div className="job__box" key={index}>
                                    {/* <div className="job__tag">hot</div> */}
                                    <div className="job__logo">
                                        {/* <img src={data.Company.avatar} alt="" /> */}
                                    </div>
                                    <div className="job__content">
                                        <div className="job__title">
                                            <Link to={`/jobs/work/${data.id}`}>
                                                <h4 className="jobTitle" title={data.name}>
                                                    {data.name}
                                                </h4>
                                            </Link>
                                        </div>
                                        <div className="job__nameCompany">
                                            <Link to={`/jobs/work/${data.id}`}>
                                                {" "}
                                                {/* <span>{data.Company.name}</span> */}
                                            </Link>
                                        </div>
                                        <div className="job__detail">
                                            <div className="job__detail--address">
                                                <div className="job__icon">
                                                    <i className="fas fa-map-marker-alt"></i>
                                                </div>
                                                <span>{data.address}</span>
                                            </div>
                                            <div className="job__detail--deadline outSize outSize">
                                                <div className="job__icon">
                                                    <i className="far fa-clock"></i>
                                                </div>
                                                <span>{formatDateWork(data.dealtime)}</span>
                                            </div>
                                            <div className="job__detail--salary">
                                                <div className="job__icon">
                                                    <i className="fas fa-dollar-sign"></i>
                                                </div>
                                                <span>
                                                    {(data.salaryFrom && data.salaryTo) ?
                                                        data.salaryFrom + " - " + data.salaryTo + " " + data.currency
                                                        : "Thương lượng"
                                                    }
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            ))
                        )}
                        {searchData === "" ? (
                            loading ? (
                                <SpinLoad />
                            ) : (
                                <div className="pagination">
                                    <Pagination
                                        defaultCurrent={page}
                                        onChange={onChangePage}
                                        total={work.count}
                                    />
                                </div>
                            )
                        ) : (
                            ""
                        )}
                    </div>
                    <div className="col-md-4">
                        <div className="box__filter">
                            <div className="filter--title">
                                <p>Thời gian làm việc </p>
                            </div>
                            <div className="filter__content">
                                <Radio.Group onChange={onChangeTime} value={time}>
                                    <Radio className="mb-1" value="0">
                                        Tất cả
                                    </Radio>
                                    <br />
                                    <Radio className="mb-1" value="Full Time">
                                        Full Time
                                    </Radio>
                                    <br />
                                    <Radio value="Part Time">Part Time</Radio>
                                </Radio.Group>
                            </div>
                        </div>
                        <div className="box__filter">
                            <div className="filter--title">
                                <p>Loại công việc</p>
                            </div>
                            <div className="filter__content">
                                <Radio.Group onChange={onChangeTypeWork} value={typeWorkValue}>
                                    <Radio className="mb-1" value="">
                                        Tất cả
                                    </Radio>
                                    <br />
                                    {typework?.rows?.map((data) => (
                                        <React.Fragment key={data.id}>
                                            <Radio className="mb-1" value={data.id}>
                                                {data.name}
                                            </Radio>
                                            <br />
                                        </React.Fragment>
                                    ))}
                                </Radio.Group>
                            </div>
                        </div>
                        <KeyTag />
                    </div>
                </div>
            </div>
        </div>
    );
}