package com.example.todo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.todo.dto.SearchEmployeesDTO;
import com.example.todo.dto.SearchStampsDTO;
import com.example.todo.form.SearchEmployeesRequest;
import com.example.todo.form.SearchStampsRequest;
import com.example.todo.form.StampUpdateRequest;
import com.example.todo.service.EmployeesInfoService;

import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class adminController
 */
@Controller
public class EmployeesInfoController {
	
	@Autowired
	private EmployeesInfoService employeesInfoService;
   
	@GetMapping(value="/admin")
	public String View(Model model) {
		model.addAttribute("searchEmployeesRequest", new SearchEmployeesRequest());
		model.addAttribute("searchStampsRequest", new SearchStampsRequest());
		return "/admin";
	} 	
	
	@RequestMapping(value="/admin",params = "emp",method=RequestMethod.POST)
	public String searchEmp(@ModelAttribute SearchEmployeesRequest searchEmployeesRequest, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
            // 入力チェックエラーの場合
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("searchEmployeesRequest", new SearchEmployeesRequest());
    		model.addAttribute("searchStampsRequest", new SearchStampsRequest());
            model.addAttribute("validationError", errorList);
            return "admin";
        }
		
		List<SearchEmployeesDTO> empInfo = employeesInfoService.getEmployees(searchEmployeesRequest);
		
		model.addAttribute("empInfo",empInfo);
		model.addAttribute("searchEmployeesRequest", new SearchEmployeesRequest());
		model.addAttribute("searchStampsRequest", new SearchStampsRequest());
		
		return "admin";
	}
	
	@RequestMapping(value="/admin",params = "stamp",method=RequestMethod.POST)
	public String searchStamp(@ModelAttribute SearchStampsRequest searchStampsRequest,HttpSession session, BindingResult result,Model model) {
		
		if (result.hasErrors()) {
            // 入力チェックエラーの場合
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            model.addAttribute("searchEmployeesRequest", new SearchEmployeesRequest());
    		model.addAttribute("searchStampsRequest", new SearchStampsRequest());
            return "admin";
        }
		
		List<SearchStampsDTO> stampInfo = employeesInfoService.getStamps(searchStampsRequest);
		
		for (SearchStampsDTO eachLog : stampInfo) {
			int tmp = eachLog.getStampTypeId();
			switch (tmp) {
				case 0: eachLog.setStampTypeIdStr("出勤"); break;
				case 1: eachLog.setStampTypeIdStr("退勤"); break;
				case 2: eachLog.setStampTypeIdStr("外出"); break;
				case 3: eachLog.setStampTypeIdStr("復帰"); break;
				default: eachLog.setStampTypeIdStr(null); break;
			}
		}
		
		model.addAttribute("stampInfo",stampInfo);
		model.addAttribute("searchEmployeesRequest", new SearchEmployeesRequest());
		model.addAttribute("searchStampsRequest", new SearchStampsRequest());
		
		return "admin";
	}
	
	
	@GetMapping(value="/admin/{id}/adminEdit")
	public String editStamp(@PathVariable int id, Model model, HttpSession session) {
		
		StampUpdateRequest newStamp = new StampUpdateRequest();
		newStamp.setId(id);
		
		model.addAttribute("stampUpdateRequest",newStamp);

		return "/adminEdit";
	}
	
	@RequestMapping(value="/stamp/update",method=RequestMethod.POST)
	public String updateStamp(@ModelAttribute StampUpdateRequest stampUpdateRequest,Model model) {
		
		employeesInfoService.updateStamps(stampUpdateRequest);
		
		View(model);
		return "redirect:/admin";
	}
	

}
