package com.wallacy.projetoestagio.repository;

import com.wallacy.projetoestagio.model.Earning;
import com.wallacy.projetoestagio.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EarningRepository extends JpaRepository<Earning, Long> {

    List<Earning> findByUserUserId(UUID userId);
}
