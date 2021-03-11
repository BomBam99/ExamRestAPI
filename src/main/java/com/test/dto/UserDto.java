package com.test.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer userId;
	
	private String email;
	
	private String password;
	
	@JsonProperty("first_name")
	private String firstName;
	
	@JsonProperty("last_name")
	private String lastName;
	
	private String age;
}
