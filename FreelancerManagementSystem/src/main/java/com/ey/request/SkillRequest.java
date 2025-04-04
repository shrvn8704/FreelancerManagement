package com.ey.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SkillRequest {
    private String name;

	public SkillRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SkillRequest(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}  
    
}
