package com.cognixia.jump.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Student;
import com.cognixia.jump.repository.StudentRepository;

@RequestMapping("/api")
@RestController
public class StudentController {
	
	@Autowired
	StudentRepository service;
	
	@GetMapping("/students")
	public List<Student> getAllStudents() {
		
		return service.findAll();
	}
	
	@GetMapping("/students/{id}")
	public Student getStudent(@PathVariable long id) {
		
		Optional<Student> studentOpt = service.findById(id);
		
		if(studentOpt.isPresent()) {
			return studentOpt.get();
		}
		
		return new Student();
	}
	
	@PostMapping("/add/student")
	public void addStudent(@RequestBody Student newStudent) {
		
		newStudent.setId(-1L);
		
		Student added = service.save(newStudent); // save() does an insert or update (depends on id passed)
		
		System.out.println("Added: " + added);
		
	}
	
	@PutMapping("/update/student")
	public @ResponseBody String updateStudent(@RequestBody Student updateStudent) {
		
		// check if student exists, then update them
		
		Optional<Student> found = service.findById(updateStudent.getId());
		
		if(found.isPresent()) {
			service.save(updateStudent);
			return "Saved: " + updateStudent.toString();
		}
		else {
			return "Could not update student, the id = " + updateStudent.getId() + " doesn't exist";
		}
		
	}
	
	@PatchMapping("/update/student/department")
	public @ResponseBody String updateDepartment(@RequestBody Map<String, String> deptInfo) {
		
		long id = Long.parseLong( deptInfo.get("id") );
		String department = deptInfo.get("department");
		
		Optional<Student> found = service.findById(id);
		
		if(found.isPresent()) {
			
			Student toUpdate = found.get();
			
			String old = toUpdate.getDepartment();
			
			toUpdate.setDepartment(department);
			
			service.save(toUpdate);
			
			return "Old Department: " + old + "\nNew Department: " + department;
		}
		else {
			return "Could not update department, student with id = " + id + " doesn't exist";
		}
		
	}
	
	
	@DeleteMapping("/delete/student/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable long id) {
		
		Optional<Student> found = service.findById(id);
		
		if(found.isPresent()) {
			
			service.deleteById(id);
			
			return ResponseEntity.status(200).body("Deleted student with id = " + id);	
		}
		else {
			return ResponseEntity.status(400)
					.header("student id", id + "")
					.body("Student with id = " + id + " not found");
		}
			
	}

}










