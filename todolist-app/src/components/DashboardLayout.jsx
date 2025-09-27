import React from "react";
import { useNavigate, Outlet, useLocation } from "react-router-dom";

const DashboardLayout = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const username = localStorage.getItem("username");
  const role = localStorage.getItem("roles");
  const token = localStorage.getItem("token");

  // Normalize roles
  let normalizedRoles = [];
  if (role) {
    try {
      normalizedRoles = JSON.parse(role); // e.g. ["ROLE_USER"]
    } catch {
      normalizedRoles = [role.trim()];
    }
  }

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  const profileDetails = () => {
    navigate("/profile-details");
  };

  const goToDashboard = () => {
    // If user not logged in → go to login
    if (!token) {
      navigate("/login");
      return;
    }

    // Logged in → go to role-based dashboard
    if (normalizedRoles.includes("ROLE_ADMIN")) {
      navigate("/admin-dashboard");
    } else if (normalizedRoles.includes("ROLE_MODERATOR")) {
      navigate("/mod-dashboard");
    } else if (normalizedRoles.includes("ROLE_USER")) {
      navigate("/user-dashboard");
    } else {
      navigate("/unauthorized");
    }
  };

  // Check if we are currently on login or signup page
  const isAuthPage =
    location.pathname === "/login" || location.pathname === "/signup";

  return (
    <div>
      {/* Header */}
      <header className="navbar navbar-dark bg-dark px-3">
        <span
          className="navbar-brand mb-0 h1"
          style={{ cursor: "pointer" }}
          onClick={goToDashboard}
        >
          Todo App - Dashboard
        </span>

        <div className="d-flex align-items-center">
          {!isAuthPage && token ? (
            <>
              <span className="text-light me-3">Hello, {username}</span>
              <button
                className="btn btn-outline-light btn-sm"
                onClick={profileDetails}
              >
                Profile
              </button>
              <button
                className="btn btn-outline-light btn-sm"
                style={{ marginLeft: "5px" }}
                onClick={handleLogout}
              >
                Logout
              </button>
            </>
          ) : null}
        </div>
      </header>

      {/* Page Content */}
      <main className="container mt-4">
        <Outlet />
      </main>
    </div>
  );
};

export default DashboardLayout;

