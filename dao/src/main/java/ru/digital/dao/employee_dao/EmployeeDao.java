package ru.digital.dao.employee_dao;

import ru.digital.models.employee_model.Employee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface EmployeeDao {
    public void create(Employee employee) throws IOException, SQLException;
    public void update(Employee employee) throws IOException, SQLException;
    public Employee getById(Long id) throws Exception;
    public List<Employee> getAll() throws Exception;
    public void deleteById(Long id) throws IOException, SQLException;

}
