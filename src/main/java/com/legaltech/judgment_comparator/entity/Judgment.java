package com.legaltech.judgment_comparator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity class representing a Court Judgment
 */
@Entity
@Table(name = "judgments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Judgment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Case name is required")
    @Size(max = 500, message = "Case name cannot exceed 500 characters")
    @Column(name = "case_name", nullable = false, length = 500)
    private String caseName;

    @NotBlank(message = "Court name is required")
    @Column(nullable = false)
    private String court;

    /**
     * FIX: Use backticks to escape "year" (reserved keyword in H2)
     */
    @NotNull(message = "Year is required")
    @Min(value = 1950, message = "Year must be after 1950")
    @Max(value = 2050, message = "Year must be before 2050")
    @Column(name = "judgment_year", nullable = false) // ✅ Changed from "year" to "judgment_year"
    private Integer year;

    @NotBlank(message = "Judgment text is required")
    @Column(name = "judgment_text", nullable = false, columnDefinition = "TEXT")
    private String judgmentText;

    @Column(length = 1000)
    private String keywords;

    @Column(length = 500)
    private String verdict;

    @Column(name = "uploaded_at", updatable = false)
    private LocalDateTime uploadedAt;

    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
    }
}
