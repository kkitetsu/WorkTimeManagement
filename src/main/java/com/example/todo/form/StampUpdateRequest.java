package com.example.todo.form;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class StampUpdateRequest implements Serializable{
	/**
	 * 編集後の打刻時間
	 */
	private LocalDateTime datetime;
	
	/**
	 * ログID
	 */
	private Integer id;
	
	/**
	 * 出退勤カテゴリ
	 */
	private int stampType_id;
	
	
	/**
	 * 申請者
	 */
	private String applicant;
	
}
