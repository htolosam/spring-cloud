package com.tolosa.oauth.api.security;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Inyectamos de Security server passwordEncoder y authenticationManager. Sobre
 * 
 * @author ho.tolosa
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private Environment env;
	
	private BCryptPasswordEncoder passwordEncoder;

	private AuthenticationManager authenticationManager;
	
	private AdditionalInformationToken informationToken;

	public @Autowired AuthorizationServerConfig(BCryptPasswordEncoder passwordEncoder,
			@Autowired AuthenticationManager authenticationManager, 
			@Autowired AdditionalInformationToken informationToken) {
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.informationToken = informationToken;
	}

	/**
	 * Permisos que va a tener el token para generarlo y validarlo
	 * tokenKeyAccess es el endpoint para generafr el token
	 * ruta:: oauth/token mediante el verbo post
	 * checkTokenAccess ruta para validar, requiere autehntiacion
	 * los dos primeros endpoints estan protegidos por autenticación
	 * via http_basic con el client_id y el secret
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}

	/**
	 * Configurar los cleintes que vana  acceder a los recursos
	 * se deben registrar con el client_id y el secret_id
	 * Toca autenticarse con las credenciales de la aplicación
	 * se define el alcance con los scopes
	 * authorizedGrantTypes tipo de concesion que tiene la autenticacion
	 * en el caso de que los usuarios existan en nuestro sistema se usa password
	 * para los users de los recursos
	 * accessTokenValiditySeconds validar el token antes de que cambiev 1 hora
	 * para colocar otro cliente se debe utilizar el metodo .and() y acto seguido
	 * withClient de nuevo
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(env.getProperty("config.security.oauth.client.id"))
		.secret(this.passwordEncoder.encode(env.getProperty("config.security.oauth.client.secret")))
		.scopes("read", "write")
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(3600);
		super.configure(clients);
	}

	/**
	 * Configuramos el authenticationManager Token sstorage (debe ser del tipo jw7
	 * Converter Componente que se encarga de guardar los datos del user en el
	 * token. todo codificado en base 64 creamos el metodo tokenStore
	 * 
	 * Para unir los datos adicionales en el token usamos TokenEnhancerChain
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(informationToken, accessTokenConverter()));
		// registrar authenticationManager
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
				.accessTokenConverter(accessTokenConverter())
				.tokenEnhancer(tokenEnhancerChain);
	}

	/**
	 * Crear el token
	 * 
	 * @return
	 */
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(this.accessTokenConverter());
	}

	/**
	 * Convertir el token y firmarlo
	 * 
	 * @return
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		// se debe agregar un codigo secreto para la firma del token
		// ese mismo token se debe validar en el servidor de recursos para verificar
		// que sea el correcto para dar acceso a los clientes a los recursos protegidos
		tokenConverter.setSigningKey(Base64.getEncoder().encodeToString(env.getProperty("config.security.oauth.jwt.key").getBytes()));
		return tokenConverter;
	}

}
