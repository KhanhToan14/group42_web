import React from 'react'
import "../../../styles/Breadcrumb.scss"
import { Link } from 'react-router-dom';
function Breadcrumbs({ name }) {

    return (
        <div className="breadcrumb">
            <div className="container">
                <Link to="/">Trang chủ</Link>
                <span className="fa fa-angle-double-right"></span>
                <Link to="/companys">Công ty</Link>
                <span className="fa fa-angle-double-right"></span>
                <span className="active">{name}</span>
            </div>
        </div>
    )
}

export default Breadcrumbs;