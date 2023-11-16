package br.com.guifroes1984.ecom.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
	
	private String username;
	
	private String password;

}
