import React, { useEffect, useState } from "react";
import workApi from "../../../api/workApi";
import { getQueryVariable } from "../../container/Functionjs";
import Footer from "../Home/Footer/Footer";
import Breadcrumbs from "./Breadcrumb/Breadcrumb";
import Job from "./ListJobs/Job";
import Search from "./Search/Search";
export default function Jobs() {
    const [state, setState] = useState({
        name: getQueryVariable("name") || "",
        location: getQueryVariable("location") || "",
        data: "",
    });
    const { name, location, data } = state;
    const [time, setTime] = useState("0");
    const [amount, setAmount] = useState("0");
    const [typeWorkValue, setTypeWorkValue] = useState(
        +getQueryVariable("typeWordId") || "",
    );
    const hangdelOnChange = (e) => {
        const { name, location } = e;
        console.log(e);
        setState({
            ...state,
            name: name,
            location: location,
        });
    };
    const onChangeTime = (e) => {
        setTime(e);
    };
    const onChangeAmount = (e) => {
        setAmount(e);
    };
    const onChangeTypeWork = (e) => {
        setTypeWorkValue(e);
    };
    useEffect(() => {
        console.log(name);
        workApi
            .search({
                keyword: name,
                // name: name,
                // nature: time,
                // location: location,
                // status: 1,
                // typeWordId: typeWorkValue,
            })
            .then(result => {
                console.log(result.data);
                setState({
                    ...state,
                    data: result.data,
                });
            });
        window.scrollTo(0, 0);
    }, [state.name]);
    return (
        <div>
            <Breadcrumbs />
            <Search
                nameSearch={name}
                locationSearch={location}
                onchange={hangdelOnChange}
            />
            <Job
                searchData={
                    data
                        ? data
                        : ""
                }
                onAmout={onChangeAmount}
                onTime={onChangeTime}
                time={time}
                amount={amount}
                typeWorkValue={typeWorkValue}
                onTypeWork={onChangeTypeWork}
            />
            <Footer />
        </div>
    );
}