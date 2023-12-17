import React from "react";
import Banner from "./Banner/Banner";
import Contact from "./Contact/Contact";
import Footer from "./Footer/Footer";
import ListCategories from "./ListCategories/ListCategories";
import ListJobs from "./ListJobs/ListJobs";

export default function Home() {
    return (
        <div>
            {/* <Menu /> */}
            <Banner />
            {/* <div style={{ display: "grid", gridTemplateColumns: "repeat(3, 1fr)" }}>
                <ListCategories style={{ flex: 1 }} /> */}
            <ListJobs style={{ flex: 1 }} />
            {/* <Contact style={{ flex: 1 }} />
            </div> */}
            <Footer />
            <div style={{ display: "grid", gridTemplateColumns: "repeat(3, 1fr)" }}>
                {/* <ListCategories style={{ flex: 1 }} /> */}
                <ListJobs style={{ flex: 1 }} />
                {/* <Contact style={{ flex: 1 }} /> */}
            </div>
            {/* <Footer /> */}
        </div>
    );
}