package com.example.demo.service.impl;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@Log
@Service
public class EmployeeServiceImp implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public boolean createEmp(Employee emp) throws IllegalArgumentException {
        try {
            // Validate mandatory fields
            if (emp.getFirstname() == null || emp.getFirstname().trim().isEmpty()) {
                throw new IllegalArgumentException("First name cannot be null or empty");
            }

            if (emp.getLastname() == null || emp.getLastname().trim().isEmpty()) {
                throw new IllegalArgumentException("Last name cannot be null or empty");
            }

            // Validate field length
            if (emp.getFirstname().length() > 24 || emp.getLastname().length() > 24) {
                throw new IllegalArgumentException("First name must be less than 50 characters");
            }

            //execute save
            employeeRepository.save(emp);
            return true;

        } catch (IllegalArgumentException e)
        {
            log.info("Exception occured : " + e);
            throw e;
        }
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmpByID(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee " + id + " not found"));
    }


    @Override
    public void updateEmployee(Integer id, Employee emp) {
        employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee " + id + " not found"));
        emp.setEmpid(id);
        employeeRepository.save(emp);

    }

    @Override
    public boolean deleteEmployee(Integer id) throws ResponseStatusException {
        try{
            if(employeeRepository.findById(id).isPresent())
            {
                employeeRepository.deleteById(id);
                return true;
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee " + id + " not found");
            }
        }catch (ResponseStatusException e){
            log.info(e.toString());
            throw e;
        }
   }

}
