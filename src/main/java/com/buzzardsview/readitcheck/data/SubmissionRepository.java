package com.buzzardsview.readitcheck.data;

import com.buzzardsview.readitcheck.model.Submission;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

    Optional<Submission> getById(Integer id);

}
