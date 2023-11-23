import React, { useEffect, useState } from "react";
import workApi from "../../../api/workApi";
import { getQueryVariable } from "../../container/Functionjs";
import Footer from "../Home/Footer/Footer";
import Breadcrumbs from "./Breadcrumb/Breadcrumb";
import Job from "./ListJobs/Job";
import Search from "./Search/Search";
function Jobs() {
    const [state, setState] = useState({
        name: getQueryVariable("name") || "",
        address: getQueryVariable("address") || "",
        data: "",
    });
    const { name, address, data } = state;
    const [time, setTime] = useState("0");
    const [amount, setAmount] = useState("0");
    const [typeWorkValue, setTypeWorkValue] = useState(
        +getQueryVariable("typeWordId") || "",
    );
    const hangdelOnChange = (e) => {
        const { name, address } = e;
        setState({
            ...state,
            name: name,
            address: address,
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
        async function fetchData() {
            const response = await workApi.search({
                name: name,
                nature: time,
                address: address,
                status: 1,
                typeWordId: typeWorkValue,
            });

            console.log("ok", response);
            setState({
                ...state,
                data: response.data,
            });
        }

        fetchData();
    }, [name, address, time, typeWorkValue, state]);

    return (
        <div>
            <Breadcrumbs />
            <Search
                nameSearch={name}
                addressSearch={address}
                onchange={hangdelOnChange}
            />
            <Job
                searchData={
                    name === "" && address === "" && time === "0" && typeWorkValue === ""
                        ? ""
                        : data
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

export default Jobs;