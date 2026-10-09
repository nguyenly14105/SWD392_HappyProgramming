package com.happyprogramming.modules.user;

import com.happyprogramming.modules.user.dto.UserResponse;

public interface UserService {
    UserResponse getByUsername(String username);
}
