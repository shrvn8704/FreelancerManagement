import React, { useContext, useEffect, useState } from "react";
import axiosInstance from "../../util/AxiosInstance";
import {
  Container,
  TextField,
  Button,
  Typography,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
  OutlinedInput,
  Box,
  Chip,
} from "@mui/material";
import { AuthContext } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";

const FreelancerProfileForm = () => {
  const { userId, role } = useContext(AuthContext);
  const [profile, setProfile] = useState(null);
  const [skills, setSkills] = useState([]);
  const [selectedSkills, setSelectedSkills] = useState([]);
  const [file, setFile] = useState(null);
  const navigate = useNavigate();
  const [formValues, setFormValues] = useState({
    name: "",
    bio: "",
    portfolioLink: "",
    pricing: "",
  });

  useEffect(() => {
    if (role !== "FREELANCER") return;
  
    axiosInstance.get(`/profiles/user/${userId}`).then((res) => {
      const data = res.data;
      setProfile(data);
      setFormValues({
        name: data.name,
        bio: data.bio,
        portfolioLink: data.portfolioLink,
        pricing: data.pricing,
      });
  
      axiosInstance.get("/skills").then((skillsRes) => {
        const allSkills = skillsRes.data;
        setSkills(allSkills);
  
        const matchedSkillIds = data.skills
          .map((name) => {
            const match = allSkills.find((s) => s.name === name);
            return match?.skillId;
          })
          .filter(Boolean); // remove undefined
  
        setSelectedSkills(matchedSkillIds);
      });
    });
  }, [userId, role]);
  

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues((prev) => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e) => {
    const selected = e.target.files[0];
    if (selected) setFile(selected);
  };
  const uploadPortfolio = async (userId, file) => {
    const formData = new FormData();
    formData.append("file", file);
  
    return axiosInstance.post(`/profiles/${userId}/upload`, formData, {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axiosInstance.put(`/profiles/${profile.profileId}`, {
        ...formValues,
        skillIds: selectedSkills,
        userId: userId,
      });

      if (file) {
        await uploadPortfolio(userId,file);
      }

      alert("Profile updated successfully!");
      navigate('/freelancer/profile');
    } catch (err) {
      console.error("Error updating profile:", err);
      alert("Something went wrong while updating your profile.");
    }
  };

  if (role !== "FREELANCER") return <Typography>Access denied</Typography>;
  if (!profile) return <Typography>Loading profile...</Typography>;

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Typography variant="h5" gutterBottom>
        Update Your Profile
      </Typography>
      <form onSubmit={handleSubmit}>
        <TextField
          fullWidth
          label="Name"
          name="name"
          value={formValues.name}
          onChange={handleChange}
          margin="normal"
        />
        <TextField
          fullWidth
          label="Bio"
          name="bio"
          value={formValues.bio}
          onChange={handleChange}
          margin="normal"
          multiline
          rows={3}
        />
        <TextField
          fullWidth
          label="Portfolio Link"
          name="portfolioLink"
          value={formValues.portfolioLink}
          onChange={handleChange}
          margin="normal"
        />
        <TextField
          fullWidth
          type="number"
          label="Pricing ($)"
          name="pricing"
          value={formValues.pricing}
          onChange={handleChange}
          margin="normal"
        />

        <FormControl fullWidth margin="normal">
          <InputLabel>Skills</InputLabel>
          <Select
            multiple
            value={selectedSkills}
            onChange={(e) => setSelectedSkills(e.target.value)}
            input={<OutlinedInput label="Skills" />}
            renderValue={(selected) => (
              <Box sx={{ display: "flex", flexWrap: "wrap", gap: 0.5 }}>
                {selected.map((id) => {
                  const skill = skills.find((s) => s.skillId === id);
                  return <Chip key={id} label={skill?.name || "Unknown"} />;
                })}
              </Box>
            )}
          >
            {skills.map((skill) => (
              <MenuItem key={skill.skillId} value={skill.skillId}>
                {skill.name}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <Button variant="contained" component="label" sx={{ mt: 2 }}>
          Upload Portfolio (PDF)
          <input type="file" hidden accept=".pdf" onChange={handleFileChange} />
        </Button>

        <Box sx={{ mt: 3 }}>
          <Button variant="contained" type="submit">
            Save Changes
          </Button>
        </Box>
      </form>
    </Container>
  );
};

export default FreelancerProfileForm;
