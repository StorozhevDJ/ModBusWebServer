package org.inteh.modbuslogger.model;

public class ViewStatusModel {
 	
	private int statusCode;
 	private String statusText;
 	
 	
 	
 	public ViewStatusModel (int code, String test) {
 		setStatusCode(code);
 		setStatusText(test);
 	}
 	
 	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
 	
}


