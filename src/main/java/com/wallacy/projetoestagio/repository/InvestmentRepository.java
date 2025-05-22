package com.wallacy.projetoestagio.repository;

import com.wallacy.projetoestagio.model.Expense;
import com.wallacy.projetoestagio.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findByUserUserId(UUID userId);
    List<Investment> findByObjectiveId(Long objectiveId);
}
