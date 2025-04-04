package com.ey.response;

public class JwtResponse {
    private String token;
    private Long userid;

    

    public JwtResponse(String token, Long userid) {
		super();
		this.token = token;
		this.userid = userid;
	}

	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
    
}
