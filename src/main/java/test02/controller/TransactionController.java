package test02.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test02.model.Transaction;
import test02.model.User;
import test02.service.TransactionService;
import test02.service.UserService;

import java.time.LocalDate;
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
        try {
            transactionService.transferMoney(fromUserId, toUserId, amount);
            return ResponseEntity.ok("Transfer completed successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public List<Transaction> searchTransactions(
            @RequestParam Double minAmount,
            @RequestParam Double maxAmount,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam Long userId) {
        User user = userService.findUserById(userId);
        return transactionService.findTransactions(minAmount, maxAmount, startDate, endDate, user);
    }
}
