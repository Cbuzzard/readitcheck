package com.buzzardsview.readitcheck.data;

import com.buzzardsview.readitcheck.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph("user-entity-graph")
    Optional<User> getById(Integer id);

}
