package org.digital.project_dto.request_project_dto;


import lombok.Data;

@Data
public class ChangeProjectStatusDto {
    private String projectCodeName;
    private String newStatus;
}
