package com.lanrenyou.admin.enums;
/**
 * @author   peijin.zhang
 * @Date	 2013-12-3		下午05:19:39 
 */
public enum AdminUserStatusEnum {
	NORMAL(1),	
	STOP(2);		
	
	private int value;
	
	AdminUserStatusEnum(int value_){
		this.value = value_;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public static AdminUserStatusEnum valueOf(int value){
		if(value == 1){
			return NORMAL;
		} else if (value == 2){
			return STOP;
		}
		return null;
	}
}

