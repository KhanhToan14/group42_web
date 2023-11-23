import React, { useState } from "react";
import { Tabs } from "antd";
import Jobs from "./Jobs/Jobs";
import AddJob from "./AddJob/AddJob";
import UserApply from "./UserApply/UserApply";
import Infor from "./Information/Information";
import JobsReject from "./JobReject/JobReject";
import ChangePassword from "./ChangePassword/ChangePassword";
function Tab({ id }) {
    const { TabPane } = Tabs;
    const [numTab, setNumTab] = useState("1");
    const [idEdit, setIdEdit] = useState(null);
    const [resetJob, setResetJob] = useState(false);
    const handleOnChaneTabs = (e, reset = false) => {
        if (e !== "2") {
            setIdEdit(null);
        }
        setNumTab(e);
        if (reset) {
            setResetJob((prev) => !prev);
        }
    };
    const handleSetIdEdit = (e) => {
        setIdEdit(e);
    };
    return (
        <div className="container mt-2">
            <Tabs
                defaultActiveKey="1"
                activeKey={numTab}
                tabPosition="left"
                onChange={(e) => handleOnChaneTabs(e)}
            >
                <TabPane tab="Các công việc đã tạo" key="1">
                    <Jobs
                        id={id}
                        heard={true}
                        onChangeTabs={(e) => {
                            handleOnChaneTabs(e);
                        }}
                        onIdEdit={(e) => {
                            handleSetIdEdit(e);
                        }}
                        resetJob={resetJob}
                    />
                </TabPane>
                <TabPane tab="Công việc bị từ chối" key="5">
                    <JobsReject
                        id={id}
                        heard={true}
                        onChangeTabs={(e) => {
                            handleOnChaneTabs(e);
                        }}
                        onIdEdit={(e) => {
                            handleSetIdEdit(e);
                        }}
                        resetJob={resetJob}
                    />
                </TabPane>
                <TabPane tab="Đăng tuyển việc" key="2">
                    <AddJob id={id} idEdit={idEdit} onChangeTabs={handleOnChaneTabs} />
                </TabPane>
                <TabPane tab="Các ứng viêc ứng tuyển" key="3">
                    <UserApply id={id} />
                </TabPane>
                <TabPane tab="Thông tin công ty" key="4">
                    <Infor id={id} />
                </TabPane>
                <TabPane tab="Đổi mật khẩu" key="6">
                    <ChangePassword id={id} />
                </TabPane>
            </Tabs>
        </div>
    );
}

export default Tab;