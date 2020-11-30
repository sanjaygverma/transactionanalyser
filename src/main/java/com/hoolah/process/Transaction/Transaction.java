package com.hoolah.process.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private String id;
    private LocalDateTime date;
    private BigDecimal amount;
    private Double amountDVersn;
    private String merchant;
    private String type;
    private String relatedTxId;

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", amountDVersn=" + amountDVersn +
                ", merchant='" + merchant + '\'' +
                ", type='" + type + '\'' +
                ", relatedTxId='" + relatedTxId + '\'' +
                '}';
    }

    public Transaction(
            String id,
            LocalDateTime dateTime,
            Double amount,
            String merchant,
            String type,
            String relatedTxId)
    {
        this.id = id;
        this.date = dateTime;
        this.amountDVersn = amount;
        this.merchant = merchant;
        this.type = type;
        this.relatedTxId = relatedTxId;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return BigDecimal.valueOf(getAmountDVersn());
    }

    public Double getAmountDVersn() {
        return amountDVersn;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getType() {
        return type;
    }

    public String getRelatedTxId() {
        return relatedTxId;
    }

}


