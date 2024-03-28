package com.example.todo.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.example.todo.mapper.EmployeesInfoMapper;

@Service
public class EmployeesInfoService {
	
	@Autowired
    private EmployeesInfoMapper employeesInfomapper;
	
	public List<SearchEmployeesDTO> getEmployees(SearchEmployeesRequest searchEmployeesRequest) {
		return employeesInfomapper.getEmployees(searchEmployeesRequest);
	}
	
	public List<SearchStampsDTO> getStamps(SearchStampsRequest searchStampsRequest) {
		return employeesInfomapper.getStamps(searchStampsRequest);
  }
	
	public List<SearchStampsDTO> getStampsById(int id){
		return employeesInfomapper.getStampsById(id);
	}
	
	public List<EmployeesEntity> getEmployeesById(SearchEmployeesRequest searchEmployeesRequest) {
		return employeesInfomapper.getEmployeesById(searchEmployeesRequest);
	}
	
	public void updateStamps(StampUpdateRequest stampUpdateRequest) {
		employeesInfomapper.updateStamps(stampUpdateRequest);
	}
	
	public List<EmployeesEntity> login(LoginRequest loginRequest){
		return employeesInfomapper.login(loginRequest);}
	
	/**
	 * @author shunsukekuzawa
	 * 
	 * Get total hours worked during the month.
	 */
	public List<WorkTimeDTO> getWorkTime(WorkTimeRequest workTimeRequest){
		return employeesInfomapper.getWorkTime(workTimeRequest);
	}
	
	/**
	 * @author kk
	 * 
	 * Get employee's log as a list of LogsEntity.
	 */
	public List<LogsEntity> getEmployeesLogs(int userId, final int SUBLISTSIZE, int startIndex) {
		return employeesInfomapper.getEmployeesLogs(userId, SUBLISTSIZE, startIndex);
	}
	
	/**
	 * @author kk
	 * 
	 * Update the user's log according to the selected option.
	 * 
	 */
	public void insertLogs(LogsEntity input) {
		employeesInfomapper.insertLogs(input);
	}
	
	/**
	 * @author kk
	 * 
	 * Create a new user.
	 */
	public void createNewUser(EmployeesEntity employeesEntity) {
		employeesInfomapper.createNewUser(employeesEntity);
	}
	
	/**
	 * @author kk
	 */
	public String getAnEmployeeFirstName(int id) {
		return employeesInfomapper.getAnEmployeeFirstName(id);
	}
	
	/**
	 * @author kk
	 */
	public String getAnEmployeeLastName(int id) {
		return employeesInfomapper.getAnEmployeeLastName(id);
	}
	
	/** @author kk */
	public LogsEntity getLastLog(int id) {
		return employeesInfomapper.getLastLog(id);
	}
	
	/** @author kk */
	public int getLogsSize(int id) {
		return employeesInfomapper.getLogsSize(id);
	}
	public void delete(int id) {
		employeesInfomapper.delete(id);
	}
}

