package test02.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test02.model.Transaction;
import test02.model.User;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAmountBetweenAndDateBetweenAndUser(Double minAmount, Double maxAmount, LocalDate startDate, LocalDate endDate, User user);
}
