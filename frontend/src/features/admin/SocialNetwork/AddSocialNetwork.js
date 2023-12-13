import React, { useEffect } from 'react'
import { useForm } from 'react-hook-form';
import { useDispatch } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import { addsocialNetwork, socialNetworkData, updatesocialNetwork } from '../Slice/socialNetworkSlice';
import socialNetworkApi, { } from "../../../api/socialNetworkApi"
function AddSocialNetwork() {
    const { id } = useParams();

    const { register, handleSubmit, reset, formState: { errors } } = useForm();
    useEffect(async () => {
        if (id) {
            reset(await socialNetworkApi.getOne(id).then(data => {
                return data
            }));
        }
    }, [])

    const dispatch = useDispatch();
    const history = useHistory();
    const actionResult = async (page) => { await dispatch(socialNetworkData(page)) }

    const onhandleSubmit = (data) => {
        if (id) {
            dispatch(updatesocialNetwork({ name: data.name, color: data.color, link: data.link, icon: data.icon, id: data.id }));
        } else {
            const action = addsocialNetwork({ name: data.name, color: data.color, link: data.link, icon: data.icon, status: 0 });
            dispatch(action);
        }
        setTimeout(() => {
            actionResult({ page: localStorage.getItem("pageSocialNetwork") || 1 });
        }, 700);
        history.push("/admin/socialNetwork");
    }
    return (
        <div id="admin">
            <div className="heading">
                <div className="heading__title">
                    <h3>{id ? "Sửa mạng xã hội" : "Thêm mạng xã hội"}</h3>
                </div>
                <div className="heading__hr"></div>
            </div>
            <div className="content">
                <form onSubmit={handleSubmit(onhandleSubmit)}>
                    <div className="form-group">
                        <label htmlFor="">Tên mạng xã hội</label>
                        <input {...register("name", { required: true })} className="form-control w-50" placeholder="" aria-describedby="helpId" />
                        {errors.name && <span className="text-danger">Bạn không được để trống!</span>}
                    </div>
                    <div className="form-group">
                        <label htmlFor="">Mã màu</label>
                        <input {...register("color", { required: true })} className="form-control w-50" placeholder="" aria-describedby="helpId" />
                        {errors.color && <span className="text-danger">Bạn không được để trống!</span>}
                    </div>
                    <div className="form-group">
                        <label htmlFor="">icon</label>
                        <input {...register("icon", { required: true })} className="form-control w-50" placeholder="" aria-describedby="helpId" />
                        {errors.icon && <span className="text-danger">Bạn không được để trống!</span>}
                    </div>
                    <div className="form-group">
                        <label htmlFor="">Link</label>
                        <input {...register("link", { required: true })} className="form-control w-50" placeholder="" aria-describedby="helpId" />
                        {errors.link && <span className="text-danger">Bạn không được để trống!</span>}
                    </div>

                    <div className="text-center mtb">{id ? <input type="submit" value="Sửa" /> : <input type="submit" value="Thêm mới" />}</div>
                </form>
            </div>
        </div>
    )
}

export default AddSocialNetwork;