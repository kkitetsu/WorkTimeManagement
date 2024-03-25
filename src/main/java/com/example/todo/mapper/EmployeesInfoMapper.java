package com.example.todo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.todo.dto.SearchEmployeesDTO;
import com.example.todo.dto.SearchStampsDTO;
import com.example.todo.form.SearchEmployeesRequest;
import com.example.todo.form.SearchStampsRequest;

@Mapper
public interface EmployeesInfoMapper{
	List<SearchEmployeesDTO> getEmployees(SearchEmployeesRequest searchEmployeesRequest);
	
	List<SearchStampsDTO> getStamps(SearchStampsRequest searchStampsRequest);
}
