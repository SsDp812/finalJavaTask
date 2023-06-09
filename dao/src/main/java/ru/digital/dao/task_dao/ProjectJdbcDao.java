package ru.digital.dao.task_dao;

import ru.digital.dto.employee_dto.request_employee_dto.Filters.CardTaskAndExecutorDto;
import ru.digital.dto.employee_dto.request_employee_dto.Filters.FilterForTaskAndExecutorByStatusesDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjectJdbcDao {
    private Connection connection;

    public ProjectJdbcDao(Connection connection) {
        this.connection = connection;
    }

    public List<CardTaskAndExecutorDto> searchTasksAndExecutors(FilterForTaskAndExecutorByStatusesDto filter) throws Exception {
        String query = "SELECT t.task_name, t.task_description, e.name, e.email" +
        "FROM task t JOIN employee e on t.task_executor = e.accountid" +
        "WHERE t.task_status = ? and e.status = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1,filter.getTaskStatus().toString());
        statement.setString(2,filter.getExecutorStatus().toString());

        ResultSet resultSet = statement.executeQuery();
        List<CardTaskAndExecutorDto> tasksAndExecutors = new ArrayList<>();
        while (resultSet.next()){
            CardTaskAndExecutorDto dto = new CardTaskAndExecutorDto(
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("task_name"),
                    resultSet.getString("task_description")
            );
            tasksAndExecutors.add(dto);
        }
        statement.close();
        resultSet.close();
        if(!tasksAndExecutors.isEmpty()){
            return tasksAndExecutors;
        }
        else{
            throw new Exception("Now tasks with this filter!");
        }
    }
}
