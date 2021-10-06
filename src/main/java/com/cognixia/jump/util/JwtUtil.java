package com.cognixia.jump.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	private final String SECRET_KEY = "jumptodoproject";
	
	//get the User name of the token
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	//get expiration from the token
	public Date extractExpiration(String token) {
			return extractClaim(token, Claims::getExpiration);
	}
	
	//method takes a token, and a claims resolver to find out what the claims are of that token
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
		
	}
	
	// actually parsing and extracting all data that could be needed from the token
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	//check token for expiration
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	// return created token after successful authentication
	public String generateToken(UserDetails userDetails) {
		
		// claims info/data you want to include in the payload of a toekn, besides user info
		Map<String, Object> claims = new HashMap<>();
		
		return createToken(claims, userDetails.getUsername());
	}
	
	//create the token
	private String createToken(Map<String, Object> claims, String subject) {
		
		
		//sets token based on Subject when is our Username
		// and expiration time (10 hours) from when token is created
		//signs and compress all the JSON information of the token
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 10)))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		
		final String username = extractUsername(token);
		
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
