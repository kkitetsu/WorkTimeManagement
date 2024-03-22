package com.example.todo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.todo.entity.EmployeesEntity;
import com.example.todo.form.LoginRequest;
import com.example.todo.form.SearchEmployeesRequest;

@Mapper
public interface EmployeesInfoMapper{
	List<EmployeesEntity> getEmployeesById(SearchEmployeesRequest searchEmployeesRequest);
	List<EmployeesEntity> login(LoginRequest loginRequest);
}
