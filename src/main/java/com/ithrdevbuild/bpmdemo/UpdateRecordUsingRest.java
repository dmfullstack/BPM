package com.ithrdevbuild.bpmdemo;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.multipart.FormDataMultiPart;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
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
 * Created by jeremiah on 1/16/14.
 *
 * This is modified from the original post here: http://www.providentcrm.com/news/being-restful-with-sugarcrm-api-and-java/
 * the original post included deprecated code.
 *
 * Posting to sugar's legacy REST Api (Sugar version 6.5.16 at the time of writing this). This is the REST api version
 * that comes out of the can in the community edition.
 * Posting here is weird, they are multi-part form POSTS and are a bit on the tricky side.
 *
 * Non community or upgraded versions need to be handled with different code.
 */
public class UpdateRecordUsingRest implements JavaDelegate {
    private Client client;
    private ObjectMapper mapper = null;
    private String sugarHost = "http://demo.m8solutions.com";
    private String restUri = "/sugar/service/v4/rest.php?";
    
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		String phoneno = (String)delegateExecution.getVariable("phoneno");
    	String firstname = (String)delegateExecution.getVariable("firstname");
    	String lastname = (String)delegateExecution.getVariable("lastname");
    	String title = (String)delegateExecution.getVariable("title");
    	String department = (String)delegateExecution.getVariable("department");
    	

		String sessionId= login();
		boolean isExistingContact = checkExistingContact(sessionId, phoneno, firstname, lastname, title, department);
		if (isExistingContact) {
			delegateExecution.setVariable("foundRecord", true);
		} else {
			delegateExecution.setVariable("foundRecord", false);
		    addContact(sessionId, phoneno, firstname, lastname, title, department);
			
		}

	}
	
    public static void main(String args[]) {
    	UpdateRecordUsingRest sc = new UpdateRecordUsingRest();
    	String phoneno = "99992222";
    	String firstname = "Ganesh";
    	String lastname = "Mane";
    	String title = "Consultant";
    	String department = "HR";
    	
    	try {
			sc.login();
			String sessionId= sc.login();
			boolean isExistingContact =sc.checkExistingContact(sessionId, phoneno, firstname, lastname, title, department);
			System.out.println("isExistingContact:" + isExistingContact);
			String contactID = sc.addContact(sessionId, phoneno, firstname, lastname, title, department);
			System.out.println("Added Contact with ID:" + contactID);
			isExistingContact =sc.checkExistingContact(sessionId, phoneno, firstname, lastname, title, department);
			System.out.println("isExistingContact:" + isExistingContact);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public UpdateRecordUsingRest(){
        client = Client.create();
        mapper = new ObjectMapper();
    }

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
    	//addContact(id);
        return id;
    }

    /***
     * Uses a sessionID and attempts to add new Lead record.
     * @param sessionId
     * @return
     */
    public String addLead(String sessionId)throws JsonGenerationException, JsonMappingException, IOException{
        String result = null;
        String url = String.format("%s%s", sugarHost, restUri);

        Map<String, Object> details = new LinkedHashMap<String, Object>();
        details.put("session", sessionId);
        details.put("module", "Leads");

        List<Map<String, String>>lead = new ArrayList<Map<String, String>>();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("name", "first_name");
        map.put("value", "dinesh");
        lead.add(map);

        map = new LinkedHashMap<String, String>();
        map.put("name", "last_name");
        map.put("value", "metkari");
        lead.add(map);

        map = new LinkedHashMap<String, String>();
        map.put("name", "status");
        map.put("value", "New");
        lead.add(map);

        map = new LinkedHashMap<String, String>();
        map.put("name", "phone_work");
        map.put("value", "111.222.4444");
        lead.add(map);

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
        lead.add(map);

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
    
    /***
     * Uses a sessionID and attempts to add new Lead record.
     * @param sessionId
     * @return
     */
    public String addContact(String sessionId, String phone_number, String first_name, String last_name, String title, String department)throws JsonGenerationException, JsonMappingException, IOException{
        String result = null;
        String url = String.format("%s%s", sugarHost, restUri);

        Map<String, Object> details = new LinkedHashMap<String, Object>();
        details.put("session", sessionId);
        details.put("module", "Contacts");

        List<Map<String, String>>lead = new ArrayList<Map<String, String>>();
        
        Map<String, String> map = new LinkedHashMap<String, String>();
             
        map.put("name", "first_name");
        map.put("value", first_name);
        lead.add(map);

        map = new LinkedHashMap<String, String>();
        map.put("name", "last_name");
        map.put("value", last_name);
        lead.add(map);
/*
        map = new LinkedHashMap<String, String>();
        map.put("name", "status");
        map.put("value", "New");
        lead.add(map);
*/
        map = new LinkedHashMap<String, String>();
        map.put("name", "phone_work");
        map.put("value", phone_number);
        lead.add(map);

      map = new LinkedHashMap<String, String>();
        map.put("name", "title");
        map.put("value", title);
        lead.add(map);
        
        map = new LinkedHashMap<String, String>();
        map.put("name", "department");
        map.put("value", department);
        lead.add(map);


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
        
        InputStream stream = new ByteArrayInputStream(result.getBytes());
        JsonNode root = mapper.readValue(stream, JsonNode.class);
        System.out.println("JsonNode:"+root.toString());
        stream.close();
        String id = null;
        if(root.has("id")){
            id = root.path("id").getTextValue();
        }
        
        return result;
    }
   
    /***
     * Uses a sessionID and attempts to add new Lead record.
     * @param sessionId
     * @return
     */
    public boolean checkExistingContact(String sessionId, String phone_number, String first_name, String last_name, String title, String department)throws JsonGenerationException, JsonMappingException, IOException{
        boolean isExistingContact=false;
    	String result = null;
        String url = String.format("%s%s", sugarHost, restUri);

        Map<String, Object> details = new LinkedHashMap<String, Object>();
        details.put("session", sessionId);
        details.put("module", "Contacts");

        List<Map<String, String>>lead = new ArrayList<Map<String, String>>();
        
        Map<String, String> map = new LinkedHashMap<String, String>();
             
        map.put("name", "first_name");
        map.put("value", first_name);
        lead.add(map);

        map = new LinkedHashMap<String, String>();
        map.put("name", "last_name");
        map.put("value", last_name);
        lead.add(map);
/*
        map = new LinkedHashMap<String, String>();
        map.put("name", "status");
        map.put("value", "New");
        lead.add(map);
*/
        map = new LinkedHashMap<String, String>();
        map.put("name", "phone_work");
        map.put("value", phone_number);
        lead.add(map);

      map = new LinkedHashMap<String, String>();
        map.put("name", "title");
        map.put("value", title);
        lead.add(map);
        
        map = new LinkedHashMap<String, String>();
        map.put("name", "department");
        map.put("value", department);
        lead.add(map);


        details.put("name_value_list", lead);

        FormDataMultiPart multiPart = new FormDataMultiPart();
        multiPart.field("method", "get_entry");
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
        
        InputStream stream = new ByteArrayInputStream(result.getBytes());
        JsonNode root = mapper.readValue(stream, JsonNode.class);
        System.out.println("JsonNode:"+root.toString());
  
        String id = null;
        if(root.has("id")){
        	isExistingContact=true;
            id = root.path("id").getTextValue();
            
        } else {
        	isExistingContact=false;
        }
        stream.close();
        return isExistingContact;
    }

   
}
