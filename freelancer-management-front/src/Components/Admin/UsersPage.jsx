import React, { useEffect, useState, useContext } from 'react';
import { Box, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Typography, Select, MenuItem } from '@mui/material';
import axiosInstance from '../../util/AxiosInstance';
import { AuthContext } from '../../context/AuthContext';

const UsersPage = () => {
    const { role } = useContext(AuthContext);
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (role === 'ADMIN') {
            fetchUsers();
        }
    }, [role]);

    const fetchUsers = async () => {
        try {
            const response = await axiosInstance.get('/admin/users');
            setUsers(response.data);
        } catch (error) {
            console.error('Error fetching users', error);
        } finally {
            setLoading(false);
        }
    };

    const handleStatusChange = async (userId, newStatus) => {
        try {
            await axiosInstance.put(`/admin/user/${userId}/status`, { status: newStatus });
            setUsers(prevUsers => prevUsers.map(user => user.userId === userId ? { ...user, userStatus: newStatus } : user));
        } catch (error) {
            console.error('Error updating user status', error);
        }
    };

    const handleDeleteUser = async (userId) => {
        try {
            await axiosInstance.delete(`/admin/user/${userId}`);
            setUsers(prevUsers => prevUsers.filter(user => user.userId !== userId));
        } catch (error) {
            console.error('Error deleting user', error);
        }
    };

    if (loading) {
        return <Typography>Loading users...</Typography>;
    }

    return (
        <Box p={3}>
            <Typography variant="h4" gutterBottom>Manage Users</Typography>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Username</TableCell>
                            <TableCell>Email</TableCell>
                            <TableCell>Role</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {users.map(user => (
                            <TableRow key={user.userId}>
                                <TableCell>{user.userId}</TableCell>
                                <TableCell>{user.username}</TableCell>
                                <TableCell>{user.email}</TableCell>
                                <TableCell>{user.role}</TableCell>
                                <TableCell>
                                    <Select
                                        value={user.userStatus}
                                        onChange={(e) => handleStatusChange(user.userId, e.target.value)}
                                    >
                                        <MenuItem value="PENDING">Pending</MenuItem>
                                        <MenuItem value="ACCEPTED">Accepted</MenuItem>
                                        <MenuItem value="REJECTED">Rejected</MenuItem>
                                    </Select>
                                </TableCell>
                                <TableCell>
                                    <Button color="error" onClick={() => handleDeleteUser(user.userId)}>Delete</Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
};

export default UsersPage;
