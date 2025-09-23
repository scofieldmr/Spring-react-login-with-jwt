import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

const ProfileDetails = () => {
  const [profile, setProfile] = useState(null);
  const username = localStorage.getItem("username");
  const token = localStorage.getItem("token");

  useEffect(() => {
    axios
      .get("http://localhost:8000/api/v1/profileDetails", {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => setProfile(res.data))
      .catch((err) => {
        if (err.response && err.response.status === 401) {
          localStorage.clear();
          window.location.href = "/login";
        } else {
          console.error(err);
        }
      });
  }, [token]);

  if (!profile) {
    return (
      <div className="d-flex justify-content-center align-items-center vh-100">
        <div className="spinner-border text-primary" role="status">
          <span className="visually-hidden">Loading profile...</span>
        </div>
      </div>
    );
  }

  return (
    <div className="container d-flex justify-content-center align-items-center mt-5">
      <div className="card profile-card shadow-lg">
        <div className="card-body">
          <h3 className="card-title text-center mb-4 text-primary fw-bold">
            Profile Details
          </h3>
          <ul className="list-group list-group-flush">
            <li className="list-group-item">
              <strong>Username:</strong> {profile.email || username}
            </li>
            <li className="list-group-item">
              <strong>First Name:</strong> {profile.firstName}
            </li>
            <li className="list-group-item">
              <strong>Last Name:</strong> {profile.lastName}
            </li>
            <li className="list-group-item">
              <strong>Email:</strong> {profile.email}
            </li>
            <li className="list-group-item">
              <strong>Role:</strong>{" "}
              <span className="badge bg-secondary">{profile.role}</span>
            </li>
            <li className="list-group-item text-center">
              <strong>Reset Password:</strong>{" "}
              <Link
                to="/reset-password"
                className="btn btn-outline-primary btn-sm ms-2"
              >
                Click Here
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default ProfileDetails;
