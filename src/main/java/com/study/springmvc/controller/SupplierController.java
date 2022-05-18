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

import com.study.springmvc.entity.Supplier;
import com.study.springmvc.repository.SupplierRepository;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@GetMapping("/")
	public String index(@ModelAttribute Supplier supplier , Model model) {
		model.addAttribute("_method","POST");
		model.addAttribute("suppliers" , supplierRepository.findAll());
		model.addAttribute("buttonName","新增");
		return "supplier";
	}
	
	@GetMapping("/{id}")
	public String get(@PathVariable("id") Long id , Model model) {
		model.addAttribute("_method","PUT");
		model.addAttribute("suppliers" , supplierRepository.findAll());
		model.addAttribute("supplier" , supplierRepository.findById(id));
		model.addAttribute("buttonName","修改");
		return "supplier";
	}
	
	@PostMapping("/")
	public String add(@Valid @ModelAttribute Supplier supplier,BindingResult result , Model model) {
		if(result.hasErrors()) {
			model.addAttribute("_method" , "POST");
			model.addAttribute("buttonName","新增");
			model.addAttribute("suppliers" , supplierRepository.findAll());
			return "supplier";
		}
		supplierRepository.save(supplier);
		return "redirect:./";
	}
	
	@PutMapping("/")
	public String update(@Valid @ModelAttribute Supplier supplier , BindingResult result , Model model) {
		if(result.hasErrors()) {
			model.addAttribute("_method" , "POST");
			model.addAttribute("buttonName","新增");
			model.addAttribute("suppliers" , supplierRepository.findAll());
			return "supplier";
		}
		supplierRepository.save(supplier);
		return "redirect:./";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		supplierRepository.deleteById(id);
		return "redirect:../";
	}
	
}
