package com.ey.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SkillResponse {
	private Long skillId;
    private String name;
	public SkillResponse(Long skillId, String name) {
		super();
		this.skillId = skillId;
		this.name = name;
	}
	public SkillResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getSkillId() {
		return skillId;
	}
	public void setSkillId(Long skillId) {
		this.skillId = skillId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    
}
