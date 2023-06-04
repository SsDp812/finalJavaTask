package org.digital.services.project_services;



import org.digital.enity_statuses.ProjectStatus;
import org.digital.exceptions.project_exceptions.*;
import org.digital.project_dao.ProjectRepository;
import org.digital.project_dao.specifications.ProjectSpecifications;
import org.digital.project_dto.request_project_dto.ChangeProjectStatusDto;
import org.digital.project_dto.request_project_dto.CreateProjectDto;
import org.digital.project_dto.request_project_dto.SearchProjectDto;
import org.digital.project_dto.request_project_dto.UpdateProjectDto;
import org.digital.project_dto.response_project_dto.ProjectCardDto;
import org.digital.project_model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ProjectService {
    private ProjectRepository repository;
    private Logger logger = LoggerFactory.getLogger("project_logger");

    @Autowired
    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public ProjectCardDto createNewProject(CreateProjectDto dto) throws Exception {
        if(dto == null){
            throw new NullProjectDtoException();
        }
        Optional<Project> projectOptional = repository.findById(dto.getProjectCodeName());
        if (projectOptional.isEmpty()) {
            Project project = new Project();
            if(!Objects.equals(dto.getProjectCodeName(),"")){
                throw new EmptyCodeNameProjectException();
            }else if(Objects.equals(dto.getProjectName(),"")){
                throw new EmptyNameProjectException();
            }
            project.setProjectCodeName(dto.getProjectCodeName());
            project.setProjectName(dto.getProjectName());
            project.setDescription(dto.getDescription());
            project.setProjectStatus(ProjectStatus.DRAFT);
            repository.save(project);
            logger.info("Created new project with code-name: " + project.getProjectCodeName());
            return ProjectMapper.getProjectCardDto(project);
        } else {
            throw new NotUniqueProjectCodeNameException();
        }
    }

    public ProjectCardDto changeProject(UpdateProjectDto dto) throws Exception {
        if(dto == null){
            throw new NullProjectDtoException();
        }
        Optional<Project> projectOptional = repository.findById(dto.getProjectCodeName());
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            if(Objects.equals(dto.getProjectCodeName(),"")){
                throw new EmptyCodeNameProjectException();
            }else if(Objects.equals(dto.getProjectName(),"")){
                throw new EmptyNameProjectException();
            }
            project.setProjectCodeName(dto.getProjectCodeName());
            project.setProjectName(dto.getProjectName());
            project.setDescription(dto.getDescription());
            logger.info("Project " + project.getProjectCodeName() + " was updated");
            repository.save(project);
            return ProjectMapper.getProjectCardDto(project);
        } else {
            throw new NotFoundProjectException();
        }
    }

    public List<ProjectCardDto> searchProject(SearchProjectDto dto) throws Exception {
        if(dto == null){
            throw new NullProjectDtoException();
        }
        List<Project> projects = repository.findAll(ProjectSpecifications.
                searchByFilterAndStatuses(dto.getTextFilter(), dto.getStatus()));
            List<ProjectCardDto> list = new ArrayList<>();
            for(var project : projects){
                list.add(ProjectMapper.getProjectCardDto(project));
            }
            return list;
    }

    public ProjectCardDto changeProjectStatus(ChangeProjectStatusDto dto) throws Exception {
        if(dto == null){
            throw new NullProjectDtoException();
        }
        Optional<Project> projectOptional = repository.findById(dto.getProjectCodeName());
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();

            if (checkAvailableToChangeStatus(project.getProjectStatus(),
                    ProjectStatus.valueOf(dto.getNewStatus()))) {
                project.setProjectStatus(ProjectStatus.valueOf(dto.getNewStatus()));
                logger.info("Project " + project.getProjectCodeName() +
                        " has new status now: " + project.getProjectStatus().toString());
                repository.save(project);
                return ProjectMapper.getProjectCardDto(project);
            } else {
                logger.error("Not available status " + dto.getNewStatus() + " for project: "
                + project.getProjectCodeName());
                throw new NotAvailableProjectStatusExeption();
            }
        } else {
            throw new NotFoundProjectException();
        }
    }


    private boolean checkAvailableToChangeStatus(ProjectStatus currentStatus, ProjectStatus newStatus) throws Exception {
        switch (currentStatus) {
            case DRAFT -> {
                return newStatus == ProjectStatus.DEVELOPING;
            }

            case DEVELOPING -> {
                return newStatus == ProjectStatus.TESTING;
            }
            case TESTING -> {
                return newStatus == ProjectStatus.DONE;
            }
            case DONE -> {
                return false;
            }
        }
        throw new NotCorrectProjectStatusException();
    }

}
