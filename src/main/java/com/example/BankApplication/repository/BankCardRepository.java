package com.example.BankApplication.repository;

import com.example.BankApplication.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankCardRepository extends JpaRepository<BankCard, Long> {

    List<BankCard> findAllByUserId(Long userId);

    List<BankCard> findByUserId(Long userId);

    boolean existsByCardNumber(String cardNumber);
}
