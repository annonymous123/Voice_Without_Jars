package org.raxa.scheduler;

	public class AlertInfo {

		
		private String pnumber;
		private String preferLanguage;
		private int msgId;
		private String aid;  
		
		public AlertInfo(String pnumber,String preferLanguage,int msgId,String aid){
			this.pnumber=pnumber;
			this.preferLanguage=preferLanguage;
			this.msgId=msgId;
			this.aid=aid;
		}
		public String getPhoneNumber(){
			return pnumber;
		}
		
		public String getAlertId(){
			return aid;
		}
		
		public int getMsgId(){
			return msgId;
		}
		
		public String getpreferLanguage(){
			return preferLanguage;
		}
}


