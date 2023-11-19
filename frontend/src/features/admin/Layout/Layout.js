import { MenuFoldOutlined, MenuUnfoldOutlined } from "@ant-design/icons";
import { Layout, Menu } from "antd";
import React, { useEffect, useState } from "react";
import { Link, Route, Switch, useHistory, useRouteMatch } from "react-router-dom";
import "../../styles/Admin/Nav.scss";
import CandidateInfor from "../Candidates/CandidateInfor";
import Candidates from "../Candidates/Candidates";
import CheckCompany from "../CheckCompany/CheckCompany";
import Companies from "../Companies/Companies";
import CompaniesInfor from "../Companies/CompaniesInfor";
import AddContact from "../Contact/AddContact";
import Contact from "../Contact/Contact";
import Jobs from "../Jobs/Jobs";
import Statistical from "../Statistical/Statistical";
import AddSocialNetwork from "../SocialNetwork/AddSocialNetwork";
import SocialNetwork from "../SocialNetwork/SocialNetwork";
import AddTypeWork from "../TypeWork/AddTypeWork";
import TypeWork from "../TypeWork/TypeWork";
import CheckJob from "../CheckJob/CheckJob"
export default function Nav() {
    const match = useRouteMatch();
    const history = useHistory();

    // console.log(match);
    const { Header, Sider, Content } = Layout;
    const [state, setState] = useState({
        collapsed: false,
        visible: true,
    });
    useEffect(() => {
        window.scrollTo(0, 0);
    }, []);

    const toggle = () => {
        setState({
            collapsed: !state.collapsed,
        });
    };


    const Logout = () => {
        localStorage.removeItem("token");
        history.push("/loginAdmin")
    }

    return (
        <div id="nav">
            <Layout>
                <Sider trigger={null} collapsible collapsed={state.collapsed}>
                    <div className="logo">
                        <Link to="/admin">
                            <p className="text-center w-100">
                                {state.collapsed === true ? (
                                    <i className="fas fa-user-shield"></i>
                                ) : (
                                    <strong>Administration</strong>
                                )}
                            </p>
                        </Link>
                    </div>
                    <Menu theme="dark" mode="inline" defaultSelectedKeys={["1"]}>
                        <Menu.Item
                            key="1"
                            icon={
                                state.collapsed === true ? (
                                    <span className="far fa-newspaper"></span>
                                ) : (
                                    <span className="far fa-newspaper mr-2"></span>
                                )
                            }
                        >
                            <Link to="/admin">Thống kê</Link>
                        </Menu.Item>
                        <Menu.Item
                            key="4"
                            icon={
                                state.collapsed === true ? (
                                    <span className="fas fa-check"></span>
                                ) : (
                                    <span className="fas fa-check mr-2"></span>
                                )
                            }
                        >
                            <Link to={`${match.url}/checkCompany`}>Kiểm tra tài khoản</Link>
                        </Menu.Item>
                        <Menu.Item
                            key="50"
                            icon={
                                state.collapsed === true ? (
                                    <span className="fas fa-check"></span>
                                ) : (
                                    <span className="fas fa-check mr-2"></span>
                                )
                            }
                        >
                            <Link to={`${match.url}/checkJobs`}>Duyệt công việc</Link>
                        </Menu.Item>

                        {/* <Menu.Item
                            key="2"
                            icon={
                                state.collapsed === true ? (
                                    <span className="fas fa-tags"></span>
                                ) : (
                                    <span className="fas fa-tags mr-2"></span>
                                )
                            }
                        >
                            <Link to={`${match.url}/tag`}>Tag</Link>
                        </Menu.Item> */}
                        <Menu.Item
                            key="5"
                            icon={
                                state.collapsed === true ? (
                                    <span className="fas fa-code-branch"></span>
                                ) : (
                                    <span className="fas fa-code-branch mr-2"></span>
                                )
                            }
                        >
                            <Link to={`${match.url}/typeWork`}>Loại công việc</Link>
                        </Menu.Item>
                        <Menu.Item
                            key="6"
                            icon={
                                state.collapsed === true ? (
                                    <span className="fas fa-briefcase"></span>
                                ) : (
                                    <span className="fas fa-briefcase mr-2"></span>
                                )
                            }
                        >
                            <Link to={`${match.url}/work`}>Công việc</Link>
                        </Menu.Item>
                        <Menu.Item
                            key="12"
                            icon={
                                state.collapsed === true ? (
                                    <span className="fas fa-user-graduate"></span>
                                ) : (
                                    <span className="fas fa-user-graduate mr-2"></span>
                                )
                            }
                        >
                            <Link to={`${match.url}/candidate`}>Ứng viên</Link>
                        </Menu.Item>
                        <Menu.Item
                            key="13"
                            icon={
                                state.collapsed === true ? (
                                    <span className="fas fa-building"></span>
                                ) : (
                                    <span className="fas fa-building mr-2"></span>
                                )
                            }
                        >
                            <Link to={`${match.url}/companies`}>Công ty</Link>
                        </Menu.Item>
                        <Menu.Item
                            key="8"
                            icon={
                                state.collapsed === true ? (
                                    <span className="fas fa-address-book"></span>
                                ) : (
                                    <span className="fas fa-address-book mr-2"></span>
                                )
                            }
                        >
                            <Link to={`${match.url}/contact`}>Liên hệ</Link>
                        </Menu.Item>
                        <Menu.Item
                            key="10"
                            icon={
                                state.collapsed === true ? (
                                    <span className="fab fa-twitter"></span>
                                ) : (
                                    <span className="fab fa-twitter mr-2"></span>
                                )
                            }
                        >
                            <Link to={`${match.url}/socialNetwork`}>Mạng xã hội</Link>
                        </Menu.Item>
                        <Menu.Item
                            key="11"
                            icon={
                                state.collapsed === true ? (
                                    <span className="fas fa-sign-out-alt"></span>
                                ) : (
                                    <span className="fas fa-sign-out-alt mr-2"></span>
                                )
                            }
                        >
                            <Link to="#" onClick={Logout}>Đăng xuất</Link>
                        </Menu.Item>

                    </Menu>
                </Sider>

                <Layout className="site-layout">
                    <Header className="site-layout-background" style={{ padding: 0 }}>
                        {/* <Headers /> */}
                        {React.createElement(
                            state.collapsed ? MenuUnfoldOutlined : MenuFoldOutlined,
                            {
                                className: "trigger",
                                onClick: toggle,
                            },
                        )}
                    </Header>
                    <Content
                        className="site-layout-background h-100vh"
                        style={{
                            margin: "24px 16px",
                            padding: 24,
                            minHeight: 280,
                        }}
                    >
                        <Switch>
                            <Route exact path={match.path}>
                                <Statistical />
                            </Route>

                            {/* <Route exact path={`${match.path}/tag`}>
                                <Tag url={match.url} />
                            </Route> */}
                            <Route exact path={`${match.path}/socialNetwork`}>
                                <SocialNetwork url={match.url} />
                            </Route>
                            <Route exact path={`${match.path}/contact`}>
                                <Contact url={match.url} />
                            </Route>
                            <Route exact path={`${match.path}/work`}>
                                <Jobs url={match.url} />
                            </Route>
                            <Route exact path={`${match.path}/typeWork`}>
                                <TypeWork url={match.url} />
                            </Route>
                            <Route exact path={`${match.path}/checkCompany`}>
                                <CheckCompany url={match.url} />
                            </Route>
                            <Route exact path={`${match.path}/checkJobs`}>
                                <CheckJob url={match.url} />
                            </Route>
                            <Route path={`${match.path}/socialNetwork/addSocialNetwork`}>
                                <AddSocialNetwork url={match.url} />
                            </Route>
                            <Route path={`${match.path}/socialNetwork/editSocialNetwork/:id`}>
                                <AddSocialNetwork url={match.url} />
                            </Route>
                            <Route path={`${match.path}/contact/addContact`}>
                                <AddContact url={match.url} />
                            </Route>
                            <Route path={`${match.path}/contact/editContact/:id`}>
                                <AddContact url={match.url} />
                            </Route>
                            <Route exact path={`${match.path}/candidate`}>
                                <Candidates url={match.url} />
                            </Route>
                            <Route path={`${match.path}/candidate/infor/:id`}>
                                <CandidateInfor url={match.url} />
                            </Route>
                            <Route exact path={`${match.path}/companies`}>
                                <Companies url={match.url} />
                            </Route>
                            <Route path={`${match.path}/companies/infor/:id`}>
                                <CompaniesInfor url={match.url} />
                            </Route>
                            {/* <Route path={`${match.path}/tag/addTag`}>
                                <AddTag url={match.url} />
                            </Route>
                            <Route path={`${match.path}/tag/editTag/:id`}>
                                <AddTag url={match.url} />
                            </Route> */}
                            <Route path={`${match.path}/typeWork/addTypeWork`}>
                                <AddTypeWork url={match.url} />
                            </Route>
                            <Route path={`${match.path}/typeWork/editTypeWork/:id`}>
                                <AddTypeWork url={match.url} />
                            </Route>

                        </Switch>
                    </Content>
                </Layout>
            </Layout>
        </div>
    );
}
