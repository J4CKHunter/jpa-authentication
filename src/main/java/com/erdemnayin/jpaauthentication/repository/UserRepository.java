package com.erdemnayin.jpaauthentication.repository;

import com.erdemnayin.jpaauthentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
