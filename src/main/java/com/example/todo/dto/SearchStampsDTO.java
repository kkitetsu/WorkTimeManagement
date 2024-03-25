package com.example.todo.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class SearchStampsDTO{
	
	private int logId;
	
	private Date datetime;
	
	private int stampTypeId;
	
	private int userId;
	
	private String lastname;
	
	private String firstname;
	
	private int cancelFlag;
	
	private String applicant;
}
