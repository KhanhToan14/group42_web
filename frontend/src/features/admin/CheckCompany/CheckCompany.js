import React, { useEffect, useState, useCallback } from "react";
import { Pagination, Popconfirm, Spin, Table } from "antd";
import { Link, useRouteMatch } from "react-router-dom";
import { QuestionCircleOutlined } from "@ant-design/icons";
import { useDispatch, useSelector } from "react-redux";
import {
    removecheckCompany,
    checkCompanyData,
} from "../Slice/checkCompanySlice";
import { updatecompany } from "../Slice/companySlice";
function CheckCompany() {
    const columns = [
        {
            title: "Công ty",
            dataIndex: "name",
        },
        {
            title: "email",
            dataIndex: "email",
        },
        {
            title: "Duyệt",
            dataIndex: "status",
        },
        {
            title: "Xoá",
            dataIndex: "action",
        },
    ];

    const match = useRouteMatch();
    const checkCompanys = useSelector(
        (state) => state.checkCompanys.checkCompany.data
    );

    const loading = useSelector((state) => state.checkCompanys.loading);
    const dispatch = useDispatch();
    const [state, setState] = useState({
        page: localStorage.getItem("pageCheckCompany") || 1,
    });
    const { page } = state;

    const actionResult = async (page) => {
        await dispatch(checkCompanyData(page));
    };

    useEffect(() => {
        localStorage.setItem("pagecheckCompany", page);
        actionResult({ page: page });
    }, [page]);

    const handleStatus = (e, id) => {
        if (e === 1) {
            dispatch(updatecompany({ status: 0, id: id }));
        } else {
            dispatch(updatecompany({ status: 1, id: id }));
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

    const hangdleDelete = (e) => {
        dispatch(removecheckCompany(e));
        setTimeout(() => {
            actionResult({ page: page });
        }, 500);
    };

    return (
        <div id="admin">
            <div className="heading">
                <div className="heading__title">
                    <h3>Kiểm tra tài khoản công ty</h3>
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
                            dataSource={checkCompanys.rows.map((ok, index) => ({
                                key: index + 1,
                                name: ok.name,
                                email: ok.email,
                                status: (
                                    <div className="action">
                                        {ok.status === 1 ? (
                                            <Link
                                                onClick={() => {
                                                    handleStatus(ok.status, ok.id);
                                                }}
                                            >
                                                <i className="far fa-thumbs-up "></i>
                                            </Link>
                                        ) : (
                                            <Link onClick={() => handleStatus(ok.status, ok.id)}>
                                                <i className="fas fa-check"></i>
                                            </Link>
                                        )}
                                    </div>
                                ),
                                action: (
                                    <div className="action">
                                        <Popconfirm
                                            title="Bạn có muốn xoá？"
                                            onConfirm={() => {
                                                hangdleDelete(ok.id);
                                            }}
                                            icon={<QuestionCircleOutlined style={{ color: "red" }} />}
                                        >
                                            <Link>
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
                            total={checkCompanys.count}
                        />
                    </div>
                )}
            </div>
        </div>
    );
}

export default CheckCompany;