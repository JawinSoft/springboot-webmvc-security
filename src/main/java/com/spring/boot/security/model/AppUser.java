package com.spring.boot.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jpa_users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
	
	@Id
	private Integer id;	
	
	@Column(name = "username")
	private String userName;
	
	private String password;
	
	private Boolean active;
	
	private String roles;

}
