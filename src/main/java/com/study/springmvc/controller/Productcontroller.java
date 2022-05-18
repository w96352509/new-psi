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

import com.study.springmvc.entity.Product;
import com.study.springmvc.repository.ProductRepository;

@Controller
@RequestMapping("/product")
public class Productcontroller {

	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping("/")
	public String index(@ModelAttribute Product product , Model model) {
		model.addAttribute("products" , productRepository.findAll());
		model.addAttribute("_method" , "POST");
		model.addAttribute("buttonName" , "新增");
		return "product";
	}
	
	@GetMapping("/{id}")
	public String get(@PathVariable("id") Long id , Model model) {
		model.addAttribute("product" , productRepository.findById(id));
		model.addAttribute("products" , productRepository.findAll());
		model.addAttribute("_method" , "PUT");
		model.addAttribute("buttonName" , "修改");
		return "product";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
	 productRepository.deleteById(id);
	 return "redirect:../";
	}
	
	@PostMapping("/")
	public String add(@Valid @ModelAttribute Product product , BindingResult result,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("_method","POST");
			model.addAttribute("products" , productRepository.findAll());
			model.addAttribute("buttonName" , "新增");
			return "product";
		}
		    productRepository.save(product);
		    return "redirect:./";
	}
	
	@PutMapping("/")
	public String update(@Valid @ModelAttribute Product product , BindingResult result,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("_method","PUT");
			model.addAttribute("products" , productRepository.findAll());
			model.addAttribute("buttonName" , "修改");
			return "product";
		}
		    productRepository.save(product);
		    return "redirect:./";
	}
	
}
