package at.ac.tuwien.big.we15.lab2.api.impl;

import at.ac.tuwien.big.we15.lab2.api.Avatar;
import at.ac.tuwien.big.we15.lab2.api.Question;
import at.ac.tuwien.big.we15.lab2.api.User;

public class Opponent implements User{
	
	private String username;
	private Avatar avatar;
	
	public Opponent(){
		this.username = "Computer";
		this.avatar = Avatar.getRandomAvatar();
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
		this.avatar  = avatar;
	}

	@Override
	public Avatar getAvatar() {
		return avatar;
	}
	
	public boolean calculateQuestion(Question q){
		float result = 1f/q.getValue();
		double random = Math.random();
		return result <= random;
	}

	
	
}
