import React, { useContext, useEffect, useState } from "react";
import {
  Container,
  Typography,
  Paper,
  Box,
  Chip,
  Divider,
  Button,
  CircularProgress,
  Link as MuiLink,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import axiosInstance from "../../util/AxiosInstance";

const ViewProfile = ({viewId}) => {
  const { userId, role } = useContext(AuthContext);
  const id = (role==="FREELANCER")?(userId):(viewId)
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
    const navigate  = useNavigate();
  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const res = await axiosInstance.get(`/profiles/user/${id}`);
        setProfile(res.data);
      } catch (err) {
        console.error("Failed to load profile:", err);
      } finally {
        setLoading(false);
      }
    };

    if (id) {
      fetchProfile();
    }
  }, [id, role]);

//   if (role !== "FREELANCER") return <Typography>Access Denied</Typography>;
  if (loading) return <CircularProgress />;
  if (!profile) return <Typography>No profile found.</Typography>;

  return (
    <Container maxWidth="sm" sx={{ mt: 4 }}>
        <Button variant="contained" onClick={() => navigate("/freelancer/profile-form")}>
         Edit
        </Button>
      <Paper elevation={3} sx={{ p: 3, borderRadius: 3 }}>
        <Typography variant="h5" gutterBottom>
          My Profile
        </Typography>
        <Divider sx={{ mb: 2 }} />

        <Box sx={{ mb: 2 }}>
          <Typography variant="subtitle1">Name</Typography>
          <Typography variant="body1">{profile.name}</Typography>
        </Box>

        <Box sx={{ mb: 2 }}>
          <Typography variant="subtitle1">Bio</Typography>
          <Typography variant="body2">{profile.bio}</Typography>
        </Box>

        <Box sx={{ mb: 2 }}>
          <Typography variant="subtitle1">Skills</Typography>
          {profile.skills && profile.skills.length > 0 ? (
            <Box sx={{ display: "flex", flexWrap: "wrap", gap: 1 }}>
              {profile.skills.map((skill, index) => (
                <Chip key={index} label={skill} color="primary" />
                ))}
            </Box>
          ) : (
            <Typography variant="body2">No skills added.</Typography>
          )}
        </Box>

        <Box sx={{ mb: 2 }}>
          <Typography variant="subtitle1">Pricing</Typography>
          <Typography variant="body2">${profile.pricing}</Typography>
        </Box>

        <Box sx={{ mb: 2 }}>
          <Typography variant="subtitle1">Portfolio</Typography>
          {profile.portfolioLink ? (
            <MuiLink href={profile.portfolioLink} target="_blank" rel="noopener">
              View Portfolio
            </MuiLink>
          ) : profile.portfolioFilePath ? (
            <MuiLink
                href={`http://localhost:8080/uploads/${profile.portfolioFilePath.split('\\').pop()}`}
                target="_blank"
                rel="noopener"
                >
                Download Portfolio
            </MuiLink>

          ) : (
            <Typography variant="body2">No portfolio uploaded.</Typography>
          )}
        </Box>
      </Paper>
    </Container>
  );
};

export default ViewProfile;
