package ru.digital.business.mail_sender_services.Impls;


import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import ru.digital.business.mail_sender_services.MailSender;
import ru.digital.commons.enity_statuses.NotifyStatus;
import ru.digital.dto.task_dto.response_task_dto.NotifyTaskDto;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class MailSenderServiceImpl implements MailSender {
    //Service to send notifications to employees
    private JavaMailSender mailSender;
    private Configuration freeMakerConfig;

    @Autowired
    public MailSenderServiceImpl(JavaMailSender mailSender, Configuration freeMakerConfig) {
        this.mailSender = mailSender;
        this.freeMakerConfig = freeMakerConfig;
    }

    public void sendNotificationAboutTask(NotifyTaskDto taskDto) throws Exception {
        //send method
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        if (taskDto.getStatus() == NotifyStatus.NEWTASK) {
            helper.setSubject("New task!");
        } else {
            helper.setSubject("Remember about task!");
        }
        helper.setTo(taskDto.getEmail());
        helper.setFrom("digitalTestMail@yandex.ru");
        String emailContent = getHtmlContent(taskDto);
        helper.setText(emailContent, true);
        try {
            //change email for real to testing
            helper.setTo("efan.daniel2002@gmail.com");
            mailSender.send(mimeMessage);
            log.info("Mail with task id = " + taskDto.getTaskId() + " was sended to " + taskDto.getEmail());
        } catch (Exception ex) {
            log.error("TaskID = " + taskDto.getTaskId() + "Email = " + taskDto.getEmail() + "Error = " + ex.getMessage());
        }
    }

    private String getHtmlContent(NotifyTaskDto taskDto) throws IOException, TemplateException {
        String templateName = "";
        if (taskDto.getStatus() == NotifyStatus.NEWTASK) {
            templateName = "taskMail.ftl";
        } else {
            templateName = "rememberMail.ftl";
        }
        //method to get html templte model for mail
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", taskDto.getEmployeeName() + " " + taskDto.getEmployeeSurname());
        model.put("taskName", taskDto.getTaskName());
        model.put("taskDescription", taskDto.getTaskDescription());
        model.put("taskDeadline", taskDto.getDeadline().toString());
        model.put("sendTo", taskDto.getEmail());
        try {
            freeMakerConfig.getTemplate(templateName)
                    .process(model, stringWriter);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return stringWriter.getBuffer()
                .toString();
    }
}
