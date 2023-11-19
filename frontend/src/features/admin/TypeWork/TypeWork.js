import React, { useEffect, useState } from "react";
import { Button, Pagination, Popconfirm, Spin, Table } from "antd";
import { Link, useHistory, useRouteMatch } from "react-router-dom";
import { QuestionCircleOutlined } from "@ant-design/icons";
import { useDispatch, useSelector } from "react-redux";
import renderHTML from "react-render-html";
import {
    removetypeWork,
    typeWorkData,
    updatetypeWork,
} from "../Slice/typeWorkSlice";
export default function TypeWork() {
    const columns = [
        {
            title: "Loại công việc",
            dataIndex: "name",
        },
        {
            title: "Icon",
            dataIndex: "icon",
        },
        {
            title: "Tình trạng",
            dataIndex: "status",
        },
        {
            title: "Sửa/Xoá",
            dataIndex: "action",
        },
    ];

    const match = useRouteMatch();
    const typeWork = useSelector((state) => state.typeWorks.typeWork.data);
    const loading = useSelector((state) => state.typeWorks.loading);
    const dispatch = useDispatch();
    const [state, setState] = useState({
        page: localStorage.getItem("pagetypeWork") || 1,
    });
    const { page } = state;
    const actionResult = async (page) => {
        await dispatch(typeWorkData(page));
    };
    useEffect(() => {
        localStorage.setItem("pagetypeWork", page);
        actionResult({ page: page });
    }, [page]);
    const history = useHistory();
    const handleStatus = (e, id) => {
        if (e === 1) {
            dispatch(updatetypeWork({ status: 0, id: id }));
        } else {
            dispatch(updatetypeWork({ status: 1, id: id }));
        }
        setTimeout(() => {
            actionResult({ page: page });
        }, 500);
    };
    const onChangePage = (page) => {
        setState({
            page: page,
            pageCurent: page,
        });
    };
    const hangdleEdit = (id) => {
        history.replace(`${match.url}/edittypeWork/${id}`);
    };
    const hangdleDelete = (e) => {
        dispatch(removetypeWork(e));
        setTimeout(() => {
            actionResult({ page: page });
        }, 500);
    };
    return (
        <div id="admin">
            <div className="heading">
                <div className="heading__title">
                    <h3>Loại công việc</h3>
                </div>
                <div className="heading__hr"></div>
            </div>
            <div className="content">
                <div className="add">
                    <Link to={`${match.url}/addTypeWork`}>
                        <Button variant="outlined" color="secondary">
                            <i className="fas fa-plus"></i>&nbsp;&nbsp; Thêm mới
                        </Button>
                    </Link>
                </div>
                {loading ? (
                    <div className="spin">
                        <Spin className="mt-5" />
                    </div>
                ) : (
                    <div>
                        <Table
                            columns={columns}
                            pagination={false}
                            dataSource={typeWork.rows.map((ok, index) => ({
                                key: index + 1,
                                name: ok.name,
                                icon: <div className="iconTypeWork">{renderHTML(ok.icon)}</div>,
                                status: (
                                    <div className="action">
                                        {ok.status === 1 ? (
                                            <Link
                                                to="#"
                                                onClick={() => {
                                                    handleStatus(ok.status, ok.id);
                                                }}
                                            >
                                                <i className="far fa-thumbs-up "></i>
                                            </Link>
                                        ) : (
                                            <Link
                                                to="#"
                                                onClick={() => handleStatus(ok.status, ok.id)}
                                            >
                                                <i className="far fa-thumbs-down "></i>
                                            </Link>
                                        )}
                                    </div>
                                ),
                                action: (
                                    <div className="action">
                                        <Popconfirm
                                            title="Bạn có muốn sửa？"
                                            onConfirm={() => {
                                                hangdleEdit(ok.id);
                                            }}
                                            icon={
                                                <QuestionCircleOutlined style={{ color: "green" }} />
                                            }
                                        >
                                            <Link to="#">
                                                <i className="far fa-edit mr-4"></i>
                                            </Link>
                                        </Popconfirm>
                                        <Popconfirm
                                            title="Bạn có muốn xoá？"
                                            onConfirm={() => {
                                                hangdleDelete(ok.id);
                                            }}
                                            icon={<QuestionCircleOutlined style={{ color: "red" }} />}
                                        >
                                            <Link to="#">
                                                <i className="far fa-trash-alt"></i>
                                            </Link>
                                        </Popconfirm>
                                    </div>
                                ),
                            }))}
                        />
                        <Pagination
                            defaultCurrent={page}
                            onChange={onChangePage}
                            total={typeWork.count}
                        />
                    </div>
                )}
            </div>
        </div>
    );
}
