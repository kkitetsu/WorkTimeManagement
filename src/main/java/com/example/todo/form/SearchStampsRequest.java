package com.example.todo.form;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SearchStampsRequest implements Serializable{
	/**
	 * 期間（始まり）
	 */
	private LocalDateTime startDate;
	
	/**
	 * 期間（終わり）
	 */
	private LocalDateTime endDate;
	
	/**
	 * 社員番号
	 */
	private int employee_id;
	
	/**
	 * 名前
	 */
	private String lastname;
	
	/**
	 * 名前
	 */
	private String firstname;

	/**
	 * 出退勤カテゴリ
	 */
	private int stampType_id;

	/**
	 * 部署ID
	 */
	private int  dpt_id;
}
