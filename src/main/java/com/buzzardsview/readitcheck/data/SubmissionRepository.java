package com.buzzardsview.readitcheck.data;

import com.buzzardsview.readitcheck.model.Submission;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

    @EntityGraph("submission-entity-graph")
    Optional<Submission> getById(Integer id);

    @Override
//    @EntityGraph("submission-entity-graph")
    List<Submission> findAll();

}
