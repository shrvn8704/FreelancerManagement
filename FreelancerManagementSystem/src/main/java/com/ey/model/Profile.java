package com.ey.model;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "profiles")
public class Profile {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long profileId;

	    @OneToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;

	    private String name;
	    private String bio;
	    private String portfolioLink;
	    private String portfolioFilePath;
	    private Double pricing;

	    @ManyToMany
	    @JoinTable(
	        name = "profile_skills",
	        joinColumns = @JoinColumn(name = "profile_id"),
	        inverseJoinColumns = @JoinColumn(name = "skill_id")
	    )
	    private Set<Skill> skills;

	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;

	    public Profile() {
	        this.createdAt = LocalDateTime.now();
	        this.updatedAt = LocalDateTime.now();
	    }

	    public Profile(User user, String name, String bio, String portfolioLink, String portfolioFilePath, Double pricing, Set<Skill> skills) {
	        this.user = user;
	        this.name = name;
	        this.bio = bio;
	        this.portfolioLink = portfolioLink;
	        this.portfolioFilePath = portfolioFilePath;
	        this.pricing = pricing;
	        this.skills = skills;
	        this.createdAt = LocalDateTime.now();
	        this.updatedAt = LocalDateTime.now();
	    }

		public Long getProfileId() {
			return profileId;
		}

		public void setProfileId(Long profileId) {
			this.profileId = profileId;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getBio() {
			return bio;
		}

		public void setBio(String bio) {
			this.bio = bio;
		}

		public String getPortfolioLink() {
			return portfolioLink;
		}

		public void setPortfolioLink(String portfolioLink) {
			this.portfolioLink = portfolioLink;
		}

		public String getPortfolioFilePath() {
			return portfolioFilePath;
		}

		public void setPortfolioFilePath(String portfolioFilePath) {
			this.portfolioFilePath = portfolioFilePath;
		}

		public Double getPricing() {
			return pricing;
		}

		public void setPricing(Double pricing) {
			this.pricing = pricing;
		}

		public Set<Skill> getSkills() {
			return skills;
		}

		public void setSkills(Set<Skill> skills) {
			this.skills = skills;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}
	    
	    
}
