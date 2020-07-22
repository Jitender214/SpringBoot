package com.springboot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.model.Employee;
import com.springboot.model.UpdateEmployeeDetails;
import com.springboot.repository.EmployeeRepository;
import com.microsoft.schemas.office.visio.x2012.main.CellType;
import com.springboot.exception.GlobalExceptionHandler;
import com.springboot.exception.ResourceNotFoundException;;


@RestController
public class RestAPIController {
	
	private static final Logger log = LogManager.getLogger(RestAPIController.class);
	@Autowired
	EmployeeRepository rep;
	
	 @GetMapping("/home")
	 public String home() {
		 System.out.println("in home method");
		 log.info("in home method");
		return "Welcome..........."; 
	 }
	 @GetMapping("/AllEmployees")
	 public List<Employee> getAllEmployees(){
		System.out.println("in AllEmp method");
		log.info("in get all employees method");
		return rep.findAll();
	 }
	 @PostMapping("/AddEmployee")
		public Employee newEmployee(@RequestBody Employee emp) {
		 	
			System.out.println("in AddEmp method");
			System.out.println(emp.getName()+emp.getAddress()+emp.getPhonenumber());
			log.info("in add employee method");
			return rep.save(emp);
	}
	 
	@GetMapping("/Employees/{id}") 
	public Optional<Employee> getEmployeeById(@PathVariable(value = "id") long id) {
		return rep.findById(id);
	}
	@DeleteMapping("/Employees/{id}")
	public String deleteEmpById(@PathVariable(value = "id") long id ) {
		rep.deleteById(id);
		return id+" number Employee got deleted";
	}
	@PutMapping("/UpdateEmp/{id}")
	public Employee updateEmployee(@PathVariable(value = "id") long id,@Validated @RequestBody UpdateEmployeeDetails EmployeeDetails){
		Employee existingdetails = rep.getOne(id); 
		existingdetails.setName(EmployeeDetails.getName());
		existingdetails.setAddress(EmployeeDetails.getAddress());
		existingdetails.setPhonenumber(EmployeeDetails.getPhonenumber());
		log.info("in update employee method using emp_id");
		return rep.save(existingdetails);
	}
	
	@PostMapping("/ImportFile")
	public List<Employee> ImportFile(@RequestParam("file") MultipartFile files) throws IOException {
		HttpStatus status = HttpStatus.OK;
		List<Employee> employeelist = new ArrayList<Employee>();
		XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
		 XSSFSheet worksheet = workbook.getSheetAt(0);
		 log.info("in upload file method");
		 for (int index=1;index < worksheet.getPhysicalNumberOfRows();index++) {
			 Employee emp = new Employee();
			 if (index > 0) {
			 System.out.println(worksheet.getPhysicalNumberOfRows());
			 XSSFRow row = worksheet.getRow(index);
			 System.out.println(row);
			 String name =  row.getCell(0).toString(); 
			 String add = row.getCell(1).toString();
			 //String number = row.getCell(2).getStringCellValue();
			 double number = row.getCell(2).getNumericCellValue();
			 System.out.println(number);
			 long phnumber = (new Double(number)).longValue();
			 System.out.println(phnumber);
			 System.out.println("Nmae +++"+name + "++++"+add+ "++++"+number);
			 log.info("in for loop "+name+"...."+add+"......."+number);
			 emp.setName(name);
			 emp.setAddress(add);
			 emp.setPhonenumber(phnumber);
			 employeelist.add(emp);
			 workbook.close();
			 }
		 }
		 
		return rep.saveAll(employeelist);
		
		 
	}

}