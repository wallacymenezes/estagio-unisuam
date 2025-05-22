package com.wallacy.projetoestagio.repository;

import com.wallacy.projetoestagio.model.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective, Long> {

    List<Objective> findByUserUserId(UUID userId);
}
