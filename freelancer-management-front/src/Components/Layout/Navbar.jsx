import React, { useContext } from 'react'
import { AppBar, Toolbar, Typography, Button, Box} from "@mui/material";
import { Link } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import Logout from '../Authentication/Logout';

const Navbar = () => {
  const {isLoggedIn,role} = useContext(AuthContext);
    return (
        <AppBar position="static">
          <Toolbar>
            <Typography variant="h6" sx={{ flexGrow: 1 }}>
              Freelancer Platform
            </Typography>
            

            {role==="ADMIN"&&(
              <Box>
              <Button color="inherit" component={Link} to="/admin/skills">
                Skills
              </Button>
              <Button color="inherit" component={Link} to="/admin/users">
                Users
              </Button>
            </Box>
            )}

            {role==="SEEKER"&&(
              <Box>
              <Button color="inherit" component={Link} to="freelancers">
                Freelancers
              </Button>
              <Button color="inherit" component={Link} to="/admin/users">
                Users
              </Button>
            </Box>
            )}
            {role==="FREELANCER"&&(
              <Box>
              <Button color="inherit" component={Link} to="/freelancer/profile">
                Profile
              </Button>
              <Button color="inherit" component={Link} to="/admin/users">
                Jobs
              </Button>
            </Box>
            )}

            {!isLoggedIn? (
                <Box>
                <Button color="inherit" component={Link} to="/">
                  Login
                </Button>
                <Button color="inherit" component={Link} to="/signup">
                  Signup
                </Button>
              </Box>
              ):(
                <Box>
                  <Logout />
                </Box>
            )}
            
          </Toolbar>
        </AppBar>
    );
}

export default Navbar