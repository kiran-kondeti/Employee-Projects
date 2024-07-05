package com.example.curd.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.curd.application.model.Employee;
import com.example.curd.application.model.EmployeeService;

@RestController
@RequestMapping("/empDetails")
public class EmployeeController {

	@Autowired
	private EmployeeService empRepo;

	@PostMapping("/addEmp")
	public List<Employee> storeEmployeeDetails(@RequestBody List<Employee> emp) {
		return empRepo.storeEmployeeDetails(emp);
	}

	@GetMapping("/getAllemps")
	public List<Employee> getAllemps() {
			return empRepo.getAllemps();
	}
}
