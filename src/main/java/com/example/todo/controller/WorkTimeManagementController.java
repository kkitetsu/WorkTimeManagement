package com.example.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class WorkTimeManagementController {
	
	/**
	 * 
	 * Show all the tasks.
	 * 
	 * @return 
	 */
	@GetMapping(value="/userMyPage")
	public String displayList(Model model, HttpSession session) {
		return "/userMyPage";
	}
	
}
