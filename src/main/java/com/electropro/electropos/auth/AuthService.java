package com.electropro.electropos.auth;

import com.electropro.electropos.dto.AuthRequestDto;
import com.electropro.electropos.dto.AuthResponseDto;
import com.electropro.electropos.dto.RegisterRequestDto;
import com.electropro.electropos.entity.Role;
import com.electropro.electropos.entity.User;
import com.electropro.electropos.repository.UserRepository;
import com.electropro.electropos.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDto register(RegisterRequestDto request) {
        var user = new User();
        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.valueOf(request.role()));
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return new AuthResponseDto(
                token,
                user.getRole().name(),
                user.getEmail(),
                user.getFirstname()
        );
    }

    public AuthResponseDto login(AuthRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = userRepository.findByEmail(request.email()).orElseThrow();
        var token = jwtService.generateToken(user);
        return new AuthResponseDto(
                token,
                user.getRole().name(),
                user.getEmail(),
                user.getFirstname()
        );
    }
}