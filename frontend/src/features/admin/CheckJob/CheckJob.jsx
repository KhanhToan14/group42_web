import React, { useEffect, useState } from "react";
import { Button, Pagination, Popconfirm, Spin, Table } from "antd";
import { Link, useHistory, useRouteMatch } from "react-router-dom";
import { QuestionCircleOutlined } from "@ant-design/icons";
import { useDispatch, useSelector } from "react-redux";
import { workCensorshipData, updateWorkCensorship } from "../Slice/workCensorshipSlice";
export default function CheckJob() {
    const columns = [
        {
            title: "Tên Công việc",
            dataIndex: "name",
        },
        {
            title: "Duyệt",
            dataIndex: "access",
        },
        {
            title: "Từ chối",
            dataIndex: "reject",
        },
    ];

    const worksCensorship = useSelector(
        (state) => state.worksCensorship.workCensorship.data
    );

    const loading = useSelector((state) => state.worksCensorship.loading);
    const dispatch = useDispatch();
    const [state, setState] = useState({
        page: localStorage.getItem("pageCheckJob") || 1,
    });
    const { page } = state;
    const actionResult = (page) => {
        dispatch(workCensorshipData(page));
    };

    useEffect(() => {
        localStorage.setItem("pagecheckJob", page);
        actionResult({ page: page });
    }, [page]);

    const handleCensorship = (id) => {
        dispatch(updateWorkCensorship({ censorship: 1, id: id }));
        setTimeout(() => {
            actionResult({ page: page });
        }, 500);
    };

    const handleReject = (id) => {
        dispatch(updateWorkCensorship({ censorship: 0, id: id }));
        setTimeout(() => {
            actionResult({ page: page });
        }, 500);
    }

    const onChangePage = (page) => {
        setState({
            page: page,
            pageCurent: page,
        });
    };

    const handleOpenNewTab = (id) => {
        const originPath = window.location.origin;

        window.open(originPath + "/checkadmin/jobs/work/" + id)
    }

    return (
        <div id="admin">
            <div className="heading">
                <div className="heading__title">
                    <h3>Kiểm duyệt công việc</h3>
                </div>
                <div className="heading__hr"></div>
            </div>
            <div className="content">
                {loading ? (
                    <div className="spin">
                        <Spin className="mt-5" />
                    </div>
                ) : (
                    <div>
                        <Table
                            columns={columns}
                            pagination={false}
                            dataSource={worksCensorship.rows.map((ok, index) => ({
                                key: index + 1,
                                name: (
                                    <Link to="#" onClick={() => handleOpenNewTab(ok.id)}>{ok.name}</Link>
                                ),
                                access: (
                                    <div className="action">
                                        <Link
                                            onClick={() => {
                                                handleCensorship(ok.id);
                                            }}
                                        >
                                            <span className="fas fa-check"></span>
                                        </Link>
                                    </div>
                                ),
                                reject: (
                                    <div className="action">
                                        <Popconfirm
                                            title="Bạn có muốn từ chối?"
                                            onConfirm={() => {
                                                // hangdleDelete(ok.id);
                                                handleReject(ok.id)
                                            }}
                                            icon={<QuestionCircleOutlined style={{ color: "red" }} />}
                                        >
                                            <Link>
                                                <span class="fas fa-window-close"></span>
                                            </Link>
                                        </Popconfirm>
                                    </div>
                                ),
                            }))}
                        />
                        <Pagination
                            defaultCurrent={page}
                            onChange={onChangePage}
                            total={worksCensorship.count}
                        />
                    </div>
                )}
            </div>
        </div>
    );
}
