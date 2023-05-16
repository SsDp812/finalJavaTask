package org.digital.employee_dao.Impls;

import org.digital.employee_dao.EmployeeDao;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class EmployeeDataStorageImpl implements EmployeeDao {

    private List<Employee> storage;

    public EmployeeDataStorageImpl() {

        storage = new ArrayList<>();

        try(FileReader reader = new FileReader("employeeDB.txt")){
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()){
                Employee employee = new Employee(
                        scanner.nextLine(),
                        scanner.nextLine(),
                        scanner.nextLine(),
                        scanner.nextLine(),
                        scanner.nextLine(),
                        EmployeeStatus.valueOf(scanner.nextLine()),
                        scanner.nextLine(),
                        scanner.nextLine(),
                        Long.parseLong(scanner.nextLine())
                );
                storage.add(employee);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void saveStorageInFile() throws IOException {
        try(FileWriter writer = new FileWriter("employeeDB.txt",false)){
            for (var employee : storage){
                writer.write(employee.getSurname());
                writer.write(employee.getName());
                writer.write(employee.getMiddleName());
                writer.write(employee.getJobTitle());
                writer.write(employee.getEmail());
                writer.write(employee.getEmployeeStatus().toString());
                writer.write(employee.getLogin());
                writer.write(employee.getPassword());
                writer.write(employee.getAccountId().toString());
            }
        }
    }

    @Override
    public void create(Employee employee) throws IOException {
       storage.add(employee);
       saveStorageInFile();
    }

    @Override
    public void update(Employee employee) throws IOException {
        for(var em : storage){
            if(Objects.equals(em.getAccountId(),employee.getAccountId())){
                em.setSurname(employee.getSurname());
                em.setName(employee.getName());
                em.setMiddleName(employee.getMiddleName());
                em.setJobTitle(employee.getJobTitle());
                em.setEmail(employee.getEmail());
                em.setEmployeeStatus(employee.getEmployeeStatus());
                em.setLogin(employee.getLogin());
                em.setPassword(employee.getPassword());
            }
        }
        saveStorageInFile();
    }

    @Override
    public Employee getById(Long id) throws Exception {
        for(var em : storage){
            if(Objects.equals(em.getAccountId(),id)){
                return em;
            }
        }
        throw new Exception("User not found!");
    }

    @Override
    public List<Employee> getAll() throws FileNotFoundException {
        return storage;
    }

    @Override
    public void deleteById(Long id) throws IOException {
        for(var em : storage){
            if(Objects.equals(em.getAccountId(),id)){
                storage.remove(em);
                break;
            }
        }
        saveStorageInFile();
    }
}
