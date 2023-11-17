package br.com.guifroes1984.ecom.dto;

import lombok.Data;

@Data
public class SignupRequest {
	
	private String email;
	
	private String password;
	
	private String name;

}
