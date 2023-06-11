package ru.digital.dao.task_dao;

import ru.digital.models.task_model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long>, JpaSpecificationExecutor<Task> {

}
