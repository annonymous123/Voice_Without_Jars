/*
 * Call SMSModule and Provide Info which will thn send message to patient
 */
package org.raxa.scheduler;



public class SMSSender implements Runnable {

	private AlertInfo patient;
	
	public SMSSender(AlertInfo patient){
		this.patient=patient;
	}
	
	public void run(){
	
	}
}


