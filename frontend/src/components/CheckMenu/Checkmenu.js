import React from "react";
import { useRouteMatch } from "react-route-dom";
import Menu from "../Home/Menu/Menu";
export default function CheckMenu() {
    const { path } = useRouteMatch();
    const HidenMenu = () => {
        return <div></div>;
    };
    return <div>{path === "/" ? <Menu /> : <HidenMenu />}</div>;
}