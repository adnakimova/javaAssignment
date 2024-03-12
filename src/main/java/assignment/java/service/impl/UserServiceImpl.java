package assignment.java.service.impl;

import assignment.java.entity.User;
import assignment.java.repository.UserRepository;
import assignment.java.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
