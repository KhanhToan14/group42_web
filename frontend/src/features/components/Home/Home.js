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
            <ListCategories />
            <ListJobs />
            <Contact />
            <Footer />
        </div>
    );
}
