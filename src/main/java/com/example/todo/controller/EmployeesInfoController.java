package com.example.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.todo.dto.SearchEmployeesDTO;
import com.example.todo.dto.SearchStampsDTO;
import com.example.todo.form.SearchEmployeesRequest;
import com.example.todo.form.SearchStampsRequest;
import com.example.todo.service.EmployeesInfoService;

/**
 * Servlet implementation class adminController
 */
@Controller
public class EmployeesInfoController {
	
	@Autowired
	private EmployeesInfoService employeesInfoService;
   
	@GetMapping(value="/admin")
	public String View(Model model) {
		model.addAttribute("searchEmployees", new SearchEmployeesRequest());
		model.addAttribute("searchStamps", new SearchStampsRequest());
		return "/admin";
	}
	
	
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	public String searchEmp(@ModelAttribute SearchEmployeesRequest searchEmployeesRequest,Model model) {
		
		List<SearchEmployeesDTO> empInfo = employeesInfoService.getEmployees(searchEmployeesRequest);
		
		model.addAttribute("empInfo",empInfo);
		
		View(model);
		return "admin";
	}
	
	@RequestMapping(value="/stamp",method=RequestMethod.POST)
	public String searchStamp(@ModelAttribute SearchStampsRequest searchStampsRequest,Model model) {
		
		List<SearchStampsDTO> stampInfo = employeesInfoService.getStamps(searchStampsRequest);
		
		model.addAttribute("stampInfo",stampInfo);
		
		View(model);
		return "admin";
	}

}
