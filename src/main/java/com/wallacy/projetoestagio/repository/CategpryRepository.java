package com.wallacy.projetoestagio.repository;

import com.wallacy.projetoestagio.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategpryRepository extends JpaRepository<Category, Long> {

}
