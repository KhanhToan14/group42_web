import React from "react";
import { useMatch } from 'react-router-dom';
import Nav from '../features/admin/Layout/Layout';
function Admin() {
    const match = useMatch();
    // console.log({match});
    return (
        <div>
            <Nav url={match.url} path={match.path} />
        </div>
    )
}

export default Admin;