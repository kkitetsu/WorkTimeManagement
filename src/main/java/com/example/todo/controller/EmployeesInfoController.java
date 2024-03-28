package com.example.todo.controller;

import java.time.LocalDateTime;
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
import com.example.todo.dto.WorkTimeDTO;
import com.example.todo.form.SearchEmployeesRequest;
import com.example.todo.form.SearchStampsRequest;
import com.example.todo.form.StampUpdateRequest;
import com.example.todo.form.WorkTimeRequest;
import com.example.todo.service.EmployeesInfoService;

import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class adminController
 */
@Controller
public class EmployeesInfoController {

	@Autowired
	private EmployeesInfoService employeesInfoService;

	/**
	 * @author shunsukekuzawa
	 * 
	 * access admin's top page.
	 * 
	 * @param model
	 * @return admin top page URL
	 */
	@GetMapping(value = "/admin")
	public String View(Model model) {
		model.addAttribute("searchEmployeesRequest", new SearchEmployeesRequest());
		model.addAttribute("searchStampsRequest", new SearchStampsRequest());
		return "/admin";
	}

	/**
	 * @author shunsukekuzawa
	 * 
	 * Get the employees' info which meet the conditions.
	 * 
	 * @param searchEmployeesRequest
	 * @param result
	 * @param model
	 * @return admin top page URL
	 */
	@RequestMapping(value = "/admin", params = "emp", method = RequestMethod.POST)
	public String searchEmp(@ModelAttribute SearchEmployeesRequest searchEmployeesRequest, BindingResult result,
			Model model) {
		//バリデーションチェック
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

		//社員の検索条件を入力し、条件を満たした社員を代入
		List<SearchEmployeesDTO> empInfo = employeesInfoService.getEmployees(searchEmployeesRequest);

		//入力された検索条件を出力するために、変数empConditionに条件を代入
		SearchEmployeesDTO empCondition = new SearchEmployeesDTO();
		empCondition.setEmployeeId(searchEmployeesRequest.getEmployee_id());
		empCondition.setDptId(searchEmployeesRequest.getDpt_id());
		empCondition.setEndDate(searchEmployeesRequest.getEndDate());
		empCondition.setStartDate(searchEmployeesRequest.getStartDate());
		empCondition.setFirstname(searchEmployeesRequest.getFirstname());
		empCondition.setLastname(searchEmployeesRequest.getLastname());
		if (searchEmployeesRequest.getPosition_id() != null) {
			empCondition.setPositionName(empInfo.get(0).getPositionName());
			empCondition.setDptName(empInfo.get(0).getDptName());
		} else {
			empCondition.setPositionName(null);
			empCondition.setDptName(null);
		}

		//検索条件の情報
		model.addAttribute("empCondition", empCondition);
		//検索条件に一致した社員のリスト
		model.addAttribute("empInfo", empInfo);

		//おまじない
		model.addAttribute("searchEmployeesRequest", new SearchEmployeesRequest());
		model.addAttribute("searchStampsRequest", new SearchStampsRequest());

		return "admin";
	}

	/**
	 * @author shunsukekuzawa
	 * 
	 * Get the stamps' info which meet the conditions. 
	 * 
	 * @param searchStampsRequest
	 * @param session
	 * @param result
	 * @param model
	 * @return admin top page URL
	 */
	@RequestMapping(value = "/admin", params = "stamp", method = RequestMethod.POST)
	public String searchStamp(@ModelAttribute SearchStampsRequest searchStampsRequest, HttpSession session,
			BindingResult result, Model model) {
		//バリデーション
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
		//打刻の検索条件を入力し、条件を満たした打刻履歴を代入
		List<SearchStampsDTO> stampInfo = employeesInfoService.getStamps(searchStampsRequest);
		LocalDateTime startDate = searchStampsRequest.getStartDate();
		LocalDateTime endDate = searchStampsRequest.getEndDate();
		for (SearchStampsDTO eachLog : stampInfo) {
			int tmp = eachLog.getStampTypeId();
			eachLog.setStartDate(startDate);
			eachLog.setEndDate(endDate);
			switch (tmp) {
			case 0:
				eachLog.setStampTypeIdStr("出勤");
				break;
			case 1:
				eachLog.setStampTypeIdStr("退勤");
				break;
			case 2:
				eachLog.setStampTypeIdStr("外出");
				break;
			case 3:
				eachLog.setStampTypeIdStr("復帰");
				break;
			}
		}

		//入力された検索条件を出力するために、変数stampConditionに条件を代入
		SearchStampsDTO stampCondition = new SearchStampsDTO();
		stampCondition.setStartDate(searchStampsRequest.getStartDate());
		stampCondition.setEndDate(searchStampsRequest.getEndDate());
		stampCondition.setFirstname(searchStampsRequest.getFirstname());
		stampCondition.setLastname(searchStampsRequest.getLastname());
		stampCondition.setUserId(searchStampsRequest.getEmployee_id());
		if (searchStampsRequest.getStampType_id() != null) {
			int tmp = searchStampsRequest.getStampType_id();
			switch (tmp) {
			case 0:
				stampCondition.setStampTypeIdStr("出勤");
				break;
			case 1:
				stampCondition.setStampTypeIdStr("退勤");
				break;
			case 2:
				stampCondition.setStampTypeIdStr("外出");
				break;
			case 3:
				stampCondition.setStampTypeIdStr("復帰");
				break;
			}
		}

		//検索条件を格納
		model.addAttribute("stampCondition", stampCondition);
		//検索条件を満たした打刻履歴を格納
		model.addAttribute("stampInfo", stampInfo);

		//おまじない
		model.addAttribute("searchEmployeesRequest", new SearchEmployeesRequest());
		model.addAttribute("searchStampsRequest", new SearchStampsRequest());

		return "admin";
	}

	/**
	 * @author shunsukekuzawa
	 * 
	 * Edit stamps' info by admin.
	 * 
	 * @param id
	 * @param model
	 * @param session
	 * @return edit page URL
	 */
	@GetMapping(value = "/admin/{id}/adminEdit")
	public String editStamp(@PathVariable int id, Model model, HttpSession session) {

		StampUpdateRequest newStamp = new StampUpdateRequest();
		newStamp.setId(id);

		//編集・更新
		List<SearchStampsDTO> stampInfo = employeesInfoService.getStampsById(id);
		for (SearchStampsDTO eachLog : stampInfo) {
			int tmp = eachLog.getStampTypeId();
			switch (tmp) {
			case 0:
				eachLog.setStampTypeIdStr("出勤");
				break;
			case 1:
				eachLog.setStampTypeIdStr("退勤");
				break;
			case 2:
				eachLog.setStampTypeIdStr("外出");
				break;
			case 3:
				eachLog.setStampTypeIdStr("復帰");
				break;
			default:
				eachLog.setStampTypeIdStr(null);
				break;
			}
		}		
		model.addAttribute("stampUpdateRequest",newStamp);
		model.addAttribute("stampInfo",stampInfo);
		model.addAttribute("searchEmployeesRequest", new SearchEmployeesRequest());
		return "/adminEdit";
	}

	/**
	 * @author shunsukekuzawa
	 * 
	 * Update stamps' info by admin.
	 * 
	 * @param stampUpdateRequest
	 * @param model
	 * @return admin top page URL
	 */
	@RequestMapping(value = "/stamp/update", method = RequestMethod.POST)
	public String updateStamp(@ModelAttribute StampUpdateRequest stampUpdateRequest, Model model) {
		//打刻履歴を更新すると申請者は、管理者
		stampUpdateRequest.setApplicant("管理者");
		employeesInfoService.updateStamps(stampUpdateRequest);

		View(model);
		return "redirect:/admin";
	}

	/**
	 * @author shunsukekuzawa
	 * 
	 * Get total hours worked during the month.
	 * 
	 * @param id
	 * @param model
	 * @param session
	 * @param workTime
	 * @return 
	 */
	@GetMapping(value = "/admin/{id}/tmp")
	public String workTimeDisplay(@PathVariable int id,Model model, HttpSession session) {
		WorkTimeRequest workTimeRequest = new WorkTimeRequest();
		workTimeRequest.setId(id);
		workTimeRequest.setFirstname(employeesInfoService.getAnEmployeeFirstName(id));
		workTimeRequest.setLastname(employeesInfoService.getAnEmployeeLastName(id));
		model.addAttribute("workTimeRequest", workTimeRequest);
		if (session.getAttribute("userId").equals("ADMIN")) {
			model.addAttribute("isAdmin", true);
		}
		return "/tmp";
	}

	@RequestMapping(value = "/tmp", method = RequestMethod.POST)
	public String workTime(Model model, HttpSession session, WorkTimeRequest workTimeRequest) {
		
		List<WorkTimeDTO> workTimeInfo = employeesInfoService.getWorkTime(workTimeRequest);
		
		int userId = 0;
		System.out.println(session.getAttribute("userId"));
		if (session.getAttribute("userId").equals("ADMIN")) {
			// This is admin
			userId = workTimeRequest.getId();
			model.addAttribute("isAdmin", true);
		} else {
			// This is accessed by user
			userId = Integer.parseInt(session.getAttribute("userId").toString());
			model.addAttribute("isAdmin", false);
		}
		
		System.out.println(model.getAttribute("isAdmin"));
		
		for (WorkTimeDTO eachLog : workTimeInfo) {
			eachLog.setStartDate(workTimeRequest.getStartDate());
			eachLog.setEndDate(workTimeRequest.getEndDate());
			eachLog.setFirstname((employeesInfoService.getAnEmployeeFirstName(userId)));
			eachLog.setLastname(employeesInfoService.getAnEmployeeLastName(userId));
		}
		
		WorkTimeRequest workRequest = new WorkTimeRequest();

		workRequest.setFirstname((employeesInfoService.getAnEmployeeFirstName(workTimeRequest.getId())));
		workRequest.setLastname(employeesInfoService.getAnEmployeeLastName(workTimeRequest.getId()));
		workRequest.setId(workTimeRequest.getId());	

		model.addAttribute("workTimeInfo", workTimeInfo);
		model.addAttribute("workTimeRequest", workRequest);
		workRequest.setId(userId);
		
		return "/tmp";
	}
	

	@RequestMapping(value="/stamp/delete", method=RequestMethod.POST)
	public String deleteStamp(@ModelAttribute StampUpdateRequest stampUpdateRequest,Model model) {
		System.out.println(stampUpdateRequest.getId());
		employeesInfoService.delete(stampUpdateRequest.getId());
		return "redirect:/admin";
		
	}
}

