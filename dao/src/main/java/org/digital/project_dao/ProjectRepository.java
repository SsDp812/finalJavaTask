package org.digital.project_dao;

import org.digital.enity_statuses.ProjectStatus;
import org.digital.project_model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProjectRepository extends JpaRepository<Project,String>, JpaSpecificationExecutor<Project> {

}
