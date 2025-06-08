package com.NSU.BookingSpecialist.service;

import com.NSU.BookingSpecialist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.NSU.BookingSpecialist.model.User;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    private AuthService authService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authService = new AuthService(userRepository, passwordEncoder);
    }

    @Test
    void registerNewUser() {
        String email = "test@example.com";
        String password = "password";
        String hashedPassword = "hashedPassword";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn(hashedPassword);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setId(12);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = authService.register(email, password);

        assertEquals(12, result.getId());
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());
    }

    @Test
    void registerExistingUser() {
        String email = "test@example.com";
        String password = "password";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mock(User.class)));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> authService.register(email, password));
        assertEquals(exception.getMessage(), "Email already exists");
    }
}
