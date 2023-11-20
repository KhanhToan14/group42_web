import { QuestionCircleOutlined } from "@ant-design/icons";
import { Pagination, Popconfirm } from "antd";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import workApi from "../../../../../api/workApi";
import { formatDateWork } from "../../../../container/Functionjs";
import SpinLoad from "../../../Spin/Spin";

export default function JobsReject({
    id,
    heard,
    hident,
    onChangeTabs,
    onIdEdit,
    resetJob,
    statusJobs = true
}) {
    const [data, setData] = useState();
    const [state, setState] = useState({
        page: localStorage.getItem("pageWorkHomeInfor") || 1,
    });

    const { page } = state;
    const [loadEffect, setLoadEffect] = useState(false);

    const getApi = async () => {
        await workApi.getAllRejectId({ page: page, id: id }).then((data) => {
            setData(data);
        });
    };

    useEffect(() => {
        localStorage.setItem("pageWorkHomeInfor", page);
        getApi();
    }, [page, loadEffect, resetJob]);

    const hangdleDelete = async (e) => {
        await workApi.deletework(e);
        setLoadEffect(!loadEffect);
    };

    const checkStatusCensorship = (status) => {
        let obj = {
            1: "Đã duyệt",
            null: "Chờ duyệt",
            0: "Từ chối",
        }
        return obj[status]
    }

    return (
        <div className="ListJob">
            {heard ? (
                <div className="heading">
                    <div className="heading__title">
                        <h3>Việc bị từ chối</h3>
                    </div>
                    <div className="heading__hr"></div>
                </div>
            ) : (
                ""
            )}

            <div className="content">
                <div className="row">
                    {!data ? (
                        <SpinLoad />
                    ) : (
                        data.data.rows.map((ok, index) => (
                            <div className="col-lg-12" key={index}>
                                <div className="job__box mb-3">
                                    {hident ? (
                                        ""
                                    ) : (
                                        <>
                                            <Popconfirm
                                                title="Bạn có muốn xoá？"
                                                onConfirm={() => {
                                                    hangdleDelete(ok.id);
                                                }}
                                                icon={
                                                    <QuestionCircleOutlined style={{ color: "green" }} />
                                                }
                                            >
                                                <div className="btn-delete-job">Xoá Công việc</div>
                                            </Popconfirm>
                                            {/* <Popconfirm
                                                title="Bạn có muốn sửa?"
                                                onConfirm={() => {
                                                    onChangeTabs("2");
                                                    onIdEdit(ok.id);
                                                }}
                                                icon={
                                                    <QuestionCircleOutlined style={{ color: "green" }} />
                                                }
                                            >
                                                <div className="btn-edit-job">Sửa Công việc</div>
                                            </Popconfirm> */}
                                        </>
                                    )}
                                    {/* <div className="job__tag">hot</div> */}
                                    <div className="job__logo">
                                        <img src={ok.Company.avatar} alt="" />
                                    </div>
                                    <div className="job__content">
                                        <div className="job__title">
                                            <Link to={`/jobs/work/${ok.id}`}>
                                                <h4 className="jobTitle">{ok.name}</h4>
                                            </Link>
                                        </div>
                                        <div className="job__nameCompany">
                                            <Link to={`/jobs/work/${ok.id}`}>
                                                <span>{ok.Company.name}</span>
                                            </Link>
                                        </div>
                                        <div className="job__detail">
                                            <div className="job__detail--address">
                                                <div className="job__icon">
                                                    <i className="fas fa-map-marker-alt"></i>
                                                </div>
                                                <span>{ok.address}</span>
                                            </div>
                                            <div className="job__detail--deadline outSize outSize">
                                                <div className="job__icon">
                                                    <i className="far fa-clock"></i>
                                                </div>
                                                <span>{formatDateWork(ok.dealtime)}</span>
                                            </div>
                                            <div className="job__detail--salary">
                                                <div className="job__icon">
                                                    <i className="fas fa-dollar-sign"></i>
                                                </div>
                                                <span>
                                                    {(ok.price1 && ok.price2) ?
                                                        ok.price1 + " - " + ok.price2 + " Triệu VNĐ"
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
                    {!data ? (
                        ""
                    ) : (
                        <div className="pagination">
                            {data.data.count === 0 ? (
                                ""
                            ) : (
                                <Pagination defaultCurrent={page} total={data.data.count} />
                            )}
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}
