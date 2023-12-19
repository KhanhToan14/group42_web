import React from "react";
import "../../../styles/SearchJobs/SearchJobs.scss";
import PropTypes from "prop-types";
import { useState } from "react";
function Search({ onchange, nameSearch, locationSearch }) {
    const [state, setState] = useState({
        name: nameSearch,
        location: locationSearch,
    });
    const { name, location } = state;
    const hangdelOnchange = (e) => {
        setState({
            ...state,
            [e.target.name]: e.target.value,
        });
    };
    const onok = (e) => {
        e.preventDefault();
        onchange({ name, location });
    };
    return (
        <div className="searchJobs">
            <div className="container">
                <form onSubmit={onok}>
                    <div className="row">
                        <div className="col-md-6">
                            <div className="key">
                                <div className="key__title">Từ khoá</div>
                                <input
                                    name="name"
                                    value={name}
                                    type="text"
                                    onChange={hangdelOnchange}
                                    placeholder="Việc làm, công ty, kỹ năng, nghành nghề, ..."
                                />
                                <i className="fas fa-search text-silver"></i>
                            </div>
                        </div>
                        <div className="col-md-3">
                            <div className="address">
                                <div className="address__title">Địa điểm</div>
                                <input
                                    type="text"
                                    name="location"
                                    value={location}
                                    onChange={hangdelOnchange}
                                    placeholder="Địa điểm"
                                />
                                <i className="fas fa-map-marker-alt text-silver"></i>
                            </div>
                        </div>
                        <div className="col-md-3">
                            <div className="btn">
                                <button type="submit">Tìm việc làm</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
}

Search.propTypes = {
    name: PropTypes.string,
    location: PropTypes.string,
    onchange: PropTypes.func.isRequired,
};
Search.defaultProps = {
    name: "",
    location: "",
};

export default Search;