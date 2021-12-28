package com.tolosa.commons.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre", unique = true, length = 50)
	private String name;
	
//	//la var mappedBy como se llama en la clase usuarios, el mappedBy es
//	//requerido si se hace bidireccional la relación
//	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
//	private List<User> users;

	/**
	 * 
	 */
	private static final long serialVersionUID = 109760831608292255L;

}
