import React, { useEffect, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { typeWorkData } from "../../../admin/Slice/typeWorkSlice";
import { GetCategoryHome } from "../../../container/Functionjs";
import "../../../styles/Home/ListCategories.scss";
import SpinLoad from "../../Spin/Spin";
import renderHTML from "react-render-html";
function ListCategories() {
    const dispatch = useDispatch();
    const actionResult = () => {
        dispatch(typeWorkData({ status: 1 }));
    };
    const typework = useSelector((state) => state.typeWorks.typeWork.data);
    const loading = useSelector((state) => state.typeWorks.loading);

    useEffect(() => {
        actionResult();
    }, []);

    return (
        <div className="categori">
            <div className="container">
                <div className="heading">
                    <div className="heading__title">
                        <h3>Chọn việc theo nghành</h3>
                    </div>
                    <div className="heading__hr"></div>
                </div>
                <div className="row">
                    {loading ? (
                        <SpinLoad />
                    ) : (
                        GetCategoryHome(typework).map((ok, index) => (
                            <div className="col-lg-3 col-md-4 col-sm-12 " key={index}>
                                <Link
                                    to={`jobs?typeWordId=${ok.id}`}
                                    className="categori__link"
                                >
                                    <div className="categori__box">
                                        <div className="categori__title">{ok.name}</div>
                                        <div className="categori__icon">
                                            {ok.icon ? renderHTML(ok.icon) : ""}
                                        </div>
                                        <div className="categori__total">{ok.length} công việc</div>
                                    </div>
                                </Link>
                            </div>
                        ))
                    )}
                </div>
            </div>
        </div>
    );
}

export default ListCategories;