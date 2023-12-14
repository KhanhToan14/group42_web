import { Modal, Popover } from "antd";
import React, { useEffect, useState, useCallback } from "react";
import { Link } from "react-router-dom";
import workApplyApi from "../../../../../api/workApplyApi";
import "../../../../styles/InfoCompany/UserApply.scss";
import SpinLoad from "../../../Spin/Spin";
import moment from "moment";
import sendMailApi from "../../../../../api/sendMail";
function UserApply({ id }) {
    const [data, setData] = useState();
    const [isLoad, setIsLoad] = useState(false);
    const [numReload] = useState(1);
    const [state, setState] = useState({
        isModalUserVisible: false,
        titleModal: "",
        date: new Date(),
        textSendMail: "",
        email: "",
        userId: "",
        workId: "",
    });

    const {
        isModalUserVisible,
        titleModal,
        // date,
        textSendMail,
        email,
        userId,
        // workId,
    } = state;

    const getApi = async () => {
        await workApplyApi.checkWorkApply({ id, userId }).then((data) => {
            console.log('data.Works', data.Works)
            setData(data.Works);
        });
    };

    const handleOk = () => {
        setState({ ...state, isModalUserVisible: false });
        sendMailApi.Send({
            email,
            textSendMail,
        });

    };

    const handleAccept = (userId, workId) => {
        workApplyApi
            .editworkApply({ userId, workId, statusActive: 1 })
            .then((data) => {
                setIsLoad(!isLoad)
            });
    }

    const handleAcceptOK = (userId, workId) => {
        workApplyApi
            .editworkApply({ userId, workId, statusActive: 2 })
            .then((data) => {
                setIsLoad(!isLoad)
            });
    }

    const handleCancel = () => {
        setState({ ...state, isModalUserVisible: false });
    };

    const handleClickContact = (name, email, userId, workId) => {
        setState({
            ...state,
            isModalUserVisible: true,
            titleModal: `Bạn đang liên hệ với ứng viên ${name}`,
            email,
            userId,
            workId,
        });
    };

    const handleOnchaneTextSendMail = (e) => {
        const { value } = e.target;
        setState({
            ...state,
            textSendMail: value,
        });
    };

    useEffect(() => {
        getApi();
    }, [numReload, isLoad]);

    let styleTextarea = {
        width: "100%",
        resize: "none",
        borderRadius: "6px",
        padding: "10px 20px",
    };

    const handleClickWork = (id) => {
        let el = document.getElementById(id);
        let ko = el.querySelector(".kkk.h-0")
        if (ko) {
            el.querySelector(".kkk.h-0").classList.remove("h-0")
        } else {
            el.getElementsByClassName("kkk")[0]?.classList.add("h-0");
        }
    }

    const handleRejectUser = (userId, workId) => {
        workApplyApi
            .editworkApply({ userId, workId, statusActive: 3 })
            .then((data) => {
                setIsLoad(!isLoad)
            });
    }

    const clearWorksReject = (data) => {
        return data.filter(item => item.WorkApplies.statusActive !== 3)
    }

    const getTitle = (statusActive) => {
        if (statusActive == null) {
            return "Mới Ứng Tuyển";
        } else if (statusActive === 1) {
            return "Chờ Phỏng Vấn";
        } else if (statusActive === 2) {
            return "Đã Nhận Ứng Viên";
        }
    }

    return (
        <div className="userApply">
            <div className="heading">
                <div className="heading__title">
                    <h3>Ứng viên ứng tuyển</h3>
                </div>
                <div className="heading__hr"></div>
            </div>

            <div className="content">
                {!data ? (
                    <SpinLoad />
                ) : (
                    data.map((ok, index) => (
                        <div className="content___box" key={index} id={"gg" + index}>
                            <div className="content___box--title" onClick={() => handleClickWork("gg" + index)}>
                                <Link
                                    to="#"
                                    className="text-dark">
                                    {ok.name}
                                </Link>
                            </div>
                            <div className="hr"></div>
                            <div className="content___box---user">
                                <div className="row " >
                                    {clearWorksReject(ok.workapply2).length === 0 ? (
                                        <p className="text-danger">Chưa có ứng viên ứng tuyển</p>
                                    ) : (
                                        <>
                                            <p className="text-danger">Có {clearWorksReject(ok.workapply2).length} ứng viên</p>
                                            <div className="kkk h-0">
                                                {clearWorksReject(ok.workapply2).map((oki, index) => (
                                                    <div className="col-md-12" key={index} >
                                                        <div className="d-flex " id="ok" style={{ transition: ".5s" }}>
                                                            <div className="content___box---user---img">
                                                                <img
                                                                    src={oki.avatar}
                                                                    alt="content_im"
                                                                    title={oki.name}
                                                                    width={150}
                                                                />
                                                            </div>
                                                            <div className="content___box---user---infor position-relative">
                                                                <table>
                                                                    <tbody>
                                                                        <tr>
                                                                            <td className="td">Tên người dùng</td>
                                                                            <td>
                                                                                <Link to={`candidates/${oki.id}`}>
                                                                                    {oki.name}
                                                                                </Link>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td className="td">Địa chỉ</td>
                                                                            <td>{oki.address}</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td className="td">Email</td>
                                                                            <td>{oki.email}</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td className="td">Điện thoại</td>
                                                                            <td>{oki.phone}</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td className="td">Giới tính</td>
                                                                            <td>{oki.male}</td>
                                                                        </tr>
                                                                        {oki.WorkApplies.sechedule && (
                                                                            <tr>
                                                                                <td className="td">Lịch phỏng vấn</td>
                                                                                <td>
                                                                                    {moment(oki.WorkApplies.sechedule).format(
                                                                                        "DD/MM/yyyy",
                                                                                    )}
                                                                                </td>
                                                                            </tr>
                                                                        )}
                                                                    </tbody>
                                                                </table>
                                                                <div className="btn-userApply">
                                                                    <div className="text"
                                                                        style={{
                                                                            position: "absolute",
                                                                            left: "50%",
                                                                            top: -40,
                                                                            fontSize: 18,
                                                                            transform: "translateX(-50%)"
                                                                        }}
                                                                    >{getTitle(oki.WorkApplies.statusActive)}</div>
                                                                    {oki.WorkApplies.link && oki.WorkApplies.statusActive !== 2 && (
                                                                        <button
                                                                            className="btn-link"
                                                                            onClick={() => {
                                                                                window.open(oki.WorkApplies.link);
                                                                            }}
                                                                        >
                                                                            Xem CV
                                                                        </button>
                                                                    )}
                                                                    {oki.WorkApplies.statusActive !== 2
                                                                        &&
                                                                        <>
                                                                            <button
                                                                                className="btn-link"
                                                                                onClick={() =>
                                                                                    handleClickContact(
                                                                                        oki.name,
                                                                                        oki.email,
                                                                                        oki.id,
                                                                                        ok.id,
                                                                                    )
                                                                                }
                                                                            >
                                                                                Liên hệ ngay
                                                                            </button>
                                                                            <button
                                                                                className="btn-link"
                                                                                onClick={() => handleRejectUser(oki.id,
                                                                                    ok.id,)}
                                                                            >
                                                                                Từ chối
                                                                            </button>
                                                                            {
                                                                                oki.WorkApplies.statusActive !== 1 ?
                                                                                    <button
                                                                                        className="btn-link"
                                                                                        onClick={() => handleAccept(oki.id, ok.id)}
                                                                                    >
                                                                                        Chấp nhận
                                                                                    </button>
                                                                                    :
                                                                                    <button
                                                                                        className="btn-link"
                                                                                        onClick={() => handleAcceptOK(oki.id, ok.id)}
                                                                                    >
                                                                                        Nhận việc
                                                                                    </button>
                                                                            }
                                                                        </>
                                                                    }
                                                                </div>
                                                                <Modal
                                                                    title={titleModal}
                                                                    visible={isModalUserVisible}
                                                                    onOk={handleOk}
                                                                    onCancel={handleCancel}
                                                                >
                                                                    <p>Lời nhắn:</p>
                                                                    <textarea
                                                                        className="box-textarea"
                                                                        name=""
                                                                        placeholder="Điền các thông tin ứng tuyển cho ứng viên và đừng quên lịch phỏng vấn cụ thể"
                                                                        value={textSendMail}
                                                                        onChange={handleOnchaneTextSendMail}
                                                                        rows="11"
                                                                        style={styleTextarea}
                                                                    ></textarea>
                                                                </Modal>

                                                                {oki.WorkApplies.statusActive !== 2 &&
                                                                    <Popover
                                                                        content={oki.WorkApplies.message}
                                                                        title="Lời nhắn"
                                                                    >
                                                                        <button className="btn-message">
                                                                            <i className="fas fa-comment-dots"></i>
                                                                        </button>
                                                                    </Popover>
                                                                }

                                                            </div>
                                                        </div>
                                                    </div>
                                                ))}
                                            </div>
                                        </>
                                    )}
                                </div>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}

export default UserApply;