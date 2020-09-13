package com.buzzardsview.readitcheck.data;

import com.buzzardsview.readitcheck.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
