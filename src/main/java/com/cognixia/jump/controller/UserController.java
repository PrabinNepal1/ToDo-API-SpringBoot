package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.AuthenticationResponse;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.ToDosUserDetailsService;
import com.cognixia.jump.service.UserService;
import com.cognixia.jump.util.JwtUtil;

@RequestMapping("/api")
@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ToDosUserDetailsService toDosUserDetailsService;
	
	
	@Autowired
	private JwtUtil jwtTokenUtil;

	@GetMapping("/user")
	public List<User> getAllUser(){
		return userRepository.findAll();
	}
	
	@PostMapping("/add/user")
	public ResponseEntity<?> addUser(@RequestBody AuthenticationRequest registeringUser) throws Exception {
		
		userService.createNewUser(registeringUser);
		
		return ResponseEntity.ok(registeringUser.getUsername() + " has been created.");
		
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
		throws Exception{
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), 
															authenticationRequest.getPassword()));
			
			
		}catch(BadCredentialsException e) {
			
			throw new Exception("Incorrect Username or password.", e);
			
		}catch(Exception e) {
			throw new Exception(e);
		}
		
		final UserDetails USER_DETAILS = toDosUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String JWT = jwtTokenUtil.generateToken(USER_DETAILS);
		
		return ResponseEntity.ok(new AuthenticationResponse(JWT));
	}
	
	
	@PostMapping("/update/user/username")
	public ResponseEntity<?> updateUsername(Authentication req, @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		
		userService.updateUserName(req, authenticationRequest);
		
		return ResponseEntity.ok(authenticationRequest.getUsername() + "Your username has been updated.");
	}
	
	
	@PostMapping("/update/user/password")
	public ResponseEntity<?> updatePassWord(Authentication req, @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		
		userService.updatePassword(req, authenticationRequest);
		
		return ResponseEntity.ok(authenticationRequest.getUsername() + ". Your password has been updated.");
	}

	
}
