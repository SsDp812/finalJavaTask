package ru.digital.dao.task_dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.digital.commons.enity_statuses.TaskStatus;
import ru.digital.models.task_model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    List<Task> findBytaskStatusIn(List<TaskStatus> statusList);
}
