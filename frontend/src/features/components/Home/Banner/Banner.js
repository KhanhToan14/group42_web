import React from "react";
import "../../../styles/Home/Banner.scss";
import { Link } from "react-router-dom";
import { useState } from "react";
import { removeVietnameseTones } from "../../../container/Functionjs";
function Banner() {
    const [state, setState] = useState({ name: "", location: "" });
    const { name, location } = state;
    const onchange = (e) => {
        setState({
            ...state,
            [e.target.name]: e.target.value,
        });
    };
    return (
        <div className="banner">
            <div className="banner__search">
                <div className="banner__search--box">
                    <div className="banner__search--box--title">
                        <h4 className="text-center">
                            Tìm kiếm công việc phù hợp với bản thân
                        </h4>
                    </div>
                    <div className="banner__search--box--content">
                        <input
                            type="text"
                            className="form-control"
                            name="name"
                            value={name}
                            onChange={onchange}
                            id=""
                            aria-describedby="helpId"
                            placeholder="Việc làm mong muốn ..."
                        />
                        <input
                            type="text"
                            className="form-control"
                            name="location"
                            value={location}
                            onChange={onchange}
                            id=""
                            aria-describedby="helpId"
                            placeholder="Địa điểm"
                        />
                        <Link
                            to={`jobs?name=${removeVietnameseTones(
                                name,
                            )}&location=${removeVietnameseTones(location)}`}
                            className="btn btn-primary"
                        >
                            <button type="button">Search</button>
                        </Link>
                    </div>
                    <div className="banner__search--suggestions"></div>
                </div>
            </div>
        </div>
    );
}

export default Banner;