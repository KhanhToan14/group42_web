import React from "react";
import "../../../styles/DetailCompany/ContentCompany.scss";
import Jobs from "../../InfoCompany/Tabs/Jobs/Jobs";
import RenderHTML from "react-native-render-html";
function ContentCompany({ data }) {
    return (
        <div className="contentCompany">
            <div className="container">
                <div className="row">
                    <div className="col-md-8">
                        <div className="company__box">
                            <div className="company__box__title">Giới thiệu</div>
                            <div className="company__box__content">
                                {data.introduce ? RenderHTML(data.introduce) : ""}
                            </div>
                        </div>
                        <div className="job">
                            <div className="job__title">Việc đã đăng tuyển</div>
                            <div className="job__content">
                                <Jobs id={data.id} hident={true} statusJobs={false} />
                            </div>
                        </div>
                    </div>
                    <div className="col-md-4">
                        <div className="company__box">
                            <div className="company__box__title">Thông tin</div>
                            <div className="company__box__detail">
                                <div className="company__box__detail__icon">
                                    <i className="fas fa-sign-in-alt"></i>
                                </div>
                                <div>
                                    <div className="company__box__detail__title">Số lượng nhân viên</div>
                                    <div className="company__box__detail__content">
                                        {data.quantity ? (
                                            data.quantity
                                        ) : (
                                            <span className="text-danger">Chưa cập nhật</span>
                                        )}
                                    </div>
                                </div>
                            </div>
                            <div className="company__box__detail">
                                <div className="company__box__detail__icon">
                                    <i className="fas fa-map-marker-alt"></i>
                                </div>
                                <div>
                                    <div className="company__box__detail__title">Địa điểm</div>
                                    <div className="company__box__detail__content">
                                        {data.address}
                                    </div>
                                </div>
                            </div>
                            <div className="company__box__detail">
                                <div className="company__box__detail__icon">
                                    <i className="fas fa-envelope"></i>
                                </div>
                                <div>
                                    <div className="company__box__detail__title">Email</div>
                                    <div className="company__box__detail__content">
                                        {data.email}
                                    </div>
                                </div>
                            </div>
                            <div className="company__box__detail">
                                <div className="company__box__detail__icon">
                                    <i className="fas fa-phone"></i>
                                </div>
                                <div>
                                    <div className="company__box__detail__title">Điện thoại</div>
                                    <div className="company__box__detail__content">
                                        {data.phone ? (
                                            data.phone
                                        ) : (
                                            <span className="text-danger">Chưa cập nhật</span>
                                        )}
                                    </div>
                                </div>
                            </div>
                            <div className="company__box__detail">
                                <div className="company__box__detail__icon">
                                    <i className="fas fa-map-marker-alt"></i>
                                </div>
                                <div>
                                    <div className="company__box__detail__title">Website công ty</div>
                                    <div className="company__box__detail__content">
                                        {data.website ? (
                                            data.website
                                        ) : (
                                            <span className="text-danger">Chưa cập nhật</span>
                                        )}
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="company__box">
                            <div className="company__box__title">Liên hệ ngay</div>
                            <div className="company__box__email">
                                <form>
                                    <input
                                        type="text"
                                        name="yourName"
                                        placeholder="Tên của bạn"
                                    />
                                    <input
                                        type="text"
                                        name="yourEmail"
                                        placeholder="Email của bạn"
                                    />
                                    <input type="text" name="title" placeholder="Tiêu đề" />
                                    <input
                                        type="text"
                                        name="messager"
                                        placeholder="Tên của bạn"
                                    />
                                    <input type="submit" value="Gửi" />
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ContentCompany;