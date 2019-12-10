package com.br.solufix.data;

public class UsuarioRequest {

	private String email;
	private String senha;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@Override
	public String toString() {
		return "UsuarioRequest [email=" + email + ", senha=" + senha + "]";
	}
	
	
}
