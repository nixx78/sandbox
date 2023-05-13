package lv.nixx.poc.datastruct.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction implements Comparable<Transaction> {

    private final String id;
    private final BigDecimal amount;
    private final String account;
    private final String currency;
    private final LocalDate lastUpdateDate;

    public Transaction(String id, Double amount, String account, String currency, String lastUpdateDate) {
        this.id = id;
        this.amount = BigDecimal.valueOf(amount);
        this.account = account;
        this.currency = currency;

        if (lastUpdateDate == null) {
            this.lastUpdateDate = null;
        } else {
            this.lastUpdateDate = LocalDate.parse(lastUpdateDate);
        }
    }

    public Transaction(String id, Double amount, String account, String currency) {
        this(id, amount, account, currency, null);
    }


    public String getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", amount=" + amount + ", account=" + account + ", currency=" + currency
                + ", lastUpdateDate=" + lastUpdateDate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((account == null) ? 0 : account.hashCode());
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((currency == null) ? 0 : currency.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transaction other = (Transaction) obj;
        if (account == null) {
            if (other.account != null)
                return false;
        } else if (!account.equals(other.account))
            return false;
        if (amount == null) {
            if (other.amount != null)
                return false;
        } else if (!amount.equals(other.amount))
            return false;
        if (currency == null) {
            if (other.currency != null)
                return false;
        } else if (!currency.equals(other.currency))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (lastUpdateDate == null) {
            return other.lastUpdateDate == null;
        } else return lastUpdateDate.equals(other.lastUpdateDate);
    }

    @Override
    public int compareTo(Transaction t) {
        return this.getLastUpdateDate().compareTo(t.getLastUpdateDate());
    }

}