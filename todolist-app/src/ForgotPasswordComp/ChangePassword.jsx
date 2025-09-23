import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import 'bootstrap/dist/css/bootstrap.min.css';

const ChangePassword = () => {

    const [newPassword, setNewPassword] = useState("");
    const [repeatNewPassword, setRepeatNewPassword] = useState("");

    const [formErrors, setFormErrors] = useState({
        newPassword: '',
        repeatNewPassword: ''
    });
    const [apiErrors, setApiErrors] = useState("");

    const navigator = useNavigate();
    const email = localStorage.getItem("email");

    const [passwordError, setPasswordError] = useState("");

    // Real-time password mismatch check
    const handleRepeatChange = (value) => {
        setRepeatNewPassword(value);
        if (newPassword && value && newPassword !== value) {
            setPasswordError("Passwords do not match");
        } else {
            setPasswordError("");
        }
    };

    function validateForm() {
        let valid = true;
        const errorCopy = { ...formErrors };

        if (!newPassword.trim()) {
            errorCopy.newPassword = "New Password Required";
            valid = false;
        }

        if (!repeatNewPassword.trim()) {
            errorCopy.repeatNewPassword = "Repeat Password Required";
            valid = false;
        }

        const passwordRegex = /^.{4,10}$/;
        if (newPassword && !passwordRegex.test(newPassword)) {
            errorCopy.newPassword = "Password should be 4 to 10 characters long";
            valid = false;
        }

        if (repeatNewPassword && !passwordRegex.test(repeatNewPassword)) {
            errorCopy.repeatNewPassword = "Password should be 4 to 10 characters long";
            valid = false;
        }

        if (newPassword && repeatNewPassword && newPassword !== repeatNewPassword) {
            setPasswordError("Passwords do not match");
            valid = false;
        } else {
            setPasswordError("");
        }

        setFormErrors(errorCopy);
        return valid;
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        setApiErrors("");

        if (!validateForm()) {
            return;
        }

        const changePass = { newPassword, repeatNewPassword };

        try {
            const res = await axios.post(
                `http://localhost:8000/api/v1/forgotPassword/changePassword/${email}`,
                changePass
            );

            console.log(res.data);
            navigator("/login");
        } catch (err) {
            setApiErrors(err.response?.data?.message || "Error changing password");
        }

    }

    return (
        <div className='container mt-3 w-50'
            style={{ border: '1px solid black', padding: '10px', height: '45vh', borderRadius: '15px' }}>
            <h2 className='text-center'>Change Password Form</h2>

            <form onSubmit={handleSubmit}>
                <div className='form-control mt-4'>
                    <input
                        type='text'
                        name='newPassword'
                        placeholder='Enter New Password'
                        value={newPassword}
                        className={`form-control mt-3 ${formErrors.newPassword || apiErrors ? 'is-invalid' : ''}`}
                        onChange={(e) => setNewPassword(e.target.value)}
                    >
                    </input>
                    {formErrors.newPassword && <div className="invalid-feedback">{formErrors.newPassword}</div>}

                    <input
                        type='text'
                        name='repeatNewPassword'
                        placeholder='Repeat New Password'
                        value={repeatNewPassword}
                        className={`form-control mt-3 ${formErrors.repeatNewPassword || apiErrors ? 'is-invalid' : ''}`}
                        onChange={(e) => handleRepeatChange(e.target.value)}
                    ></input>
                    {formErrors.repeatNewPassword && <div className="invalid-feedback">{formErrors.repeatNewPassword}</div>}
                </div>

                {passwordError && <div className="text-danger mt-2">{passwordError}</div>}
                {apiErrors && <div className="text-danger mt-2">{apiErrors}</div>}

                <div>
                    <button className='btn btn-success'
                        style={{ marginTop: '15px' }}
                    >Change Password</button>
                </div>
            </form>
        </div>
    )
}

export default ChangePassword