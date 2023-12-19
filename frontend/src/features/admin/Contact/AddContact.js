import React, { useEffect } from 'react'
import { useForm } from 'react-hook-form';
import { useDispatch } from 'react-redux';
import { useHistory, useParams } from 'react-router';
import { addcontact, contactData, updatecontact } from '../Slice/contactSlice';
import contactApi, { } from "../../../api/contactApi"
function Addcontact() {
    const { id } = useParams();

    const { register, handleSubmit, reset, formState: { errors } } = useForm();
    useEffect(async () => {
        if (id) {
            reset(await contactApi.getOne(id).then(data => {
                return data
            }));
        }
    }, [])

    const dispatch = useDispatch();
    const history = useHistory();
    const actionResult = async (page) => { await dispatch(contactData(page)) }

    const onhandleSubmit = (data) => {
        if (id) {
            dispatch(updatecontact({ email: data.email, location: data.location, phone: data.phone, description: data.description, id: data.id }));
        } else {
            const action = addcontact({ email: data.email, location: data.location, phone: data.phone, description: data.description, status: 0 });
            dispatch(action);
        }
        setTimeout(() => {
            actionResult({ page: localStorage.getItem("pageContact") || 1 });
        }, 700);
        history.push("/admin/contact");
    }
    return (
        <div id="admin">
            <div className="heading">
                <div className="heading__title">
                    <h3>{id ? "Sửa liên hệ" : "Thêm liên hệ"}</h3>
                </div>
                <div className="heading__hr"></div>
            </div>
            <div className="content">
                <form onSubmit={handleSubmit(onhandleSubmit)}>
                    <div className="form-group">
                        <label htmlFor="">Email liên hệ</label>
                        <input {...register("email", { required: true })} className="form-control w-50" placeholder="" aria-describedby="helpId" />
                        {errors.email && <span className="text-danger">Bạn không được để trống!</span>}
                    </div>
                    <div className="form-group">
                        <label htmlFor="">Địa chỉ</label>
                        <input {...register("location", { required: true })} className="form-control w-50" placeholder="" aria-describedby="helpId" />
                        {errors.location && <span className="text-danger">Bạn không được để trống!</span>}
                    </div>
                    <div className="form-group">
                        <label htmlFor="">Số điện thoại</label>
                        <input {...register("phone", { required: true })} className="form-control w-50" placeholder="" aria-describedby="helpId" />
                        {errors.phone && <span className="text-danger">Bạn không được để trống!</span>}
                    </div>
                    <div className="form-group">
                        <label htmlFor="">Thông tin</label>
                        <textarea {...register("description", { required: true })} className="form-control w-50" placeholder="" rows="5" />
                        {errors.description && <span className="text-danger">Bạn không được để trống!</span>}
                    </div>
                    <div className="text-center mtb">{id ? <input type="submit" value="Sửa" /> : <input type="submit" value="Thêm mới" />}</div>
                </form>
            </div>
        </div>
    )
}

export default Addcontact;