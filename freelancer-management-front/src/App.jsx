import { useState,React } from 'react'
import './App.css'
import {BrowserRouter as Router, Routes, Route,Navigate} from 'react-router-dom'
import Layout from './Components/Layout/Layout'
import Login from './Components/Authentication/Login'
import Home from './Components/Home'
import Signup from './Components/Authentication/Signup'
import ProtectedRoute from './Components/ProtectedRoute'
import SkillManagement from './Components/Admin/SkillManagement'
import UsersPage from './Components/Admin/UsersPage'
import FreelancerProfileForm from './Components/Freelancer/FreelancerProfileForm'
import ViewProfile from './Components/Freelancer/ViewProfile'
import FreelancerList from './Components/seeker/FreelancerList'
function App() {
  const [viewId,setViewId] = useState('');
  return (
    <>
      <Router>
        <Layout />
        <Routes>
          {/* Public*/}
          <Route path="/signup" element={<Signup />} />
          <Route path="/" element={<Login />} />

          {/* Protected*/}
          <Route element={<ProtectedRoute />}>
            <Route path="/home" element={<Home />} />
            <Route path="/admin/skills" element={<SkillManagement />} />
            <Route path="/admin/users" element={<UsersPage />} />
            <Route path="/freelancer/profile-form" element={<FreelancerProfileForm />} />
            <Route path="/freelancer/profile" element={<ViewProfile viewId={viewId} />} />
            <Route path="/freelancers" element={<FreelancerList setViewId={setViewId} />} />

          </Route>

          {/* Default  */}
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </Router>
    </>
  )
}

export default App
