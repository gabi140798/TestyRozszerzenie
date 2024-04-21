package test02.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test02.exceptions.InsufficientFundsException;
import test02.exceptions.UserNotFoundException;
import test02.model.Transaction;
import test02.model.User;
import test02.repository.TransactionRepository;
import test02.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void transferMoney(Long fromUserId, Long toUserId, double amount) throws InsufficientFundsException {
        User sender = userRepository.findById(fromUserId)
                .orElseThrow(() -> new UserNotFoundException("Sender not found with id: " + fromUserId));

        User recipient = userRepository.findById(toUserId)
                .orElseThrow(() -> new UserNotFoundException("Recipient not found with id: " + toUserId));

        if (sender.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds for this transaction.");
        }

        sender.setBalance(sender.getBalance() - amount);
        userRepository.save(sender);

        recipient.setBalance(recipient.getBalance() + amount);
        userRepository.save(recipient);
    }

    public List<Transaction> findTransactions(Double minAmount, Double maxAmount, LocalDate startDate, LocalDate endDate, User user) {
        return transactionRepository.findByAmountBetweenAndDateBetweenAndUser(minAmount, maxAmount, startDate, endDate, user);
    }
}
