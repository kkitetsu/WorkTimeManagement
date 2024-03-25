package com.example.todo.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SearchEmployeesDTO{
	private int employeeId;
	
	private String loginPW;
	
	private String lastname;
	
	private String firstname;
	
	private int dptId;
	
	private int positionId;
	
	private String positionName;
	
	private String dptName;
	
	private LocalDate birthday;
}
