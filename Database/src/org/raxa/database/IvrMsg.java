package org.raxa.database;

public class IvrMsg {
		
	private int id;
	
	private int ivrId;
	
	private int itemNumber;
	
	private String content;
	
	public IvrMsg(int id,int number,String content){
		
		this.ivrId=id;
		this.itemNumber=number;
		this.content=content;
	}
	
	public IvrMsg(){}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public int getIvrId(){
		return ivrId;
	}
	
	public void setIvrId(int id){
		ivrId=id;
	}
	
	public int getItemNumber(){
		return itemNumber;
	}
	
	public void setItemNumber(int number){
		itemNumber=number;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String text){
		content=text;
	}
	
	
}
