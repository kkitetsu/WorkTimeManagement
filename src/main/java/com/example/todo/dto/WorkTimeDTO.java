package com.example.todo.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class WorkTimeDTO {
	
	private String totalWorkTime;
	
	private Integer workCount;
	
	private String lastname;
	
	private String firstname;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
}
