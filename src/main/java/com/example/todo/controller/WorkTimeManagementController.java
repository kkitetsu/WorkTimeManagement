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
		return "home";
	}
	
	@GetMapping(value="/create")
	public String displaycreatepage(Model model, HttpSession session) {
		return "createAccount";
	}
	
	@GetMapping(value="/detail")
	public String displaycreatepage1(Model model, HttpSession session) {
		return "detail";
	}
	/**
	 * @author kk
	 * 
	 * Create a new user.
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createUser(Model model, HttpSession session, 
										@RequestParam("employee_id") String id,
										@RequestParam("loginPW") String pwd) {
		
		EmployeesEntity employeeEntity = new EmployeesEntity();
		employeeEntity.setEmployee_id(Integer.parseInt(id));
		employeeEntity.setLoginPW(pwd);
		
		try {
			employeesinfoservice.createNewUser(employeeEntity);
		} catch (Exception e) {
			model.addAttribute("errorMsg", "この会員はすでに登録されております");
			return "createAccount";
		}
			
		session.setAttribute("userFirstName", employeesinfoservice.getAnEmployeeFirstName(Integer.parseInt(id)));
		session.setAttribute("userId", id);
		model.addAttribute("userName", employeesinfoservice.getAnEmployeeFirstName(Integer.parseInt(id)));
		
		return "userMyPage";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String UserLogin( @ModelAttribute LoginRequest loginrequest, 
											Model model, HttpSession session) {
		
		/** 
		 * @author kk 
		 * If the login info is admin, jump to admin page.
		 */
		if (loginrequest.getLogin_id() == 4755 && loginrequest.getLogin_pw().equals("4755")) {
			return "redirect:admin";
		}
		
		List<EmployeesEntity> user_info = employeesinfoservice.login(loginrequest);
		if (user_info.isEmpty()) { 
			model.addAttribute("errMsg", "社員番号もしくはパスワードが違います");
			model.addAttribute("logininfo", new LoginRequest() );
			return "/home"; 
		}
		
		/** @author kk session */
		session.setAttribute("userFirstName", user_info.get(0).getFirstname());
		session.setAttribute("userId", loginrequest.getLogin_id());
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
			logsEntity.setApplicant("本人");
			logsEntity.setNote("XXXX");
			logsEntity.setUserId((int) session.getAttribute("userId"));
			logsEntity.setStampTypeId(Integer.parseInt(selectedOption));
			
			employeesInfoService.insertLogs(logsEntity);
			
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
	public String userLogPage(@RequestParam(defaultValue = "1") int currPage, Model model, HttpSession session) {
		System.out.println(currPage);
		if (session.getAttribute("userFirstName") == null) {
			return "redirect:/home";
		}
		List<LogsEntity> allData = employeesInfoService.getEmployeesLogs((int) session.getAttribute("userId"));
		
		int sublistSize = 5;
		int startIndex = (currPage - 1) * sublistSize;
        int endIndex = Math.min(startIndex + sublistSize, allData.size());
		
		List<LogsEntity> logs = allData.subList(startIndex, endIndex); 
		
		System.out.println("Current Page: " + currPage + "endIndex" + endIndex);
		
		model.addAttribute("logs", logs);
		model.addAttribute("userName", session.getAttribute("userFirstName"));
		model.addAttribute("currentPage", currPage);
		model.addAttribute("maxPageNum", (int) (Math.ceil(allData.size() / sublistSize)));
		
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

		return "userLogPage"; 
	}
	
}
