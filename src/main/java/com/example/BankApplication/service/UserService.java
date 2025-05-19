package com.example.BankApplication.service;

import com.example.BankApplication.entity.BankCard;
import com.example.BankApplication.entity.User;
import com.example.BankApplication.repository.BankCardRepository;
import com.example.BankApplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BankCardRepository bankCardRepository;

    private final PasswordEncoder passwordEncoder;

    public User createUser(String fullName, String email, String password) {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(User.RoleName.USER);
        return userRepository.save(user);
    }

    public User createAdmin(String fullName, String email, String password) {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(User.RoleName.ADMIN);
        return userRepository.save(user);
    }

    public List<User> getListOfUsers() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User blockUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setActive(false);
            return userRepository.save(user);
        }
        return null;
    }

    public User unblockUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setActive(true);
            return userRepository.save(user);
        }
        return null;
    }

    public User refactorUser(Long id, String fullName, String email, String password) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            return userRepository.save(user);
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public  List<BankCard> listCardsHAveUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return bankCardRepository.findAllByUserId(user.getId());
        }
        return null;
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

}
