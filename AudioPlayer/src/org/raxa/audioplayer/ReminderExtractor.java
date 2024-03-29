

package org.raxa.audioplayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import org.raxa.alertmessage.MedicineInformation;
import org.raxa.alertmessage.MessageTemplate;
import org.raxa.database.Alert;
import org.raxa.database.HibernateUtil;
import org.raxa.database.IvrMsg;



public class ReminderExtractor {
		private String pid;
		private Logger logger = Logger.getLogger(this.getClass());
		
		public ReminderExtractor(String pid){
			this.pid=pid;
			
		}
		
		public ReminderExtractor(){}
		 
		protected List<MedicineInformation> getMedicineInfo(Date time){
			return getMedicineInfo(pid,time);
		}
		/**
		 * Return all patient medicine info,whose id is pid to be,to be taken from the start of the day.
		 * @param pid
		 * @return
		 */
		protected List<MedicineInformation> getMedicineInfo(String pid,Date time){
			System.out.println("In getmedicine Info:"+pid);
			List<MedicineInformation> list=new ArrayList<MedicineInformation>();
			try{
			time.setHours(0);
			time.setMinutes(1);
			Session session=HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			String hql="from Alert where scheduleTime>=:time and pid=:pid order by scheduleTime";
			Query query=session.createQuery(hql);
			query.setString("pid", pid);
			query.setTimestamp("time",time);
			List<Alert> content=(List<Alert>)query.list();
			for(Alert alert: content){
				List<String> itemContents=new ArrayList<String>();
		   		String hql2="from IvrMsg where ivrId=:msgId order by itemNumber";
	    		Query query2=session.createQuery(hql2);
	    		query2.setInteger("msgId",alert.getMessageId());
	    		List<IvrMsg> results=(List<IvrMsg>)query2.list();	
	    		for(IvrMsg i:results){
	    			itemContents.add(i.getContent());
	    			System.out.println(i.getContent());
	    		}
	    			MedicineInformation m=new MessageTemplate().getMedicineInformation(itemContents);
	    			m.setScheduleTime(alert.getScheduleTime());
	    			list.add(m);
	    	}
			
			session.getTransaction().commit();
			session.close();
			}
			catch(Exception ex){
				ex.printStackTrace();
				logger.info("Some error occured while in incoming-call and fetching information about patient with id "+pid);
				list=null;
			}
			return list;
		}

		
	
}
