package org.digital.employee_dao;

import org.digital.employee_model.Employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface EmployeeDao {
    public void create(Employee employee) throws IOException;
    public void update(Employee employee) throws IOException;
    public Employee getById(Long id) throws Exception;
    public List<Employee> getAll() throws FileNotFoundException;
    public void deleteById(Long id) throws IOException;

}
