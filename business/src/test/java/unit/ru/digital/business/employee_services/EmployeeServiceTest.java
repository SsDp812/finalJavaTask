package unit.ru.digital.business.employee_services;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.digital.business.employee_services.EmployeeMapper;
import ru.digital.business.employee_services.Impls.EmployeeServiceImpl;
import ru.digital.commons.enity_statuses.EmployeeStatus;
import ru.digital.dao.employee_dao.EmployeeRepository;
import ru.digital.dto.employee_dto.request_employee_dto.*;
import ru.digital.dto.employee_dto.response_employee_dto.EmployeeCardDto;
import ru.digital.models.employee_model.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void createNewEmployee() throws Exception {
        Employee employee = getSomeEmloyee();
        EmployeeCardDto card = EmployeeMapper.getEmployeeDtoCard(employee);
        Employee employee2 = getSomeEmloyee();
        employee2.setAccountId(Long.parseLong("1"));
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee2);
        Mockito.when(passwordEncoder.encode(employee.getPassword())).thenReturn(employee.getPassword());
        EmployeeCardDto employeedto = employeeService.createNewEmployee(getCreateDto(employee));
        Assertions.assertEquals(employee.getSurname(), employeedto.getSurname());
        Assertions.assertEquals(employee.getName(), employeedto.getName());
        Assertions.assertEquals(employee.getMiddleName(), employeedto.getMiddleName());
        Assertions.assertEquals(employee.getJobTitle(), employeedto.getJobTitle());
        Assertions.assertEquals(employee.getEmail(), employeedto.getEmail());
        Assertions.assertEquals(employee.getEmployeeStatus(), employeedto.getStatus());
    }

    @Test()
    public void createNewEmployeeByNullDto() {
        try {
            employeeService.createNewEmployee(null);
        } catch (Exception ex) {
            Assertions.assertEquals("Employee dto is null!", ex.getMessage());
        }
    }

    @Test
    public void createNewEmployeeWithEmptyName() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setName(null);
            EmployeeCardDto card = EmployeeMapper.getEmployeeDtoCard(employee);
            Employee employee2 = getSomeEmloyee();
            employee2.setAccountId(Long.parseLong("1"));
            EmployeeCardDto employeedto = employeeService.createNewEmployee(getCreateDto(employee));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty name for employee!", ex.getMessage());
        }
    }

    @Test
    public void createNewEmployeeWithEmptySurname() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setSurname(null);
            EmployeeCardDto card = EmployeeMapper.getEmployeeDtoCard(employee);
            Employee employee2 = getSomeEmloyee();
            employee2.setAccountId(Long.parseLong("1"));
            EmployeeCardDto employeedto = employeeService.createNewEmployee(getCreateDto(employee));
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
            Employee employee2 = getSomeEmloyee();
            employee2.setAccountId(Long.parseLong("1"));
            EmployeeCardDto employeedto = employeeService.createNewEmployee(getCreateDto(employee));
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
            Employee employee2 = getSomeEmloyee();
            employee2.setAccountId(Long.parseLong("1"));
            EmployeeCardDto employeedto = employeeService.createNewEmployee(getCreateDto(employee));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty password!", ex.getMessage());
        }
    }

    @Test
    public void updateEmployee() throws Exception {
        Employee employee = getSomeEmloyee();
        employee.setAccountId(Long.valueOf(1));

        Employee oldEmoloyee = getSomeEmloyee();
        oldEmoloyee.setAccountId(Long.valueOf(1));
        oldEmoloyee.setName("Petr");
        oldEmoloyee.setSurname("Lipov");

        Mockito.when(employeeRepository.findById(oldEmoloyee.getAccountId())).thenReturn(Optional.of(oldEmoloyee));
        Mockito.when(passwordEncoder.encode(employee.getPassword())).thenReturn(employee.getPassword());
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        EmployeeCardDto employeedto = employeeService.changeEmployeeInfo(getUpdateDto(employee));
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
            employeeService.changeEmployeeInfo(null);
        } catch (Exception ex) {
            Assertions.assertEquals("Employee dto is null!", ex.getMessage());
        }
    }

    @Test
    public void updateEmployeeWithEmptyNewName() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setAccountId(Long.valueOf(1));
            Mockito.when(employeeRepository.findById(employee.getAccountId())).thenReturn(Optional.of(employee));
            Employee employeeForUpdate = getSomeEmloyee();
            employeeForUpdate.setAccountId(Long.valueOf(1));
            employeeForUpdate.setName(null);
            employeeService.changeEmployeeInfo(getUpdateDto(employeeForUpdate));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty name for employee!", ex.getMessage());
        }
    }

    @Test
    public void updateEmployeeWithEmptyNewSurname() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setAccountId(Long.valueOf(1));
            Mockito.when(employeeRepository.findById(employee.getAccountId())).thenReturn(Optional.of(employee));
            Employee employeeForUpdate = getSomeEmloyee();
            employeeForUpdate.setAccountId(Long.valueOf(1));
            employeeForUpdate.setSurname(null);
            employeeService.changeEmployeeInfo(getUpdateDto(employeeForUpdate));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty employee surname!", ex.getMessage());
        }
    }

    @Test
    public void updateEmployeeWithEmptyNewLogin() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setAccountId(Long.valueOf(1));
            Mockito.when(employeeRepository.findById(employee.getAccountId())).thenReturn(Optional.of(employee));
            Employee employeeForUpdate = getSomeEmloyee();
            employeeForUpdate.setAccountId(Long.valueOf(1));
            employeeForUpdate.setLogin(null);
            employeeService.changeEmployeeInfo(getUpdateDto(employeeForUpdate));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty login!", ex.getMessage());
        }
    }

    @Test
    public void updateEmployeeWithEmptyNewPassword() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setAccountId(Long.valueOf(1));
            Mockito.when(employeeRepository.findById(employee.getAccountId())).thenReturn(Optional.of(employee));
            Employee employeeForUpdate = getSomeEmloyee();
            employeeForUpdate.setAccountId(Long.valueOf(1));
            employeeForUpdate.setPassword(null);
            employeeService.changeEmployeeInfo(getUpdateDto(employeeForUpdate));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty password!", ex.getMessage());
        }
    }

    @Test
    public void deleteEmployee() throws Exception {
        Employee employee = getSomeEmloyee();
        employee.setAccountId(Long.valueOf(1));
        Mockito.when(employeeRepository.findById(employee.getAccountId())).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        EmployeeCardDto deletedEmployee = employeeService.deleteEmployee(getDeleteDto(employee));
        Assertions.assertEquals(employee.getSurname(), deletedEmployee.getSurname());
        Assertions.assertEquals(employee.getName(), deletedEmployee.getName());
        Assertions.assertEquals(employee.getMiddleName(), deletedEmployee.getMiddleName());
        Assertions.assertEquals(employee.getJobTitle(), deletedEmployee.getJobTitle());
        Assertions.assertEquals(employee.getEmail(), deletedEmployee.getEmail());
        Assertions.assertEquals(employee.getEmployeeStatus(), EmployeeStatus.DELETED);
    }

    @Test
    public void deleteEmployeeByFalseId() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setAccountId(Long.valueOf(1));
            Mockito.when(employeeRepository.findById(employee.getAccountId())).thenReturn(Optional.ofNullable(null));
            EmployeeCardDto deletedEmployee = employeeService.deleteEmployee(getDeleteDto(employee));
            Assertions.assertEquals(employee.getSurname(), deletedEmployee.getSurname());
            Assertions.assertEquals(employee.getName(), deletedEmployee.getName());
            Assertions.assertEquals(employee.getMiddleName(), deletedEmployee.getMiddleName());
            Assertions.assertEquals(employee.getJobTitle(), deletedEmployee.getJobTitle());
            Assertions.assertEquals(employee.getEmail(), deletedEmployee.getEmail());
            Assertions.assertEquals(employee.getEmployeeStatus(), EmployeeStatus.DELETED);
        } catch (Exception ex) {
            Assertions.assertEquals("Employee was not found!", ex.getMessage());
        }
    }

    @Test
    public void deleteDeletedEmployee() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setAccountId(Long.valueOf(1));
            employee.setEmployeeStatus(EmployeeStatus.DELETED);
            Mockito.when(employeeRepository.findById(employee.getAccountId())).thenReturn(Optional.of(employee));
            EmployeeCardDto deletedEmployee = employeeService.deleteEmployee(getDeleteDto(employee));
            Assertions.assertEquals(employee.getSurname(), deletedEmployee.getSurname());
            Assertions.assertEquals(employee.getName(), deletedEmployee.getName());
            Assertions.assertEquals(employee.getMiddleName(), deletedEmployee.getMiddleName());
            Assertions.assertEquals(employee.getJobTitle(), deletedEmployee.getJobTitle());
            Assertions.assertEquals(employee.getEmail(), deletedEmployee.getEmail());
            Assertions.assertEquals(employee.getEmployeeStatus(), EmployeeStatus.DELETED);
        } catch (Exception ex) {
            Assertions.assertEquals("Employee was deleted!", ex.getMessage());
        }
    }

    @Test
    public void getEmployeeCardById() throws Exception {
        Employee employee = getSomeEmloyee();
        employee.setAccountId(Long.valueOf("1"));

        Mockito.when(employeeRepository.findById(employee.getAccountId())).thenReturn(Optional.of(employee));

        EmployeeCardDto employeedto = employeeService.getEmployeeCardById(Long.valueOf("1"));
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

            Mockito.when(employeeRepository.findById(employee.getAccountId())).thenReturn(Optional.ofNullable(null));

            EmployeeCardDto employeedto = employeeService.getEmployeeCardById(Long.valueOf("1"));
        } catch (Exception ex) {
            Assertions.assertEquals("Employee was not found!", ex.getMessage());
        }
    }

    @Test
    public void deleteByNullDto() {
        try {
            employeeService.deleteEmployee(null);
        } catch (Exception ex) {
            Assertions.assertEquals("Employee dto is null!", ex.getMessage());
        }
    }

    @Test
    public void getEmployeeCardByPassword() throws Exception {
        Employee employee = getSomeEmloyee();
        Mockito.when(employeeRepository.findByLogin(employee.getLogin())).thenReturn(Optional.of(employee));
        Mockito.when(passwordEncoder.matches(any(), any())).thenReturn(true);
        GetEmployeeByLoginAndPassword dto = new GetEmployeeByLoginAndPassword();
        dto.setPassword(employee.getPassword());
        dto.setLogin(employee.getLogin());
        EmployeeCardDto employeedto = employeeService.getEmployeeByAccount(dto);
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
            Mockito.when(employeeRepository.findByLogin(employee.getLogin())).thenReturn(Optional.of(employee));
            Mockito.when(passwordEncoder.matches(any(), any())).thenReturn(false);
            GetEmployeeByLoginAndPassword dto = new GetEmployeeByLoginAndPassword();
            dto.setPassword(employee.getPassword());
            dto.setLogin(employee.getLogin());
            EmployeeCardDto employeedto = employeeService.getEmployeeByAccount(dto);
            Assertions.assertEquals(employee.getSurname(), employeedto.getSurname());
            Assertions.assertEquals(employee.getName(), employeedto.getName());
            Assertions.assertEquals(employee.getMiddleName(), employeedto.getMiddleName());
            Assertions.assertEquals(employee.getJobTitle(), employeedto.getJobTitle());
            Assertions.assertEquals(employee.getEmail(), employeedto.getEmail());
            Assertions.assertEquals(employee.getEmployeeStatus(), employeedto.getStatus());
        } catch (Exception ex) {
            Assertions.assertEquals("Employee was not found!", ex.getMessage());
        }
    }

    @Test
    public void searchEmployeeByName() throws Exception {
        Employee employee = getSomeEmloyee();
        Mockito.when(employeeRepository.findAll((Specification<Employee>) any())).thenReturn(Arrays.asList(employee));
        List<EmployeeCardDto> employeedto = employeeService.searchEmployee(new SearchEmployeeDto(employee.getName()));
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

    private Employee getSomeEmloyee() {
        Employee employee = new Employee(
                null,
                "Ivanov",
                "Ivan",
                "Ivanovich",
                "IT",
                "login",
                "password",
                "email",
                EmployeeStatus.ACTIVE
        );
        return employee;
    }
}
