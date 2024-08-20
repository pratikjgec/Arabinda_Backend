package com.arobinda.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.arobinda.model.Myuser;
import com.arobinda.repo.AdminRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{

	
	@Autowired
	private AdminRepo repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Myuser> user=repository.findByUserName(username);
		
		if(user.isPresent()) {
			return User.builder()
					.username(user.get().getUserName())
					.password(user.get().getPassword())
					.roles(user.get().getRole())
					.build();
		}else {
			throw new UsernameNotFoundException(username);
		}
	}

}
