import workApi from "../../../api/workApi";

const { createSlice, createAsyncThunk } = require("@reduxjs/toolkit");

export const workData = createAsyncThunk(
    "works/workData",
    async (page, name) => {
        const work = await workApi.getAll({ currentPage: page });
        return work;
    },
);
const Work = createSlice({
    name: "works",
    initialState: {
        work: [],
        loading: true,
        error: "",
    },
    reducers: {
        addwork: (state, action) => {
            workApi.postwork(action.payload);
        },
        removework: (state, action) => {
            workApi.deletework(action.payload);
        },
        updatework: (state, action) => {
            workApi.editwork(action.payload);
        },
    },
    extraReducers: {
        [workData.pending]: (state) => {
            state.loading = true;
        },
        [workData.rejected]: (state, action) => {
            state.loading = true;
            state.error = action.error;
        },
        [workData.fulfilled]: (state, action) => {
            state.loading = false;
            state.work = action.payload;
        },
    },
});
const { reducer, actions } = Work;
export const { addwork, removework, updatework } = actions;

export default reducer;
