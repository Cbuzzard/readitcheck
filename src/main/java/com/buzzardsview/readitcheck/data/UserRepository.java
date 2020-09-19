package com.buzzardsview.readitcheck.data;

import com.buzzardsview.readitcheck.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @EntityGraph("user-entity-graph")
    Optional<User> getByGoogleId(String id);

}
