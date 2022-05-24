package com.study.springmvc.controller;

import java.util.Optional;

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

import com.study.springmvc.entity.Purchase;
import com.study.springmvc.entity.PurchaseItem;
import com.study.springmvc.entity.Supplier;
import com.study.springmvc.repository.EmployeeRepository;
import com.study.springmvc.repository.ProductRepository;
import com.study.springmvc.repository.PurchaseItemRepository;
import com.study.springmvc.repository.PurchaseRepository;
import com.study.springmvc.repository.SupplierRepository;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {
	
	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private PurchaseItemRepository purchaseItemRepository;
	
	@GetMapping("/")
	public String index(@ModelAttribute Purchase purchase , Model model) {
		model.addAttribute("_method", "POST");
		model.addAttribute("purchases" , purchaseRepository.findAll());
		model.addAttribute("suppliers" , supplierRepository.findAll());
		model.addAttribute("employees" , employeeRepository.findAll());
		return "purchase";
	}
	
	@GetMapping("/{pid}")
	public String get(@PathVariable("pid") Long pid , Model model) {
		Purchase purchase = purchaseRepository.findById(pid).get();
		model.addAttribute("_method", "PUT");
		model.addAttribute("purchase" , purchase );
		// 得到時只顯示得到供應商的table
		model.addAttribute("purchases" , purchaseRepository.findBySupplier(purchase.getSupplier()));
		model.addAttribute("suppliers" , supplierRepository.findAll());
		model.addAttribute("employees" , employeeRepository.findAll());
		return "purchase";
	}
	
	@GetMapping("/delete/{pid}")
	public String delete(@PathVariable("pid") Long pid) {
		purchaseRepository.deleteById(pid);
		return "redirect:../";
	}
	
	@PostMapping("/")
	public String add(@Valid @ModelAttribute Purchase purchase, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("_method", "POST");
			model.addAttribute("purchases", purchaseRepository.findAll());
			model.addAttribute("suppliers", supplierRepository.findAll());
			model.addAttribute("employees", employeeRepository.findAll());
			return "purchase";
		}
		purchaseRepository.save(purchase);
		return "redirect:./";
	}
	
	@PutMapping("/")
	public String update(@Valid @ModelAttribute Purchase purchase, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("_method", "PUT");
			model.addAttribute("purchases", purchaseRepository.findAll());
			model.addAttribute("suppliers", supplierRepository.findAll());
			model.addAttribute("employees", employeeRepository.findAll());
			return "purchase";
		}
		purchaseRepository.save(purchase);
		return "redirect:./";
	}
	// ----------------------
	
	@GetMapping("/{pid}/item")
	public String viewItem(@PathVariable("pid") Long pid , Model model , @ModelAttribute PurchaseItem purchaseItem) {
		model.addAttribute("_method", "POST");
		model.addAttribute("purchase" , purchaseRepository.findById(pid).get());
		model.addAttribute("products" , productRepository.findAll());
		model.addAttribute("employees" , employeeRepository.findAll());
		return "purchaseItem";
	}
	
	@GetMapping("/{pid}/item/{iid}")
	public String viewItem(@PathVariable("pid") Long pid , @PathVariable("iid") Long iid , Model model) {
		model.addAttribute("_method", "PUT");
		model.addAttribute("purchaseItem",purchaseItemRepository.findById(iid).get());
		model.addAttribute("purchase" , purchaseRepository.findById(pid).get());
		model.addAttribute("products" , productRepository.findAll());
		model.addAttribute("employees" , employeeRepository.findAll());
		return "purchaseItem";
	}
	
	@GetMapping("/{pid}/item/delete/{iid}")
	public String deleteItem(@PathVariable("iid") long iid) {
		purchaseItemRepository.deleteById(iid);
		return "redirect:../";
	}
	
	@PostMapping("/{pid}/item")
	public String addItem(PurchaseItem purchaseItem)  {
		purchaseItemRepository.save(purchaseItem);
		return "redirect:./item";
	}
	
	@PutMapping("/{pid}/item")
	public String updateItem(PurchaseItem purchaseItem)  {
		purchaseItemRepository.save(purchaseItem);
		return "redirect:./item";
	}
}
