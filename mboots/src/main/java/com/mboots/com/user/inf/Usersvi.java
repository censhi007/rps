package com.mboots.com.user.inf;

import com.mboots.com.user.model.User;

public interface Usersvi {

	public User findUser(String name,String pwd);
	public User updateUser(User u);
}
