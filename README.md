=============================================
INSTALLING AND CONFIGURING ASTERISK

=============================================
To install asterisk follow this link http://blogs.digium.com/2012/11/14/how-to-install-asterisk-11-on-ubuntu-12-4-lts/
     
Ensure to  use make menuselect while installing asterisk 
   
select format_mp3.so from make menuselect:it will load module to play mp3 files.

Sample sip.conf,manager.conf,extensions.conf,logger.conf are present in asteriskConf Folder

=============================================

;Configuring /etc/asterisk/sip.conf:Create a SIP user SIP/1000abc that have context=incoming-call
 
 ;For testing purpose it is necessary to create SIP/1000abc as org.raxa.scheduler.OutgoingCallManager redirects all call to sip/1000abc

=============================================

[1000abc]

type=peer

allow=all

udpbindaddr=0.0.0.0 

bindaddr=0.0.0.0

secret=yoursecret

host=dynamic

context=incoming-call
   
=============================================   
;Configuring /etc/asterisk/extensions.conf:add two context outgoing-call and incoming call

=============================================


[outgoing-call]

exten=>100,1,SET(count=0)

exten=>100,2,AGI(agi://127.0.0.1/hello.agi?msgId=${msgId}&language=${preferLanguage}&aid=${aid}&ttsNotation=${ttsNotation})

exten=>100,3,GOTO(outgoing-call,122,1)


exten=>122,1,NoOp(Text:${message})

same=>n,NoOp(Text:${language})

;Here googletranslate goes

;now only support english

same=>n,agi(googletts.agi,${message},en)

same=>n,GOTO(outgoing-call,100,2)



[incoming-call]

exten=>100,1,Answer()

same=>n,AGI(agi://127.0.0.1/hello.agi)


=============================================
;edit /etc/asterisk/manager.conf and add the following lines

;follow http://ofps.oreilly.com/titles/9781449332426/asterisk-AMI.html for further details

=============================================

[general]

enabled = yes

port = 5038

bindaddr = 127.0.0.1

webenabled=yes

allowmultiplelogin=yes


[manager]

secret = squirrel

deny = 0.0.0.0/0.0.0.0

permit = 127.0.0.1/255.0.0.0

read=system,call,log,verbose,agent,command,user,all,call,user

write=system,call,log,verbose,agent,command,user,all


=============================================
;edit /etc/asterisk/logger.conf : This is done to log information about asterisk server.Suppose your project location is 

;/home/user/Project_Voice/logFiles/asteriskLog. add the following line in logger.conf

=============================================

/home/user/Project_Voice/logFiles/asteriskLog => notice,warning,error,dtmf


=============================================
INSTALLING A SIP PHONE

=============================================

install any sip phone.This is a way to install twinkle
 
 sudo apt-get update
 
 sudo apt-get install twinkle
  
 For configuring twinkle:http://www.callcentric.com/support/device/twinkle


=============================================
INSTALLING GOOGLE TTS

=============================================
  follow this Link:https://github.com/zaf/asterisk-googletts
   
  for testing use the example in here :http://zaf.github.io/asterisk-googletts/   


=============================================
INSTALL ANT

=============================================
  sudo apt-get -u install ant
  
  set environment variable ANT_HOME JAVA_HOME
  
  follow this link:http://ant.apache.org/manual/install.html



=============================================
INSTALLING JDK IN UBUNTU
 
Follow this:http://www.wikihow.com/Install-Oracle-Java-on-Ubuntu-Linux

=============================================


=============================================
SOURCE CODE CONFIGURATION AND DEPENDENCY

=============================================

build.xml creates a jar of the module

build1.xml creates a "fat" jar of the module i.e that jar will include all jars used by module.

AlertMessage,AlertRegistration,Database are non-runnable jar(no main function)

  
============================================= 
IMPORTANT-DEPENDENCY:AlertRegistration,Scheduler,AudioPlayer require hibernate jars.Due to size concern they are not included in source code.
  
============================================= 

Copy /Database/lib/Database to
    
1.AlertRegistration/lib
     
2.Scheduler/lib
    
3.AudioPlayer/lib


=============================================
Steps to run the project(to be followed in the order as described)

=============================================

1.Database:Edit /resource/hibernate.cfg.xml according to your requirement.Set username,Password and url

2.ant compile jar

3.IMPORTANT:Copy the compiled jar to Scheduler/lib,AudioPlayer/lib,AlertRegistration/lib



4.Open AlertMessage

5.open english.properties and other language.properties file and fill in the require fields.

6.IMPORTANT:Create jar(ant compile jar)Copy the compiled jar to Scheduler/lib,AudioPlayer/lib


7.Open AlertRegistration

8.fill the properties file

9.Use this jar to register Patient for Reminders

10.Read the  org/raxa/registration/registrationInterface.java to know about the API



11.open AudioPlayer

12. fill the properties file

13.ant compile jar run



14.open  Scheduler

15.fill the properties file

16. ant compile jar run


 
   
