import React from "react";
import "../../../styles/Breadcrumb.scss"
import { Link } from "react-router-dom";
export default function Breadcrumbs() {
    return (
        <div className="breadcrumb">
            <div className="container">
                <Link to="/">Trang chủ</Link>
                <span className="fa fa-angle-double-ringt"></span>
                <span className="active">Việc làm</span>
            </div>
        </div>
    )
}