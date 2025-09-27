import DashboardLayout from "./DashboardLayout";

const AdminDashboard = () => {
  return (
    <div className="container">
       <div>
          <h2>Admin Dashboard</h2>
          <p>Here you can manage users, roles, and system settings.</p>
       </div>

       <div className="card profile-card shadow-lg">
        <div className="card-body">
          <h3 className="card-title text-center mb-4 text-primary fw-bold">
            User Details
          </h3>
          <ul className="list-group list-group-flush">
            

          </ul>
        </div>

       </div>
    </div>
  );
};

export default AdminDashboard;