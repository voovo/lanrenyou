/**
 * com.hadwins.payment.enums.PayerTypeEnum.java
 *
 * date		2013-12-3
 * author	peijin.zhang
 * Copyright (c) 2013, zhangpj All Rights Reserved.
*/

package com.lanrenyou.admin.enums;
/**
 * @author   peijin.zhang
 * @Date	 2013-12-3		下午05:19:39 
 */
public enum AdminRoleStatusEnum {
	NORMAL(1, "正常"),	
	STOP(2, "暂停");		
	
	private int value;
	private String text;
	
	AdminRoleStatusEnum(int value_, String text_){
		this.value = value_;
		this.text = text_;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public String getText(){
		return this.text;
	}
	
	public static AdminRoleStatusEnum valueOf(int value){
		if(value == 1){
			return NORMAL;
		} else if (value == 2){
			return STOP;
		}
		return null;
	}
}

