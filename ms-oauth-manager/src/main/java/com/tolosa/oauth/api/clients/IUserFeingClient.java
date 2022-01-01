package com.tolosa.oauth.api.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tolosa.commons.app.models.entity.User;

@FeignClient(name = "users-manager")
public interface IUserFeingClient {

	@GetMapping("/users/search/search-user")
	public User findByUserName(@RequestParam String userName);
	
}
