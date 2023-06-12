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
import ru.digital.models.task_model.Task;

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

    public void sendNewTask(Task task) throws Exception {
        //send method
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("New task!");
        helper.setTo(task.getExecutor().getEmail());
        helper.setFrom("digitalTestMail@yandex.ru");
        String emailContent = getHtmlContentTemplate(task);
        helper.setText(emailContent, true);
        try {
            //change email for real to testing
            helper.setTo("efan.daniel2002@gmail.com");
            mailSender.send(mimeMessage);
            log.info("Mail with task id = " + task.getTaskId() + " was sended to " + task.getExecutor().getEmail());
        } catch (Exception ex) {
            log.error("TaskID = " + task.getTaskId() + "Email = " + task.getExecutor().getEmail() + "Error = " + ex.getMessage());
        }
    }

    private String getHtmlContentTemplate(Task task) throws IOException, TemplateException {
        //method to get html templte model for mail
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", task.getExecutor().getName() + " " + task.getExecutor().getSurname());
        model.put("taskName", task.getTaskName());
        model.put("taskDescription", task.getTaskDescription());
        model.put("taskDeadline", task.getDeadLineTime().toString());
        model.put("sendTo", task.getExecutor().getEmail());
        try {
            freeMakerConfig.getTemplate("taskMail.ftl")
                    .process(model, stringWriter);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return stringWriter.getBuffer()
                .toString();
    }
}
