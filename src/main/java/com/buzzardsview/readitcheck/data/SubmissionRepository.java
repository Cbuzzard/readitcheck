package com.buzzardsview.readitcheck.data;

import com.buzzardsview.readitcheck.model.Submission;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

    Optional<Submission> getById(Integer id);

}
