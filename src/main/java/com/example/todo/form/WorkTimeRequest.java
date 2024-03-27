package com.example.todo.form;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@Data
public class WorkTimeRequest implements Serializable{
	/*
	 * 指定された月
	 */
	private LocalDate month;
	
	/*
	 * ユーザーID
	 */
	private Integer user_id;
}
