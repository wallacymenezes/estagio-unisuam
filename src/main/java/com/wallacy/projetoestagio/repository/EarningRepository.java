package com.wallacy.projetoestagio.repository;

import com.wallacy.projetoestagio.model.Earning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarningRepository extends JpaRepository<Earning, Long> {

}
