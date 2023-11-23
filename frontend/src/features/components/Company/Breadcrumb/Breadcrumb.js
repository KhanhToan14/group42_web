import React from 'react'
import { Link } from 'react-router-dom'
import "../../../styles/Breadcrumb.scss"
function Breadcrumb() {
    return (
        <div className="breadcrumb">
            <div className="container">
                <Link to="/">Trang chủ</Link>
                <span className="fa fa-angle-double-right"></span>
                <span className="active">Công ty</span>
            </div>
        </div>
    )
}

export default Breadcrumb;