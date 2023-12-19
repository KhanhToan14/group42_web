import React from "react";
import { useRouteMatch } from "react-router-dom";
import Menu from "../Home/Menu/Menu";
function CheckMenu() {
    const { path } = useRouteMatch();
    const HidenMenu = () => {
        return <div></div>;
    };
    return <div>{path === "/" ? <Menu /> : <HidenMenu />}</div>;
}

export default CheckMenu;