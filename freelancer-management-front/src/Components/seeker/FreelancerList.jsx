import React, { useEffect, useState, useContext } from "react";
import {
  Card,
  CardContent,
  Typography,
  Button,
  Chip,
  Box,
  Grid,
  Container
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../../util/AxiosInstance";
import { AuthContext } from "../../context/AuthContext";

const FreelancerList = ({setViewId}) => {
  const [freelancers, setFreelancers] = useState([]);
  const { role } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    if (role !== "SEEKER") return;

    axiosInstance.get("/profiles").then((res) => {
      setFreelancers(res.data);
    }).catch((err) => {
      console.error("Failed to fetch freelancers:", err);
    });
  }, [role]);

  const handleViewProfile = (profileId) => {
    setViewId(profileId);
    navigate(`/freelancer/profile`);
  };

  const handleSendRequest = async (freelancerId) => {
    try {
      await axiosInstance.post(`/hire/send-request/${freelancerId}`);
      alert("Request sent successfully!");
    } catch (error) {
      console.error("Error sending request:", error);
      alert("Failed to send request.");
    }
  };

  if (role !== "SEEKER") return <Typography>Access denied</Typography>;

  return (
    <Container>
      <Typography variant="h4" gutterBottom>Available Freelancers</Typography>
      <Grid container spacing={2}>
        {freelancers.map((freelancer) => (
          <Grid item xs={12} sm={6} md={4} key={freelancer.profileId}>
            <Card>
              <CardContent>
                <Typography variant="h6">{freelancer.name}</Typography>
                <Typography variant="body2" gutterBottom>{freelancer.bio}</Typography>
                <Typography variant="body2">Pricing: ${freelancer.pricing}/hr</Typography>

                <Box sx={{ mt: 1, mb: 1 }}>
                  {freelancer.skills?.map((skill, index) => (
                    <Chip
                      key={index}
                      label={typeof skill === "string" ? skill : skill.name}
                      size="small"
                      sx={{ mr: 0.5, mb: 0.5 }}
                    />
                  ))}
                </Box>

                <Button
                  variant="outlined"
                  sx={{ mr: 1 }}
                  onClick={() => handleViewProfile(freelancer.profileId)}
                >
                  View Profile
                </Button>

                <Button
                  variant="contained"
                  color="primary"
                  onClick={() => handleSendRequest(freelancer.userId)}
                >
                  Send Request
                </Button>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Container>
  );
};

export default FreelancerList;
