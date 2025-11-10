package com.example.mvc.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mvc.entity.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    // ✅ Custom query to get records between two dates
    List<Hospital> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
