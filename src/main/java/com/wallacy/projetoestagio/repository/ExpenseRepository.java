package com.wallacy.projetoestagio.repository;

import com.wallacy.projetoestagio.model.Category;
import com.wallacy.projetoestagio.model.Expense;
import com.wallacy.projetoestagio.enums.ExpenseStatus; // Importar o ExpenseStatus
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserUserId(UUID userId);
    List<Expense> findByCategory(Category category);

    // NOVO MÉTODO: Buscar despesas por ID de usuário e status
    List<Expense> findByUserUserIdAndStatus(UUID userId, ExpenseStatus status);
}