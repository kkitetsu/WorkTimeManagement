package com.example.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class CreateAccountController {
	
	/**
	 * @author kk
	 * 
	 * Access to user my page.
	 * 
	 * @return 
	 */

	@GetMapping(value="/create")
	public String displayUserMyPage(Model model, HttpSession session) {
		return "/createAccount.html";
	}
}
