import { QuestionCircleOutlined } from "@ant-design/icons";
import { Image, Pagination, Popconfirm, Spin, Table } from "antd";
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useMatch } from "react-router-dom";
import { Link, useNavigate } from "react-router-dom";
import { removeuser, userData } from "../Slice/userSlice";
export default function Candidates() {
    const columns = [
        {
            title: "Tên ứng viên",
            dataIndex: "name",
        },
        {
            title: "Ảnh đại diện",
            dataIndex: "avatar",
        },
        {
            title: "Action",
            dataIndex: "action",
        },
    ];

    const users = useSelector((state) => state.users.user.data);
    console.log("users", users);
    const loading = useSelector((state) => state.users.loading);
    const dispatch = useDispatch();
    const [state, setState] = useState({
        page: localStorage.getItem("pageUser") || 1,
    });
    const { page } = state;

    const actionResult = (page) => {
        dispatch(userData(page));
    };

    useEffect(() => {
        localStorage.setItem("pageUser", page);
        actionResult({ page: page });
    }, [page]);

    const navigate = useNavigate();

    const onChangePage = (page) => {
        setState({
            page: page,
            pageCurent: page,
        });
    };
    const match = useMatch();
    const hangdleDelete = (e) => {
        dispatch(removeuser(e));
        setTimeout(() => {
            actionResult({ page: page });
        }, 500);
    };
    return (
        <div id="admin">
            <div className="heading">
                <div className="heading__title">
                    <h3>Ứng viên</h3>
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
                            dataSource={users.rows.map((ok, index) => ({
                                key: index + 1,
                                name: <Link to={`${match.url}/infor/${ok.id}`}>{ok.name}</Link>,
                                avatar: <Image src={ok.avatar} width="200px" />,
                                action: (
                                    <div className="action">
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
                            total={users.count}
                        />
                    </div>
                )}
            </div>
        </div>
    );
}
