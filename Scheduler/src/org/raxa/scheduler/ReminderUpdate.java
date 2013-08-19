package org.raxa.scheduler;

import java.sql.Timestamp;
import java.util.List;
import org.raxa.alertmessage.GetJson;
import org.raxa.alertmessage.MessageTemplate;
import org.raxa.alertmessage.Reminder;
import org.raxa.database.Alert;
import org.raxa.database.HibernateUtil;
import org.raxa.database.IvrMsg;
import org.raxa.database.VariableSetter;
import org.apache.log4j.Logger;
import org.hibernate.Session;


public class ReminderUpdate implements VariableSetter {
	protected Logger logger = Logger.getLogger(this.getClass());

	public void resetReminder(String pid,String name,String preferLanguage,int alertType){
		GetJson json=new GetJson();
		if(alertType==IVR_TYPE){
			List<Reminder> reminder=json.getAlert(pid);
			MessageTemplate m=new MessageTemplate();
			if((!(reminder==null)) && reminder.size()>=1){
				for(Reminder r:reminder){
					List<String> template=m.templatize(r.getrawmessage(), preferLanguage, name, pid);  //have not implemented the feature to join all alert that occur between 30 minutes interval.Must incl}
					if(!(template.equals(null) && template.size()>1)){
						r.setTemplatizeMessage(template);
						addReminderToDatabase(pid,r,alertType);
					}
				}
			}
	   }
	}
			
	public void addReminderToDatabase(String pid,Reminder r,int alertType){
		logger.info("Adding reminder to database for patient:"+pid);
		int msgId=getMsgId();int count=0;			//return the max+1 msgID
		Session session = HibernateUtil.getSessionFactory().openSession();
		Timestamp time=new Timestamp(r.getTime().getTime());
		Alert a=new Alert(r.getAlertId(),pid,alertType,msgId,time,null);a.setretryCount(0);a.setIsExecuted(false);a.setServiceInfo(null);
		session.beginTransaction();
		for(String content:r.getTemplatizeMessage()){
			IvrMsg msg=new IvrMsg(msgId,++count,content);
			int id = (Integer) session.save(msg);
			msg.setId(id);
			session.persist(msg);
		}
		session.save(a);
		session.getTransaction().commit();
		session.close();
		logger.info("Added reminder for alert of patient:"+pid);
	}
	/*
	 * Return max msgId + 1.
	 *
	 */
	
	public int getMsgId(){
		int maxID;
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		String hql="select max(a.ivrId) from IvrMsg a";
		List list = session.createQuery(hql).list(); 
		if((Integer)list.get(0)==null)
			maxID=0;
		else 
			maxID = ( (Integer)list.get(0) ).intValue();
		session.getTransaction().commit();
		session.close();
		return maxID+1;
	}

}
