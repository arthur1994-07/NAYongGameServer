package com.common.nayong.controller;

import com.common.core.base.log.Log;
import com.common.core.web.struct.JsonRespond;
import com.common.nayong.enumerate.PaymentIntent;
import com.common.nayong.enumerate.PaymentType;
import com.common.nayong.service.PaymentService;
import com.paypal.api.payments.Links;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/payment")
public class PaymentController {

    public static class IDRequest {
        public long id;
    }

    public static class PaymentRequest {
        public double amount;
        public String currency;
        public String description;
        public String cancelUrl;
        public String successUrl;
        public PaymentType paymentType;
        public PaymentIntent paymentIntent;
    }

    public static class SuccessPayRequest {
        public String paymentId;
        public String payerId;
    }

    @Value("${custom.paypal.pay_success}")
    private String mSuccessUrl;

    @Value("${custom.paypal.pay_cancel}")
    private String mCancelUrl;

    @Autowired private PaymentService mService;

    @PostMapping(value = "/public/pay")
    public ResponseEntity<JsonRespond<String>>pay(@RequestBody PaymentRequest request) throws Exception {
        try {
            var payment = mService.createPayment(request.amount, request.currency, request.description, request.successUrl,
                    request.cancelUrl, request.paymentType, request.paymentIntent);
            var link = payment.getLinks().stream().filter(s -> s.getRel().equals("approval_url")).findAny().orElse(null);
            if (link == null) throw new Exception("invalid payment");
            var result = "redirect:" + link.getHref();
            return ResponseEntity.ok(new JsonRespond<>(result));
        } catch(Exception ex) {
            Log.e(ex.getMessage());
        }
        return ResponseEntity.ok(new JsonRespond<>(null));
    }

    @PostMapping(value = "/public/pay/success")
    public ResponseEntity<JsonRespond<String>> successPay(@RequestBody SuccessPayRequest request) throws Exception {
        try {
            var payment = mService.executePayment(request.paymentId, request.payerId);
            if (payment.getState().equals("approved")) return ResponseEntity.ok(new JsonRespond<>("success"));
        } catch (Exception ex) {
            Log.e(ex.getMessage());
        }
        return ResponseEntity.ok(new JsonRespond<>("redirect:/"));
    }

    @PostMapping(value = "/public/pay/cancel")
    public ResponseEntity<JsonRespond<String>> cancelPay() {
        return ResponseEntity.ok(new JsonRespond<>("cancel"));
    }
}
