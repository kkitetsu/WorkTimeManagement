package com.example.todo.form;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class SearchEmployeesRequest implements Serializable {
	/**
	 * 期間（始まり）
	 */
	private Date startDate;
	
	/**
	 * 期間（終わり）
	 */
	private Date endDate;
	
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
	 * 部署ID
	 */
	private int  dpt_id;
	
	/**
	 * 役職ID
	 */
	private int  position_id;
}



