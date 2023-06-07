package org.digital.employee_dao.specifications;

import jakarta.persistence.criteria.Predicate;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.springframework.data.jpa.domain.Specification;


public interface EmployeeSpecifications {
    static Specification<Employee> searchByFilterAndStatuses(String searchText, EmployeeStatus employeeStatus) {
        return (root, query, builder) -> {
            Predicate searchTextPredicate = builder.or(
                    builder.like(builder.lower(root.get("surname")), "%" + searchText.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("name")), "%" + searchText.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("middleName")), "%" + searchText.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("jobTitle")), "%" + searchText.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("email")), "%" + searchText.toLowerCase() + "%")
            );
            Predicate statusPredicate = builder.equal(root.get("employeeStatus"), "0");
            return builder.and(searchTextPredicate, statusPredicate);
        };
    }
}