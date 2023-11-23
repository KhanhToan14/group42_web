import React from "react";
import Footer from "../Home/Footer/Footer";
import Breadcrumb from "./Breadcrumb/Breadcrumb";
import Companys from "./Companys/Companys";
function Company() {
    return (
        <div>
            {/* <Menu /> */}
            <Breadcrumb />
            <Companys />
            <Footer />
        </div>
    );
}

export default Company;