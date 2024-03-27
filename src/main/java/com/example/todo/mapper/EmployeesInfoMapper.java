package com.example.todo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.todo.dto.SearchEmployeesDTO;
import com.example.todo.dto.SearchStampsDTO;
import com.example.todo.dto.WorkTimeDTO;
import com.example.todo.entity.EmployeesEntity;
import com.example.todo.entity.LogsEntity;
import com.example.todo.form.LoginRequest;
import com.example.todo.form.SearchEmployeesRequest;
import com.example.todo.form.SearchStampsRequest;
import com.example.todo.form.StampUpdateRequest;
import com.example.todo.form.WorkTimeRequest;

@Mapper
public interface EmployeesInfoMapper{
	/**
	 * @author shunsukekuzawa
	 * 
	 * Get the employees' info which meet the conditions. 
	 * 
	 * @param searchEmployeesRequest
	 * @return employees' info in a list.
	 */
	List<SearchEmployeesDTO> getEmployees(SearchEmployeesRequest searchEmployeesRequest);	
	
	/**
	 * @author shunsukekuzawa
	 * 
	 * Get the stamps' info which meet the conditions. 
	 * 
	 * @param searchStampsRequest
	 * @return stamps' info in a list.
	 */
	List<SearchStampsDTO> getStamps(SearchStampsRequest searchStampsRequest);
	
	/**
	 * @author shunsukekuzawa
	 * 
	 * Get the employees' info which meet the conditions by their id. 
	 * 
	 * @param id
	 * @return stamps' info in a list.
	 */
	List<SearchStampsDTO> getStampsById(int id);
	
	/**
	 * @author shunsukekuzawa
	 * 
	 * Get total hours worked during the month.
	 * 
	 * @param workTimeRequest
	 * @return total hours (hh:mm:ss)
	 */
	List<WorkTimeDTO> getWorkTime(WorkTimeRequest workTimeRequest);
	
	/**
	 * @author shunsukekuzawa
	 * 
	 * Update stamps' info by admin.
	 * 
	 * @param stampUpdateRequest
	 */
	void updateStamps(StampUpdateRequest stampUpdateRequest);
	
	/**
	 * @author kk
	 * 
	 * Get the first name of the employee by its id.
	 * 
	 * @param id
	 * @return
	 */
	String getAnEmployeeFirstName(int id);

	/**
	 * @author kk
	 * 
	 * Get the user's log.
	 * 
	 * @return logs in a list
	 */
	List<LogsEntity> getEmployeesLogs(int userId);
	
	/**
	 * @author kk
	 * 
	 * Update the user's log according to input.
	 * 
	 * @param data need for updating the log
	 */
	void insertLogs(LogsEntity logsEntity);
	
	/**
	 * @author kk
	 * 
	 * Create new user. 会員登録
	 * 
	 * @param employee's data
	 */
	void createNewUser(EmployeesEntity employeesEntity);
	
	
	List<EmployeesEntity> getEmployeesById(SearchEmployeesRequest searchEmployeesRequest);
	List<EmployeesEntity> login(LoginRequest loginRequest);

}
