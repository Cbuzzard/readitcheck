package com.buzzardsview.readitcheck.data;

import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.dto.SubmissionForListDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

    @EntityGraph("submission-entity-graph")
    Optional<Submission> getById(Integer id);

    @Override
//    @EntityGraph("submission-entity-graph")
    List<Submission> findAll();

}
