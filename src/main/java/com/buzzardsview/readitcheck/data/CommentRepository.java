package com.buzzardsview.readitcheck.data;

import com.buzzardsview.readitcheck.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
