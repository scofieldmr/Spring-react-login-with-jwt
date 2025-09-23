import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';

const SignupPage = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
    role: ""
  });

  const [error, setError] = useState("");
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setMessage("");

    try {
      const res = await axios.post("http://localhost:8000/api/v1/signup", formData);
      setMessage(res.data.message || "Signup successful!");
      setTimeout(() => navigate("/login"), 2000); // redirect to login
    } catch (err) {
      setError(err.response?.data?.message || "Signup failed");
    }
  };

  return (
    <div className="container mt-5">
      <h2>Sign Up</h2>
      {error && <p className="text-danger">{error}</p>}
      {message && <p className="text-success">{message}</p>}

      <form onSubmit={handleSubmit}>
        <input className="form-control mb-2" 
        type="text" 
        name="firstName"
        placeholder="First Name" 
        onChange={handleChange} />

        <input className="form-control mb-2" 
        type="text"
         name="lastName" 
        placeholder="Last Name" 
        onChange={handleChange} />

        <input className="form-control mb-2" 
        type="email" 
        name="email" 
        placeholder="Email" 
        onChange={handleChange} />

        <input className="form-control mb-2" 
        type="password" 
        name="password" 
        placeholder="Password"
         onChange={handleChange} />

        <input className="form-control mb-2"
         type="password" 
         name="confirmPassword" 
         placeholder="Confirm Password" 
         onChange={handleChange} />

        <input className="form-control mb-2" 
        type="text" 
        name="role" 
        placeholder="Role (e.g. USER)" 
        onChange={handleChange} />

        <button className="btn btn-primary w-100" type="submit">Sign Up</button>
      </form>
    </div>
  );
};

export default SignupPage;
