import { Pagination, Spin, Table } from "antd";
import React, { useEffect, useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { updatework, workData } from "../Slice/workSlice";
export default function Work() {
    const columns = [
        {
            title: "Tên công việc",
            dataIndex: "name",
        },
        {
            title: "Tình trạng",
            dataIndex: "status",
        },
    ];

    const work = useSelector((state) => state.works.work.data);
    const loading = useSelector((state) => state.works.loading);
    const [isLoad, setIsLoad] = useState(false);

    const debounce = useRef(null);
    const dispatch = useDispatch();
    const [state, setState] = useState({
        page: localStorage.getItem("pagework") || 1,
    });
    const { page } = state;
    const actionResult = async (page, name) => {
        await dispatch(workData(page, name));
    };
    const [nameCompanies, setNameCompanies] = useState("");
    useEffect(() => {
        localStorage.setItem("pagework", page);
        actionResult({ page: page, name: nameCompanies });
    }, [page, isLoad]);
    const handleStatus = (e, id) => {
        if (e === 1) {
            dispatch(updatework({ status: 0, id: id }));
        } else {
            dispatch(updatework({ status: 1, id: id }));
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

    const handleOnchange = (e) => {
        const { value } = e.target;
        setNameCompanies(value);
        if (debounce.current) {
            clearTimeout(debounce.current);
        }
        debounce.current = setTimeout(() => {
            setIsLoad(!isLoad);
        }, 500);
    };

    return (
        <div id="admin">
            <div className="heading">
                <div className="heading__title">
                    <h3>Công việc</h3>
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
                                autoFocus
                                onChange={handleOnchange}
                                placeholder="Tìm theo tên công ty..."
                            />
                        </div>
                        <Table
                            columns={columns}
                            pagination={false}
                            dataSource={work.rows.map((ok, index) => ({
                                key: index + 1,
                                name: <Link to={`/jobs/work/${ok.id}`}>{ok.name}</Link>,
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
                            }))}
                        />
                        <Pagination
                            defaultCurrent={page}
                            onChange={onChangePage}
                            total={work.count}
                        />
                    </div>
                )}
            </div>
        </div>
    );
}
