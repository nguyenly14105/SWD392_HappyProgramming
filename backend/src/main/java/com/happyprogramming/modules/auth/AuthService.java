package com.happyprogramming.modules.auth;

import com.happyprogramming.modules.auth.dto.AuthResponse;
import com.happyprogramming.modules.auth.dto.LoginRequest;
import com.happyprogramming.modules.auth.dto.RegisterRequest;
import com.happyprogramming.modules.user.dto.UserResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    UserResponse getCurrentUser(String username);
}
