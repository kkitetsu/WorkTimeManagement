package com.example.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.entity.EmployeesEntity;
import com.example.todo.form.LoginRequest;
import com.example.todo.form.SearchEmployeesRequest;
import com.example.todo.mapper.EmployeesInfoMapper;



@Service
public class EmployeesInfoService {
	
	@Autowired
    private EmployeesInfoMapper employeesInfomapper;
	
	public List<EmployeesEntity> getEmployeesById(SearchEmployeesRequest searchEmployeesRequest) {
		return employeesInfomapper.getEmployeesById(searchEmployeesRequest);
	}
	public List<EmployeesEntity> login(LoginRequest loginRequest){
		return employeesInfomapper.login(loginRequest);
	}
}
















