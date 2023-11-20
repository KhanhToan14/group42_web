import React from "react";
import { useMatch } from "react-route-dom";
import Menu from "../Home/Menu/Menu";
export default function CheckMenu() {
    const { path } = useMatch();
    const HidenMenu = () => {
        return <div></div>;
    };
    return <div>{path === "/" ? <Menu /> : <HidenMenu />}</div>;
}