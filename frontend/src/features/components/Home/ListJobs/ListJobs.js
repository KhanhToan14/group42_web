import React, { useEffect, useCallback } from "react";
import { Link } from "react-router-dom";
import "../../../styles/Home/ListJobs.scss";
import { useDispatch, useSelector } from "react-redux";
import { workData } from "../../../admin/Slice/workSlice";
import SpinLoad from "../../Spin/Spin";
import { formatDateWork } from "../../../container/Functionjs";

function ListJobs() {
    const works = useSelector((state) => state.works.work.data);
    console.log(works);
    const loading = useSelector((state) => state.works.loading);
    const dispatch = useDispatch();
    const actionResult = async (page) => {
        dispatch(workData(page));
    };
    useEffect(() => {
        actionResult({ currentPage: 1, status: 1 });
    }, []);

    return (
        <div className="ListJob">
            <div className="heading">
                <div className="heading__title">
                    <h3>
                        <Link to="/jobs">Công việc</Link> nổi bật
                    </h3>
                </div>
                <div className="heading__hr"></div>
            </div>
            <div className="container">
                <div className="row">
                    {loading ? (
                        <SpinLoad />
                    ) : (
                        works.map((work, index) => (
                            <div className="col-lg-6" key={index}>
                                <div className="job__box">
                                    {/* <div className="job__tag">hot</div> */}
                                    <div className="job__logo">
                                        {/* <img src={work.Company.avatar} alt="" /> */}
                                    </div>
                                    <div className="job__content">
                                        <div className="job__title">
                                            <Link to={`/jobs/work/${work.id}`}>
                                                <h4 className="jobTitle">{work.name}</h4>
                                            </Link>
                                        </div>
                                        <div className="job__nameCompany">
                                            <Link to={`/jobs/work/${work.id}`}>
                                                {/* <span>{work.Company.name}</span> */}
                                            </Link>
                                        </div>
                                        <div className="job__detail">
                                            <div className="job__detail--address">
                                                <div className="job__icon">
                                                    <i className="fas fa-map-marker-alt"></i>
                                                </div>
                                                <span>{work.location}</span>
                                            </div>
                                            <div className="job__detail--deadline outSize outSize">
                                                <div className="job__icon">
                                                    <i className="far fa-clock"></i>
                                                </div>
                                                {/* <span>{formatDateWork(work.createdAt)}</span> */}
                                            </div>
                                            <div className="job__detail--salary">
                                                <div className="job__icon">
                                                    <i className="fas fa-dollar-sign"></i>
                                                </div>
                                                <span>
                                                    {(work.salaryFrom && work.salaryTo) ?
                                                        work.salaryFrom + " - " + work.salaryTo + " " + work.currency
                                                        : "Thương lượng"
                                                    }
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        ))
                    )}
                </div>
            </div>
        </div>
    );
}

export default ListJobs;