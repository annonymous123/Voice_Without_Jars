package org.raxa.registration;

public interface registrationInterface {
	
	/*
	 * This will add Reminder for alertType(1 for IVR and 2 for SMS) the patient with uuid:pid.
	 * The reminder will be in effect from the next day(midnight).
	 */
	public boolean addReminder(String pid,String preferLanguage,int alertType);
	
	/*
	 * This will stop sending reminder to the patient with uuid:pid for alert of alertType(1 for IVR and 2 for SMS)
	 */
	public boolean deleteReminder(String pid,int alertType);
	
	
}
