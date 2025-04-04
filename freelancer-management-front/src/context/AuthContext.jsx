import { createContext, useState, useEffect } from "react";
import axiosInstance from "../util/AxiosInstance";
export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState(null);
    const [userId, setUserId] = useState('');
    const [role,setRole] = useState('');
    const fetchUser = async (userId) => {
        try {
            const response = await axiosInstance.get(`/users/${userId}`);
            setUser(response.data);
            setRole(response.data.role)
        } catch (error) {
            console.error("Error fetching user:", error);
        }
    };

    useEffect(() => {
        const storedUserId = localStorage.getItem("userId");
        if (storedUserId && !user) {  
            setUserId(storedUserId);  
            fetchUser(storedUserId);
            setIsLoggedIn(true);
        }
    }, []);
    useEffect(() => {
        console.log("Updated user:", user);
        console.log("role(state):",role);

    }, [user]);
    useEffect(() => {
        if (userId) {
            fetchUser(userId);
        }
    }, [userId]);
    return (
        <AuthContext.Provider value={{
            isLoggedIn,
            user,
            setUser,
            setIsLoggedIn,
            fetchUser,
            userId,
            setUserId,
            role,
            setRole
        }}>
            {isLoggedIn && user === null ? <div>Loading...</div> : children}
        </AuthContext.Provider>
    );
};
