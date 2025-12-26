package com.legaltech.judgment_comparator.service;

import com.legaltech.judgment_comparator.entity.Judgment;
import com.legaltech.judgment_comparator.repository.JudgmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service layer for Judgment-related business logic
 * 
 * @Service - Marks as Spring service component
 * @Slf4j - Lombok: Auto-generates logger (log.info(), log.error())
 * @RequiredArgsConstructor - Lombok: Generates constructor for final fields
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class JudgmentService {

    /**
     * Constructor injection (recommended over @Autowired)
     * 'final' ensures immutability
     */
    private final JudgmentRepository judgmentRepository;

    /**
     * Save a new judgment
     * 
     * @Transactional - Ensures database transaction safety
     */
    @Transactional
    public Judgment saveJudgment(Judgment judgment) {
        log.info("Saving judgment: {}", judgment.getCaseName());
        return judgmentRepository.save(judgment);
    }

    /**
     * Get all judgments
     */
    public List<Judgment> getAllJudgments() {
        log.info("Fetching all judgments");
        return judgmentRepository.findAll();
    }

    /**
     * Get judgment by ID
     * Throws exception if not found
     */
    public Judgment getJudgmentById(Long id) {
        log.info("Fetching judgment with ID: {}", id);
        return judgmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Judgment not found with ID: " + id));
    }

    /**
     * Search judgments by keyword
     */
    public List<Judgment> searchJudgments(String searchTerm) {
        log.info("Searching judgments with term: {}", searchTerm);
        return judgmentRepository.searchJudgments(searchTerm);
    }

    /**
     * Delete judgment by ID
     */
    @Transactional
    public void deleteJudgment(Long id) {
        log.info("Deleting judgment with ID: {}", id);
        if (!judgmentRepository.existsById(id)) {
            throw new RuntimeException("Judgment not found with ID: " + id);
        }
        judgmentRepository.deleteById(id);
    }

    /**
     * CORE FEATURE: Compare two judgments
     * Returns similarity analysis
     */
    public Map<String, Object> compareJudgments(Long id1, Long id2) {
        log.info("Comparing judgments: {} vs {}", id1, id2);

        // Fetch both judgments
        Judgment judgment1 = getJudgmentById(id1);
        Judgment judgment2 = getJudgmentById(id2);

        // Perform similarity analysis
        double similarity = calculateSimilarity(
                judgment1.getJudgmentText(),
                judgment2.getJudgmentText());

        // Find common keywords
        List<String> commonKeywords = findCommonKeywords(
                judgment1.getKeywords(),
                judgment2.getKeywords());

        // Build result
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("judgment1", Map.of(
                "id", judgment1.getId(),
                "caseName", judgment1.getCaseName(),
                "court", judgment1.getCourt(),
                "year", judgment1.getYear(),
                "verdict", judgment1.getVerdict() != null ? judgment1.getVerdict() : "N/A"));

        result.put("judgment2", Map.of(
                "id", judgment2.getId(),
                "caseName", judgment2.getCaseName(),
                "court", judgment2.getCourt(),
                "year", judgment2.getYear(),
                "verdict", judgment2.getVerdict() != null ? judgment2.getVerdict() : "N/A"));

        result.put("analysis", Map.of(
                "similarityPercentage", Math.round(similarity * 100.0) / 100.0,
                "commonKeywords", commonKeywords,
                "sameVerdictType", isSameVerdictType(judgment1.getVerdict(), judgment2.getVerdict()),
                "yearDifference", Math.abs(judgment1.getYear() - judgment2.getYear())));

        result.put("conclusion", generateConclusion(similarity, judgment1, judgment2));

        return result;
    }

    /**
     * Calculate text similarity using Jaccard Index
     * Formula: intersection(A, B) / union(A, B)
     */
    private double calculateSimilarity(String text1, String text2) {
        // Convert to lowercase and split into words
        Set<String> words1 = Arrays.stream(text1.toLowerCase().split("\\s+"))
                .filter(word -> word.length() > 3) // Ignore short words
                .collect(Collectors.toSet());

        Set<String> words2 = Arrays.stream(text2.toLowerCase().split("\\s+"))
                .filter(word -> word.length() > 3)
                .collect(Collectors.toSet());

        // Calculate intersection
        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);

        // Calculate union
        Set<String> union = new HashSet<>(words1);
        union.addAll(words2);

        // Jaccard similarity
        if (union.isEmpty())
            return 0.0;
        return ((double) intersection.size() / union.size()) * 100;
    }

    /**
     * Find common keywords between two judgments
     */
    private List<String> findCommonKeywords(String keywords1, String keywords2) {
        if (keywords1 == null || keywords2 == null) {
            return Collections.emptyList();
        }

        Set<String> set1 = Arrays.stream(keywords1.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        Set<String> set2 = Arrays.stream(keywords2.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        set1.retainAll(set2);
        return new ArrayList<>(set1);
    }

    /**
     * Check if verdicts are similar type
     */
    private boolean isSameVerdictType(String verdict1, String verdict2) {
        if (verdict1 == null || verdict2 == null)
            return false;

        verdict1 = verdict1.toLowerCase();
        verdict2 = verdict2.toLowerCase();

        // Simple keyword matching
        if (verdict1.contains("dismiss") && verdict2.contains("dismiss"))
            return true;
        if (verdict1.contains("allow") && verdict2.contains("allow"))
            return true;
        if (verdict1.contains("reject") && verdict2.contains("reject"))
            return true;

        return false;
    }

    /**
     * Generate AI-like conclusion
     */
    private String generateConclusion(double similarity, Judgment j1, Judgment j2) {
        StringBuilder conclusion = new StringBuilder();

        if (similarity > 70) {
            conclusion.append("These judgments are HIGHLY SIMILAR (")
                    .append(String.format("%.2f", similarity))
                    .append("% match). ");
            conclusion.append("They likely deal with similar legal principles and precedents.");
        } else if (similarity > 40) {
            conclusion.append("These judgments show MODERATE SIMILARITY (")
                    .append(String.format("%.2f", similarity))
                    .append("% match). ");
            conclusion.append("They may share some common legal concepts but differ in application.");
        } else {
            conclusion.append("These judgments are SIGNIFICANTLY DIFFERENT (")
                    .append(String.format("%.2f", similarity))
                    .append("% match). ");
            conclusion.append("They likely address different legal issues or use different reasoning.");
        }

        if (j1.getCourt().equalsIgnoreCase(j2.getCourt())) {
            conclusion.append(" Both were decided by the same court (").append(j1.getCourt()).append(").");
        }

        return conclusion.toString();
    }
}
