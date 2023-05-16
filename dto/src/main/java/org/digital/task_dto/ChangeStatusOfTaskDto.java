package org.digital.task_dto;


import lombok.Data;

@Data
public class ChangeStatusOfTaskDto {
    private Long taskId;
    private String taskStatus;
}
