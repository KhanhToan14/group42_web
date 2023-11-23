import React from 'react'
import "../../../styles/Home/Contact.scss"
function Contact() {
    return (
        <div className="contact">
            <div className="contact__title">
                <h3>Liên hệ phản ánh</h3>
            </div>
            <div className="contact__detail">
                <p>Liên lạc với chúng tôi nếu bạn gặp vấn đề gì đó.</p>
            </div>
            <div className="contact__gmail">
                <textarea name="" id="" cols="30" rows="4" placeholder="Nội dung ..."></textarea>
                <button>Phản hồi</button>
            </div>
        </div>
    )
}

export default Contact;