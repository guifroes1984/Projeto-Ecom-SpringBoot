package br.com.guifroes1984.ecom.controller;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.guifroes1984.ecom.dto.AuthenticationRequest;
import br.com.guifroes1984.ecom.entity.Usuario;
import br.com.guifroes1984.ecom.repository.UserRepository;
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
		Optional<Usuario> optionalUsuario = userRepository.findFirstByEmail(userDetails.getUsername());
		final String jwt = jwtUtils.generateToken(userDetails.getUsername());
		
		if (optionalUsuario.isPresent()) {
			response.getWriter().write(new JSONObject()
					.put("userId", optionalUsuario.get().getId())
					.put("role", optionalUsuario.get().getRole())
					.toString()
			);
			
			response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
		}
	}
	
}
