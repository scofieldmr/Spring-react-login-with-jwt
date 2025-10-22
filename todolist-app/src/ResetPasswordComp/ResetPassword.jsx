import axios from 'axios';
import { useNavigate, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import React, { useEffect, useState } from "react";

const ResetPassword = () => {
    const [currentPassword, setCurrentPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmNewPassword, setConfirmNewPassword] = useState("");
    const navigator = useNavigate();
    const email = localStorage.getItem("username");
    const token = localStorage.getItem("token");

    const REACT_APP_API_URL = "https://spring-login-backend.onrender.com/api/v1";

    const LOCAL_HOST = "http://localhost:8000/api/v1/";


    const [formError, setFormError] = useState({
        currentPassword: "",
        newPassword: "",
        confirmNewPassword: ""
    });

    const [apiError, setApiError] = useState("");

    const [passwordError, setPasswordError] = useState("");

    // Real-time password mismatch check
    const handleRepeatChange = (value) => {
        setConfirmNewPassword(value);
        if (newPassword && value && newPassword !== value) {
            setPasswordError("Passwords do not match");
        } else {
            setPasswordError("");
        }
    };

    function validateForm() {
        let valid = true;
        const errorCopy = { ...formError };
        const passwordRegex = /^.{4,10}$/;

        if (!currentPassword.trim()) {
            errorCopy.currentPassword = "Enter Current Password";
            valid = false;
        }
        else {
            if (currentPassword && !passwordRegex.test(currentPassword)) {
                errorCopy.currentPassword = "Password should be 4 to 10 characters long";
                valid = false;
            }
        }

        if (!newPassword.trim()) {
            errorCopy.newPassword = "New Password Required";
            valid = false;
        }
        else {
            if (newPassword && !passwordRegex.test(newPassword)) {
                errorCopy.newPassword = "Password should be 4 to 10 characters long";
                valid = false;
            }
        }

        if (!confirmNewPassword.trim()) {
            errorCopy.confirmNewPassword = "Repeat Current Password Required";
            valid = false;
        }
        else {
            if (confirmNewPassword && !passwordRegex.test(confirmNewPassword)) {
                errorCopy.confirmNewPassword = "Password should be 4 to 10 characters long";
                valid = false;
            }
        }

        if (newPassword && confirmNewPassword && newPassword !== confirmNewPassword) {
            setPasswordError("Passwords do not match");
            valid = false;
        } else {
            setPasswordError("");
        }

        setFormError(errorCopy);
        return valid;

    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        setApiError("");

        if(!validateForm()){
            return;
        }

        try{
            const resetPass = {currentPassword,newPassword,confirmNewPassword};

            console.log(resetPass);

            const res = await axios.post(
                `${REACT_APP_API_URL}/resetPassword/`,resetPass,
                { 
                  headers: { 
                    Authorization: `Bearer ${token}`,
                   "Content-Type": "application/json"
                }
            }
            );

            console.log(res.data);
            navigator("/login");

        }
        catch(err){
            setApiError(err.response?.data?.message || "Error changing password");
        }
    }
    return (
        <div className='container mt-3 w-75'
             style={{border:'1px solid black',borderRadius:'20px',padding:'20px',height:'60vh'}}>
            <h3 className='text-center'>Reset password</h3>

            <form onSubmit={handleSubmit}>
                <div className='form-control mt-3 p-3'>
                    <input
                        type='text'
                        name='currentPassword'
                        placeholder='Enter current Password'
                        value={currentPassword}
                        className={`form-control mt-3 ${formError.currentPassword || apiError ? 'is-invalid':''}`}
                        onChange={(e) => setCurrentPassword(e.target.value)}
                    >
                    </input>
                    {formError.currentPassword && <div className='invalid-feedback'>{formError.currentPassword}</div>}
                    <input
                        type='text'
                        name='newPassword'
                        placeholder='Enter New Password'
                        value={newPassword}
                        className={`form-control mt-3 ${formError.newPassword || apiError ? 'is-invalid':''}`}
                        onChange={(e) => setNewPassword(e.target.value)}
                    ></input>
                    {formError.newPassword && <div className='invalid-feedback'>{formError.newPassword}</div>}

                    <input
                        type='text'
                        name='confirmNewPassword'
                        placeholder='Repeat New Password'
                        value={confirmNewPassword}
                        className={`form-control mt-3 ${formError.confirmNewPassword || apiError ? 'is-invalid':''}`}
                        onChange={(e) => handleRepeatChange(e.target.value)}
                    ></input>
                    {formError.confirmNewPassword && <div className='invalid-feedback'>{formError.confirmNewPassword}</div>}
                    
                    {passwordError && <div className='text-danger mt-2'>{passwordError}</div>}
                    {apiError && <div className='text-danger mt-2'>{apiError}</div>}
                </div>

                <div className='mt-3'>
                    <button className='btn btn-success mt-1'>Submit</button>
                </div>
            </form>

        </div>
    )
}

export default ResetPassword