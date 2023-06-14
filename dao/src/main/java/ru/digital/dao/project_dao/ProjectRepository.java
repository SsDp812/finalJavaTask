package ru.digital.dao.project_dao;

import ru.digital.models.project_model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectRepository extends JpaRepository<Project,String>, JpaSpecificationExecutor<Project> {

}
