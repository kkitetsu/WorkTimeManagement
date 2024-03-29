package com.example.todo.form;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SearchEmployeesRequest implements Serializable {
	/**
	 * 期間（始まり）
	 */
	private LocalDate startDate;
	
	/**
	 * 期間（終わり）
	 */
	private LocalDate endDate;
	
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
	 * 部署ID
	 */
	private Integer  dpt_id;
	
	/**
	 * 役職ID
	 */
	private Integer  position_id;
	
	private String positionName;

	private String dptName;
}



