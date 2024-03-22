package com.example.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todo.entity.LogsEntity;
import com.example.todo.service.EmployeesInfoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WorkTimeManagementController {
	
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
		return "/userMyPage";
	}

	@GetMapping(value="/home")
	public String displayUserMyPage(Model model, HttpSession session) {
		return "/home";
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
			LogsEntity logsEntity = new LogsEntity();
			logsEntity.setApplicant("Honnin");
			logsEntity.setNote("XXXX");
			logsEntity.setUser_id(1);
			logsEntity.setStampType_id(Integer.parseInt(selectedOption));
			System.out.println(selectedOption);
			employeesInfoService.updateLogs(logsEntity);
			
            return "/alertAndRedirect";
            
        } else if (action.equals("checkHistory")) {
            return "redirect:/userLogPage";
            
        } else {
            return "/home";
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
		List<LogsEntity> logs = employeesInfoService.getEmployeesLogs();
		model.addAttribute("logs", logs);
		return "userLogPage"; 
	}
	
}
