package com.example.ormlearn.repository;

import com.example.ormlearn.entity.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AttemptRepository extends JpaRepository<Attempt, Integer> {

    // ----------------------------------------------------------------
    // Hands-on 3: fetch a single quiz attempt for a given user, joining
    // user -> attempt -> attempt_question -> question -> attempt_option
    // -> options in one HQL query. 'fetch' is used on every one-to-many /
    // many-to-many step so the whole graph comes back in one SQL query.
    // ----------------------------------------------------------------
    @Query(value = "SELECT a FROM Attempt a " +
            "left join fetch a.user u " +
            "left join fetch a.attemptQuestionList aq " +
            "left join fetch aq.question q " +
            "left join fetch q.optionList " +
            "left join fetch aq.attemptOptionList ao " +
            "left join fetch ao.selectedOption " +
            "WHERE u.id = :userId AND a.id = :attemptId")
    Attempt getAttempt(@Param("userId") int userId, @Param("attemptId") int attemptId);
}
