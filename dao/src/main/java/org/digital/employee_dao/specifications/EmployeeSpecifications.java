package org.digital.employee_dao.specifications;

import jakarta.persistence.criteria.Predicate;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.springframework.data.jpa.domain.Specification;

public interface EmployeeSpecifications {
    static Specification<Employee> searchByFilterAndStatuses(String searchText, EmployeeStatus employeeStatus) {
        return (root, query, builder) -> {
            Predicate searchTextPredicate = builder.or(
                    builder.like(root.get("surname"), "%" + searchText + "%"),
                    builder.like(root.get("name"), "%" + searchText + "%"),
                    builder.like(root.get("middleName"), "%" + searchText + "%"),
                    builder.like(root.get("jobTitle"), "%" + searchText + "%"),
                    builder.like(root.get("login"), "%" + searchText + "%"),
                    builder.like(root.get("email"), "%" + searchText + "%")
            );
            Predicate statusPredicate = builder.equal(root.get("employeeStatus"), employeeStatus);
            return builder.and(searchTextPredicate, statusPredicate);
        };
    }
}

