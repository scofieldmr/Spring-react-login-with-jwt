import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import { GoogleOAuthProvider, GoogleLogin } from "@react-oauth/google";

const SignupPage = () => {
  const navigate = useNavigate();

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [role, setRole] = useState("");

  const REACT_APP_API_URL= "https://spring-login-backend.onrender.com/api/v1";

  const LOCAL_HOST = "http://localhost:8000/api/v1/";

  const [formError, setFormError] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
    role: ""
  });

  const [apiError, setApiError] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [message, setMessage] = useState("");

  function validateField(name, value) {
  let error = "";

  if (name === "email") {
    if (!value.trim()) error = "Email Required !";
    else {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(value)) {
        error = "Enter a valid email address!";
      }
    }
  }

  setFormError((prev) => ({ ...prev, [name]: error }));
}

  function validateForm() {
    let valid = true;
    const passwordRegex = /^.{4,10}$/;

    const errorCopy = { ...formError };

    if (!firstName.trim()) {
      errorCopy.firstName = "First Name Required !";
      valid = false;
    }

    if (!lastName.trim()) {
      errorCopy.lastName = "Last Name Required !";
      valid = false;
    }

    if (!email.trim()) {
      errorCopy.email = "Email Required !";
      valid = false;
    }
    else {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(email)) {
        errorCopy.email = "Enter a valid email address!";
        valid = false;
      }
    }

    if (!password.trim()) {
      errorCopy.password = "Password Required !"
      valid = false;
    }
    else {
      if (password && !passwordRegex.test(password)) {
        errorCopy.password = "Password should be 4 to 10 characters long";
        valid = false;
      }
    }

    if (!confirmPassword.trim()) {
            errorCopy.confirmPassword = "Repeat Current Password Required";
            valid = false;
        }
        else {
            if (confirmPassword && !passwordRegex.test(confirmPassword)) {
                errorCopy.confirmPassword = "Password should be 4 to 10 characters long";
                valid = false;
            }
        }

    if (!role.trim()) {
      errorCopy.role = "Role Required !";
      valid = false;
    }

    setFormError(errorCopy);
    return valid;
  }

  const handleRepeatChange = (value) => {
    setConfirmPassword(value);
    if (password && value && confirmPassword !== value) {
      setPasswordError("Passwords do not match");
    } else {
      setPasswordError("");
    }
  }


  const handleSubmit = async (e) => {
    const formData = {firstName,lastName,email,password,confirmPassword,role};

    e.preventDefault();
    setApiError("");
    setMessage("");

    if(!validateForm()){
      return;
    }

    try {
      const res = await axios.post(`${REACT_APP_API_URL}/signup`, formData);
      setMessage(res.data.message || "Signup successful!");
      setTimeout(() => navigate("/login"), 1000); // redirect to login
    } catch (err) {
      setApiError(err.response?.data?.message || "Signup failed");
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center">
      <div className="card shadow-lg p-4 login-card">
        <h2 className="text-center">Sign Up Page</h2>

        <form onSubmit={handleSubmit}>
          <input
            type="text"
            name="firstName"
            placeholder="First Name"
            value={firstName}
            className={`form-control mt-3 ${formError.firstName || apiError ? 'is-invalid' : ''}`}
            onChange={(e) => setFirstName(e.target.value)} />

          {formError.firstName && <div className="invalid-feedback">{formError.firstName}</div>}

          <input
            type="text"
            name="lastName"
            placeholder="Last Name"
            value={lastName}
            className={`form-control mt-3 ${formError.lastName || apiError ? 'is-invalid' : ''}`}
            onChange={(e) => setLastName(e.target.value)} />

          {formError.lastName && <div className="invalid-feedback">{formError.lastName}</div>}

          <input 
            type="email"
            name="email"
            placeholder="Email"
            value={email}
            className={`form-control mt-3 ${formError.email || apiError ? 'is-invalid' :''}`}
            onChange={(e) =>{ setEmail(e.target.value),
               validateField("email", e.target.value); 
            }}/>

            {formError.email && <div className="invalid-feedback">{formError.email}</div>}

          <input
            type="password"
            name="password"
            placeholder="Password with 4 - 10 characters"
            value={password}
            className={`form-control mt-3 ${formError.password || apiError ? 'is-invalid': ''}`}
            onChange={(e) => setPassword(e.target.value)} />

            {formError.password && <div className="invalid-feedback">{formError.password}</div>}

          <input 
            type="password"
            name="confirmPassword"
            placeholder="Confirm Password"
            value={confirmPassword}
            className={`form-control mt-3 ${formError.confirmPassword || apiError ? 'is-invalid': ''}`}
            onChange={(e) => handleRepeatChange(e.target.value)} />

            {formError.confirmPassword && <div className="invalid-feedback">{formError.confirmPassword}</div>}

          <select
           name="role"
           value={role}
           className={`form-control mt-3 ${formError.role || apiError ? 'is-invalid':''}`}
           onChange={(e) => setRole(e.target.value)}
          >
            <option value="">---SELECT ROLE---</option>
            <option value="ADMIN">ADMIN</option>
            <option value="MODERATOR">MODERATOR</option>
            <option value="USER">USER</option>
          </select>

          {formError.role && <div className="is-invalid">{formError.role}</div>}

          <button className="btn btn-primary mt-3 w-100" type="submit">Sign Up</button>
        </form>

        <div className="mt-3">
         <GoogleOAuthProvider>
          </GoogleOAuthProvider>
      </div>
      </div>

    </div>
  );
};

export default SignupPage;
