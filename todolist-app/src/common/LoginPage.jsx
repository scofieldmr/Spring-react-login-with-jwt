import React, { useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';

const LoginPage = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [formError, setFormError] = useState({
    username: "",
    password: ""
  });
  const [apiError, setApiError] = useState("");

  function validateForm() {
    let valid = true;

    const errorCopy = { ...formError };

    if (!username.trim()) {
      errorCopy.username = "Username Required!"
      return false;
    }
    else {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(username)) {
        errorCopy.username = "Enter a valid email address!";
        valid = false;
      }
    }

    if (!password.trim()) {
      errorCopy.password = "Password required!"
      valid = false;
    }
    else {
      const passwordRegex = /^.{4,10}$/;
      if (password && !passwordRegex.test(password)) {
        errorCopy.password = "Password should be 4 to 10 characters long";
        valid = false;
      }
    }

    setFormError(errorCopy);
    return valid;
  }

  const handleSubmit = async (e) => {

    console.log("Username", username);
    console.log("Password", password);
    e.preventDefault();
    setApiError("");

    if (!validateForm()) {
      return;
    }

    try {
      const loginData = { username, password };

      console.log("Username", username);

      console.log("Sending loginData:", loginData);
      const res = await axios.post("http://localhost:8000/api/v1/jwtlogin", loginData);

      console.log("Full response:", res);
      console.log("Response data:", res.data);

      if (!res.data) {
        throw new Error("Empty response from backend");
      }

      const { username: uname, role, tokenType, token } = res.data;

      console.log("username:", uname);
      console.log("role (raw):", role);
      console.log("tokenType", tokenType);
      console.log("jwt:", token);

      // Normalize role → always array
      let normalizedRoles = [];
      if (Array.isArray(role)) {
        normalizedRoles = role;
      } else if (typeof role === "string") {
        if (role.startsWith("[") && role.endsWith("]")) {
          normalizedRoles = role.slice(1, -1).split(",").map(r => r.trim());
        } else {
          normalizedRoles = [role.trim()];
        }
      }

      // Save to localStorage
      localStorage.setItem("token", token);
      localStorage.setItem("roles", JSON.stringify(normalizedRoles));
      localStorage.setItem("username", uname);

      console.log("roles", JSON.stringify(normalizedRoles));

      // Redirect based on role
      if (normalizedRoles.includes("ROLE_ADMIN")) {
        navigate("/admin-dashboard");
      } else if (normalizedRoles.includes("ROLE_MODERATOR")) {
        navigate("/mod-dashboard");
      } else if (normalizedRoles.includes("ROLE_USER")) {
        navigate("/user-dashboard");
      } else {
        navigate("/unauthorized");
      }
    } catch (err) {
      console.log("Axios error:", err);
      console.log("Backend error response:", err.response?.data);

      setApiError(
        err.response?.data?.message ||
        err.response?.data?.error ||
        "Login failed"
      );

    }
  };

  return (
    <div className="login-container d-flex justify-content-center align-items-center vh-100">
       <div className="card shadow-lg p-4 login-card">
      <h2 className="text-center">Login Page</h2>

      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="username"
          placeholder="Email / Username"
          value={username}
          className={`form-control mt-3 ${formError.username || apiError ? "is-invalid" : ""}`}
          onChange={(e) => setUsername(e.target.value)}>
        </input>
        {formError.username && <div className="invalid-feedback">{formError.username}</div>}

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={password}
          className={`form-control mt-3 ${formError.password || apiError ? "is-invalid" : ""}`}
          onChange={(e) => setPassword(e.target.value)}>
        </input>
        {formError.password && <div className="invalid-feedback"> {formError.password}</div>}

        {apiError && <div className="text-danger mt-2">{apiError}</div>}

        <div>
          <button className="btn btn-success mt-3 w-100" type="submit">Login</button>
        </div>
      </form>

      {/* Signup link */}
      <div className="text-center mt-3">
        <p>
          Don’t have an account ?
          <Link to="/signup" className="btn btn-link">
            Sign up here
          </Link>
        </p>
      </div>

      <div className="text-center mb-3">
        <p>
          <Link to="/email-verify" className="btn btn-link" >
            Forgot Password?
          </Link>
        </p>
      </div>
      </div>
    </div>
  );
};

export default LoginPage;
