package com.wallacy.projetoestagio.repository;

import com.wallacy.projetoestagio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
