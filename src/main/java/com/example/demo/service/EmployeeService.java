package com.example.demo.service;

import com.example.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    boolean createEmp(Employee emp);
    List<Employee> getEmployees();
    Employee getEmpByID(Integer id);
    void updateEmployee(Integer id, Employee emp);
    boolean deleteEmployee(Integer id);
}
