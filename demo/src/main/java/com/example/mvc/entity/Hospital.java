package com.example.mvc.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientName;
    private int age;
    private String gender;
    private Double weight;
    private String symptoms;
    private String previousDisease;   // ✅ NEW FIELD
    private String createdBy;
    private LocalDateTime createdAt;

    @PrePersist
    public void initializeDefaults() {
        if (this.createdBy == null || this.createdBy.isEmpty()) {
            this.createdBy = "Dr. Ankita Chaudhari";
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
