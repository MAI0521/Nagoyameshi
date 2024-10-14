package com.example.nagoyameshi.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.StripeService;
import com.example.nagoyameshi.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

@Controller
public class StripeWebhookController {

    private static final Logger logger = LoggerFactory.getLogger(StripeWebhookController.class);

    private static final String CHECKOUT_SESSION_COMPLETED = "checkout.session.completed";
    private static final String INVOICE_PAID = "invoice.paid";
    private static final String INVOICE_PAYMENT_FAILED = "invoice.payment_failed";

    private final StripeService stripeService;

    @Value("${stripe.api-key}")
    private String stripeApiKey;

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    public StripeWebhookController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @Autowired
    private UserService userService;

    @PostMapping("/stripe/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, 
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Stripe.apiKey = stripeApiKey;

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            logger.error("Error verifying Stripe signature", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Stripe signature");
        }

        switch (event.getType()) {
            case CHECKOUT_SESSION_COMPLETED:
            	EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
                Optional<StripeObject> sessionOptional = dataObjectDeserializer.getObject();

                sessionOptional.ifPresentOrElse(sessionObject -> {
                    if (sessionObject instanceof Session) {
                        Session session = (Session) sessionObject;

                        // Ensure that customer details are present
                        if (session.getCustomerDetails() != null) {
                            String customerEmail = session.getCustomerDetails().getEmail();
                            logger.info("Preparing to update payment status for email: {}", customerEmail);
                            
                            if (customerEmail != null) {
                                userService.updateUserPaidStatus(customerEmail);
                            } else {
                                logger.error("Customer email is null.");
                            }
                        } else {
                            logger.error("Customer details are null.");
                        }
                    } else {
                        logger.error("Session is null or not a valid Session object.");
                    }
                }, () -> {
                    logger.error("Failed to deserialize session object from event.");
                });
                break;
            case INVOICE_PAID:
                // Handle invoice paid
                break;
            case INVOICE_PAYMENT_FAILED:
                // Handle payment failure
                break;
            default:
                logger.info("Unhandled event type: {}", event.getType());
        }

        return ResponseEntity.ok("Success");
    }
}
