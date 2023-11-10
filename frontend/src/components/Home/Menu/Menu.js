import React from "react";
import { useRouteMatch } from "react-router-dom";
import Mn from "./Mn";
// import "./menujs"
export default function ListMenu() {
    const match = useRouteMatch();
    let checkMenu = match.isExact;
    return (
        <div>
            <Mn class={`menu ${checkMenu ? "" : "notMenu"}`} />
        </div>
    );
}
