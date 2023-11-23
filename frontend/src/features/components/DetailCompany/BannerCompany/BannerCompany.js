import React from 'react'
import "../../../styles/DetailCompany/BannerCompany.scss"
function BannerCompany({ avatar, banner, name, address }) {

    return (
        <div className="bannerCompany" style={{
            background: `url(${banner}) repeat center`,
            backgroundSize: "cover"
        }}>
            <div className="bannerCompany__content">
                <div className="bannerCompany__content__img">
                    <img src={avatar} alt="profile" height="100%" />
                </div>
                <div className="company__margin">
                    <div className="bannerCompany__content__title">
                        {name}
                    </div>
                    <div className="bannerCompany__content__address">
                        {address}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default BannerCompany;
