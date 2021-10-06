package com.cognixia.jump.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.exception.UserAlreadyExistsException;
import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	public boolean createNewUser(AuthenticationRequest registeringUser) throws Exception{
		
		Optional<User> isAlreadyRegistered = userRepository.findByUsername(registeringUser.getUsername());
		
		if(isAlreadyRegistered.isPresent()) {
			throw new UserAlreadyExistsException(registeringUser.getUsername());
		}
		
		User newUser = new User();
		newUser.setName(registeringUser.getName());
		newUser.setUsername(registeringUser.getUsername());
		newUser.setPassword(passwordEncoder.encode(registeringUser.getPassword()));
		newUser.setEnabled(true);
		newUser.setRole(registeringUser.getRole());
		
		userRepository.save(newUser);
		return true;
	}
	
	public boolean updateUserName(Authentication req, AuthenticationRequest user) throws Exception{
		
		Optional<User> isAlreadyRegistered = userRepository.findByUsername(req.getName());
		
		if(isAlreadyRegistered.isPresent()) {
			User newUser = new User();
			
			newUser.setUsername(user.getUsername());
			
			userRepository.save(newUser);
			
			return true;
			
			
		}
		
		throw new ResourceNotFoundException(user.getUsername());
		
		
	}
	
	
	public boolean updatePassword(Authentication req, AuthenticationRequest user) throws Exception{
		
		Optional<User> isAlreadyRegistered = userRepository.findByUsername(req.getName());
		
		if(isAlreadyRegistered.isPresent()) {
			User newUser = new User();
			
			newUser.setPassword(passwordEncoder.encode(user.getPassword()));
			
			userRepository.save(newUser);
			
			return true;
			
			
		}
		
		throw new ResourceNotFoundException(user.getUsername());
		
		
	}

}
