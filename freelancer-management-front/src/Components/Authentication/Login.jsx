import React, { useContext, useEffect, useState } from "react";
import { Typography, Button,TextField, Container, Paper } from "@mui/material";
import {useNavigate } from "react-router-dom";
import axiosInstance from "../../util/AxiosInstance";
import { AuthContext } from "../../context/AuthContext";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const {setIsLoggedIn,userId,setUserId,fetchUser} = useContext(AuthContext);
    
    const navigate = useNavigate();
  
    const handleLogin = async (e) => {
      e.preventDefault();
      try {
        const response = await axiosInstance.post("/auth/login", { username, password });
        console.log(response.data);
        localStorage.setItem("token", response.data.token);
        localStorage.setItem("userId", response.data.userid);
        setUserId(response.data.userid);
        setIsLoggedIn(true)
       // fetchUser(response.data.userId)
        navigate("/home");
      } catch (error) {
        console.error("Login failed", error);
      }
    };
    
  
    return (
      <Container component="main" maxWidth="xs">
        <Paper elevation={3} sx={{ p: 3, mt: 5 }}>
          <Typography variant="h5" gutterBottom>
            Login
          </Typography>
          <form onSubmit={handleLogin}>
            <TextField fullWidth label="Username" margin="normal" value={username} onChange={(e) => setUsername(e.target.value)} />
            <TextField fullWidth label="Password" type="password" margin="normal" value={password} onChange={(e) => setPassword(e.target.value)} />
            <Button type="submit" variant="contained" fullWidth sx={{ mt: 2 }}>
              Login
            </Button>
          </form>
        </Paper>
      </Container>
    );
}

export default Login