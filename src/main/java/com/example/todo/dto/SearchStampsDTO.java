package com.example.todo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SearchStampsDTO{
	
	private Integer logId;
	
	private LocalDateTime datetime;
	
	private Integer stampTypeId;
	
	private String stampTypeIdStr;
	
	private Integer userId;
	
	private String lastname;
	
	private String firstname;
	
	private Integer cancelFlag;
	
	private String applicant;
	
	//ここからは検索条件
	/**
	 * 期間（始まり）
	 */
	private LocalDateTime startDate;
	
	/**
	 * 期間（終わり）
	 */
	private LocalDateTime endDate;
	
}
