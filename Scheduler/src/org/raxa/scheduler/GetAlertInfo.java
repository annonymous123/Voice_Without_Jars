/*
 * It will provide patient content of Message that will be played or texted to the patient.
 * 
 * provide information given one of the following info
 * 1.patientId,alertType
 * 2.patientnumber,alertType
 * 3.scheduleTime,AlertType
 */

package org.raxa.scheduler;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.raxa.database.HibernateUtil;
import org.raxa.database.VariableSetter;


import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Properties;


public class GetAlertInfo implements VariableSetter,MedicineInformerConstant {
	static Logger logger = Logger.getLogger(GetAlertInfo.class);
	
	/**
	 * Get all alerts from table alert where scheduleTime to be reminded is less than time(system time)  
	 * @param time
	 * @param alertType
	 * @return
	 */
	public List<AlertInfo> getPatientInfoOnTime(Date time,int alertType){
		String hql=null;Session session = HibernateUtil.getSessionFactory().openSession();
		List<AlertInfo> a=null;
		if(alertType==IVR_TYPE)
			  hql=IVR_MEDICINE_QUERY_DATE;
		if(alertType==SMS_TYPE)
			  hql=SMS_MEDICINE_QUERY_DATE;
		
		 try{
			 logger.info("Getting the alerts for the patient who need to be called before:"+time.toString());
			 session.beginTransaction();
			 Query query=session.createQuery(hql);
			 query.setTimestamp("systemTime", time);
			 query.setBoolean("isExecuted",false);
			 query.setInteger("alertType",alertType);
			 query.setInteger("retryCount",getMaxRetry());
			 Iterator results=query.list().iterator();
			 a=getPatientList(results);
		 }
		 catch(Exception ex){
			 logger.error("Error in getPatientListOnTime "+time.toString());
			 ex.printStackTrace();
			 return null;
		 }
		 session.getTransaction().commit();
		 session.close();
		 return a;
	}
	/**
	 * return list of AlertInfo from 
	 * @param results
	 * @return List<AlertInfo>
	 */
	public List<AlertInfo> getPatientList(Iterator results){
		List<AlertInfo> listOfPatients=new ArrayList<AlertInfo>();
		if(!(results.hasNext()))
			return null;
		try{
			Object[] row=(Object[]) results.next();
			
			while(true){
			  String pnumber=(String) row[0];
			  String preferLanguage=(String) row[1];
			  int msgId=(Integer) row[2];
			  String aid=(String) row[3];
			  listOfPatients.add(new AlertInfo(pnumber,preferLanguage,msgId,aid));
			  if(results.hasNext())
				  row=(Object[]) results.next();
			  else break;
			}
		}
		catch(Exception ex){
			logger.error("unable to get patientList on "+new Date());
			return null;
		}
		return listOfPatients;
	}
	
	/**
	 * return (max)MsgId+1 from ivrMsg
	 * @return 
	 */
	public int getMaxRetry(){
		Properties prop = new Properties();
		int MAX_TRY=3;						//initialising;if try fails.
		try{
		logger.info("Trying to get the max retry count");
		prop.load(GetAlertInfo.class.getClassLoader().getResourceAsStream("config.properties"));
		MAX_TRY=Integer.parseInt(prop.getProperty("Max_Retry"));
		return MAX_TRY;
		}
		catch (IOException ex) {
			logger.info("Unable to get the max retryCount,setting it to 3");
    		return MAX_TRY;
        }
	}
}