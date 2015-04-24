package at.ac.tuwien.big.we15.lab2.api.impl;

import at.ac.tuwien.big.we15.lab2.api.Avatar;
import at.ac.tuwien.big.we15.lab2.api.User;

public class UserImpl implements User{
	
	String username;
	Avatar avatar;
	
	public UserImpl(String username, Avatar avatar){
		this.username = username;
		this.avatar = avatar;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	@Override
	public Avatar getAvatar() {
		return avatar;
	}

}
