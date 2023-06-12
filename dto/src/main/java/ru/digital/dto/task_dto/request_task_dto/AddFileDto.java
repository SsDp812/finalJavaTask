package ru.digital.dto.task_dto.request_task_dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
public class AddFileDto {
    private MultipartFile file;
    private Long taskId;
}
