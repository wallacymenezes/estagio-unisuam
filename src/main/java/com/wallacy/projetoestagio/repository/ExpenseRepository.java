package com.wallacy.projetoestagio.repository;

import com.wallacy.projetoestagio.model.Category;
import com.wallacy.projetoestagio.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserUserId(UUID userId);
    List<Expense> findByCategory(Category category);
}
