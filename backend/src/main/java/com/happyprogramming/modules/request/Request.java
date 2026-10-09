package com.happyprogramming.modules.request;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Request entity — scaffold
 * Represents a mentoring request from a Mentee to a Mentor
 */
@Entity
@Table(name = "requests")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: Add @ManyToOne relation to User (mentee) once user module is stable
    @Column(nullable = false)
    private Long menteeId;

    // TODO: Add @ManyToOne relation to User (mentor)
    @Column(nullable = false)
    private Long mentorId;

    @Column(length = 1000)
    private String message;

    @Column(nullable = false, length = 30)
    @Builder.Default
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
