import { DatePicker, Input, message, Select, Space } from "antd";
import JoditEditor from "jodit-react";
import moment from "moment";
import React, { useCallback, useEffect, useMemo, useState } from "react";
import { useForm } from "react-hook-form";
import { useDispatch, useSelector } from "react-redux";
import { getProvinces } from "sub-vi";
import workApi from "../../../../../api/workApi";
import workTypeOfWorkApi from "../../../../../api/workTypeOfWorkApi";
import { typeWorkData } from "../../../../admin/Slice/typeWorkSlice";
import { addwork, updatework } from "../../../../admin/Slice/workSlice";
import { FormatProvince } from "../../../../container/Functionjs";
import SpinLoad from "../../../Spin/Spin";
function AddJob({ id, idEdit, onChangeTabs }) {
    const dispatch = useDispatch();
    const actionResultTypeOfWork = () => {
        dispatch(typeWorkData({ status: 1 }));
    };

    useEffect(async () => {
        if (idEdit) {
            reset(
                await workApi.getOne(idEdit).then((data) => {
                    setState({
                        ...state,
                        salaryFrom: String(data.salaryFrom),
                        salaryTo: String(data.salaryTo),
                        nature: data.nature,
                        date: data.dealtime,
                        location: data.location,
                        typeofworkId: data.TypeOfWorks[0]?.id,
                    });
                    setDescripton(data.description);
                    setInterest(data.interest);
                    setForm(data.form);
                    setExprience(data.exprience);
                    return data;
                }),
            );
        } else {
            reset(objDefault);
            setDescripton("");
            setInterest("");
            setForm("");
            setExprience("");
        }
    }, [idEdit]);
    const typeWorks = useSelector((state) => state.typeWorks.typeWork.data);
    const loadingTypeWork = useSelector((state) => state.typeWorks.loading);
    const { register, handleSubmit, reset } = useForm();
    const [interest, setInterest] = useState();
    const [exprience, setExprience] = useState();
    const [form, setForm] = useState();
    const [description, setDescripton] = useState();
    let objDefault = {
        load: false,
        typeofworkId: 2,
        location: "Hà Nội",
        salaryFrom: "",
        nature: "Full Time",
        request: "Không yêu cầu",
        salaryTo: "",
        date: undefined,
    };
    const [state, setState] = useState({
        ...objDefault,
    });
    const {
        salaryFrom,
        salaryTo,
        nature,
        request,
        date,
        load,
        typeofworkId,
        location,
    } = state;
    const onSubmit = async (data) => {
        if (
            data.name === "" ||
            request === "" ||
            nature === "" ||
            interest === "" ||
            description === "" ||
            exprience === "" ||
            form === "" ||
            data.location === "" ||
            data.phone === "" ||
            data.quantity === "" ||
            data.email === "" ||
            data.locationGoogle === "" ||
            date === ""
        ) {
            message.warning("Bạn chưa nhập đầy đủ thông tin!");
        } else {
            setState({
                ...state,
                load: true,
            });

            var workType = [];
            workType.push({ typeofworkId: typeofworkId });
            if (idEdit) {
                await workTypeOfWorkApi.deleteWorkTypeOfWork(idEdit);
                await workTypeOfWorkApi.postWorkTypeOfWork([
                    {
                        typeofworkId,
                        workId: idEdit,
                    },
                ]);

                const action = updatework({
                    workType,
                    companyId: id,
                    id: idEdit,
                    name: data.name,
                    status: 1,
                    salaryFrom,
                    salaryTo,
                    request,
                    nature,
                    interest,
                    description,
                    exprience,
                    form,
                    location,
                    phone: data.phone,
                    quantity: data.quantity,
                    email: data.email,
                    locationGoogle: data.locationGoogle,
                    dealtime: date,
                });
                dispatch(action);
                setState({
                    ...state,
                    load: false,
                });
                onChangeTabs("1", true);
            } else {
                const action = addwork({
                    workType,
                    companyId: id,
                    name: data.name,
                    status: 1,
                    salaryFrom: salaryFrom ? salaryFrom : 0,
                    salaryTo: salaryTo ? salaryTo : 0,
                    request,
                    nature,
                    interest,
                    description,
                    exprience,
                    form,
                    location,
                    phone: data.phone,
                    quantity: data.quantity,
                    email: data.email,
                    locationGoogle: data.locationGoogle,
                    dealtime: date,
                });
                dispatch(action);
                setState({
                    ...state,
                    load: false,
                });
                onChangeTabs("1", true);
            }
        }
    };
    const onChangesalaryFrom = (e) => {
        setState({
            ...state,
            salaryFrom: e.target.value,
        });
    };
    const onChangesalaryTo = (e) => {
        setState({
            ...state,
            salaryTo: e.target.value,
        });
    };
    const onChangeNature = (e) => {
        setState({
            ...state,
            nature: e,
        });
    };
    const onChangeTypeWork = (e) => {
        setState({
            ...state,
            typeofworkId: e,
        });
    };
    const onChangeRequest = (e) => {
        setState({
            ...state,
            request: e,
        });
    };
    const onChangeDate = (date, dateString) => {
        setState({
            ...state,
            date: dateString,
        });
    };
    useEffect(() => {
        actionResultTypeOfWork();
    }, []);
    const data = [];


    const onchangelocation = (e) => {
        setState({
            ...state,
            location: e,
        });
    };
    return (
        <div className="infor">
            <div className="heading">
                <div className="heading__title">
                    <h3>Đăng tuyển việc làm</h3>
                </div>
                <div className="heading__hr"></div>
            </div>
            <div className="content pb-4">
                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className="d-flex">
                        <div className="form-group w-45">
                            <label htmlFor="">Tên công việc</label>
                            <input
                                type="text"
                                className="form-control"
                                {...register("name")}
                                id=""
                                aria-describedby="helpId"
                                placeholder=""
                            />
                        </div>
                        <div className="form-group w-45">
                            <label htmlFor="">Địa chỉ</label>

                            <Select
                                value={location}
                                onChange={onchangelocation}
                                className="form-control w-100"
                            >
                                {getProvinces().map((data, index) => (
                                    <Select.Option value={FormatProvince(data.name)} key={index}>
                                        {data.name}
                                    </Select.Option>
                                ))}
                            </Select>
                        </div>
                    </div>
                    <div className="d-flex">
                        <div className="form-group w-45">
                            <label htmlFor="">Email</label>
                            <input
                                type="text"
                                className="form-control"
                                {...register("email")}
                                id=""
                                aria-describedby="helpId"
                                placeholder=""
                            />
                        </div>
                        <div className="form-group w-45">
                            <label htmlFor="">Link google map</label>
                            <input
                                type="text"
                                className="form-control"
                                {...register("locationGoogle")}
                                id=""
                                aria-describedby="helpId"
                                placeholder=""
                            />
                        </div>
                    </div>
                    <div className="d-flex">
                        <div className="form-group w-45">
                            <label htmlFor="">Điện thoại</label>
                            <input
                                type="text"
                                className="form-control"
                                {...register("phone")}
                                id=""
                                aria-describedby="helpId"
                                placeholder=""
                            />
                        </div>
                        <div className="form-group w-45">
                            <label htmlFor="">Mức lương</label>
                            <Input.Group compact className="w-100">
                                <Input
                                    style={{ width: "45%", textAlign: "center" }}
                                    onChange={onChangesalaryFrom}
                                    placeholder="Ít nhất"
                                    value={salaryFrom}
                                />
                                <Input
                                    className="site-input-split"
                                    style={{
                                        width: "10%",
                                        textAlign: "center",
                                    }}
                                    placeholder="~"
                                    disabled
                                />
                                <Input
                                    className="site-input-right"
                                    style={{
                                        width: "45%",
                                        textAlign: "center",
                                    }}
                                    onChange={onChangesalaryTo}
                                    placeholder="Nhiều nhất"
                                    value={salaryTo}
                                />
                            </Input.Group>
                            <small>Không nhập nếu bạn muốn thương lượng</small>
                        </div>
                    </div>
                    <div className="d-flex">
                        <div className="form-group w-45">
                            <label htmlFor="">Hạn chót</label>
                            <Space direction="vertical" className="w-100">
                                <DatePicker
                                    onChange={onChangeDate}
                                    className="form-control input-ant"
                                    value={moment(date ? date : new Date(), "YYYY-MM-DD")}
                                />
                            </Space>
                            {console.log('new Date()', new Date())}
                            {console.log('date', date ? date : new Date())}
                            {console.log("date", moment(date ? date : new Date(), "YYYY-MM-DD"))}
                        </div>

                        <div className="form-group w-45">
                            <label htmlFor="">Loại công việc</label>
                            {loadingTypeWork ? (
                                <SpinLoad />
                            ) : (
                                <Select
                                    value={typeofworkId}
                                    onChange={onChangeTypeWork}
                                    className="form-control w-100"
                                >
                                    {typeWorks.rows.map((data, index) => (
                                        <Select.Option value={data.id} key={index}>
                                            {data.name}
                                        </Select.Option>
                                    ))}
                                </Select>
                            )}
                        </div>
                    </div>
                    <div className="d-flex">
                        <div className="form-group w-45">
                            <label htmlFor="">Tính chất công việc</label>
                            <Select
                                defaultValue="Full Time"
                                onChange={onChangeNature}
                                className="form-control w-100"
                                value={nature}
                            >
                                <Select.Option value="Full Time">Full Time</Select.Option>
                                <Select.Option value="Part Time">Part Time</Select.Option>
                            </Select>
                        </div>
                        <div className="form-group w-45">
                            {/* <label htmlFor="">Yêu cầu bằng cấp</label>
                            <Select
                                defaultValue="Không yêu cầu"
                                onChange={onChangeRequest}
                                className="form-control w-100"
                                value={request}
                            >
                                <Select.Option value="Không yêu cầu">
                                    Không yêu cầu
                                </Select.Option>
                                <Select.Option value="Cao đẳng">Cao đẳng</Select.Option>
                                <Select.Option value="Đại học">Đại học</Select.Option>
                                <Select.Option value="Cao học">Cao học</Select.Option>
                                <Select.Option value="Tiến sỹ">Tiến sỹ</Select.Option>
                                <Select.Option value="Thạc sỹ">Thạc sỹ</Select.Option>
                            </Select> */}
                            <label htmlFor="">Số lượng quyển</label>
                            <input
                                type="text"
                                className="form-control"
                                {...register("quantity")}
                                id=""
                                aria-describedby="helpId"
                                placeholder=""
                            />
                        </div>
                    </div>

                    <div className="form-group">
                        <label htmlFor="">Mô tả</label>
                        <JoditEditor
                            value={description}
                            tabIndex={1}
                            onChange={(e) => setDescripton(e)}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="">quyền lợi được hưởng</label>
                        <JoditEditor
                            value={interest}
                            tabIndex={1}
                            onChange={(e) => setInterest(e)}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="">Yêu cầu công việc</label>
                        <JoditEditor
                            value={form}
                            tabIndex={1}
                            onChange={(e) => setForm(e)}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="">Yêu cầu kinh nghiệm</label>
                        <JoditEditor
                            value={exprience}
                            tabIndex={1}
                            onChange={(e) => setExprience(e)}
                        />
                    </div>
                    {load ? (
                        <SpinLoad />
                    ) : (
                        <div className="text-center mtb">
                            <input
                                type="submit"
                                value={idEdit ? "Sửa công việc" : "Tạo công việc"}
                            />
                        </div>
                    )}
                </form>
            </div>
        </div>
    );
}

export default AddJob;