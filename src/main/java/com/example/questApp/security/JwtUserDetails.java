package com.example.questApp.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.questApp.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtUserDetails implements UserDetails{
	
	public Long id;
	private String userName;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	private JwtUserDetails(Long id, String userName, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static JwtUserDetails create(User user) {
		List<GrantedAuthority> authoritiesList = new ArrayList<GrantedAuthority>();
		authoritiesList.add(new SimpleGrantedAuthority("user"));
		return new JwtUserDetails(user.getId(), user.getUserName(), user.getPassword(), authoritiesList);
	}
	
	@Override
	public boolean isAccountNonExpired() {		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return userName;
	}

}
