package br.com.guifroes1984.ecom.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.guifroes1984.ecom.dto.SignupRequest;
import br.com.guifroes1984.ecom.dto.UserDto;
import br.com.guifroes1984.ecom.entity.User;
import br.com.guifroes1984.ecom.enums.UserRole;
import br.com.guifroes1984.ecom.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserDto createUser(SignupRequest signupRequest) {
		User usuario = new User();
		
		usuario.setEmail(signupRequest.getEmail());
		usuario.setName(signupRequest.getName());
		usuario.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		usuario.setRole(UserRole.CUSTOMER);
		User createdUsuario = userRepository.save(usuario);
		
		UserDto userDto = new UserDto();
		userDto.setId(createdUsuario.getId());
		
		return userDto;
	}
	
	public boolean hasuserWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}
	
	@PostConstruct
	public void createAdminAccount() {
		User adminAccount = userRepository.findByRole(UserRole.ADMIN);
		if (null == adminAccount) {
			User usuario = new User();
			usuario.setEmail("admin@teste.com");
			usuario.setName("admin");
			usuario.setRole(UserRole.ADMIN);
			usuario.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(usuario);
		}
	}
	
}
