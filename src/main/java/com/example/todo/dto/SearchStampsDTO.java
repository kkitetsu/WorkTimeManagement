package com.example.todo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SearchStampsDTO{
	
	private int logId;
	
	private LocalDateTime datetime;
	
	private int stampTypeId;
	
	private String stampTypeIdStr;
	
	private int userId;
	
	private String lastname;
	
	private String firstname;
	
	private int cancelFlag;
	
	private String applicant;
}
