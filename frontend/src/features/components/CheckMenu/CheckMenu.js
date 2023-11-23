import React from "react";
import { useMatch } from "react-router-dom";
import Menu from "../Home/Menu/Menu";
function CheckMenu() {
    const { path } = useMatch();
    const HidenMenu = () => {
        return <div></div>;
    };
    return <div>{path === "/" ? <Menu /> : <HidenMenu />}</div>;
}

export default CheckMenu;