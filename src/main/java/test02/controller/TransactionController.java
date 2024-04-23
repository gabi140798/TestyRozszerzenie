package test02.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test02.model.Transaction;
import test02.model.TransactionCriteria;
import test02.model.User;
import test02.service.TransactionService;
import test02.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/transactions")
@Slf4j
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestParam Long fromUserId, @RequestParam Long toUserId, @RequestParam double amount) {
        transactionService.transferMoney(fromUserId, toUserId, amount);
        return ResponseEntity.ok("Transfer completed successfully.");
    }

    @GetMapping("/search")
    public List<Transaction> searchTransactions(@ModelAttribute TransactionCriteria criteria) {
        User user = userService.findUserById(criteria.getUserId());
        return transactionService.findTransactions(criteria, user);
    }
}
