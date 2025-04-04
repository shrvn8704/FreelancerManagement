package com.ey.response;

import java.util.Set;
import java.util.stream.Collectors;

import com.ey.model.Profile;
import com.ey.model.Skill;

public class ProfileResponse {
    private Long profileId;
    private String name;
    private String bio;
    private String portfolioLink;
    private String portfolioFilePath;
    private Double pricing;
    private Set<String> skills; // List of skill names

    public ProfileResponse(Profile profile) {
        this.profileId = profile.getProfileId();
        this.name = profile.getName();
        this.bio = profile.getBio();
        this.portfolioLink = profile.getPortfolioLink();
        this.portfolioFilePath = profile.getPortfolioFilePath();
        this.pricing = profile.getPricing();
        this.skills = profile.getSkills().stream().map(Skill::getName).collect(Collectors.toSet());
    }

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
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

	public Set<String> getSkills() {
		return skills;
	}

	public void setSkills(Set<String> skills) {
		this.skills = skills;
	}

    
}
