package com.wallacy.projetoestagio.repository;

import com.wallacy.projetoestagio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(UUID userId);

    Optional<User> findByName(String name);
}
