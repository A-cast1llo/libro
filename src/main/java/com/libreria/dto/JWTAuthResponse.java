package com.libreria.dto;

public class JWTAuthResponse {
	private String accessToken;
	private String tokenType = "Bearer";

	public JWTAuthResponse() {
	}

	public JWTAuthResponse(String accessToken, String tokenType) {
		this.accessToken = accessToken;
		this.tokenType = tokenType;
	}

	// Getters y setters
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	@Override
	public String toString() {
		return "JWTAuthResponse [accessToken=" + accessToken + ", tokenType=" + tokenType + "]";
	}
}
