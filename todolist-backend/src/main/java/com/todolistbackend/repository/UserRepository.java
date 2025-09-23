package com.todolistbackend.repository;

import com.todolistbackend.entity.MyUsers;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<MyUsers, Integer> {

    boolean existsByEmail(@NotBlank(message = "Email Required! ") String email);

    MyUsers findByEmail(@NotBlank(message = "Username Required") String username);

    List<MyUsers> findAllByRole(String role);
}
