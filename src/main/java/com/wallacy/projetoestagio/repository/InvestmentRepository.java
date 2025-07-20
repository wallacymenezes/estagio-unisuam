package com.wallacy.projetoestagio.repository;

import com.wallacy.projetoestagio.model.Expense;
import com.wallacy.projetoestagio.model.Investment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findByUserUserId(UUID userId);
    List<Investment> findByObjectiveId(Long objectiveId);

    @Modifying
    @Transactional
    @Query("UPDATE Investment i SET i.objective = null WHERE i.objective.id = :objectiveId")
    void disassociateInvestmentsFromObjective(@Param("objectiveId") Long objectiveId);
}
