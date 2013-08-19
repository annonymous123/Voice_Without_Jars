package org.raxa.database;

import java.sql.Timestamp;

public class Alert {
     private String aid;
     
     private String pid;
     
     private int  alertType;
     
     private int msgId;
     
     private Timestamp scheduleTime;   
     
     private Timestamp lastTry;
     
     private boolean isExecuted;
     
     private int retryCount;
     
     private String serviceInfo;
     
     public Alert(String aid,String pid,int alertType,int msgId,Timestamp scheduleTime,Timestamp lastTry){
    	 this.aid=aid;
    	 this.pid=pid;
    	 this.alertType=alertType;
    	 this.msgId=msgId;
    	 this.scheduleTime=scheduleTime;
    	 this.lastTry=lastTry;
    }
     
     public Alert(){}
     
     public String getAlertId(){
    	 return aid;
     }
     
     public void setAlertId(String id){
    	 aid=id;
     }
     
     public String getPatientId(){
 		return pid;
 	 }
 	
     public void setPatientId(String id){
 		pid=id;
 	 }
     
     public void setAlertType(int type){
    	 alertType=type;
     }
     
     public int getAlertType(){
    	 return alertType;
     }
     
     public void setMessageId(int id){
    	 msgId=id;
     }
     
     public int getMessageId(){
    	 return msgId;
     }
     
    
     public void setScheduleTime(Timestamp time){
    	 scheduleTime=time;
     }
     
     public Timestamp getScheduleTime(){
    	 return scheduleTime;
     }
     
     public Timestamp getLastTried(){
    	 return lastTry;
     }
     
     public void setLastTried(Timestamp datetime){
    	 lastTry=datetime;
     }
     
     public boolean  getIsExecuted(){
    	 return isExecuted;
     }
     
     public void setIsExecuted(boolean  status){
    	 isExecuted=status;
     }
     
     public void setretryCount(int count){
    	 retryCount=count;
     }
     
     public int getretryCount(){
    	 return retryCount;
     }
     
     public String getServiceInfo(){
  		return serviceInfo;
  	 }
     
     public void setServiceInfo(String s){
    	 serviceInfo=s;
     }
     
}
