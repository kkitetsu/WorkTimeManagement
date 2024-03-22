package com.example.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todo.entity.EmployeesEntity;
import com.example.todo.form.LoginRequest;
import com.example.todo.service.EmployeesInfoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WorkTimeManagementController {
	@Autowired
	private EmployeesInfoService employeesinfoservice;	
	/**
	 * @author kk
	 * 
	 * Access to user my page.
	 * 
	 * @return 
	 */
	@GetMapping(value="/home")
	public String displayhome(Model model, HttpSession session) {
		model.addAttribute("logininfo", new LoginRequest() );
		return "/home";
	}
	
	@GetMapping(value="/create")
	public String displaycreatepage(Model model, HttpSession session) {
		return "createAccount";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String UserLogin( 
			@ModelAttribute LoginRequest loginrequest, 
			Model model) 
	{
		List<EmployeesEntity> user_info = employeesinfoservice.login(loginrequest);
		if(user_info.isEmpty()) {return  "redirect:home";}
		System.out.println(user_info);
		return "userMyPage";
	}
	
	/**
	 * @author kk
	 * 
	 * Logic after button click in userMyPage.
	 * 
	 * @param model
	 * @param session
	 * @return alertAndRedirect, clockinPage, or top page
	 */
	@RequestMapping(value="/clockin", method=RequestMethod.POST)
	public String clockIn(Model model, HttpSession session, @RequestParam("action") String action,
															@RequestParam("selectedOption") String selectedOption) {
		if (action.equals("clockin")) {
			System.out.println(selectedOption);
            return "/alertAndRedirect";
        } else if (action.equals("checkHistory")) {
            return "/clockinPage";
        } else {
        	// TODO: redirect to top page
            return "";
        }
	}
	
	/**
	 * @author kk
	 * 
	 * ユーザが打刻のちに、アラート画面と次の打刻画面への引き継ぎメソッド.
	 * 
	 * @return clockinPage
	 */
	@GetMapping("/tmppage")
	public String tmpPage() {
	    return "clockinPage"; 
	}
	
}
