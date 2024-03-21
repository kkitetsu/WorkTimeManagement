package com.example.todo.entity;

import java.io.Serializable;

import lombok.Data;


@Data
public class PositionsEntity implements Serializable{
	
	private int position_id;
	
	private String position_name;
}
