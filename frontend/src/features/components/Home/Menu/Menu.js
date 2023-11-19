import React from "react";
import { useMatch } from "react-router-dom";
import Mn from "./Mn";
// import "./menujs"
export default function ListMenu() {
    const match = useMatch();
    let checkMenu = match.isExact;
    return (
        <div>
            <Mn class={`menu ${checkMenu ? "" : "notMenu"}`} />
        </div>
    );
}
