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

import com.study.springmvc.entity.Customer;
import com.study.springmvc.repository.CustomerRepository;


@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/")
	public String index(@ModelAttribute Customer customer , Model model) {
		model.addAttribute("_method" , "POST");
		model.addAttribute("buttonName" , "新增");
		model.addAttribute("customers" , customerRepository.findAll());
		return "customer";
	}
	
	@GetMapping("/{id}")
    public String get(Model model , @PathVariable("id") Long id) {
		model.addAttribute("_method" , "PUT");
		model.addAttribute("buttonName" , "修改");
		model.addAttribute("customer" , customerRepository.findById(id));
		model.addAttribute("customers" , customerRepository.findAll());
		return "customer";
    }
    	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		customerRepository.deleteById(id);
		return "redirect:../";
	}
	
	@PostMapping("/")
	public String add(@Valid @ModelAttribute Customer customer , BindingResult result , Model model) {
		if(result.hasErrors()) {
			model.addAttribute("_method" , "POST");
			model.addAttribute("buttonName" , "新增");
			model.addAttribute("customers" , customerRepository.findAll());
			return "customer";
		}
		   customerRepository.save(customer);
		return "redirect:./";
	}
	
	@PutMapping("/")
	public String update(@Valid @ModelAttribute Customer customer , BindingResult result , Model model) {
		if(result.hasErrors()) {
			model.addAttribute("_method" , "PUT");
			model.addAttribute("buttonName" , "修改");
			model.addAttribute("customers" , customerRepository.findAll());
			return "customer";
		}
		   customerRepository.save(customer);
		return "redirect:./";
	}
	
	
}
