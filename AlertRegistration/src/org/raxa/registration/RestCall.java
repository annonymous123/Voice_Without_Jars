package org.raxa.registration;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;


public class RestCall {
	static String username = null;
	static String password = null;
	static String URLBase = null;
	private static Logger logger=Logger.getLogger(RestCall.class);
	/**
	 * 
	 * HTTP POST
	 * @param URLPath
	 * @param input
	 * @return boolean
	 * @throws Exception
	 */
	public static Boolean getRequestPost(String URLPath, StringEntity input){
        String URL = URLBase + URLPath;
        Boolean response =  false;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
        	HttpPost httpPost = new HttpPost(URL);
        	System.out.println(URL);
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
            BasicScheme scheme = new BasicScheme();
            Header authorizationHeader = scheme.authenticate(credentials, httpPost);
            httpPost.setHeader(authorizationHeader);
            httpPost.setEntity(input);
            //System.out.println("Executing request: " + httpGet.getRequestLine());
            //System.out.println(response);
//            response = httpclient.execute(httpGet,responseHandler);
            HttpResponse responseRequest = httpclient.execute(httpPost);
            
    		if (responseRequest.getStatusLine().getStatusCode() != 204 && responseRequest.getStatusLine().getStatusCode() != 201) {
    			throw new RuntimeException("Failed : HTTP error code : "
    				+ responseRequest.getStatusLine().getStatusCode());
    		}
     
    		
    		httpclient.getConnectionManager().shutdown();
    		response = true;
        } 
        catch(Exception ex){
        	ex.printStackTrace();
        }
        finally {
            httpclient.getConnectionManager().shutdown();
        }
        return response;
    }
	/**
	 * HTTP GET
	 * @param URLPath
	 * @return
	 * @throws Exception
	 */
	public static String getRequestGet(String URLPath){
        String URL = URLBase + URLPath;
        String response =  "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
         HttpGet httpGet = new HttpGet(URL);

            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
            BasicScheme scheme = new BasicScheme();
            Header authorizationHeader = scheme.authenticate(credentials, httpGet);
            httpGet.setHeader(authorizationHeader);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            
            logger.info("Executing request: " + httpGet.getRequestLine());
            
           response = httpclient.execute(httpGet,responseHandler);
      		/*   ObjectMapper m=new ObjectMapper();
			JsonNode rootNode = m.readTree(new File("resource/reminder.json"));
			return(rootNode.toString());
			*/
        } 
        catch(Exception ex){
        	ex.printStackTrace();
        	return null;
        }
        finally {
            httpclient.getConnectionManager().shutdown();
        }
       return response;
    }


	public static void setUsername(String username) {
		RestCall.username = username;
	}


	public static void setPassword(String password) {
		RestCall.password = password;
	}


	public static void setURLBase(String uRLBase) {
		URLBase = uRLBase;
	}
  
}
