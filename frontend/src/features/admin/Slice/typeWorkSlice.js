import typeWorkApi from "../../../api/typeWorkApi";

const { createSlice, createAsyncThunk } = require("@reduxjs/toolkit");

export const typeWorkData = createAsyncThunk('typeWorks/typeWorkData', async (page) => {
    const typeWork = await typeWorkApi.getAll(page);
    return typeWork;
})
const TypeWork = createSlice({
    name: "typeWorks",
    initialState: {
        typeWork: [],
        loading: true,
        error: ''
    },
    reducers: {
        addtypeWork: (state, action) => {
            typeWorkApi.posttypeWork(action.payload);
        },
        removetypeWork: (state, action) => {
            typeWorkApi.deletetypeWork(action.payload);
        },
        updatetypeWork: (state, action) => {
            typeWorkApi.edittypeWork(action.payload);
        }
    },
    extraReducers: {
        [typeWorkData.pending]: (state) => {
            state.loading = true;
        },
        [typeWorkData.rejected]: (state, action) => {
            state.loading = true;
            state.error = action.error;
        },
        [typeWorkData.fulfilled]: (state, action) => {
            state.loading = false;
            state.typeWork = action.payload;
        }
    }
});
const { reducer, actions } = TypeWork;
export const { addtypeWork, removetypeWork, updatetypeWork } = actions;

export default reducer;
