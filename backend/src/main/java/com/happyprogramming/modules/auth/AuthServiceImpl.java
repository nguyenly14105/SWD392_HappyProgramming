package com.happyprogramming.modules.auth;

import com.happyprogramming.modules.auth.dto.AuthResponse;
import com.happyprogramming.modules.auth.dto.LoginRequest;
import com.happyprogramming.modules.auth.dto.RegisterRequest;
import com.happyprogramming.modules.user.User;
import com.happyprogramming.modules.user.UserRepository;
import com.happyprogramming.modules.user.UserServiceImpl;
import com.happyprogramming.modules.user.dto.UserResponse;
import com.happyprogramming.shared.exception.DuplicateResourceException;
import com.happyprogramming.shared.exception.ResourceNotFoundException;
import com.happyprogramming.shared.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserServiceImpl userServiceImpl;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String username = request.getUsername().trim();
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByUsername(username)) {
            throw new DuplicateResourceException("Tên đăng nhập '" + username + "' đã được sử dụng");
        }

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Email '" + email + "' đã được sử dụng");
        }

        String assignedRole = "ROLE_USER";
        if (request.getRole() != null) {
            String roleUpper = request.getRole().trim().toUpperCase();
            if (roleUpper.equals("ROLE_MENTOR") || roleUpper.equals("MENTOR")) {
                assignedRole = "ROLE_MENTOR";
            }
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName().trim())
                .phone(request.getPhone() != null ? request.getPhone().trim() : null)
                .role(assignedRole)
                .avatar(null)
                .build();

        User savedUser = userRepository.save(user);
        String token = jwtUtils.generateToken(savedUser.getUsername(), savedUser.getRole(), savedUser.getId());

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .user(userServiceImpl.mapToResponse(savedUser))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String usernameOrEmail = request.getUsername().trim();

        User user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail.toLowerCase()))
                .orElseThrow(() -> new BadCredentialsException("Tên đăng nhập hoặc mật khẩu không chính xác"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Tên đăng nhập hoặc mật khẩu không chính xác");
        }

        String token = jwtUtils.generateToken(user.getUsername(), user.getRole(), user.getId());

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .user(userServiceImpl.mapToResponse(user))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username))
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thông tin người dùng: " + username));
        return userServiceImpl.mapToResponse(user);
    }
}
