package com.happyprogramming.modules.auth.dto;

import com.happyprogramming.modules.user.dto.UserResponse;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponse {
    private String token;
    @Builder.Default
    private String tokenType = "Bearer";
    private UserResponse user;
}
