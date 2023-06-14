package ru.digital.dao.employee_dao.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.digital.commons.enity_statuses.EmployeeStatus;
import ru.digital.models.employee_model.Employee;


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
            return searchTextPredicate;
        };
    }

}