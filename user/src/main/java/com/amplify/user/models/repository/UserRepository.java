package com.amplify.user.models.repository;


import com.amplify.user.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

 
}
