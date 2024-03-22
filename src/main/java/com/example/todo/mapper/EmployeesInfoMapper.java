package com.example.todo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.todo.entity.EmployeesEntity;
import com.example.todo.entity.LogsEntity;
import com.example.todo.form.SearchEmployeesRequest;

@Mapper
public interface EmployeesInfoMapper{
	List<EmployeesEntity> getEmployeesById(SearchEmployeesRequest searchEmployeesRequest);
	
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
