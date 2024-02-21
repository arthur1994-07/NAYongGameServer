package com.common.nayong.service;

import com.common.nayong.enumerate.PaymentIntent;
import com.common.nayong.enumerate.PaymentType;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    @Autowired private APIContext mContext;

    @Retryable(value = {LockAcquisitionException.class }, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public Payment createPayment(Double total, String currency, String description,
                                 String successUrl, String cancelUrl,
                                 String type, String intent) throws Exception {
        var amount = new Amount();
        amount.setCurrency(currency);
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));

        var transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        var payer = new Payer();
        payer.setPaymentMethod(type);

        var payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        var redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);

        return payment.create(mContext);
    }

    @Retryable(value = {LockAcquisitionException.class }, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public Payment executePayment(String paymentId, String payerId) throws Exception {
        var payment = new Payment();
        payment.setId(paymentId);
        var paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(mContext, paymentExecute);
    }


}
