import { configureStore } from '@reduxjs/toolkit';
import workReducer from "../features/admin/Slice/workSlice"
import companyReducer from "../features/admin/Slice/companySlice"
import checkCompanyReducer from "../features/admin/Slice/checkCompanySlice"
import typeWorkReducer from "../features/admin/Slice/typeWorkSlice"
import userReducer from "../features/admin/Slice/userSlice"
import contactReducer from "../features/admin/Slice/contactSlice"
import socialNetworkReducer from "../features/admin/Slice/socialNetworkSlice"
import worksCensorshipReducer from "../features/admin/Slice/workCensorshipSlice"
const rootReducer = {
    works: workReducer,
    companys: companyReducer,
    checkCompanys: checkCompanyReducer,
    users: userReducer,
    typeWorks: typeWorkReducer,
    contacts: contactReducer,
    socialNetworks: socialNetworkReducer,
    worksCensorship: worksCensorshipReducer,
}
export default configureStore({
    reducer: rootReducer
});