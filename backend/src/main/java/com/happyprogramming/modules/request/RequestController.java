package com.happyprogramming.modules.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * RequestController — scaffold
 * TODO: Implement mentoring request endpoints (create, approve/reject, list)
 */
@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class RequestController {
    // TODO: Inject RequestService and add endpoints
}
