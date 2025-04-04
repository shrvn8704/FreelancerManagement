
import React, { useState, useEffect, useContext } from "react";
import { Button, TextField, List, ListItem, ListItemText, IconButton, Container, Typography, Paper } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import axiosInstance from "../../util/AxiosInstance";
import { AuthContext } from "../../context/AuthContext";

const SkillManagement = () => {
  const [skills, setSkills] = useState([]);
  const [newSkill, setNewSkill] = useState("");
  const { user } = useContext(AuthContext);

  useEffect(() => {
    fetchSkills();
  }, []);

  const fetchSkills = async () => {
    try {
      const response = await axiosInstance.get("/admin/skills");
      setSkills(response.data);
    } catch (error) {
      console.error("Error fetching skills", error);
    }
  };

  const handleAddSkill = async () => {
    if (!newSkill.trim()) return;
    try {
      const response = await axiosInstance.post("/admin/skills", { name: newSkill });
      setSkills([...skills, response.data]);
      setNewSkill("");
    } catch (error) {
      console.error("Error adding skill", error);
    }
  };

  const handleDeleteSkill = async (skillId) => {
    try {
      await axiosInstance.delete(`/admin/skills/${skillId}`);
      setSkills(skills.filter((skill) => skill.skillId !== skillId));
    } catch (error) {
      console.error("Error deleting skill", error);
    }
  };

  if (user.role !== "ADMIN") {
    return <Typography variant="h6">Access Denied</Typography>;
  }

  return (
    <Container component={Paper} sx={{ p: 3, mt: 5 }}>
      <Typography variant="h5" gutterBottom>
        Skill Management
      </Typography>
      <TextField
        fullWidth
        label="New Skill"
        value={newSkill}
        onChange={(e) => setNewSkill(e.target.value)}
        sx={{ mb: 2 }}
      />
      <Button variant="contained" onClick={handleAddSkill} sx={{ mb: 2 }}>
        Add Skill
      </Button>
      <List>
        {skills.map((skill) => (
          <ListItem key={skill.skillId} secondaryAction={
            <IconButton edge="end" onClick={() => handleDeleteSkill(skill.skillId)}>
              <DeleteIcon />
            </IconButton>
          }>
            <ListItemText primary={skill.name} />
          </ListItem>
        ))}
      </List>
    </Container>
  );
};

export default SkillManagement;
