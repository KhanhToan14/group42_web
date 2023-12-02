import "antd/dist/antd.css";
import React, { useEffect, useState } from "react";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  useRouteMatch
} from "react-router-dom";
import checkLoginApi from "./api/checkLogin";
import "./App.scss";
import Admin from "./app/Admin";
import Candidates from "./features/components/Candidates/Candidates";
import CheckMenu from "./features/components/CheckMenu/CheckMenu";
import Company from "./features/components/Company/Company";
import DetailCandidate from "./features/components/DetailCandidate/DetailCandidate";
import DetailCompany from "./features/components/DetailCompany/DetailCompany";
import DetailJob from "./features/components/DetailJob/DetailJob";
import Empty from "./features/components/Empty/Empty";
import Home from "./features/components/Home/Home";
import InforCompany from "./features/components/InfoCompany/InfoCompany";
import InforUser from "./features/components/InfoUser/InfoUser";
import Jobs from "./features/components/Jobs/Jobs";
import Login from "./features/components/Login/Login";
import LoginAdmin from "./features/components/Login/LoginAdmin";
import Register from "./features/components/Register/Register";
import { checkBar } from "./features/container/Functionjs";
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
