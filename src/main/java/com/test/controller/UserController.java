package com.test.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.dao.UserRepository;
import com.test.dto.UserDto;
import com.test.entity.UserEntity;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/users/save")
	private ResponseEntity<String> saveUser(@RequestBody UserDto userDto) {
		try {
			UserEntity userEntity = new UserEntity();
			userEntity.setUserId(0);
			userEntity.setEmail(userDto.getEmail());
			userEntity.setPassword(userDto.getPassword());
			userEntity.setFirstName(userDto.getFirstName());
			userEntity.setLastName(userDto.getLastName());
			userEntity.setAge(userDto.getAge());
			userRepository.save(userEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Create User Success", HttpStatus.CREATED);
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserEntity>> findAll() {
		return new ResponseEntity<List<UserEntity>>(userRepository.findAll(), HttpStatus.OK);
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<String> deleteById(@PathVariable("userId") Integer userId) {
		try {
			userRepository.deleteById(userId);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Delete User Success", HttpStatus.OK);
	}

	@PostMapping("/users/{userId}")
	private ResponseEntity<String> editUser(@PathVariable("userId") Integer userId, @RequestBody UserDto userDto) {
		Optional<UserEntity> user = userRepository.findById(userId);
		if (user.isPresent()) {
			UserEntity entity = user.get();
			
			if (StringUtils.isNotBlank(userDto.getFirstName())) {
				entity.setFirstName(userDto.getFirstName());
			}
			
			if (StringUtils.isNotBlank(userDto.getLastName())) {
				entity.setLastName(userDto.getLastName());
			}
			
			if (StringUtils.isNotBlank(userDto.getAge())) {
				entity.setAge(userDto.getAge());
			}			
			
			userRepository.save(entity);
			return new ResponseEntity<String>("Update User Success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

}
