package org.digital.services.project_services;


import jakarta.transaction.Transactional;
import org.digital.enity_statuses.ProjectStatus;
import org.digital.project_dao.ProjectRepository;
import org.digital.project_dto.request_project_dto.ChangeProjectStatusDto;
import org.digital.project_dto.request_project_dto.CreateProjectDto;
import org.digital.project_dto.request_project_dto.SearchProjectDto;
import org.digital.project_dto.request_project_dto.UpdateProjectDto;
import org.digital.project_dto.response_project_dto.ProjectCardDto;
import org.digital.project_model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ProjectService {
    private ProjectRepository repository;

    @Autowired
    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public void createNewProject(CreateProjectDto dto) throws Exception {
        Optional<Project> projectOptional = repository.findById(dto.getProjectCodeName());
        if (projectOptional.isEmpty()) {
            Project project = new Project();
            if(!Objects.equals(dto.getProjectCodeName(),"")){
                project.setProjectCodeName(dto.getProjectCodeName());
            }else{
                throw new Exception("Empty code name!");
            }
            if(!Objects.equals(dto.getProjectName(),"")){
                project.setProjectName(dto.getProjectName());
            }else{
                throw new Exception("Empty name!");
            }
            project.setDescription(dto.getDescription());
            project.setProjectStatus(ProjectStatus.DRAFT);
            repository.save(project);
        } else {
            throw new Exception("Code name is not unique");
        }
    }

    public void changeProject(UpdateProjectDto dto) throws Exception {
        Optional<Project> projectOptional = repository.findById(dto.getProjectCodeName());
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            if(!Objects.equals(dto.getProjectCodeName(),"")){
                project.setProjectCodeName(dto.getProjectCodeName());
            }else{
                throw new Exception("Empty code name!");
            }
            if(!Objects.equals(dto.getProjectName(),"")){
                project.setProjectName(dto.getProjectName());
            }else{
                throw new Exception("Empty name!");
            }
            project.setDescription(dto.getDescription());
            repository.save(project);
        } else {
            throw new Exception("Error project Code name, project not found!");
        }
    }

    public List<ProjectCardDto> searchProject(SearchProjectDto dto) throws Exception {
        Optional<List<Project>> optionalProjects = repository.findByProjectNameContainingIgnoreCaseOrProjectCodeNameContainingIgnoreCaseAndProjectStatusIn(dto.getTextFilter(),dto.getStatus());
        if(optionalProjects.isPresent()){
            List<Project> projects = optionalProjects.get();
            List<ProjectCardDto> list = new ArrayList<>();
            for(var project : projects){
                ProjectCardDto projectDto = new ProjectCardDto();
                projectDto.setProjectName(project.getProjectName());
                projectDto.setProjectCodeName(project.getProjectCodeName());
                projectDto.setProjectStatus(project.getProjectStatus().toString());
                projectDto.setDescription(project.getDescription());
                list.add(projectDto);
            }
            return list;
        }else{
            throw new Exception("Projects not found!");
        }
    }

    public void changeProjectStatus(ChangeProjectStatusDto dto) throws Exception {
        Optional<Project> projectOptional = repository.findById(dto.getProjectCodeName());
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();

            if (checkAvailableToChangeStatus(project.getProjectStatus(),
                    ProjectStatus.valueOf(dto.getNewStatus()))) {
                project.setProjectStatus(ProjectStatus.valueOf(dto.getNewStatus()));
                repository.save(project);
            } else {
                throw new Exception("You cant set this status");
            }
        } else {
            throw new Exception("Error project Code name, project not found!");
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
        throw new Exception("Error status");
    }

}
