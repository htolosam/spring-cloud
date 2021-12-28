package com.tolosa.users.api.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.tolosa.commons.app.models.entity.User;

/**
 * Interface Repository o DAO de usuario
 * @author ho.tolosa
 *
 */
@RepositoryRestResource(path="users")
public interface IUserDAO extends PagingAndSortingRepository<User, Integer> {

	/**
	 * Metodo para filtrar por medio del username del usuario,
	 * en este caso se esta usando JPA QueryMethod
	 * @param userName
	 * @return
	 */
	@RestResource(path="search-user")
	public User findByUserName(@Param("userName") String userName);
	
	
	@Query("select u from User u where u .userName = ?1")
	public User getUser(String userName);
	
}
