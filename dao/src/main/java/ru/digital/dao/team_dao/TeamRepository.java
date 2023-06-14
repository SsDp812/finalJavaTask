package ru.digital.dao.team_dao;

import ru.digital.models.project_model.Project;
import ru.digital.models.team_model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByProject(Project project);
}
