package com.example.questApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.questApp.entities.User;
import com.example.questApp.requests.UserRequest;
import com.example.questApp.responses.AuthResponse;
import com.example.questApp.security.JwtTokenProvider;
import com.example.questApp.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	
	
	
	public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
			UserService userService, PasswordEncoder passwordEncoder) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody UserRequest loginRequest) {
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken
				(loginRequest.getUsername(), loginRequest.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenProvider.generateJwtToken(auth);
		User user = userService.getUserByUsername(loginRequest.getUsername());
		AuthResponse authResponse = new AuthResponse();
		authResponse.setMessage("Bearer " +jwtToken);
		authResponse.setUserId(user.getId());
		return authResponse;
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody UserRequest request) {
		
		AuthResponse authResponse = new AuthResponse();
		if(userService.getUserByUsername(request.getUsername()) != null) {
			authResponse.setMessage("Username is already in use!");
			return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST); 
		}
		User user = new User();
		user.setUserName(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		userService.createUser(user);
		authResponse.setMessage("User successfully registered");
		return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
	}
}
