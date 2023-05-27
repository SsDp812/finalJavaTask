package org.digital.project_dao.specifications;

import jakarta.persistence.criteria.Predicate;
import org.digital.enity_statuses.ProjectStatus;
import org.digital.project_model.Project;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProjectSpecifications {
    static Specification<Project> searchByFilterAndStatuses(String searchText, List<ProjectStatus> projectStatuses) {
        return (root, query, builder) -> {
            Predicate searchTextPredicate = builder.or(
                    builder.like(builder.lower(root.get("projectName")), "%" + searchText.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("projectCode")), "%" + searchText.toLowerCase() + "%")
            );
            Predicate statusPredicate = builder.in(root.get("projectStatus")).value(projectStatuses);
            return builder.and(searchTextPredicate, statusPredicate);
        };
    }
}