package com.tolosa.oauth.api.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.tolosa.commons.app.models.entity.User;
import com.tolosa.oauth.api.services.IUserService;

/**
 * Clase para la informacon adicional del token
 * 
 * @author ho.tolosa
 *
 */
@Component
public class AdditionalInformationToken implements TokenEnhancer {

	private IUserService userService;

	public AdditionalInformationToken(@Autowired IUserService userService) {
		this.userService = userService;
	}

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> information = new HashMap<String, Object>();
		User user = userService.findByUserName(authentication.getName());
		if (Objects.nonNull(user)) {
			information.put("names", user.getName());
			information.put("lastName", user.getLastName());
			information.put("email", user.getEmail());
		}
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(information);
		return accessToken;
	}

}
