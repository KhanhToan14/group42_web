import workApi from "../../../api/workApi";

const { createSlice, createAsyncThunk } = require("@reduxjs/toolkit");

export const workCensorshipData = createAsyncThunk(
    "works/workCensorshipData",
    async (page, name) => {
        const workCensorship = await workApi.getAllCensorship(page, name);
        return workCensorship;
    },
);
const WorkCensorship = createSlice({
    name: "worksCensorship",
    initialState: {
        workCensorship: [],
        loading: true,
        error: "",
    },
    reducers: {
        updateWorkCensorship: (state, action) => {
            workApi.editwork(action.payload);
        },
    },
    extraReducers: {
        [workCensorshipData.pending]: (state) => {
            state.loading = true;
        },
        [workCensorshipData.rejected]: (state, action) => {
            state.loading = true;
            state.error = action.error;
        },
        [workCensorshipData.fulfilled]: (state, action) => {
            state.loading = false;
            state.workCensorship = action.payload;
        },
    },
});
const { reducer, actions } = WorkCensorship;
export const { updateWorkCensorship } = actions;

export default reducer;
