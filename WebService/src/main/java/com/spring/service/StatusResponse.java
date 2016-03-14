package com.spring.service;

import flexjson.JSONSerializer;

public class StatusResponse {
	public static final int STATUS_CODE_ERROR = 0;
	public static final int STATUS_CODE_WARNING = 1;
	public static final int STATUS_CODE_SUCCESS = 2;

	public static final int STATUS_ADVISOR_KEEP = 1;
	public static final int STATUS_ADVISOR_RELOAD = 2;

	int statusCode = STATUS_CODE_SUCCESS;
	String statusMessage;
	int statusAdvisor = STATUS_ADVISOR_KEEP;

	public StatusResponse() {
		super();
	}
	
	public StatusResponse(int statusCode, String statusMessage, int statusAdvisor) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.statusAdvisor = statusAdvisor;
	}

	public StatusResponse(int statusCode, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public int getStatusAdvisor() {
		return statusAdvisor;
	}

	public void setStatusAdvisor(int statusAdvisor) {
		this.statusAdvisor = statusAdvisor;
	}
	
	static public String addResultToReturnData(String input){
		String reponse="";
		
		return reponse;
	}
	
	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}
}
