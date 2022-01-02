package com.tolosa.oauth.api.services;

import com.tolosa.commons.app.models.entity.User;

public interface IUserService {
	
	public User findByUserName(String userName);

}
