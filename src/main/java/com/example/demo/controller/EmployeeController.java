package com.example.demo.controller;
import com.example.demo.constants.StringConstants;
import com.example.demo.service.EmployeeService;

import com.example.demo.entity.Employee;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee emp){
        try{
            if(employeeService.createEmp(emp)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.info(StringConstants.EXCEPTION_ALERT + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employeeList;
        try{
            employeeList = employeeService.getEmployees();
            return new ResponseEntity<>(employeeList,HttpStatus.OK);

        }catch (Exception e){
            log.info(StringConstants.EXCEPTION_ALERT + e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getEmployeeByID/{id}")
    public ResponseEntity<Employee> getEmployeeByID(@PathVariable Integer id){

        try{
            return new ResponseEntity<>(employeeService.getEmpByID(id),HttpStatus.OK);

        }catch(Exception e){
            log.info(StringConstants.EXCEPTION_ALERT + e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee emp){
        try{
            employeeService.updateEmployee(id, emp);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.info(StringConstants.EXCEPTION_ALERT + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable Integer id){
        try{
            if (employeeService.deleteEmployee(id)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
           else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            log.info(StringConstants.EXCEPTION_ALERT + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
     }

}
