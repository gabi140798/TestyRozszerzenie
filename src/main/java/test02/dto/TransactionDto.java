package test02.dto;

import test02.model.Transaction;
import test02.model.TransactionType;

import java.time.LocalDate;

public record TransactionDto(Long id, double amount, TransactionType transactionType, LocalDate date, Long userId) {

    public static TransactionDto from(Transaction transaction) {
        return new TransactionDto(transaction.getId(), transaction.getAmount(), transaction.getType(), transaction.getTransactionDate(), transaction.getUser().getId());
    }
}
