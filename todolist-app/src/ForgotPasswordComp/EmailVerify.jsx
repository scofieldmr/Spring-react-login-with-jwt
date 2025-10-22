import React, { useState } from 'react'
import axios from "axios";
import { useNavigate } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';

const EmailVerify = () => {
    const [email, setEmail] = useState("");
    const [formError, setFormError] = useState("");  // frontend validation error
    const [apiError, setApiError] = useState("");    // backend validation error

    const navigate = useNavigate();

    const REACT_APP_API_URL = "https://spring-login-backend.onrender.com/api/v1";

    const LOCAL_HOST = "http://localhost:8000/api/v1/";

    // Frontend validation
    function validateForm() {
        if (!email.trim()) {
            setFormError("Email is required!");
            return false;
        }
        // basic regex for email validation
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            setFormError("Enter a valid email address!");
            return false;
        }
        setFormError("");
        return true;
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        setApiError(""); // clear previous API errors

        if (!validateForm()) return;

        try {
            const res = await axios.post(
                `${REACT_APP_API_URL}/forgotPassword/email-verify/${email}`
            );

            console.log(res.data);

            localStorage.setItem("email",email);

            // if backend says success → navigate
            navigate("/otp-verify");
        } catch (err) {
            setApiError(err.response?.data?.message || "Enter Registered email address!");
        }
    };

    return (
        <div className="container mt-5 w-50"
           style={{border:'1px solid black',borderRadius:'20px',height:'40vh',padding:'20px'}}
        >
            <h2 className='text-center'>Email Verification For OTP</h2>

            <form onSubmit={handleSubmit}>
                    <input
                        type="email"
                        name="email"
                        placeholder="Email"
                        value={email}
                        className={`form-control mt-3 ${formError || apiError ? 'is-invalid' : ''}`}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    {/* Show frontend error */}
                    {formError && <div className="invalid-feedback">{formError}</div>}
                    {/* Show backend error */}
                    {apiError && <div className="invalid-feedback">{apiError}</div>}
            
                <button className="btn btn-success w-100 mt-4" type="submit">
                    Send OTP
                </button>
            </form>
        </div>
    );
};

export default EmailVerify;
