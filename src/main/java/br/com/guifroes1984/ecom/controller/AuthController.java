package br.com.guifroes1984.ecom.controller;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.guifroes1984.ecom.dto.AuthenticationRequest;
import br.com.guifroes1984.ecom.dto.SignupRequest;
import br.com.guifroes1984.ecom.dto.UserDto;
import br.com.guifroes1984.ecom.entity.User;
import br.com.guifroes1984.ecom.repository.UserRepository;
import br.com.guifroes1984.ecom.services.auth.AuthService;
import br.com.guifroes1984.ecom.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;
	
	private final UserDetailsService userDetailsService;
	
	private final UserRepository userRepository;
	
	private final JwtUtils jwtUtils;
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String HEADER_STRING = "Authorization";
	
	private final AuthService authService;
	
	@PostMapping("/authenticate")
	public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, 
										  HttpServletResponse response) throws IOException, JSONException {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), 
					authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Usuário ou senha incorretos.");
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		Optional<User> optionalUsuario = userRepository.findFirstByEmail(userDetails.getUsername());
		final String jwt = jwtUtils.generateToken(userDetails.getUsername());
		
		if (optionalUsuario.isPresent()) {
			response.getWriter().write(new JSONObject()
					.put("userId", optionalUsuario.get().getId())
					.put("role", optionalUsuario.get().getRole())
					.toString()
			);
			
			response.addHeader("Access-Control-Expose-Headers", "Authorization");
			response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, " + 
					"X-Requested-With, Content-Type, Accept, X-Custom-header");
			response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
		}
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
		if (authService.hasuserWithEmail(signupRequest.getEmail())) {
			return new ResponseEntity<>("Usuário já existe", HttpStatus.NOT_ACCEPTABLE);
		}
		
		UserDto userDto = authService.createUser(signupRequest);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}
	
}
