import React, { useEffect, useCallback } from "react";
import { useState } from "react";
import { useParams } from "react-router-dom";
import companyApi from "../../../api/companyApi";
import Footer from "../Home/Footer/Footer";
import Spin from "../Spin/Spin";
import BannerCompany from "./BannerCompany/BannerCompany";
import Breadcrumb from "./Breadcrumb/Breadcrumb";
import CompanyContent from "./CompanyContent/CompanyContent";

function DetailCompany() {
    const { id } = useParams();
    const [data, setData] = useState();
    const getApi = async () => {
        return await companyApi.getOne(id).then((data) => {
            setData(data);
        });
    };
    useEffect(() => {
        getApi();
        window.scrollTo(0, 0);
    }, []);
    // console.log(data);

    return (
        <div>
            {/* <Menu /> */}
            {data ? (
                <div>
                    <Breadcrumb name={data.name} />
                    <BannerCompany
                        avatar={data.avatar}
                        banner={data.banner}
                        name={data.name}
                        location={data.location}
                    />
                    <CompanyContent data={data} />
                </div>
            ) : (
                <Spin />
            )}
            <Footer />
        </div>
    );
}

export default DetailCompany;