package com.happyprogramming.modules.request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * RequestRepository — scaffold
 */
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByMenteeId(Long menteeId);
    List<Request> findByMentorId(Long mentorId);
    List<Request> findByStatus(String status);
}
