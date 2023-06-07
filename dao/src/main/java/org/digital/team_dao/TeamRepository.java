package org.digital.team_dao;

import org.digital.project_model.Project;
import org.digital.team_model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByProject(Project project);
}
