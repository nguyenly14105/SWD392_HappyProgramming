package com.happyprogramming.modules.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * AdminController — scaffold
 * TODO: Implement admin management endpoints (user management, mentor approval, statistics)
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
    // TODO: Inject AdminService and add endpoints
}
