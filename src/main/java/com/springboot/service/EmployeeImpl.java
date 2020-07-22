package com.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
@Component
public class EmployeeImpl {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
	public void addEmployee(Employee emp) {
		employeeRepository.save(emp);
	}
	public Optional<Employee> getEmpById(long id) {
		return employeeRepository.findById(id);
	}
	
}
