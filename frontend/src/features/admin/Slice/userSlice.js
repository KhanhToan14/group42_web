import userApi from "../../../api/userApi";

const { createSlice, createAsyncThunk } = require("@reduxjs/toolkit");

export const userData = createAsyncThunk('users/userData', async (page) => {
    const user = await userApi.getAll(page);
    return user;
})
const User = createSlice({
    name: "users",
    initialState: {
        user: [],
        loading: true,
        error: ''
    },
    reducers: {
        adduser: (state, action) => {
            userApi.postuser(action.payload);
        },
        removeuser: (state, action) => {
            userApi.deleteuser(action.payload);
        },
        updateuser: (state, action) => {
            userApi.edituser(action.payload);
        }
    },
    extraReducers: {
        [userData.pending]: (state) => {
            state.loading = true;
        },
        [userData.rejected]: (state, action) => {
            state.loading = true;
            state.error = action.error;
        },
        [userData.fulfilled]: (state, action) => {
            state.loading = false;
            state.user = action.payload;
        }
    }
});
const { reducer, actions } = User;
export const { adduser, removeuser, updateuser } = actions;

export default reducer;
