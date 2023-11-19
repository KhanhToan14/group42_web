import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import workApi from "../../../api/workApi";
import Footer from "../Home/Footer/Footer";
import BannerJob from "./BannerJob/BannerJob";
import Breadcrumb from "./Breadcrumb/Breadcrumb";
import Jd from "./Jd/Jd";

export default function DetailJob({ isAdmin }) {
    const { id } = useParams();
    const [isLoad, setIsLoad] = useState(false)
    const getApi = async () => {
        return await workApi.getOne(id).then((data) => {
            return data;
        });
    };
    const [data, setData] = useState();
    useEffect(() => {
        Promise.all([getApi()]).then(function (data) {
            setData(data[0]);
        });
        window.scrollTo(0, 0);
    }, [isLoad]);

    const reload = () => {
        setIsLoad(!isLoad)
    }
    return (
        <div>
            {!isAdmin && <Breadcrumb name={data ? data.name : ""} />}

            <BannerJob
                name={data ? data.Company.name : ""}
                avatar={data ? data.Company.avatar : ""}
                banner={data ? data.Company.banner : ""}
            />
            {data ? <Jd data={data} id={id} isAdmin={isAdmin} reload={reload} /> : ""}
            {!isAdmin && <Footer />}
        </div>
    );
}
