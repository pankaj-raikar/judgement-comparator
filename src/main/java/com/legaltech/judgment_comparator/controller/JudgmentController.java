package com.legaltech.judgment_comparator.controller;

import com.legaltech.judgment_comparator.entity.Judgment;
import com.legaltech.judgment_comparator.service.JudgmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Judgment APIs
 * 
 * @RestController - Combines @Controller + @ResponseBody
 *                 Automatically converts return values to JSON
 * @RequestMapping - Base URL path for all endpoints
 * @CrossOrigin - Allows frontend on different port/domain (CORS)
 */
@RestController
@RequestMapping("/api/judgments")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class JudgmentController {

    private final JudgmentService judgmentService;

    /**
     * POST /api/judgments - Upload new judgment
     * 
     * @PostMapping - Maps HTTP POST requests
     * @RequestBody - Converts JSON to Judgment object
     * @Valid - Triggers validation (@NotBlank, @Size, etc.)
     *        ResponseEntity<> - Allows custom HTTP status codes
     */
    @PostMapping
    public ResponseEntity<Judgment> uploadJudgment(@Valid @RequestBody Judgment judgment) {
        log.info("POST /api/judgments - Uploading judgment: {}", judgment.getCaseName());
        Judgment saved = judgmentService.saveJudgment(judgment);
        return new ResponseEntity<>(saved, HttpStatus.CREATED); // 201 Created
    }

    /**
     * GET /api/judgments - Get all judgments
     */
    @GetMapping
    public ResponseEntity<List<Judgment>> getAllJudgments() {
        log.info("GET /api/judgments - Fetching all judgments");
        List<Judgment> judgments = judgmentService.getAllJudgments();
        return ResponseEntity.ok(judgments); // 200 OK
    }

    /**
     * GET /api/judgments/{id} - Get judgment by ID
     * 
     * @PathVariable - Extracts {id} from URL
     *               Example: GET /api/judgments/5 → id = 5
     */
    @GetMapping("/{id}")
    public ResponseEntity<Judgment> getJudgmentById(@PathVariable Long id) {
        log.info("GET /api/judgments/{} - Fetching judgment", id);
        Judgment judgment = judgmentService.getJudgmentById(id);
        return ResponseEntity.ok(judgment);
    }

    /**
     * GET /api/judgments/search?term=article21
     * 
     * @RequestParam - Extracts query parameter from URL
     */
    @GetMapping("/search")
    public ResponseEntity<List<Judgment>> searchJudgments(@RequestParam String term) {
        log.info("GET /api/judgments/search?term={}", term);
        List<Judgment> results = judgmentService.searchJudgments(term);
        return ResponseEntity.ok(results);
    }

    /**
     * POST /api/judgments/compare?id1=1&id2=2
     * CORE FEATURE - Compare two judgments
     */
    @PostMapping("/compare")
    public ResponseEntity<Map<String, Object>> compareJudgments(
            @RequestParam Long id1,
            @RequestParam Long id2) {
        log.info("POST /api/judgments/compare?id1={}&id2={}", id1, id2);

        if (id1.equals(id2)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Cannot compare a judgment with itself"));
        }

        Map<String, Object> comparison = judgmentService.compareJudgments(id1, id2);
        return ResponseEntity.ok(comparison);
    }

    /**
     * DELETE /api/judgments/{id} - Delete judgment
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteJudgment(@PathVariable Long id) {
        log.info("DELETE /api/judgments/{}", id);
        judgmentService.deleteJudgment(id);
        return ResponseEntity.ok(Map.of("message", "Judgment deleted successfully"));
    }
}
