package com.study.springmvc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springmvc.entity.Employee;
import com.study.springmvc.repository.DepartmentRepository;
import com.study.springmvc.repository.EmployeeRepository;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@GetMapping("/")
	public String index(@ModelAttribute Employee employee , Model model) {
		
		model.addAttribute("departments" , departmentRepository.findAll());
		model.addAttribute("employees" , employeeRepository.findAll());
		model.addAttribute("_method" , "POST");
		model.addAttribute("buttonName" , "新增");
		return "employee";
	}
	
	@GetMapping("/{id}")
	public String get(@PathVariable("id") Long id, Model model) {
		model.addAttribute("_method", "PUT");
		model.addAttribute("employee", employeeRepository.getById(id));
		model.addAttribute("employees", employeeRepository.findAll());
		model.addAttribute("departments", departmentRepository.findAll());
		model.addAttribute("buttonName" , "修改");
		return "employee";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id , Model model) {
		
		employeeRepository.deleteById(id);
		
		return "redirect:../";
	}
	
	@PostMapping("/")
	public String add(@Valid @ModelAttribute Employee employee , BindingResult result , Model model) {
		if(result.hasErrors()) {
			model.addAttribute("departments" , departmentRepository.findAll());
			model.addAttribute("employees" , employeeRepository.findAll());
			model.addAttribute("_method" , "POST");
			model.addAttribute("buttonName" , "新增");
			return "employee";
		}
		   employeeRepository.save(employee);
		   return "redirect:./";
	}
	
	@PutMapping("/")
	public String update(@Valid @ModelAttribute Employee employee , BindingResult result , Model model) {
		if(result.hasErrors()) {
			model.addAttribute("departments" , departmentRepository.findAll());
			model.addAttribute("employees" , employeeRepository.findAll());
			model.addAttribute("_method" , "PUT");
			model.addAttribute("buttonName" , "修改");
			return "employee";
		}
		   employeeRepository.save(employee);
		   return "redirect:./";
	}
	
}
