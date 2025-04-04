import React, { useContext } from 'react'
import { Button } from '@mui/material'
import axiosInstance from '../../util/AxiosInstance'
import { useNavigate,Link } from 'react-router-dom'
import { AuthContext } from '../../context/AuthContext'

const Logout = () => {
    const navigate = useNavigate();
    const {setUser,setIsLoggedIn,setRole} = useContext(AuthContext);
    const handleLogout = async() =>{
        const response = await axiosInstance.post(`/auth/logout`);
        
        localStorage.removeItem('token');
        localStorage.removeItem('userId');
        setUser(null);
        setIsLoggedIn(false)
        setRole(null)
        alert("Logged Out!");
        navigate('/');
    }
  return (
    <Button color="inherit" onClick={()=>{handleLogout()}}>
        Logout
    </Button>
  )
}

export default Logout