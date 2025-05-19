package com.example.BankApplication.controller;

import com.example.BankApplication.entity.BankCard;
import com.example.BankApplication.service.BankCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card")
public class BankCardController {

    private final BankCardService bankCardService;

    @PostMapping("/createBankCard")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createBankCard(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(bankCardService.createBankCard(userId));
        } catch (Exception e) {
            throw new RuntimeException("Произошла непредвиденная ошибка", e);
        }
    }

    @GetMapping("/findById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bankCardService.findById(id));
    }

    @PutMapping("/refactorCard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> refactorCard(@RequestParam Long id, @RequestParam String fullName, @RequestParam LocalDate expirationDate, @RequestParam BankCard.CardStatus status, @RequestParam BigDecimal balance, @RequestParam String cardNumber) {
        return ResponseEntity.ok(bankCardService.refactorCard(id, fullName, expirationDate, status, balance, cardNumber));
    }

    @PutMapping("/offActivateCard/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> offActivateCard(@PathVariable Long id) {
        return ResponseEntity.ok(bankCardService.offActivateCard(id));
    }

    @PutMapping("/activateCard/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> activateCard(@PathVariable Long id) {
        return ResponseEntity.ok(bankCardService.activateCard(id));
    }

    @GetMapping("/getListOfBankCards")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getListOfBankCards() {
        return ResponseEntity.ok(bankCardService.getListOfBankCards());
    }

    @DeleteMapping("/deleteCard/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCard(@PathVariable Long id) {
        return ResponseEntity.ok(bankCardService.deleteCard(id));
    }

    @GetMapping("/findByUserId/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(bankCardService.findCardByUserId(userId));
    }

    @GetMapping("/getMyCardNumber/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BankCard>> getMyCardNumber(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(bankCardService.getMyCardNumber(userId));
        } catch (Exception e) {
            throw new RuntimeException("Произошла непредвиденная ошибка", e);
        }
    }

}
