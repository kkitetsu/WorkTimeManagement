package com.example.todo.form;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Pattern;
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
	@Pattern(regexp = "^[0-9]*$", message = "半角数字で入力してください")
	private Integer employee_id;
	
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
	private Integer stampType_id;

	/**
	 * 部署ID
	 */
	private int  dpt_id;
}
