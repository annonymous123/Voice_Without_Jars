package org.raxa.audioplayer;

import java.sql.Timestamp;

public class classA {
		private int msgId;
		private Timestamp scheduleTime;
		
		public classA(int a,Timestamp b){
			msgId=a;
			scheduleTime=b;
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
	     
	     public String toString(){
	    	 return "msgId:"+msgId+" "+"scheduleTime:"+scheduleTime;
	     }
}
