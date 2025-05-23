package com.wallacy.projetoestagio.repository;

import com.wallacy.projetoestagio.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
    List<Category> findByUserUserId(UUID userId);
    Optional<Category> findById(Long id);
}
