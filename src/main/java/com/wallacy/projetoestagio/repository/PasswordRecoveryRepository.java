package com.wallacy.projetoestagio.repository;

import com.wallacy.projetoestagio.model.PasswordRecovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecovery, Long> {

    Optional<PasswordRecovery> findByOtp(String otp);
    void deleteByEmail(String email);

    @Query("SELECT p FROM PasswordRecovery p WHERE p.otp = :otp AND p.expiration > :now")
    Optional<PasswordRecovery> findValidOtp(@Param("otp") String otp, @Param("now") Instant now);
}
