package com.example.todo.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SearchEmployeesDTO {
	private Integer employeeId;

	private String loginPW;

	private String lastname;

	private String firstname;

	private Integer dptId;

	private Integer positionId;

	private String positionName;

	private String dptName;

	private LocalDate birthday;

	private Integer checkFlag;

	//ここからは検索条件
	/**
	 * 期間（始まり）
	 */
	private LocalDate startDate;

	/**
	 * 期間（終わり）
	 */
	private LocalDate endDate;
}
