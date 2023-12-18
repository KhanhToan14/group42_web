import { Avatar, Dropdown, Menu } from "antd";
import React, { createRef, useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import checkLoginApi from "../../../../api/checkLogin";
import {
    checkBar,
    funLine,
    lineSlide,
    openMenu,
} from "../../../container/Functionjs";
import logo from "../../../images/jobs.png";
import "../../../styles/Home/Menu.scss";
function Mn(props) {
    const okok = (bar_ref, nav_ref, line_ref) => {
        setTimeout(() => {
            lineSlide();
            openMenu(bar_ref.current);
            funLine();
            checkBar(bar_ref.current, nav_ref.current, line_ref.current);
            window.addEventListener("resize", (e) => {
                funLine();
                checkBar(bar_ref.current, nav_ref.current, line_ref.current);
            });
        }, 500);
    };
    let { pathname } = useLocation();
    const bar_el = createRef();
    const nav_el = createRef();
    const line_el = createRef();
    const [user, setUser] = useState();
    okok(bar_el, nav_el, line_el);
    useEffect(() => {
        checkLoginApi.checkLogin().then((userData) => {
            setUser(userData);
        });
        let idClass = pathname.slice(1);
        if (nav_el.current) {
            let ListMenu = nav_el.current.querySelectorAll(".item");
            nav_el.current.querySelector(".item.active").classList.remove("active");
            for (let i = 0; i < ListMenu.length; i++) {
                if (ListMenu[i].id === idClass) {
                    ListMenu[i].classList.add("active");
                }
            }
        }
    }, []);

    const inforCompany = (
        <Menu.Item key="1">
            <Link to="/InforCompany">Thông tin cá nhân</Link>
        </Menu.Item>
    );
    const inforUser = (
        <Menu.Item key="2">
            <Link to="/InforUser">Thông tin cá nhân</Link>
        </Menu.Item>
    );
    const onLogOut = () => {
        localStorage.removeItem("token");
        setUser("");
    };
    const logOut = (
        <Menu.Item key="3">
            <Link to="/" onClick={onLogOut}>
                Đăng xuất
            </Link>
        </Menu.Item>
    );
    const imgDefault = "https://1.bp.blogspot.com/-m3UYn4_PEms/Xnch6mOTHJI/AAAAAAAAZkE/GuepXW9p7MA6l81zSCnmNaFFhfQASQhowCLcBGAsYHQ/s1600/Cach-Lam-Avatar-Dang-Hot%2B%25281%2529.jpg";
    const ss = (
        <Menu>
            {!user &&
                <Menu.Item key="0">
                    <Link to="/login">Đăng nhập</Link>
                </Menu.Item>
            }
            {user && (user.type === "company" ? inforCompany : inforUser)}
            {user && logOut}
        </Menu>
    );
    return (
        <div className={props.class}>
            <div className="menu__brand">
                <Link to="/">
                    <img src={logo} height={80} alt="" />
                </Link>
            </div>
            <div className="menu--right">
                <div className="bar menu__bar" ref={bar_el}>
                    <div className="line--top"></div>
                    <div className="line--mid"></div>
                    <div className="line--bot"></div>
                </div>
                <nav ref={nav_el}>
                    <div className="item active" id="">
                        <Link to="/">Trang chủ</Link>
                    </div>
                    <div className="item" id="candidates">
                        <Link to="/candidates">Ứng viên</Link>
                    </div>
                    <div className="item" id="jobs">
                        <Link to="/jobs">Việc làm</Link>
                    </div>
                    <div className="item" id="companys">
                        <Link to="/companys">Nhà tuyển dụng</Link>
                    </div>
                    <div className="line_slide" ref={line_el}></div>
                    <Dropdown overlay={ss} trigger={["click"]}>
                        <span className="nav-link">
                            <Avatar size="small" src={user ? user.avatar : imgDefault} />
                        </span>
                    </Dropdown>
                </nav>
            </div>
        </div>
    );
}

export default Mn;