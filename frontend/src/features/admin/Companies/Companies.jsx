import { QuestionCircleOutlined } from "@ant-design/icons";
import { Pagination, Popconfirm, Spin, Table } from "antd";
import React, { useEffect, useRef, useState } from "react";
import { useDispatch } from "react-redux";
import { useRouteMatch } from "react-router-dom";
import { Link } from "react-router-dom";
import companyApi from "../../../api/companyApi";
import { removecompany } from "../Slice/companySlice";
function Companies() {
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

    // const companys = useSelector((state) => state.companys.company.data);
    // const loading = useSelector((state) => state.companys.loading);
    const dispatch = useDispatch();
    const [state, setState] = useState({
        page: localStorage.getItem("pageUser") || 1,
        nameCompanies: "",
        companys: [],
        loading: true,
    });
    const { page, nameCompanies, companys, loading } = state;
    const [isLoad, setIsLoad] = useState(false);

    const debounce = useRef(null);

    useEffect(() => {
        localStorage.setItem("pageUser", page);
        companyApi.search({ page, name: nameCompanies, status: 1 }).then((data) => {
            setState({ ...state, companys: data.data, loading: false });
        });
    }, [page, isLoad, nameCompanies, state]);

    const onChangePage = (page) => {
        setState({
            page: page,
            pageCurent: page,
        });
    };

    const hangdleDelete = (e) => {
        dispatch(removecompany(e));
        setTimeout(() => {
            setIsLoad(!isLoad);
        }, 500);
    };

    const handleOnchange = (e) => {
        let { value } = e.target;
        setState({
            ...state,
            nameCompanies: value,
        });
        if (debounce.current) {
            clearTimeout(debounce.current);
        }
        debounce.current = setTimeout(() => {
            setIsLoad(!isLoad);
        }, 500);
    };

    const match = useRouteMatch();

    return (
        <div id="admin">
            <div className="heading">
                <div className="heading__title">
                    <h3>Công ty</h3>
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
                        <div className="input-seach">
                            <input
                                type="text"
                                value={nameCompanies}
                                onChange={handleOnchange}
                                placeholder="Nhập tên công ty..."
                            />
                        </div>
                        <Table
                            columns={columns}
                            pagination={false}
                            dataSource={companys.rows.map((ok, index) => ({
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
                            total={companys.count}
                        />
                    </div>
                )}
            </div>
        </div>
    );
}

export default Companies;