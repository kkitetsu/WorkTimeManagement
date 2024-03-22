package com.example.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.todo.entity.EmployeesEntity;
import com.example.todo.form.SearchEmployeesRequest;
import com.example.todo.service.EmployeesInfoService;

/**
 * Servlet implementation class adminController
 */
@Controller
public class EmployeesInfoController {
	
	@Autowired
	private EmployeesInfoService employeesInfoService;
   
	@GetMapping(value="/admin")
	public String adminView(Model model) {
		model.addAttribute("searchEmployees", new SearchEmployeesRequest());
		return "/admin";
	} 	
	
	@RequestMapping(value="/admin/search",method=RequestMethod.POST)
	public String search(@ModelAttribute SearchEmployeesRequest searchEmployeesRequest,Model model) {
		
		List<EmployeesEntity> empInfo = employeesInfoService.getEmployeesById(searchEmployeesRequest);
		
		model.addAttribute("empInfo",empInfo);
		
		
		return "/result";
	}

}
