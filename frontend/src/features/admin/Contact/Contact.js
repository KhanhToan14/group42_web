import React, { useEffect, useState } from "react";
import { Button, Pagination, Popconfirm, Spin, Table } from "antd";
import { Link, useNavigate, useMatch } from "react-router-dom";
import { QuestionCircleOutlined } from "@ant-design/icons";
import { useDispatch, useSelector } from "react-redux";
import {
    removecontact,
    contactData,
    updatecontact,
} from "../Slice/contactSlice";
export default function Contact() {
    const columns = [
        {
            title: "email",
            dataIndex: "email",
        },
        {
            title: "điện thoại",
            dataIndex: "phone",
        },
        {
            title: "Tình trạng",
            dataIndex: "status",
        },
        {
            title: "Action",
            dataIndex: "action",
        },
    ];

    const match = useMatch();
    const contact = useSelector((state) => state.contacts.contact.data);
    const loading = useSelector((state) => state.contacts.loading);
    const dispatch = useDispatch();
    const [state, setState] = useState({
        page: localStorage.getItem("pageContact") || 1,
    });
    const { page } = state;
    const actionResult = async (page) => {
        await dispatch(contactData(page));
    };
    useEffect(() => {
        localStorage.setItem("pageContact", page);
        actionResult({ page: page });
    }, [page]);
    const navigate = useNavigate();
    const handleStatus = (e, id) => {
        if (e === 1) {
            dispatch(updatecontact({ status: 0, id: id }));
        } else {
            dispatch(updatecontact({ status: 1, id: id }));
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
        na(`${match.url}/editContact/${id}`);
    };
    const hangdleDelete = (e) => {
        dispatch(removecontact(e));
        setTimeout(() => {
            actionResult({ page: page });
        }, 500);
    };
    return (
        <div id="admin">
            <div className="heading">
                <div className="heading__title">
                    <h3>Liên hệ</h3>
                </div>
                <div className="heading__hr"></div>
            </div>
            <div className="content">
                <div className="add">
                    <Link to={`${match.url}/addContact`}>
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
                            dataSource={contact.rows.map((ok, index) => ({
                                key: index + 1,
                                email: ok.email,
                                phone: ok.phone,
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
                            total={contact.count}
                        />
                    </div>
                )}
            </div>
        </div>
    );
}
