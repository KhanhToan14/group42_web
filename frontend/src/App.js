import React, { useEffect, useState } from "react";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  useRouteMatch
} from "react-router-dom";
import "./App.scss";
import Company from "./components/Company/Company";
import Home from "./components/Home/Home";
import InforCompany from "./components/InfoCompany/InfoCompany";
import InforUser from "./components/InfoUser/InfoUser";
import Login from "./components/Login/Login";
import LoginAdmin from "./components/Login/LoginAdmin";
import Register from "./components/Register/Register";
import { checkBar } from "./utils/Functionjs";
function App() {
  useEffect(() => {
    checkBar();
  }, []);

  const [isLoad, setIsLoad] = useState(true);

  const handleLogin = () => {
    setIsLoad(!isLoad);
  };

  const [checkAdmin, setCheckAdmin] = useState();
  useEffect(() => {
    checkLoginApi.checkLogin().then((ok) => {
      let user = ok.data.user.role;
      if (user === "admin" || user === "grant") {
        setCheckAdmin(
          <Route path="/admin">
            <Ladmin />
          </Route>,
        );
      } else {
        setCheckAdmin(
          <Route path="/admin">
            <Empty />
          </Route>,
        );
      }
    });
  }, [isLoad]);

  return (
    <div>
      <Router>
        <Switch>
          <Route path={["/admin", "/register", "/Login", "/checkadmin", "/loginAdmin", "/",]}>
            <CheckMenu />
          </Route>
        </Switch>

        <Switch>
          <Route exact path="/">
            <Home />
          </Route>
          {checkAdmin}
          <Route exact path="/jobs">
            <Jobs />
          </Route>
          <Route exact path="/jobs/work/:id">
            <DetailJob />
          </Route>
          <Route exact path="/checkadmin/jobs/work/:id">
            <DetailJob isAdmin />
          </Route>
          <Route exact path="/companys">
            <Company />
          </Route>
          <Route exact path="/companys/:id">
            <DetailCompany />
          </Route>
          <Route exact path="/candidates">
            <Candidates />
          </Route>
          <Route exact path="/candidates/:id">
            <DetailCandidate />
          </Route>
          <Route exact path="/login">
            <Login onLogin={handleLogin} />
          </Route>
          <Route exact path="/loginAdmin">
            <LoginAdmin onLogin={handleLogin} />
          </Route>
          <Route exact path="/register">
            <Register />
          </Route>
          <Route exact path="/inforCompany">
            <InforCompany />
          </Route>
          <Route exact path="/inforUser">
            <InforUser />
          </Route>

        </Switch>
      </Router>
    </div>
  );
}
function Ladmin() {
  let { path, url } = useRouteMatch();

  return <Admin path={path} url={url} />;
}
export default App;
