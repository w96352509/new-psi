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

import com.study.springmvc.entity.Department;
import com.study.springmvc.repository.DepartmentRepository;

@Controller
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@GetMapping("/")
	public String index(@ModelAttribute Department department ,Model model) {
		model.addAttribute("buttonName","新增");
		model.addAttribute("_method" , "POST");		
		model.addAttribute("departments",departmentRepository.findAll());
		return "department";
	}
	
	@GetMapping("/{id}")
	public String get(@PathVariable("id") Long id,Model model) {
		model.addAttribute("buttonName","修改");
		model.addAttribute("_method" , "PUT");	
		model.addAttribute("department" , departmentRepository.findById(id));
		model.addAttribute("departments",departmentRepository.findAll());
		return "department";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		departmentRepository.deleteById(id);
		return "redirect:../";
	}
	
	@PostMapping("/")
	public String add(@Valid @ModelAttribute Department department,BindingResult result ,Model model) {
		if (result.hasErrors()) {
			model.addAttribute("_method" , "POST");	
			model.addAttribute("departments",departmentRepository.findAll());
			return "department";
		}
		departmentRepository.save(department);
		return "redirect:./";
	}
	
	@PutMapping("/")
	public String update(@Valid @ModelAttribute Department department,BindingResult result ,Model model) {
		if (result.hasErrors()) {
			model.addAttribute("_method" , "PUT");	
			model.addAttribute("departments",departmentRepository.findAll());
			return "department";
		
		}
		departmentRepository.save(department);
		return "redirect:./";
	}
	
}
