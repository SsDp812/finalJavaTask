package org.digital.task_dao;

import org.digital.employee_model.Employee;
import org.digital.enity_statuses.TaskStatus;
import org.digital.task_model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    Optional<List<Task>> findByTaskNameContainingIgnoreCaseAndTaskStatusInAndExecutorOrAuthorAndDeadLineTimeBetweenOrStartTaskTimeBetweenOrderByEditTaskTimeDesc(
            String searchText, List<TaskStatus> taskStatuses, Employee executor, Employee author,
            Date deadLineTimeStart, Date deadLineTimeEnd, Date startTaskTimeStart, Date startTaskTimeEnd);
}
