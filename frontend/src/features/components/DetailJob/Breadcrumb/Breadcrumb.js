import React from 'react'
import "../../../styles/Breadcrumb.scss"
import { Link } from 'react-router-dom';
function Breadcrumb(props) {

    return (
        <div className="breadcrumb">
            <div className="container">
                <Link to="/">Trang chủ</Link>
                <span className="fa fa-angle-double-right"></span>
                <Link to="/">Việc làm</Link>
                <span className="fa fa-angle-double-right"></span>
                <span className="active">{props.name}</span>
            </div>
        </div>
    )
}

export default Breadcrumb;