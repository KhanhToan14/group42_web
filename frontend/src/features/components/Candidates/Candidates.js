import React from "react";
import Footer from "../Home/Footer/Footer";
import Breadcrumb from "../Company/Breadcrumb/Breadcrumb";
import ListCandidates from "./ListCandidates/ListCandidates";

function Candidates() {
    return (
        <div>
            <Breadcrumb />
            <ListCandidates />
            <Footer />
        </div>
    );
}
export default Candidates;