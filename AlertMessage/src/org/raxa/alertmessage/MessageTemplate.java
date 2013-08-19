/*
 * This class does the following:
 * Input:raw message String or Template message String.
 * 1)Parse the Alert Message.
 * 2)Then it templatize the message and store the string as JSON Format with the Json schema mapped from ContentForamat.Class
 * 4)mode-1 will play using TTS and mode-2 will play using Audio Files provided the location of  audio file is in preferLanguage.properties and 
 * is stored as tagName. eg in hindi.properties we have header1=/home/atul/Documents/voice/greetings/hindi/header1.mp3
 * 5)if mode not found default is 1
 * 6)Assuming mode of dose and medicine name to be 1.Can be changed.
 * 
 * CAUTION:
 * 		1)Any type of punctuation like ',' '.' is highly discouraged as it will make the tts to stop executig that line
 * 		2)Medicine name should be send as lowercase otherwise the TTS will pronounce each Letter of the word.
 *      
 *
 *Though it is used by the server every midnight to update alert,it needs to be changed if there is any change in message format
 *
 *Method:parseMessage(String message) takes the message and templatise it.Any changes in message format should be reflected there.
 *
 *Assuming only tablet and volume exist.More data needs to be provided to know what kind og medicine it is.Should be updated.
 *Right now considering all medicine to be tablet.If a medicine is volume isTablet should be set to false
 *
 */

package org.raxa.alertmessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class MessageTemplate implements MessageInterface {
		
		Properties prop = new Properties();
		private Logger logger = Logger.getLogger(this.getClass());
		private List<String> Tmessage;
		private String medicineDose;
		private String medicine;			//should be in lowerCase
		private String tabletOrVolume;  // either "tablet" or volume;
		private String name;
		private final String PatientNameFieldRepresentation="name";
		private final String MedicineFieldRepresentation="medicine";
		private final String MedicineTypeFieldRepresentation="type";
		private final String ModeFieldRepresentation="mode";
		private final String Header1Representation="header1";
		private final String Header2TypeFieldRepresentation="header2";
		private final String Footer1FieldRepresentation="footer1";
		private final String EndingFieldRepresentation="ending";
		private final String DoseFieldRepresentation="dose";
		
		private boolean isTablet;
		
		
		/**
		 * 	 * For now Assuming Template of Raw Message is "Take x of y" as the template of Raxa Alert is not Ready
		 * Even Assuming that y is always a doze as the template as of now does not provide that information.
		 * Tags:
		 * "header1":Greetings(eg.Welcome to Raxa.Good Morning)
		 * "name":Name of the Patient
		 * "header2":Introductory Line (eg.Today You have to take)
		 * "dose":doze of Medicine(Solid)
		 *"tablet"
		 *"volume"
		 * "footer1":Language Specific (eg:(in Hindi) lena hai)
		 * "ending":Good-Bye Message (eg.We hope you will get well soon.GoodBye)
		 * @param message
		 * @param preferLanguage
		 * @param name
		 * @param pid
		 * @return
		 */
		
		public List<String> templatize(String message,String preferLanguage,String name,String pid){
			this.name=name;
			Properties prop = new Properties();
			parseMessage(message);
			String propertyFile=preferLanguage.toLowerCase()+"properties";
			Tmessage=new ArrayList<String>();
			String header1,header2,footer1,ending;
			int header1mode,header2mode,footer1mode,endingmode,tabletOrVolumemode;
			try{
				logger.info("Trying to get mode in which the words should be spoken from "+propertyFile);
				prop.load(this.getClass().getClassLoader().getResourceAsStream(propertyFile));
				header1mode=Integer.parseInt(prop.getProperty("header1mode",String.valueOf(TTS_MODE)));  //If headerMode not provided it will use TTS
				header2mode=Integer.parseInt(prop.getProperty("header2mode",String.valueOf(TTS_MODE)));
				footer1mode=Integer.parseInt(prop.getProperty("footer1mode",String.valueOf(TTS_MODE)));
				endingmode=Integer.parseInt(prop.getProperty("endingmode",String.valueOf(TTS_MODE)));
				tabletOrVolumemode=Integer.parseInt(prop.getProperty("tabletOrVolume",String.valueOf(TTS_MODE)));
			}
			catch(Exception ex3){
				logger.error("Unable to set mode,making all mode to 1 for patient ID  "+pid);
				header1mode=header2mode=footer1mode=endingmode=tabletOrVolumemode=TTS_MODE;
			}
		//This part is hard-coded.Proper changes to be made once raxa-alert API is well documented	
			try{
				logger.info("Setting the content of the message"+propertyFile);
				prop.load(this.getClass().getClassLoader().getResourceAsStream("english.properties"));
				header1=prop.getProperty("header1Text",null);
				header2=prop.getProperty("header2Text",null);
				footer1=prop.getProperty("footer1Text",null);
				ending=prop.getProperty("endingText",null);
				Tmessage.add(convertToJsonString(new ContentFormat(Header1Representation,header1,header1mode)));
				Tmessage.add(convertToJsonString(new ContentFormat(PatientNameFieldRepresentation,name,TTS_MODE)));
				Tmessage.add(convertToJsonString(new ContentFormat(Header2TypeFieldRepresentation,header2,header2mode)));
				Tmessage.add(convertToJsonString(new ContentFormat(DoseFieldRepresentation,medicineDose,TTS_MODE)));
				
				if(isTablet)
					tabletOrVolume="tablet";
				else tabletOrVolume="volume";
					
				Tmessage.add(convertToJsonString(new ContentFormat(MedicineTypeFieldRepresentation,tabletOrVolume+ "of",tabletOrVolumemode)));
				Tmessage.add(convertToJsonString(new ContentFormat(MedicineFieldRepresentation,medicine.toLowerCase(),TTS_MODE)));
				Tmessage.add(convertToJsonString(new ContentFormat(Footer1FieldRepresentation,footer1,footer1mode)));
				Tmessage.add(convertToJsonString(new ContentFormat(EndingFieldRepresentation,ending,endingmode)));
			}
			catch(IOException ex) {
				logger.error("Unable to set the content of the message for patient"+pid+" with message "+message);
				Tmessage=null;
				return null;
			}
		
			return Tmessage;
			
		}
		
		public void parseMessage(String message){
			String separator=" ";
			String[] content=message.split(separator);
			medicineDose=content[1];
			String lastWordBeforeMedicineName=" of "; // of y
			int indexOfMedicine=message.lastIndexOf(lastWordBeforeMedicineName)+lastWordBeforeMedicineName.length();   //Since medicine can be of two or more words
			medicine=message.substring(indexOfMedicine, message.length());
			isTablet=checkIfTabletOrVolume(medicine);
		}
		
		
		/**
		 * return either "tablet" or "volume".Right now the template does not provide the info.
		 * Will change according to template of message;
		 * @param medicine
		 * @return boolean
		 */
		public boolean checkIfTabletOrVolume(String medicine){
			//return fales if volume
			return true;					
		}
		
		/**
		 * convert ContentFormat Object to json String
		 * @param format
		 * @return JSONString
		 */
		public String convertToJsonString(ContentFormat format){
			ObjectMapper m = new ObjectMapper();
			ObjectNode jString=m.createObjectNode();
			jString.put("field", format.getField());
			jString.put("content",format.getContent());
			jString.put("mode",format.getMode());
			return jString.toString();
		}
		/**
		 * De Templatise the message
		 * Be sure that the words are not in capital Letters. eg.Acetemide,acetamide are fine but not ACETAMIDE
		 * @param contents of a particular msgId
		 * @return exclude static part like header1,header2,footer1,ending
		 */
		public MedicineInformation getMedicineInformation(List<String> contents){
			MedicineInformation m=new MedicineInformation();
			for(String item : contents){
				ContentFormat info= parseString(item);
			
				if(info.getField().equals(MedicineFieldRepresentation))
					m.setName(info.getContent().toLowerCase());
				else if(info.getField().equals(MedicineTypeFieldRepresentation)){
					m.setType(info.getContent());
					m.setMode(info.getMode());
				}
				else if(info.getField().equals(DoseFieldRepresentation))
					m.setDose(info.getContent());
				
			}
			
			return m;
		}
		
		public ContentFormat parseString(String itemContent){
			try{
				logger.info("Parsing the content of msgId(Json String)");
	    		ObjectMapper mapper = new ObjectMapper();
	    		return (mapper.readValue(itemContent, ContentFormat.class));
			}
			catch(Exception ex){
				return null;
			}
		}
		
	    public String getTextToconvertToVoice(MedicineInformation info){
	    	int hours=info.getScheduleTime().getHours();
			if(hours>=12)
				hours=hours-12;
			int minutes=info.getScheduleTime().getMinutes();
			String space=" ";
			String afterHour=":";		//Depends upon which works best with the TTS used.
			String afterMinute=" ";
			String message=info.getDose()+space+"of"+space+info.getName()+space+"at"+space+hours+afterHour+space+minutes+afterMinute;
			return message;
	    }
		
}		
