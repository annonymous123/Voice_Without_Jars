package org.raxa.database;

public class SmsMsg {
	
private int id;	
	
private int smsId;

private int itemNumber;

private String content;

public SmsMsg(int id,int number,String content){
	
	this.smsId=id;
	this.itemNumber=number;
	this.content=content;
}

public SmsMsg(){}

public int getId(){
	return id;
}

public void setId(int id){
	this.id=id;
}

public int getSmsId(){
	return smsId;
}

public void setSmsId(int id){
	smsId=id;
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