package test02.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import test02.exceptions.InsufficientFundsException;
import test02.exceptions.UserNotFoundException;
import test02.model.Transaction;
import test02.model.TransactionCriteria;
import test02.model.User;
import test02.repository.TransactionRepository;
import test02.repository.UserRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void transferMoney(Long fromUserId, Long toUserId, double amount) throws InsufficientFundsException {
        Long lowerId = Math.min(fromUserId, toUserId);
        Long higherId = Math.max(fromUserId, toUserId);

        User user1 = userRepository.findByIdForUpdate(lowerId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + lowerId));
        User user2 = userRepository.findByIdForUpdate(higherId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + higherId));

        User sender = user1.getId().equals(fromUserId) ? user1 : user2;
        User receiver = user1.getId().equals(toUserId) ? user1 : user2;

        if (sender.getBalance() >= amount) {
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);
        } else {
            throw new InsufficientFundsException("Insufficient funds");
        }
    }


        public List<Transaction> findTransactions(TransactionCriteria criteria, User user) {
        return transactionRepository.findByAmountBetweenAndDateBetweenAndUser(
                criteria.getMinAmount(),
                criteria.getMaxAmount(),
                criteria.getStart(),
                criteria.getFinish(),
                user);
    }
}
