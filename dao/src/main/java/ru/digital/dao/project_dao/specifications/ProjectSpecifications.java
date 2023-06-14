package ru.digital.dao.project_dao.specifications;


import jakarta.persistence.criteria.Predicate;
import ru.digital.commons.enity_statuses.ProjectStatus;
import ru.digital.models.project_model.Project;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProjectSpecifications {
    static Specification<Project> searchByFilterAndStatuses(String searchText, List<ProjectStatus> projectStatuses) {
        return (root, query, builder) -> {
            Predicate searchTextPredicate = builder.or(
                    builder.like(builder.lower(root.get("projectName")), "%" + searchText.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("projectCodeName")), "%" + searchText.toLowerCase() + "%")
            );
            Predicate statusPredicate = builder.in(root.get("projectStatus")).value(projectStatuses);
            return builder.and(searchTextPredicate, statusPredicate);
        };
    }
}