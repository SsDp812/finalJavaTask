package ru.digital.dao.employee_dao;

import ru.digital.models.employee_model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> findByLoginAndPassword(String login, String password);
    Optional<Employee> findByLogin(String login);
}
