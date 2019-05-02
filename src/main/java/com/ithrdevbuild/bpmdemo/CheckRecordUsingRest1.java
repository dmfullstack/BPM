package com.ithrdevbuild.bpmdemo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;

import com.ithrdevbuild.bpmdemo.crm.Link_name_to_fields_array;
import com.ithrdevbuild.bpmdemo.crm.SugarsoapLocator;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.multipart.FormDataMultiPart;

/*import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;*/


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.multipart.FormDataMultiPart;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
* This class checks if existing record available. 
* 
* @author dineshmetkari
* 
*/
public class CheckRecordUsingRest1 implements JavaDelegate {

	private final Logger LOGGER = Logger.getLogger(CheckRecordUsingRest.class.getName());
	
	public static final String REST_URL = "http://demo.m8solutions.com/sugar/service/v4/rest.php";
	
	public static final String SOAP_URL = "http://demo.m8solutions.com/sugar/service/v4/soap.php?wsdl";
	
	  private com.sun.jersey.api.client.Client client;
	    private ObjectMapper mapper = null;
	    private String sugarHost = "http://demo.m8solutions.com";
	    private String restUri = "/sugar/service/v4/rest.php?";
	    
	    @Override
		public void execute(DelegateExecution delegateExecution) throws Exception {
			
			String sessionid=login();
		  	addContact(sessionid);
		  	delegateExecution.setVariable("foundRecord", true);
	    }

	/*@Override
	public void execute1(DelegateExecution delegateExecution) throws Exception {
		
		String sessionid=login();
	  	addContact(sessionid);
		
        *//**
         * SOAP Client
         *//*
		
	 	String phoneno = (String)delegateExecution.getVariable("phoneno");
    	String firstname = (String)delegateExecution.getVariable("firstname");
    	String lastname = (String)delegateExecution.getVariable("lastname");
    	String title = (String)delegateExecution.getVariable("title");
    	String department = (String)delegateExecution.getVariable("department");
    	
        try {
        	
        	String url = "http://demo.m8solutions.com/sugar/service/v4/soap.php?wsdl";
			SugarsoapLocator loc = new SugarsoapLocator();
			com.ithrdevbuild.bpmdemo.crm.SugarsoapPortType pt = loc.getsugarsoapPort(new java.net.URL(url));
			com.ithrdevbuild.bpmdemo.crm.User_auth user_auth = new com.ithrdevbuild.bpmdemo.crm.User_auth();
			user_auth.setUser_name("admin");
			user_auth.setPassword("fe01ce2a7fbac8fafaed7c982a04e229");

			java.lang.String application_name = null;
			com.ithrdevbuild.bpmdemo.crm.Name_value[] name_value_list = new com.ithrdevbuild.bpmdemo.crm.Name_value[1];
			com.ithrdevbuild.bpmdemo.crm.Name_value nv = new com.ithrdevbuild.bpmdemo.crm.Name_value();
			nv.setName("");
			nv.setValue("Contacts");
			com.ithrdevbuild.bpmdemo.crm.Entry_value ev = pt.login(user_auth, application_name, name_value_list);

			String mn = ev.getModule_name();
			System.out.println(mn);

			com.ithrdevbuild.bpmdemo.crm.Name_value[] nvArr = ev.getName_value_list();
			for (int x = 0; x < nvArr.length; x++) {
				System.out.println(nvArr[x].getName() + " = " + nvArr[x].getValue());
			}

			Link_name_to_fields_array[] arr = new Link_name_to_fields_array[1];
			arr[0] = new Link_name_to_fields_array();
			arr[0].setName("phone_work");
			arr[0].setValue(new String[] { phoneno });

			
			 * get_entries(java.lang.String session, java.lang.String module_name, java.lang.String[] ids, java.lang.String[] select_fields, com.sugarcrm.www.sugarcrm.Link_name_to_fields_array[] link_name_to_fields_array, boolean track_view) throws java.rmi.RemoteException;
			 
			
			com.ithrdevbuild.bpmdemo.crm.Get_entry_result_version2 getRes = pt.get_entries(null, "Contacts", null, null,arr, true);

			com.ithrdevbuild.bpmdemo.crm.Entry_value[] evArr = getRes.getEntry_list();
			if (evArr!=null) {
			for (com.ithrdevbuild.bpmdemo.crm.Entry_value evx : evArr) {
				System.out.println(evx.getId() + " " + evx.getModule_name());
				delegateExecution.setVariable("foundRecord", true);
			}
			} else {
				delegateExecution.setVariable("foundRecord", false);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
        
        *//** 
		 * Rest Client
		 *//*
		
		try {
			
			Client client = ClientBuilder.newClient().register(new RestAuthenticator("admin", "fe01ce2a7fbac8fafaed7c982a04e229"));
	        WebTarget target = client.target(REST_URL);
	        target = target.queryParam("module_name", "Contacts").queryParam("phone_work", phoneno);
	        System.out.println(target.getUri().toString());
	
	        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE);
	        Response response = builder.get();
	        String list = response.readEntity(String.class);
	        System.out.println(""+list);
        
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}*/
	
	 /***
     * Attempts to login and returns a session id if logged in.
     * Multi-part form post.
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public String login() throws NoSuchAlgorithmException, IOException{
        // user credentials, should be obvious but this user credentials as well as the host ip should be passed in via
        // config file.
        String username = "admin";
        String password = "demo";

        String url = String.format("%s%s", sugarHost, restUri);

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String passwordHash = new BigInteger(1, md5.digest(password.getBytes()))
                .toString(16);

        // the order is important, so use a ordered map
        Map<String, String> userCredentials = new LinkedHashMap<String, String>();
        userCredentials.put("user_name", username);
        userCredentials.put("password", passwordHash);

        Map<String, Object>bodyPart = new LinkedHashMap<String, Object>();
        bodyPart.put("user_auth", userCredentials);
        bodyPart.put("application_name", "RestClient");

        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.field("method", "login");
        multiPart.field("input_type", "JSON");
        multiPart.field("response_type", "JSON");
        multiPart.field("rest_data", mapper.writeValueAsString(bodyPart));

        ClientResponse clientResponse = client.resource(url)
                .accept("application/json")
                .entity(multiPart)
                .type(MediaType.MULTIPART_FORM_DATA_TYPE)
                .post(ClientResponse.class);

        String body = clientResponse.getEntity(String.class);
        System.out.println("Response BOdy:"+body);
        InputStream stream = new ByteArrayInputStream(body.getBytes());
        JsonNode root = mapper.readValue(stream, JsonNode.class);
        System.out.println("JsonNode:"+root.toString());
        stream.close();

        String id = null;
        if(root.has("id")){
            id = root.path("id").getTextValue();
        }
    	//addLead(id);
  
        return id;
    }

    
	 /***
     * Uses a sessionID and attempts to add new Lead record.
     * @param sessionId
     * @return
     */
    public String addContact(String sessionId)throws JsonGenerationException, JsonMappingException, IOException{
        String result = null;
        String url = String.format("%s%s", sugarHost, restUri);

        Map<String, Object> details = new LinkedHashMap<String, Object>();
        details.put("session", sessionId);
        details.put("module", "Contacts");

        List<Map<String, String>>lead = new ArrayList<Map<String, String>>();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("name", "first_name");
        map.put("value", "hello");
        lead.add(map);

        map = new LinkedHashMap<String, String>();
        map.put("name", "last_name");
        map.put("value", "world");
        lead.add(map);

     /*   map = new LinkedHashMap<String, String>();
        map.put("name", "status");
        map.put("value", "New");
        lead.add(map);
*/
        map = new LinkedHashMap<String, String>();
        map.put("name", "phone_work");
        map.put("value", "123.999.8888");
        lead.add(map);

        map = new LinkedHashMap<String, String>();
        map.put("name", "title");
        map.put("value", "Mr");
        lead.add(map);
        
        map = new LinkedHashMap<String, String>();
        map.put("name", "department");
        map.put("value", "IT");
        lead.add(map);
     
        /*	
        map = new LinkedHashMap<String, String>();
        map.put("name", "email1");
        map.put("value", "john@somewhere.com");
        lead.add(map);

       map = new LinkedHashMap<String, String>();
        map.put("name", "lead_source");
        map.put("value", "Web Site");
        lead.add(map);

        map = new LinkedHashMap<String, String>();
        map.put("name", "status_description");
        map.put("value", "This is a lead added by rest call. This would contain details requested by the user.");
        lead.add(map);

        map = new LinkedHashMap<String, String>();
        map.put("name", "lead_source_description");
        map.put("value", "source of the lead");
        lead.add(map);

        map = new LinkedHashMap<String, String>();
        map.put("name", "account_name");
        map.put("value", "Acme Does Something Right Company");
        lead.add(map);

        map = new LinkedHashMap<String, String>();
        map.put("name", "primary_address_street");
        map.put("value", "123 Some Street");
        lead.add(map);

        map = new LinkedHashMap<String, String>();
        map.put("name", "primary_address_city");
        map.put("value", "denver");
        lead.add(map);

        map = new LinkedHashMap<String, String>();
        map.put("name", "primary_address_state");
        map.put("value", "CO");
        lead.add(map);

        map = new LinkedHashMap<String, String>();
        map.put("name", "primary_address_postalcode");
        map.put("value", "12345");
        lead.add(map);*/

        details.put("name_value_list", lead);

        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.field("method", "set_entry");
        multiPart.field("input_type", "JSON");
        multiPart.field("response_type", "JSON");
        multiPart.field("rest_data", mapper.writeValueAsString(details));

        ClientResponse clientResponse = client.resource(url)
                .accept("application/json")
                .entity(multiPart)
                .type(MediaType.MULTIPART_FORM_DATA_TYPE)
                .post(ClientResponse.class);

        // TODO: Examine the response for codes coming from the service and response appropriately.
        result = clientResponse.getEntity(String.class);
        System.out.println(result);
        return result;
    }

}
