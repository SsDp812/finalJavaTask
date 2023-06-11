package ru.digital.business.project_services;


import lombok.extern.slf4j.Slf4j;
import ru.digital.commons.enity_statuses.ProjectStatus;
import ru.digital.dao.project_dao.ProjectRepository;
import ru.digital.dao.project_dao.specifications.ProjectSpecifications;
import ru.digital.dto.project_dto.request_project_dto.ChangeProjectStatusDto;
import ru.digital.dto.project_dto.request_project_dto.CreateProjectDto;
import ru.digital.dto.project_dto.request_project_dto.SearchProjectDto;
import ru.digital.dto.project_dto.request_project_dto.UpdateProjectDto;
import ru.digital.dto.project_dto.response_project_dto.ProjectCardDto;
import ru.digital.models.project_model.Project;
import ru.digital.dao.team_dao.TeamRepository;
import ru.digital.models.team_member_model.TeamMember;
import ru.digital.models.team_model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digital.commons.exceptions.project_exceptions.*;

import java.util.*;

@Service
@Transactional
@Slf4j
public class ProjectServiceImpl implements ProjectService{
    private ProjectRepository repository;
    private TeamRepository teamRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository, TeamRepository teamRepository) {
        this.repository = repository;
        this.teamRepository = teamRepository;
    }

    public ProjectCardDto createNewProject(CreateProjectDto dto) throws Exception {
        if (dto == null) {
            throw new NullProjectDtoException();
        }
        if (Objects.equals(dto.getProjectCodeName(), "") || dto.getProjectCodeName() == null) {
            throw new EmptyCodeNameProjectException();
        }
        Optional<Project> projectOptional = repository.findById(dto.getProjectCodeName());
        if (projectOptional.isEmpty()) {
            Project project = new Project();
            if (Objects.equals(dto.getProjectName(), "") || dto.getProjectName() == null) {
                throw new EmptyNameProjectException();
            }
            project.setProjectCodeName(dto.getProjectCodeName());
            project.setProjectName(dto.getProjectName());
            project.setDescription(dto.getDescription());
            project.setProjectStatus(ProjectStatus.DRAFT);
            project = repository.save(project);
            Team team = new Team();
            team.setProject(project);
            team.setMembers(new ArrayList<TeamMember>());
            team = teamRepository.save(team);
            log.info("Created new project with code-name: " + project.getProjectCodeName());
            return ProjectMapper.getProjectCardDto(project);
        } else {
            throw new NotUniqueProjectCodeNameException();
        }
    }

    public ProjectCardDto changeProject(UpdateProjectDto dto) throws Exception {
        if (dto == null) {
            throw new NullProjectDtoException();
        }
        Optional<Project> projectOptional = repository.findById(dto.getProjectCodeName());
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            if (Objects.equals(dto.getProjectCodeName(), "") || dto.getProjectCodeName() == null) {
                throw new EmptyCodeNameProjectException();
            } else if (Objects.equals(dto.getProjectName(), "") || dto.getProjectName() == null) {
                throw new EmptyNameProjectException();
            }
            project.setProjectCodeName(dto.getProjectCodeName());
            project.setProjectName(dto.getProjectName());
            project.setDescription(dto.getDescription());
            log.info("Project " + project.getProjectCodeName() + " was updated");
            project = repository.save(project);
            return ProjectMapper.getProjectCardDto(project);
        } else {
            throw new NotFoundProjectException();
        }
    }

    public List<ProjectCardDto> searchProject(SearchProjectDto dto) throws Exception {
        if (dto == null) {
            throw new NullProjectDtoException();
        }
        List<Project> projects = repository.findAll(ProjectSpecifications.
                searchByFilterAndStatuses(dto.getTextFilter(), dto.getStatus()));
        List<ProjectCardDto> list = new ArrayList<>();
        for (var project : projects) {
            list.add(ProjectMapper.getProjectCardDto(project));
        }
        return list;
    }

    public ProjectCardDto changeProjectStatus(ChangeProjectStatusDto dto) throws Exception {
        if (dto == null) {
            throw new NullProjectDtoException();
        }
        Optional<Project> projectOptional = repository.findById(dto.getProjectCodeName());
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();

            if (checkAvailableToChangeStatus(project.getProjectStatus(),
                    ProjectStatus.valueOf(dto.getNewStatus()))) {
                project.setProjectStatus(ProjectStatus.valueOf(dto.getNewStatus()));
                log.info("Project " + project.getProjectCodeName() +
                        " has new status now: " + project.getProjectStatus().toString());
                project = repository.save(project);
                return ProjectMapper.getProjectCardDto(project);
            } else {
                log.error("Not available status " + dto.getNewStatus() + " for project: "
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
