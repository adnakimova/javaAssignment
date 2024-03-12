package assignment.java.service;

import org.springframework.http.ResponseEntity;

public interface PaymentService {
    boolean makePayment(String username);
}
