package com.example.BankApplication.service;

import com.example.BankApplication.utils.CardNumberUtil;
import com.example.BankApplication.entity.BankCard;
import com.example.BankApplication.entity.User;
import com.example.BankApplication.repository.BankCardRepository;
import com.example.BankApplication.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class BankCardService {

    private final BankCardRepository bankCardRepository;

    private final UserRepository userRepository;

    public BankCard createBankCard(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            String cardNumber;
            while (true) {
                cardNumber = CardNumberUtil.generateCardNumber();
                String encryptedCardNumber = CardNumberUtil.encryptCardNumber(cardNumber);
                if (!bankCardRepository.existsByCardNumber(encryptedCardNumber)) {
                    BankCard bankCard = new BankCard(user, user.getFullName(), BankCard.CardStatus.ACTIVE, new BigDecimal(0));
                    bankCard.setCardNumber(encryptedCardNumber);
                    return bankCardRepository.save(bankCard);
                }
            }
        }
        return null;
    }

    public BankCard findById(Long id) {
        return bankCardRepository.findById(id).orElse(null);
    }

    public BankCard refactorCard(Long id, String fullName, LocalDate expirationDate, BankCard.CardStatus status, BigDecimal balance, String cardNumber) {
        BankCard bankCard = bankCardRepository.findById(id).orElse(null);
        if (bankCard != null) {
            bankCard.setFullName(fullName);
            bankCard.setExpiryDate(expirationDate);
            bankCard.setStatus(status);
            bankCard.setBalance(balance);
            bankCard.setCardNumber(cardNumber);
            return bankCardRepository.save(bankCard);
        }
        return null;
    }

    public BankCard offActivateCard(Long id) {
        BankCard bankCard = bankCardRepository.findById(id).orElse(null);
        if (bankCard != null) {
            bankCard.setStatus(BankCard.CardStatus.BLOCKED);
            return bankCardRepository.save(bankCard);
        }
        return null;
    }

    public BankCard activateCard(Long id) {
        BankCard bankCard = bankCardRepository.findById(id).orElse(null);
        if (bankCard != null) {
            bankCard.setStatus(BankCard.CardStatus.ACTIVE);
            return bankCardRepository.save(bankCard);
        }
        return null;
    }

    public BankCard deleteCard(Long id) {
        BankCard bankCard = bankCardRepository.findById(id).orElse(null);
        if (bankCard != null) {
            bankCardRepository.delete(bankCard);
            return bankCard;
        }
        return null;
    }

    public List<BankCard> findCardByUserId(Long userId) {
        return bankCardRepository.findByUserId(userId);
    }

    public List<BankCard> getListOfBankCards() {
        return bankCardRepository.findAll();
    }

    public List<BankCard> getMyCardNumber(Long userId) throws Exception {
        List<BankCard> bankCard = bankCardRepository.findByUserId(userId);
        if (bankCard != null) {
            for (BankCard card : bankCard) {
                String encryptedCardNumber = card.getCardNumber();
                String decryptedCardNumber = CardNumberUtil.decryptCardNumber(encryptedCardNumber);
                card.setCardNumber(decryptedCardNumber);
            }
            return bankCard;
        }
        return null;
    }


}
