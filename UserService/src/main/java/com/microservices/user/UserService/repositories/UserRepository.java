package com.microservices.user.UserService.repositories;

import com.microservices.user.UserService.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
