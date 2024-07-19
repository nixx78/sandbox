package lv.nixx.poc.features.java21.pattern_matching;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PatternMatchingSample {


    @Test
    void getBalanceWithoutSwitchPattern() {

        assertAll(
                () -> assertEquals(2.00, getBalanceWithoutSwitchPattern(new SavingsAccount())),
                () -> assertEquals(3.00, getBalanceWithoutSwitchPattern(new TermAccount())),
                () -> assertEquals(4.00, getBalanceWithoutSwitchPattern(new CurrentAccount()))
        );
    }

    @Test
    void getBalanceWithSwitchPattern() {
        assertAll(
                () -> assertEquals(2.00, getBalanceWithSwitchPattern(new SavingsAccount())),
                () -> assertEquals(3.00, getBalanceWithSwitchPattern(new TermAccount())),
                () -> assertEquals(4.00, getBalanceWithSwitchPattern(new CurrentAccount()))
        );
    }

    static double getBalanceWithoutSwitchPattern(Account account) {
        double balance = 0;
        if (account instanceof SavingsAccount sa) {
            balance = sa.getSavings();
        } else if (account instanceof TermAccount ta) {
            balance = ta.getTermAccount();
        } else if (account instanceof CurrentAccount ca) {
            balance = ca.getCurrentAccount();
        }
        return balance;
    }

    static double getBalanceWithSwitchPattern(Account account) {
        double result = 0;
        switch (account) {
            case null -> throw new RuntimeException("Account can't be null");
            case SavingsAccount sa -> result = sa.getSavings();
            case TermAccount ta -> result = ta.getTermAccount();
            case CurrentAccount ca -> result = ca.getCurrentAccount();
            default -> result = account.getBalance();
        }
        ;
        return result;
    }

    static class Account {
        double getBalance() {
            return 1;
        }
    }

    static class SavingsAccount extends Account {
        double getSavings() {
            return 2;
        }
    }

    static class TermAccount extends Account {
        double getTermAccount() {
            return 3;
        }
    }

    static class CurrentAccount extends Account {
        double getCurrentAccount() {
            return 4;
        }
    }

}
