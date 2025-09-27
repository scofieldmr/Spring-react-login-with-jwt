import './App.css'
import { Navigate } from 'react-router-dom'
import { BrowserRouter, Routes, Route, Router } from 'react-router-dom'
import LoginPage from './common/LoginPage'
import AdminDashboard from './components/AdminDashboard'
import SignupPage from './common/SignupPage'
import UserDashboard from './components/UserDashboard'
import ModDashboard from './components/ModDashboard'
import ProfileDetails from './components/ProfileDetails'
import DashboardLayout from './components/DashboardLayout'
import ProtectedRoute from './route/ProtectedRoute'
import EmailVerify from './ForgotPasswordComp/EmailVerify'
import OTPVerify from './ForgotPasswordComp/OTPVerify'
import ChangePassword from './ForgotPasswordComp/ChangePassword'
import ResetPassword from './ResetPasswordComp/ResetPassword'


function App() {


  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route element={<DashboardLayout />}>
            {/* Public pages */}
            <Route path="/login" element={<LoginPage />} />
            <Route path="/signup" element={<SignupPage />} />
            <Route path='/email-verify' element={<EmailVerify />} />
            <Route path='/otp-verify' element={<OTPVerify />} />
            <Route path='/change-password' element={<ChangePassword />} />
            <Route path='/reset-password' element={<ResetPassword />} />

            {/* Protected routes */}
            <Route element={<ProtectedRoute />}>
              <Route path="/user-dashboard" element={<UserDashboard />} />
              <Route path="/mod-dashboard" element={<ModDashboard />} />
              <Route path="/admin-dashboard" element={<AdminDashboard />} />
              <Route path="/profile-details" element={<ProfileDetails />} />
            </Route>
            </Route>

                      {/* Default route → Login */}
             <Route path="/" element={<Navigate to="/login" replace />} />

            {/* Unauthorized */}
            <Route path="/unauthorized" element={<h2>Unauthorized Access 🚫</h2>} />

           
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
