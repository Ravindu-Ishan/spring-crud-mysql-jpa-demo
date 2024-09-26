package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.impl.EmployeeServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/*
    Mockito is used to inject mock data onto entity
 */

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImp empService;

    @Test
    void createEmp() {

        //Mock data
        Employee testData = new Employee(1, "TestFirstName", "TestLastName", 50000.0);

        //behaviour
        when(employeeRepository.save(testData)).thenReturn(testData);

        //method call and result store
        boolean result = empService.createEmp(testData);

        //verify
        Mockito.verify(employeeRepository, Mockito.times(1)).save(testData);
        assertThat(result).isTrue();

    }
    @Test
    void createEmployeesFirstNameNotNull(){

        //Mock data
        Employee testData = new Employee(1, null, "TestLastName", 50000.0);

        // Check if IllegalArgumentException is thrown
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
           empService.createEmp(testData);  // Call the method that should throw the exception
        });

        // Assert the exception message
        assertEquals("First name cannot be null or empty", exception.getMessage());

    }
    @Test
    void createEmployeesLastNameNotNull(){

        //Mock data
        Employee testData = new Employee(1, "Firstname", null, 50000.0);

        // Check if IllegalArgumentException is thrown
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            empService.createEmp(testData);  // Call the method that should throw the exception
        });

        // Assert the exception message
        assertEquals("Last name cannot be null or empty", exception.getMessage());

    }

    @Test
    void createEmployeesFieldLength(){

        //Mock data
        Employee testData = new Employee(1, "Firstname", "ThisIsLongerThan24Characters", 50000.0);

        // Check if IllegalArgumentException is thrown
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            empService.createEmp(testData);  // Call the method that should throw the exception
        });

        // Assert the exception message
        assertEquals("First name must be less than 50 characters", exception.getMessage());

    }

    @Test
    void getEmployees() {
        //Mock data
        List<Employee> testData = List.of(
                new Employee(1, "TestFirstName", "TestLastName", 50000.0),
                new Employee(2, "TestFirstName", "TestLastName", 50000.0)
        );

        //behaviour
        when(employeeRepository.findAll()).thenReturn(testData);

        //method call and result store
        List<Employee> result =  new ArrayList<>(empService.getEmployees());

        //assertions
        assertThat(result).isEqualTo(testData).hasSize(2);
    }

    @Test
    void getEmpByID() {

        //Mock data
        List<Employee> testData = Arrays.asList(
                new Employee(1, "firstname1", "TestLastName1", 50000.0),
                new Employee(2, "firstname2", "TestLastName2", 50000.0)
        );

        //behaviour
        when(employeeRepository.findById(2)).thenReturn(Optional.of(testData.get(1)));

        //method call and result store
        Employee result =  empService.getEmpByID(2);

        //assertions
        assertThat(result).isEqualTo(testData.get(1));
    }

    @Test
    void updateEmployee() {

        // Mock data
        Employee empOriginal = new Employee(1, "FirstName", "LastName", 0);
        Employee empUpdate = new Employee(1, "ChangeName", "ChangeLastName", 50000 );

        //behaviour
        when(employeeRepository.findById(1)).thenReturn(Optional.of(empOriginal));
        when(employeeRepository.save(empUpdate)).thenReturn(empUpdate);

        //method call
        empService.updateEmployee(1, empUpdate);

        //verify
        Mockito.verify(employeeRepository, Mockito.times(1)).save(empUpdate);

    }

    @Test
    void deleteEmployee() {
        // Mock data
        Employee employee = new Employee(1, "FirstName", "LastName", 0);

        //behaviour
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        //result
        boolean result = empService.deleteEmployee(1);

        /*
            Return type of deleteById():
            The deleteById() method of Spring Data JPA's CrudRepository doesn't return a value.
            It performs the delete operation without returning anything. You don't need to mock its return.
         */

        //assert
        assertThat(result).isTrue();


    }

    @Test
    void negativeDeleteEmployee() {
        //test id
        int id = 5;

        // Mock data
        Employee employee = new Employee(1, "FirstName", "LastName", 0);

        //behaviour
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

               // Assert if IllegalArgumentException is thrown
      assertThrows(ResponseStatusException.class, () -> {
            empService.deleteEmployee(id);  // Call the method that should throw the exception
        });

    }
}