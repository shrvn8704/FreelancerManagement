package com.ey.request;

import java.util.Set;

public class ProfileRequest {
    private Long userId;
    private String name;
    private String bio;
    private String portfolioLink;
    private Double pricing;
    private Set<Long> skillIds; // List of skill IDs
	public ProfileRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProfileRequest(Long userId, String name, String bio, String portfolioLink, Double pricing,
			Set<Long> skillIds) {
		super();
		this.userId = userId;
		this.name = name;
		this.bio = bio;
		this.portfolioLink = portfolioLink;
		this.pricing = pricing;
		this.skillIds = skillIds;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Double getPricing() {
		return pricing;
	}
	public void setPricing(Double pricing) {
		this.pricing = pricing;
	}
	public Set<Long> getSkillIds() {
		return skillIds;
	}
	public void setSkillIds(Set<Long> skillIds) {
		this.skillIds = skillIds;
	}

    
}
