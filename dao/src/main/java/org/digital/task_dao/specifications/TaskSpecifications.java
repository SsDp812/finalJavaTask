package org.digital.task_dao.specifications;


import jakarta.persistence.criteria.Predicate;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.TaskStatus;
import org.digital.task_dto.request_task_dto.SearchTaskDto;
import org.digital.task_model.Task;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface TaskSpecifications {
    static Specification<Task> searchTask(String searchText,
                                          List<TaskStatus> taskStatuses,
                                          Employee executor, Employee author,
                                          Date deadLineTimeStart, Date deadLineTimeEnd,
                                          Date startTaskTimeStart, Date startTaskTimeEnd) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchText != null && !searchText.isEmpty()) {
                predicates.add(builder.like(builder.lower(root.get("taskName")), "%" + searchText.toLowerCase() + "%"));
            }


            if (taskStatuses != null && !taskStatuses.isEmpty()) {
                predicates.add(root.get("taskStatus").in(taskStatuses));
            }


            if (executor != null || author != null) {
                List<Predicate> executorOrAuthorPredicates = new ArrayList<>();
                if (executor != null) {
                    executorOrAuthorPredicates.add(builder.equal(root.get("executor"), executor));
                }
                if (author != null) {
                    executorOrAuthorPredicates.add(builder.equal(root.get("author"), author));
                }
                predicates.add(builder.or(executorOrAuthorPredicates.toArray(new Predicate[0])));
            }


            if (deadLineTimeStart != null || deadLineTimeEnd != null) {
                predicates.add(builder.between(root.get("deadLineTime"), deadLineTimeStart, deadLineTimeEnd));
            }

            if (startTaskTimeStart != null || startTaskTimeEnd != null) {
                predicates.add(builder.between(root.get("startTaskTime"), startTaskTimeStart, startTaskTimeEnd));
            }

            query.orderBy(builder.desc(root.get("editTaskTime")));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
