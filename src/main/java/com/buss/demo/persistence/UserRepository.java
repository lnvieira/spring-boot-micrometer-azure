package com.buss.demo.persistence;

import org.springframework.data.repository.CrudRepository;

import com.buss.demo.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

}

