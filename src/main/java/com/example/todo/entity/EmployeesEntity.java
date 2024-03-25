package com.example.todo.entity;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class EmployeesEntity implements Serializable{
	
	int employee_id;
	
	private String loginPW;
	
	private String lastname;
	
	private String firstname;
	
	private int dptId;
	
	private int positionId;
	
	private String mail;
	
	private String phone;
	
	private String address;
	
	private Date birthday;
	
	private Date created_date;
	
	private String position_name;
}
