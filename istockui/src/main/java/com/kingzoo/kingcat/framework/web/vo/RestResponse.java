package com.kingzoo.kingcat.framework.web.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by gonghongrui on 16/6/12.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {

	private boolean success;

	private String errorCode;
	private String errorMsssage;
	private T data;


	public RestResponse(boolean success, T t){
		this.success = success;
		this.data = t;
	}

	public RestResponse(boolean success, String errorCode, String errorMsssage){
		this.success = success;
		this.errorCode = errorCode;
		this.errorMsssage = errorMsssage;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsssage() {
		return errorMsssage;
	}

	public void setErrorMsssage(String errorMsssage) {
		this.errorMsssage = errorMsssage;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
