package com.example.todo.form;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class WorkTimeRequest implements Serializable{
	/*
	 * 指定された月
	 */
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	/*
	 *ユーザーID 
	 */
	private Integer id;
	
	private String lastname;
	
	private String firstname;

}
