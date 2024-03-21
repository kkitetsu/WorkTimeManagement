package com.example.todo.entity;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class EmployeesEntity implements Serializable{
	
	private String loginPW;
	
	private String lastname;
	
	private String firstname;
	
	private int dpt_id;
	
	private int position_id;
	
	private String mail;
	
	private String phone;
	
	private String address;
	
	private Date birthday;
	
	private Date cancel_date;
	
}
