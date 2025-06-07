package com.NSU.BookingSpecialist.controller;

import com.NSU.BookingSpecialist.service.AuthService;
import com.NSU.BookingSpecialist.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    public static class SignupRequest {
        @NotBlank @Email
        public String email;

        @NotBlank
        public String password;
    }

    public static class SignupResponse {
        public Integer id;
        public String email;

        public SignupResponse(Integer id, String email) {
            this.id = id;
            this.email = email;
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        User user = authService.register(signupRequest.email, signupRequest.password);
        SignupResponse resp = new SignupResponse(user.getId(), user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
}
