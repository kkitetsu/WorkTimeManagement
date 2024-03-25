package com.example.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.dto.SearchEmployeesDTO;
import com.example.todo.dto.SearchStampsDTO;
import com.example.todo.form.SearchEmployeesRequest;
import com.example.todo.form.SearchStampsRequest;
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
}
