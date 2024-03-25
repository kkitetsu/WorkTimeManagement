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
import com.example.todo.entity.LogsEntity;
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
	 * Employees information service.
	 */
	@Autowired
	private EmployeesInfoService employeesInfoService;
	
	/**
	 * @author kk
	 * 
	 * Access to user my page.
	 * 
	 * @return 
	 */
	@GetMapping(value="/userMyPage")
	public String UserMyPage(Model model, HttpSession session) {
		if (session.getAttribute("userFirstName") == null) {
			return "redirect:/home";
		}
		model.addAttribute("userName", session.getAttribute("userFirstName"));
		return "/userMyPage";
	}
	
	@GetMapping(value="/home")
	public String displayhome(Model model, HttpSession session) {
		session.invalidate();
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
			Model model, HttpSession session) 
	{
		List<EmployeesEntity> user_info = employeesinfoservice.login(loginrequest);
		if(user_info.isEmpty()) {return  "redirect:home";}
		/** @author kk session */
		session.setAttribute("userFirstName", user_info.get(0).getFirstname());
		model.addAttribute("userName", session.getAttribute("userFirstName"));
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
		if (session.getAttribute("userFirstName") == null) {
			return "redirect:/home";
		}
		if (action.equals("clockin")) {
			LogsEntity logsEntity = new LogsEntity();
			logsEntity.setApplicant("Honnin");
			logsEntity.setNote("XXXX");
			logsEntity.setUserId(1);
			logsEntity.setStampTypeId(Integer.parseInt(selectedOption));
			
			employeesInfoService.insertLogs(logsEntity);
			
			System.out.println(logsEntity.getStampTypeId());
			
            return "/alertAndRedirect";
            
        } else if (action.equals("checkHistory")) {
        	
        	return "redirect:/userLogPage";
        } else {
        	
        	session.invalidate();    // Logout and back to home
            return "redirect:/home";
        }
	}
	
	/**
	 * @author kk
	 * 
	 * ユーザが打刻のちに、アラート画面と次の打刻画面への引き継ぎメソッド.
	 * 
	 * @return clockinPage
	 */
	@GetMapping("/userLogPage")
	public String userLogPage(Model model, HttpSession session) {
		if (session.getAttribute("userFirstName") == null) {
			return "redirect:/home";
		}
		List<LogsEntity> logs = employeesInfoService.getEmployeesLogs();
		model.addAttribute("logs", logs);
		model.addAttribute("userName", session.getAttribute("userFirstName"));
		for (LogsEntity eachLog : logs) {
			int tmp = eachLog.getStampTypeId();
			switch (tmp) {
				case 0: eachLog.setStampTypeIdStr("出勤"); break;
				case 1: eachLog.setStampTypeIdStr("退勤"); break;
				case 2: eachLog.setStampTypeIdStr("外出"); break;
				case 3: eachLog.setStampTypeIdStr("復帰"); break;
				default: eachLog.setStampTypeIdStr(null); break;
			}
		}
		System.out.println(logs);
		return "userLogPage"; 
	}
	
}
