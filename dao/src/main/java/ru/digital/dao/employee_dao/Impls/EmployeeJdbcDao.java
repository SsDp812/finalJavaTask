package ru.digital.dao.employee_dao.Impls;

import ru.digital.dao.employee_dao.EmployeeDao;
import ru.digital.models.employee_model.Employee;
import ru.digital.commons.enity_statuses.EmployeeStatus;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeJdbcDao implements EmployeeDao {

    private Connection connection;

    public EmployeeJdbcDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Employee employee) throws IOException, SQLException {
        String query = "INSERT INTO employees (surname, name, middle_name, job_title, login, password, email, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try( PreparedStatement statement = connection.prepareStatement(query);){
            statement.setString(2, employee.getSurname());
            statement.setString(3, employee.getName());
            statement.setString(4, employee.getMiddleName());
            statement.setString(5, employee.getJobTitle());
            statement.setString(6, employee.getLogin());
            statement.setString(7, employee.getPassword());
            statement.setString(8, employee.getEmail());
            statement.setString(9, EmployeeStatus.ACTIVE.toString());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Employee employee) throws IOException, SQLException {
        String query = "UPDATE employees SET surname=?, name=?, middle_name=?, job_title=?, login=?, password=?, email=?, status=? WHERE accountid=?";
        try(PreparedStatement statement = connection.prepareStatement(query);){
            statement.setString(1, employee.getSurname());
            statement.setString(2, employee.getName());
            statement.setString(3, employee.getMiddleName());
            statement.setString(4, employee.getJobTitle());
            statement.setString(5, employee.getLogin());
            statement.setString(6, employee.getPassword());
            statement.setString(7, employee.getEmail());
            statement.setString(8, employee.getEmployeeStatus().toString());
            statement.setLong(9, employee.getAccountId());
            statement.executeUpdate();
        }
    }

    @Override
    public Employee getById(Long id) throws Exception {
        String query = "SELECT * FROM employees WHERE accountid=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Employee(
                            resultSet.getLong("accountid"),
                            resultSet.getString("surname"),
                            resultSet.getString("name"),
                            resultSet.getString("middle_name"),
                            resultSet.getString("job_title"),
                            resultSet.getString("login"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            EmployeeStatus.valueOf(resultSet.getString("status"))
                    );
                } else {
                    throw new Exception("Employee not found!");
                }
            }
        }
    }

    @Override
    public List<Employee> getAll() throws Exception {
        String query = "SELECT * FROM employees";
        try( PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();){
            List<Employee> employees = new ArrayList<>();
            while(resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getLong("accountid"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getString("middle_name"),
                        resultSet.getString("job_title"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        EmployeeStatus.valueOf(resultSet.getString("status"))
                );
                employees.add(employee);
            }
            if(!employees.isEmpty()){
                return employees;
            }else{
                throw new Exception("Employee table is empty!");
            }
        }

    }

    @Override
    public void deleteById(Long id) throws IOException, SQLException {
        String query = "DELETE FROM employees WHERE accountid=?";
        try(PreparedStatement statement = connection.prepareStatement(query);){
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

}
