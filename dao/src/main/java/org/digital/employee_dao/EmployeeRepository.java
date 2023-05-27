package org.digital.employee_dao;

import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> findByLoginAndPassword(String login, String password);
}
