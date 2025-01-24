
package com.crm.controller;

import com.crm.entity.Employee;
import com.crm.payload.EmployeeDto;
import com.crm.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    //http://localhost:8081/api/v1/employee/add
    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(
           @Valid @RequestBody EmployeeDto dto,
            BindingResult result
       ){
         if(result.hasErrors()){
             return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
         }


           EmployeeDto employeeDto = employeeService.addEmployee(dto);
           return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);



//        System.out.println(employee.getName());
//        System.out.println(employee.getEmailId());
//        System.out.println(employee.getMobile());

    }

    //http://localhost:8081/api/v1/employee?id=1
    @DeleteMapping
    public ResponseEntity deleteEmployee(
            @RequestParam Long id
    ){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("deleted",HttpStatus.OK);

    }


    //http://localhost:8081/api/v1/employee?id=1
    @PutMapping
    public ResponseEntity<EmployeeDto> UpdateEmployee(
            @RequestParam Long id,
            @RequestBody EmployeeDto dto


    ){
        EmployeeDto employeeDto = employeeService.updateEmployee(id, dto);
        return new ResponseEntity<>(employeeDto,HttpStatus.OK);

    }

    //http://localhost:8081/api/v1/employee?pageSize=3&pageNo=1&sortBy=email&sortdir=asc
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees(
            @RequestParam(value = "pageSize",required = false, defaultValue = "5")int pageSize,
            @RequestParam(value ="pageNo",required = false, defaultValue = "0")int pageNO,
            @RequestParam(value ="sortBy",required = false, defaultValue = "name")String sortBy,
            @RequestParam(value ="sortDir",required = false, defaultValue = "asc")String sortDir



    ){
        List<EmployeeDto> employeesDto = employeeService.getEmployee(pageSize,pageNO,sortBy,sortDir);
        return new ResponseEntity<>(employeesDto,HttpStatus.OK);

    }

    // http://localhost:8081/api/v1/employee/employeeId/1
    @GetMapping("/employeeId/{empId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(
            @PathVariable long empId


    ){
        EmployeeDto dto = employeeService.getEmployeeById(empId);
        return  new ResponseEntity<>(dto, HttpStatus.OK);

    }


}