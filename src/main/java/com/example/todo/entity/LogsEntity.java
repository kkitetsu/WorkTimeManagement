package com.example.todo.entity;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class LogsEntity implements Serializable{
	
	private Date datetime;
	
	private int stampTypeId;
	
	private int user_id;
	
	private String applicant;
	
	private String note;
	
	private boolean cancel_flag;
}
