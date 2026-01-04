package zipstore.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zipstore.backend.dto.LoginRequest;
import zipstore.backend.dto.RegisterRequest;
import zipstore.backend.entity.User;
import zipstore.backend.repository.UserRepository;
import zipstore.backend.security.JwtUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public String register (RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new RuntimeException("Email is already in use");
        }


        User user = User.builder()
                .name(registerRequest.name())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .role("USER")
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login (LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );

        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(() ->
                new RuntimeException("User not found!"));

        return jwtUtils.generateToken(user);
    }
}
