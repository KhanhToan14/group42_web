import contactApi from "../../../api/contactApi";

const { createSlice, createAsyncThunk } = require("@reduxjs/toolkit");

export const contactData = createAsyncThunk('contacts/contactData', async (page) => {
    const contact = await contactApi.getAll(page);
    return contact;
})
const Contact = createSlice({
    name: "contacts",
    initialState: {
        contact: [],
        loading: true,
        error: ''
    },
    reducers: {
        addcontact: (state, action) => {
            contactApi.postcontact(action.payload);
        },
        removecontact: (state, action) => {
            contactApi.deletecontact(action.payload);
        },
        updatecontact: (state, action) => {
            contactApi.editcontact(action.payload);
        }
    },
    extraReducers: {
        [contactData.pending]: (state) => {
            state.loading = true;
        },
        [contactData.rejected]: (state, action) => {
            state.loading = true;
            state.error = action.error;
        },
        [contactData.fulfilled]: (state, action) => {
            state.loading = false;
            state.contact = action.payload;
        }
    }
});
const { reducer, actions } = Contact;
export const { addcontact, removecontact, updatecontact } = actions;

export default reducer;
