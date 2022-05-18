package com.study.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages")
public class PageController {
	
	@RequestMapping("/")
	public String index() {
		return "psi_tables";
	}
	
}
