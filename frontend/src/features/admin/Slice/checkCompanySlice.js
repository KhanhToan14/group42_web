import checkCompanyApi from "../../../api/companyApi";

const { createSlice, createAsyncThunk } = require("@reduxjs/toolkit");

export const checkCompanyData = createAsyncThunk('checkCompanys/checkCompanyData', async (page) => {
    const checkCompany = await checkCompanyApi.getCheck(page);
    return checkCompany;
})
const CheckCompany = createSlice({
    name: "checkCompanys",
    initialState: {
        checkCompany: [],
        loading: true,
        error: ''
    },
    reducers: {
        addcheckCompany: (state, action) => {
            checkCompanyApi.postcompany(action.payload);
        },
        removecheckCompany: (state, action) => {
            checkCompanyApi.deletecompany(action.payload);
        },
        updatecheckCompany: (state, action) => {
            checkCompanyApi.editcompany(action.payload);
        }
    },
    extraReducers: {
        [checkCompanyData.pending]: (state) => {
            state.loading = true;
        },
        [checkCompanyData.rejected]: (state, action) => {
            state.loading = true;
            state.error = action.error;
        },
        [checkCompanyData.fulfilled]: (state, action) => {
            state.loading = false;
            state.checkCompany = action.payload;
        }
    }
});
const { reducer, actions } = CheckCompany;
export const { addcheckCompany, removecheckCompany, updatecheckCompany } = actions;

export default reducer;
