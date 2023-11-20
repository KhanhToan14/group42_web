import companyApi from "../../../api/companyApi";

const { createSlice, createAsyncThunk } = require("@reduxjs/toolkit");

export const companyData = createAsyncThunk('companys/companyData', async (page) => {
    const company = await companyApi.getAll(page);
    return company;
})
const Company = createSlice({
    name: "companys",
    initialState: {
        company: [],
        loading: true,
        error: ''
    },
    reducers: {
        addcompany: (state, action) => {
            companyApi.postcompany(action.payload);
        },
        removecompany: (state, action) => {
            companyApi.deletecompany(action.payload);
        },
        updatecompany: (state, action) => {
            companyApi.editcompany(action.payload);
        }
    },
    extraReducers: {
        [companyData.pending]: (state) => {
            state.loading = true;
        },
        [companyData.rejected]: (state, action) => {
            state.loading = true;
            state.error = action.error;
        },
        [companyData.fulfilled]: (state, action) => {
            state.loading = false;
            state.company = action.payload;
        }
    }
});
const { reducer, actions } = Company;
export const { addcompany, removecompany, updatecompany } = actions;

export default reducer;
