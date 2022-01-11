package com.tolosa.commons.app.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_name")
	private String userName;

	private String password;

	private Boolean enabled;

	@Column(name = "nombre", unique = true, length = 50)
	private String name;

	@Column(name = "apellido")
	private String lastName;

	@Column(unique = true, length = 200)
	private String email;
	
	@Column(name = "intentos")
	private Integer attempts;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuarios_has_roles", joinColumns = @JoinColumn(name = "usuarios_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
	private List<Role> roles;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
