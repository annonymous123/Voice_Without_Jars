package org.raxa.database;

public class PAlert {
	
	private int paid;
	private String pid;
	private int alertType;
	
	public PAlert(String pid,int alertType){
		this.pid=pid;
		this.alertType=alertType;
	}
	
	public PAlert(){}
	
	public int getPatientAlertId(){
		return paid;
	}
	
	public void setPatientAlertId(int id){
		paid=id;
	}
	
	public String getPatientId(){
		return pid;
	}
	
	public int getAlertType(){
		return alertType;
	}
	
	public void setPatientId(String id){
		pid=id;
	}
	
	public void setAlertType(int type){
		alertType=type;
	}
}
