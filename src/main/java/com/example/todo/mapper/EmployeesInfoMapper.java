package com.example.todo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.todo.dto.SearchEmployeesDTO;
import com.example.todo.dto.SearchStampsDTO;
import com.example.todo.entity.EmployeesEntity;
import com.example.todo.entity.LogsEntity;
import com.example.todo.form.LoginRequest;
import com.example.todo.form.SearchEmployeesRequest;
import com.example.todo.form.SearchStampsRequest;
import com.example.todo.form.StampUpdateRequest;

@Mapper
public interface EmployeesInfoMapper{
	
	List<SearchEmployeesDTO> getEmployees(SearchEmployeesRequest searchEmployeesRequest);
	List<SearchStampsDTO> getStamps(SearchStampsRequest searchStampsRequest);
	List<EmployeesEntity> getEmployeesById(SearchEmployeesRequest searchEmployeesRequest);
	List<EmployeesEntity> login(LoginRequest loginRequest);
	void updateStamps(StampUpdateRequest stampUpdateRequest);

	
	/**
	 * @author kk
	 * 
	 * Get the user's log.
	 * 
	 * @return logs in a list
	 */
	List<LogsEntity> getEmployeesLogs();
	
	/**
	 * @author kk
	 * 
	 * Update the user's log according to input.
	 * 
	 * @param data need for updating the log
	 */
	void insertLogs(LogsEntity logsEntity);

}
