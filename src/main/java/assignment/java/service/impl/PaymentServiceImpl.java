package assignment.java.service.impl;

import assignment.java.entity.User;
import assignment.java.repository.UserRepository;
import assignment.java.service.PaymentService;
import assignment.java.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final UserRepository userRepository;
    private final UserService userService;
    @Override
    public boolean makePayment(String username) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            System.out.println("User not found");
            return false;
        }

        BigDecimal newBalance = user.getBalance().subtract(new BigDecimal(1.1));
//        if (newBalance < 0) {
//            return false;
//        }
        user.setBalance(newBalance);
        userService.updateUser(user);

        return true;
    }
}
