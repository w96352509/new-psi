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
import org.springframework.web.bind.annotation.RequestParam;

import com.study.springmvc.entity.Customer;
import com.study.springmvc.entity.Order;
import com.study.springmvc.entity.OrderItem;
import com.study.springmvc.repository.CustomerRepository;
import com.study.springmvc.repository.EmployeeRepository;
import com.study.springmvc.repository.OrderItemRepository;
import com.study.springmvc.repository.OrderRepository;
import com.study.springmvc.repository.ProductRepository;
import com.study.springmvc.validator.InventoryValidator;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private InventoryValidator InventoryValidator;
	
	@GetMapping("/")
	public String index(@ModelAttribute Order order , Model model , @RequestParam(name = "customer_id" , required = false) Long customer_id) {
		if(customer_id == null) {
			model.addAttribute("orders", orderRepository.findAll());
		} else {
			Customer customer = customerRepository.findById(customer_id).get();
			order.setCustomer(customer);
			model.addAttribute("orders", orderRepository.findByCustomer(customer));
		}
		model.addAttribute("_method" , "POST");
		model.addAttribute("customers" , customerRepository.findAll());
		model.addAttribute("employees" , employeeRepository.findAll());
		return "order";
	}
	
	@GetMapping("/{id}")
	public String get(@PathVariable("id") Long id , Model model) {
		model.addAttribute("order", orderRepository.findById(id).get());
		model.addAttribute("orders", orderRepository.findAll());
		model.addAttribute("_method" , "PUT");
		model.addAttribute("customers" , customerRepository.findAll());
		model.addAttribute("employees" , employeeRepository.findAll());
		return "order";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		orderRepository.deleteById(id);
		return"redirect:../";
	}
	
	@PostMapping("/")
	public String add(@Valid @ModelAttribute Order order , BindingResult result ,Model model) {
		if (result.hasErrors()) {
			model.addAttribute("orders", orderRepository.findAll());
			model.addAttribute("_method" , "POST");
			model.addAttribute("customers" , customerRepository.findAll());
			model.addAttribute("employees" , employeeRepository.findAll());
			System.out.println("不可空白");
			return "order";
		}
		orderRepository.save(order);
		return "redirect:./";
	}
	
	@PutMapping("/")
	public String update(@Valid @ModelAttribute Order order , BindingResult result , Model model) {
		if (result.hasErrors()) {
			model.addAttribute("_method" ,"PUT");
			model.addAttribute("orders" , orderRepository.findAll());
			model.addAttribute("customers" , customerRepository.findAll());
			model.addAttribute("employees" , employeeRepository.findAll());
		}
		  orderRepository.save(order);
		return "redirect:./";
	}
	/*
	----------------------------------------------------------------------
	 * 訂單細目
	 * --------------------------------------------------------------------
	 * GET  -> /{pid}/item              -> viewItem
	 * GET  -> /{pid}/item/{iid}        -> getItem
	 * GET  -> /{pid}/item/delete/{iid} -> deleteItem
	 * POST -> /{pid}/item              -> addItem
	 * PUT  -> /{pid}/item              -> updateItem
	 ---------------------------------------------------------------------
	 */
	 
	@GetMapping("/{oid}/item")
	public String viewItem(@PathVariable("oid") Long oid , Model model , OrderItem orderItem) {
		model.addAttribute("order" , orderRepository.findById(oid).get());
		model.addAttribute("products" , productRepository.findAll());
		model.addAttribute("_method" , "POST");
		return "orderItem";
	}
	
	@GetMapping("/{oid}/item/{iid}")
	public String getItem(@PathVariable("oid") Long oid , @PathVariable("iid") Long iid ,@ModelAttribute OrderItem orderItem,Model model) {
		model.addAttribute("order", orderRepository.findById(oid).get());
		model.addAttribute("orderItem", orderItemRepository.findById(iid).get());
		model.addAttribute("products", productRepository.findAll());
		model.addAttribute("_method", "PUT");
		return "orderItem";
	}
	
	@GetMapping("/{oid}/item/delete/{iid}")
	public String delete(@PathVariable("oid") Long oid , @PathVariable("iid") Long iid) {
		orderItemRepository.deleteById(iid);
		return "redirect:../";
	}
	
	@PostMapping("/{oid}/item")
	public String addItem(OrderItem orderItem, @PathVariable("oid") long oid, BindingResult result, Model model)  {
		InventoryValidator.validate(orderItem, result);
		if(result.hasErrors()) {
			model.addAttribute("order", orderRepository.findById(oid).get());
			model.addAttribute("orderItem", orderItem);
			model.addAttribute("products", productRepository.findAll());
			model.addAttribute("_method", "POST");
			return "orderitem";
		}
		orderItemRepository.save(orderItem);
		return "redirect:./item";
	}
	
	@PutMapping("/{oid}/item")
	public String updateItem(OrderItem orderItem, @PathVariable("oid") long oid, BindingResult result, Model model)  {
		InventoryValidator.validate(orderItem, result);
		if(result.hasErrors()) {
			model.addAttribute("order", orderRepository.findById(oid).get());
			model.addAttribute("orderItem", orderItem);
			model.addAttribute("products", productRepository.findAll());
			model.addAttribute("_method", "PUT");
			return "orderitem";
		}
		orderItemRepository.save(orderItem);
		return "redirect:./item";
	}
}
