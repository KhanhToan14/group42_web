import { QuestionCircleOutlined } from "@ant-design/icons";
import { Pagination, Popconfirm, Spin, Table } from "antd";
import React, { useEffect, useState, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useRouteMatch, useHistory } from "react-router-dom";
import { Link } from "react-router-dom";
import { removeuser, userData } from "../Slice/userSlice";
function Candidates() {
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

    const actionResult = useCallback(() => {
        dispatch(userData(page));
    }, [page, dispatch]);

    useEffect(() => {
        localStorage.setItem("pageUser", page);
        actionResult({ page: page });
    }, [page, actionResult]);

    const history = useHistory();

    const onChangePage = (page) => {
        setState({
            page: page,
            pageCurent: page,
        });
    };
    const match = useRouteMatch();
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
                                avatar: <img src={ok.avatar} alt="profile" width="200px" />,
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
        </div >
    );
}

export default Candidates;