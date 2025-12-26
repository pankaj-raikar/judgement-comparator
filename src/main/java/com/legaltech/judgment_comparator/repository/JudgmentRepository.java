package com.legaltech.judgment_comparator.repository;

import com.legaltech.judgment_comparator.entity.Judgment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Judgment entity
 * 
 * @Repository - Marks as Spring Data repository
 *             JpaRepository<Judgment, Long> provides:
 *             - save(), findById(), findAll(), deleteById()
 *             - count(), existsById()
 * 
 *             Spring Data JPA auto-implements these methods!
 */
@Repository
public interface JudgmentRepository extends JpaRepository<Judgment, Long> {

    /**
     * Find judgments by case name (partial match)
     * Method naming convention: findBy + FieldName + Containing
     * Spring auto-generates SQL:
     * SELECT * FROM judgments WHERE case_name LIKE '%value%'
     */
    List<Judgment> findByCaseNameContainingIgnoreCase(String caseName);

    /**
     * Find by court name
     */
    List<Judgment> findByCourtIgnoreCase(String court);

    /**
     * Find by year
     */
    List<Judgment> findByYear(Integer year);

    /**
     * Find by keywords (for advanced search)
     */
    List<Judgment> findByKeywordsContainingIgnoreCase(String keyword);

    /**
     * Custom JPQL query - Find by multiple criteria
     * 
     * @Query - Defines custom query
     *        :caseName - Named parameter
     */
    @Query("SELECT j FROM Judgment j WHERE " +
            "LOWER(j.caseName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(j.keywords) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(j.court) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Judgment> searchJudgments(@Param("searchTerm") String searchTerm);
}
