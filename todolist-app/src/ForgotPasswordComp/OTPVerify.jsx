import React, { useState } from 'react'
import axios from "axios";
import { useNavigate } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';

const OTPVerify = () => {

    const[otp,setOtp] = useState("");
    const[formErrors,setFormErrors] = useState("");
    const[apiErrors,setApiErrors] = useState(""); 

    const email = localStorage.getItem("email");

    const navigator = useNavigate();

    function validateForm(){
        let valid = true;
        const errorCopy = {...formErrors};

        if(!otp.trim()){
           setFormErrors('OTP Required');
           return false;
        }

        const otpRegex = /^\d{6}$/
        if(!otpRegex.test(otp)){
            setFormErrors("Enter Valid OTP!");
            return false;
        }
        
        setFormErrors("");
        return valid;
    
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        setApiErrors("");

        if (!validateForm()) {
            return;
        }

        try{
            const res = await axios.post(
                `http://localhost:8000/api/v1/forgotPassword/otp-verify/${email}/${otp}`
            );

            console.log(res.data);

            navigator("/change-password");
        }
        catch (err) {
            setApiErrors(err.response?.data?.message || "Enter Valid OTP!");
        }
    }

  return (
    <div className='container mt-2'>
        <h2>OTP Verify</h2>
        
        <form onSubmit={handleSubmit}>
            <div className='form-control mt-3'>
               <input 
                 type='number'
                 name = 'otp'
                 placeholder='Enter OTP'
                 value={otp}
                 className={`form-control ${formErrors || apiErrors ? 'is-invalid':''}`}
                 onChange={(e)=> setOtp(e.target.value)}
               >
               </input>
                 {/* Show frontend error */}
                    {formErrors && <div className="invalid-feedback">{formErrors}</div>}
                    {/* Show backend error */}
                    {apiErrors && <div className="invalid-feedback">{apiErrors}</div>}
            </div>

            <div>
                <button className='btn btn-success w-100'>
                    Submit
                </button>
            </div>
  
        </form>

    </div>
  )
}

export default OTPVerify