package com.example.todo.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class LogsEntity implements Serializable{
	
	private Timestamp datetime;
	
	private int stampTypeId;

	private String stampTypeIdStr;
	
	private int userId;
	
	private String applicant;
	
	private String note;
	
	private boolean cancelFlag;
}
