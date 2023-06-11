package services.employee_services;

import org.digital.Main;
import org.digital.employee_dto.request_employee_dto.*;
import org.digital.services.employee_services.EmployeeMapper;
import services.BaseTest;
import org.digital.employee_dto.response_employee_dto.EmployeeCardDto;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.services.employee_services.Impls.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = Main.class)
public class EmployeeServiceIntegrationTest extends BaseTest {

    @Autowired
    private EmployeeServiceImpl service;

    @Test
    public void createEmployee() throws Exception {
        Employee employee = getSomeEmloyee();
        EmployeeCardDto dto = service.createNewEmployee(getCreateDto(employee));
        Assertions.assertEquals(employee.getName(), dto.getName());
        Assertions.assertEquals(employee.getEmail(), dto.getEmail());
        Assertions.assertEquals(employee.getSurname(), dto.getSurname());
        Assertions.assertEquals(employee.getEmployeeStatus().toString(), dto.getStatus().toString());
    }


    @Test()
    public void createNewEmployeeByNullDto() {
        try {
            service.createNewEmployee(null);
        } catch (Exception ex) {
            Assertions.assertEquals("Employee dto is null!", ex.getMessage());
        }
    }

    @Test
    public void createNewEmployeeWithEmptyName() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setName(null);
            EmployeeCardDto employeedto = service.createNewEmployee(getCreateDto(employee));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty name for employee!", ex.getMessage());
        }
    }

    @Test
    public void createEmployeeWithNotUniqueLogin(){
        try{
            Employee employee = getSomeEmloyee();
            service.createNewEmployee(getCreateDto(employee));
            Employee employee2 = getSomeEmloyee();
            employee2.setLogin(employee.getLogin());
            service.createNewEmployee(getCreateDto(employee2));
        } catch (Exception ex) {
            Assertions.assertEquals("Not unique login!",ex.getMessage());
        }
    }

    @Test
    public void changeEmployeeLoginToNotUnique(){
        try{
            Employee employee = getSomeEmloyee();
            service.createNewEmployee(getCreateDto(employee));
            Employee employee2 = getSomeEmloyee();
            EmployeeCardDto dto = service.createNewEmployee(getCreateDto(employee2));
            employee2.setAccountId(dto.getId());
            employee2.setLogin(employee.getLogin());
            service.changeEmployeeInfo(getUpdateDto(employee2));
        }catch (Exception ex) {
            Assertions.assertEquals("Not unique login!",ex.getMessage());
        }
    }

    @Test
    public void createNewEmployeeWithEmptySurname() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setSurname(null);
            EmployeeCardDto card = EmployeeMapper.getEmployeeDtoCard(employee);
            EmployeeCardDto employeedto = service.createNewEmployee(getCreateDto(employee));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty employee surname!", ex.getMessage());
        }
    }

    @Test
    public void createNewEmployeeWithEmptyLogin() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setLogin(null);
            EmployeeCardDto card = EmployeeMapper.getEmployeeDtoCard(employee);
            EmployeeCardDto employeedto = service.createNewEmployee(getCreateDto(employee));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty login!", ex.getMessage());
        }
    }

    @Test
    public void createNewEmployeeWithEmptyPassword() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setPassword(null);
            EmployeeCardDto card = EmployeeMapper.getEmployeeDtoCard(employee);
            EmployeeCardDto employeedto = service.createNewEmployee(getCreateDto(employee));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty password!", ex.getMessage());
        }
    }

    @Test
    public void updateEmployee() throws Exception {
        Employee employee = getSomeEmloyee();

        EmployeeCardDto dto = service.createNewEmployee(getCreateDto(employee));
        employee.setAccountId(dto.getId());
        employee.setName("Petr");
        employee.setSurname("Lipov");

        EmployeeCardDto employeedto = service.changeEmployeeInfo(getUpdateDto(employee));
        Assertions.assertEquals(employee.getSurname(), employeedto.getSurname());
        Assertions.assertEquals(employee.getName(), employeedto.getName());
        Assertions.assertEquals(employee.getMiddleName(), employeedto.getMiddleName());
        Assertions.assertEquals(employee.getJobTitle(), employeedto.getJobTitle());
        Assertions.assertEquals(employee.getEmail(), employeedto.getEmail());
        Assertions.assertEquals(employee.getEmployeeStatus(), employeedto.getStatus());
    }

    @Test
    public void updateEmployeeByNullDto() {
        try {
            service.changeEmployeeInfo(null);
        } catch (Exception ex) {
            Assertions.assertEquals("Employee dto is null!", ex.getMessage());
        }
    }

    @Test
    public void updateEmployeeWithEmptyNewName() {
        try {
            Employee employee = getSomeEmloyee();
            EmployeeCardDto dto = service.createNewEmployee(getCreateDto(employee));
            employee.setAccountId(dto.getId());
            employee.setName(null);
            service.changeEmployeeInfo(getUpdateDto(employee));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty name for employee!", ex.getMessage());
        }
    }

    @Test
    public void updateEmployeeWithEmptyNewSurname() {
        try {
            Employee employee = getSomeEmloyee();
            EmployeeCardDto dto = service.createNewEmployee(getCreateDto(employee));
            employee.setAccountId(dto.getId());
            employee.setSurname(null);
            service.changeEmployeeInfo(getUpdateDto(employee));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty employee surname!", ex.getMessage());
        }
    }

    @Test
    public void updateEmployeeWithEmptyNewLogin() {
        try {
            Employee employee = getSomeEmloyee();
            EmployeeCardDto dto = service.createNewEmployee(getCreateDto(employee));
            employee.setAccountId(dto.getId());
            employee.setLogin(null);
            service.changeEmployeeInfo(getUpdateDto(employee));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty login!", ex.getMessage());
        }
    }

    @Test
    public void updateEmployeeWithEmptyNewPassword() {
        try {
            Employee employee = getSomeEmloyee();
            EmployeeCardDto dto = service.createNewEmployee(getCreateDto(employee));
            employee.setAccountId(dto.getId());
            employee.setPassword(null);
            service.changeEmployeeInfo(getUpdateDto(employee));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty password!", ex.getMessage());
        }
    }

    @Test
    public void deleteEmployee() throws Exception {
        Employee employee = getSomeEmloyee();
        EmployeeCardDto dto = service.createNewEmployee(getCreateDto(employee));
        employee.setAccountId(dto.getId());
        EmployeeCardDto deletedEmployee = service.deleteEmployee(getDeleteDto(employee));
        Assertions.assertEquals(employee.getSurname(), deletedEmployee.getSurname());
        Assertions.assertEquals(employee.getName(), deletedEmployee.getName());
        Assertions.assertEquals(employee.getMiddleName(), deletedEmployee.getMiddleName());
        Assertions.assertEquals(employee.getJobTitle(), deletedEmployee.getJobTitle());
        Assertions.assertEquals(employee.getEmail(), deletedEmployee.getEmail());
        Assertions.assertEquals(deletedEmployee.getStatus().toString(), EmployeeStatus.DELETED.toString());
    }

    @Test
    public void deleteEmployeeByFalseId() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setAccountId(Long.valueOf(99));
            EmployeeCardDto deletedEmployee = service.deleteEmployee(getDeleteDto(employee));
        } catch (Exception ex) {
            Assertions.assertEquals("Employee was not found!", ex.getMessage());
        }
    }

    @Test
    public void deleteDeletedEmployee() {
        try {
            Employee employee = getSomeEmloyee();
            EmployeeCardDto dto = service.createNewEmployee(getCreateDto(employee));
            employee.setEmployeeStatus(EmployeeStatus.DELETED);
            employee.setAccountId(dto.getId());
            EmployeeCardDto deletedEmployee = service.deleteEmployee(getDeleteDto(employee));
        } catch (Exception ex) {
            Assertions.assertEquals("Employee was deleted!", ex.getMessage());
        }
    }

    @Test
    public void getEmployeeCardById() throws Exception {
        Employee employee = getSomeEmloyee();
        EmployeeCardDto dto = service.createNewEmployee(getCreateDto(employee));
        employee.setAccountId(dto.getId());

        EmployeeCardDto employeedto = service.getEmployeeCardById(new GetByIdEmployeeDto(employee.getAccountId()));
        Assertions.assertEquals(employee.getSurname(), employeedto.getSurname());
        Assertions.assertEquals(employee.getName(), employeedto.getName());
        Assertions.assertEquals(employee.getMiddleName(), employeedto.getMiddleName());
        Assertions.assertEquals(employee.getJobTitle(), employeedto.getJobTitle());
        Assertions.assertEquals(employee.getEmail(), employeedto.getEmail());
        Assertions.assertEquals(employee.getEmployeeStatus(), employeedto.getStatus());
    }


    @Test
    public void getEmployeeCardByFalseId() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setAccountId(Long.valueOf("1"));

            EmployeeCardDto employeedto = service.getEmployeeCardById(new GetByIdEmployeeDto(employee.getAccountId()));
        } catch (Exception ex) {
            Assertions.assertEquals("Employee was not found!", ex.getMessage());
        }
    }

    @Test
    public void deleteByNullDto() {
        try {
            service.deleteEmployee(null);
        } catch (Exception ex) {
            Assertions.assertEquals("Employee dto is null!", ex.getMessage());
        }
    }

    @Test
    public void getEmployeeCardByPassword() throws Exception {
        Employee employee = getSomeEmloyee();
        employee.setLogin("!!");
        employee.setPassword("!!");
        EmployeeCardDto dto = service.createNewEmployee(getCreateDto(employee));
        EmployeeCardDto employeedto = service.getEmployeeByAccount(new GetEmployeeByLoginAndPassword(
                employee.getLogin(),
                employee.getPassword()
        ));
        Assertions.assertEquals(employee.getSurname(), employeedto.getSurname());
        Assertions.assertEquals(employee.getName(), employeedto.getName());
        Assertions.assertEquals(employee.getMiddleName(), employeedto.getMiddleName());
        Assertions.assertEquals(employee.getJobTitle(), employeedto.getJobTitle());
        Assertions.assertEquals(employee.getEmail(), employeedto.getEmail());
        Assertions.assertEquals(employee.getEmployeeStatus(), employeedto.getStatus());
    }

    @Test
    public void getEmployeeCardByFalsePassword() {
        try {
            Employee employee = getSomeEmloyee();
            GetEmployeeByLoginAndPassword dto = new GetEmployeeByLoginAndPassword();
            dto.setPassword("-----");
            dto.setLogin(employee.getLogin());
            EmployeeCardDto employeedto = service.getEmployeeByAccount(dto);
        } catch (Exception ex) {
            Assertions.assertEquals("Employee was not found!", ex.getMessage());
        }
    }

    @Test
    public void searchEmployeeByName() throws Exception {
        Employee employee = getSomeEmloyee();
        EmployeeCardDto dto = service.createNewEmployee(getCreateDto(employee));
        List<EmployeeCardDto> employeedto = service.searchEmployee(new SearchEmployeeDto(employee.getName()));
        Assertions.assertEquals(employee.getSurname(), employeedto.get(0).getSurname());
        Assertions.assertEquals(employee.getName(), employeedto.get(0).getName());
        Assertions.assertEquals(employee.getMiddleName(), employeedto.get(0).getMiddleName());
        Assertions.assertEquals(employee.getJobTitle(), employeedto.get(0).getJobTitle());
        Assertions.assertEquals(employee.getEmail(), employeedto.get(0).getEmail());
        Assertions.assertEquals(employee.getEmployeeStatus(), employeedto.get(0).getStatus());
    }

    private CreateEmployeeDto getCreateDto(Employee employee) {
        return new CreateEmployeeDto(
                employee.getSurname(),
                employee.getName(),
                employee.getMiddleName(),
                employee.getJobTitle(),
                employee.getEmail(),
                employee.getLogin(),
                employee.getPassword()
        );
    }


    private UpdateEmployeeDto getUpdateDto(Employee employee) {
        return new UpdateEmployeeDto(
                employee.getAccountId(),
                employee.getSurname(),
                employee.getName(),
                employee.getMiddleName(),
                employee.getJobTitle(),
                employee.getEmail(),
                employee.getLogin(),
                employee.getPassword()
        );
    }

    private DeleteEmployeeDto getDeleteDto(Employee employee) {
        return new DeleteEmployeeDto(employee.getAccountId());
    }

}

