import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import {jwtDecode} from "jwt-decode";

const ProtectedRoute = () => {
  const token = localStorage.getItem("token");

  const isTokenExpired = (token) => {
    try {
      const decoded = jwtDecode(token);
      return decoded.exp * 1000 < Date.now(); // expired if exp < now
    } catch (err) {
      return true; // invalid token
    }
  };

  if (!token || isTokenExpired(token)) {
    localStorage.clear();
    return <Navigate to="/login" replace />;
  }

  return <Outlet />; // render nested routes
};

export default ProtectedRoute;
