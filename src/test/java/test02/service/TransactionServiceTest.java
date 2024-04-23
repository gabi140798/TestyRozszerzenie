package test02.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test02.exceptions.InsufficientFundsException;
import test02.model.Transaction;
import test02.model.TransactionCriteria;
import test02.model.TransactionType;
import test02.model.User;
import test02.repository.TransactionRepository;
import test02.repository.UserRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void testTransferMoneySuccess() {
        User sender = new User("Kamil", "Nowak", 1000);
        User recipient = new User("Magdalena", "Kowal", 1500);
        Long senderId = 1L;
        Long recipientId = 2L;

        when(userRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(userRepository.findById(recipientId)).thenReturn(Optional.of(recipient));

        transactionService.transferMoney(senderId, recipientId, 500);

        assertEquals(500, sender.getBalance(), 0.01);
        assertEquals(2000, recipient.getBalance(), 0.01);
        verify(userRepository).save(sender);
        verify(userRepository).save(recipient);
    }

    @Test
    public void testTransferMoneyInsufficientFunds() {
        User sender = new User("Kamil", "Nowak", 300);
        User recipient = new User("Magdalena", "Kowal", 1500);
        Long senderId = 1L;
        Long recipientId = 2L;

        when(userRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(userRepository.findById(recipientId)).thenReturn(Optional.of(recipient));

        assertThrows(InsufficientFundsException.class, () -> {
            transactionService.transferMoney(senderId, recipientId, 500);
        });
    }

    @Test
    public void testFindTransactions() {
        User user = new User("Kamil", "Nowak", 1000);
        Transaction transaction = new Transaction(300, LocalDate.now(), TransactionType.TRANSFER, user);
        List<Transaction> expectedTransactions = Collections.singletonList(transaction);

        TransactionCriteria criteria = new TransactionCriteria(200.0, 400.0, LocalDate.now(), LocalDate.now(), user.getId());

        when(transactionRepository.findByAmountBetweenAndDateBetweenAndUser(criteria.getMinAmount(), criteria.getMaxAmount(), criteria.getStart(), criteria.getFinish(), user))
                .thenReturn(expectedTransactions);

        List<Transaction> result = transactionService.findTransactions(criteria, user);

        assertFalse(result.isEmpty(), "The result should not be empty");
        assertEquals(1, result.size(),"Expected 1 transaction in the result");
        assertEquals(300.0, result.get(0).getAmount(), 0.001,"The transaction amount should match");
    }
}