package com.example.seguranca.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UsuarioRequest {

	@NotNull
	private Long id;

	@NotBlank
	private String password;

	private String login;

	private String token;

	private String returnUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
