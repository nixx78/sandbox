package lv.nixx.poc.features.java20.casesamples.sealed;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class PaymentProcessorTest {
    private final PaymentProcessor paymentProcessor = new PaymentProcessor();

    @Test
    void test() {
        System.out.println(processPaymentResult(paymentProcessor.processPayment(null)));
        System.out.println(processPaymentResult(paymentProcessor.processPayment(BigDecimal.ZERO)));
        System.out.println(processPaymentResult(paymentProcessor.processPayment(BigDecimal.valueOf(10))));
        System.out.println(processPaymentResult(paymentProcessor.processPayment(BigDecimal.valueOf(101))));
    }

    private String processPaymentResult(PaymentResult result) {
        return switch (result) {
            case null -> "Response n/a";
            case PaymentResult.Success str -> String.format("Success, id [%s]", str.paymentId());
            case PaymentResult.Fail str ->  String.format("Fail, message [%s]", str.message());
            case PaymentResult.SuccessWithWarning str -> String.format("Success with warning, id [%s] warning [%s]", str.paymentId(), str.message());
        };
    }


}
