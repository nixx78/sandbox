package lv.nixx.poc.features.java20.casesamples.sealed;


sealed interface PaymentResult
        permits PaymentResult.Success, PaymentResult.Fail, PaymentResult.SuccessWithWarning {

    record Success(Long paymentId) implements PaymentResult {
    }

    record Fail(String message) implements PaymentResult {
    }

    record SuccessWithWarning(Long paymentId, String message) implements PaymentResult {
    }


}
