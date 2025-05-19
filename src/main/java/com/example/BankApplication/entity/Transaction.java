package com.example.BankApplication.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bank_card_id", nullable = false)
    private BankCard bankCard;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "date", nullable = false)
    private String date;

    @ManyToOne
    @JoinColumn(name = "recipient_bank_card_id", nullable = false)
    private BankCard recipientBankCard;

    @Getter
    public enum TransactionType {
        DEPOSIT("Пополнение"),
        WITHDRAWAL("Снятие"),
        TRANSFER("Перевод");

        private final String description;

        TransactionType(String description) {
            this.description = description;
        }
    }
}
