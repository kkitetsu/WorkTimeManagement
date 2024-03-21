package com.example.todo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class DepartmentsEntity implements Serializable{
	
	private int dpt_id;
	
	private String dpt_name;
}
