package lv.nixx.poc.features.java20.casesamples.sealed;

import java.math.BigDecimal;

public class PaymentProcessor {

    PaymentResult processPayment(BigDecimal amount) {

        if (amount == null) {
            return null;
        }

        if (amount.equals(BigDecimal.ZERO)) {
            return new PaymentResult.Fail("Amount can't be null");
        }

        if (BigDecimal.valueOf(100).compareTo(amount) > 0) {
            return new PaymentResult.SuccessWithWarning(100L, "WarningMessage");
        }

        if (BigDecimal.ZERO.compareTo(amount) <= 0) {
            return new PaymentResult.Success(123L);
        }

        return null;
    }


}
