
import React, { useEffect, useState } from "react";
import {
  BrowserRouter as Routes,
  Route,
  useMatch
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
          <Route path="/admin" element={<Ladmin />} />
        );
      } else {
        setCheckAdmin(
          <Route path="/admin" element={<Empty />} />
        );
      }
    });
  }, [isLoad]);

  return (
    <div>
      <Routes>
        {/* <Route path={["/admin", "/register", "/Login", "/checkadmin", "/loginAdmin", "/",]} element={<CheckMenu />} /> */}
        <Route exact path="/" element={<Home />} />
        {checkAdmin}
        <Route exact path="/jobs" element={<Jobs />} />
        {/* < exact path="/jobs/work/:id" element={<DetailJob />} /> */}
        {/* < exact path="/checkadmin/jobs/work/:id" element={<DetailJob isAdmin />} /> */}
        <Route exact path="/companys" element={<Company />} />
        <Route exact path="/companys/:id" element={<DetailCompany />} />
        <Route exact path="/candidates" element={<Candidates />} />
        <Route exact path="/candidates/:id" element={<DetailCandidate />} />
        <Route exact path="/login" element={<Login onLogin={handleLogin} />} />
        <Route exact path="/loginAdmin" element={<LoginAdmin onLogin={handleLogin} />} />
        {/* <Route exact path="/register" element={<Register />} /> */}
        {/* <Route exact path="/inforCompany" element={<InforCompany />} /> */}
        {/* <Route exact path="/inforUser" element={<InforUser />} /> */}
      </Routes>
    </div>
  );
}
function Ladmin() {
  let { path, url } = useMatch();

  return <Admin path={path} url={url} />;
}
export default App;
