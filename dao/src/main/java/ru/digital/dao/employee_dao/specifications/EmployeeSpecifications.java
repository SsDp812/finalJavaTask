package ru.digital.dao.employee_dao.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.digital.commons.enity_statuses.EmployeeStatus;
import ru.digital.models.employee_model.Employee;


public interface EmployeeSpecifications {
    static Specification<Employee> searchByFilterAndStatuses(String searchText, EmployeeStatus employeeStatus) {
        return (root, query, builder) -> {
            Predicate searchTextPredicate = builder.or(
                    builder.like(root.get("surname"), "%" + searchText + "%"),
                    builder.like(root.get("name"), "%" + searchText + "%"),
                    builder.like(root.get("middleName"), "%" + searchText + "%"),
                    builder.like(root.get("jobTitle"), "%" + searchText + "%"),
                    builder.like(root.get("email"), "%" + searchText + "%")
            );
            Predicate statusPredicate = builder.equal(root.get("employeeStatus"), "0");
            return searchTextPredicate;
        };
    }

}