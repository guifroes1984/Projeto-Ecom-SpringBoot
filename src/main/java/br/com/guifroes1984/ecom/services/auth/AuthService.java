package br.com.guifroes1984.ecom.services.auth;

import br.com.guifroes1984.ecom.dto.SignupRequest;
import br.com.guifroes1984.ecom.dto.UserDto;

public interface AuthService {
	
	UserDto createUser(SignupRequest signupRequest);

	boolean hasuserWithEmail(String email);
}
