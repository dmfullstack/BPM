package com.ithrdevbuild.bpmdemo;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.ithrdevbuild.bpmdemo.crm.Link_name_to_fields_array;
import com.ithrdevbuild.bpmdemo.crm.SugarsoapLocator;

/**
* This class updates the record using rest. 
* 
* @author dineshmetkari
* 
*/
public class UpdateRecordUsingRest1 implements JavaDelegate  {

	private final Logger LOGGER = Logger.getLogger(CheckRecordUsingRest.class.getName());
	
	public static final String SOAP_URL = "http://demo.m8solutions.com/sugar/service/v4/soap.php?wsdl";
	String sessionId="";
	@Override
	public void execute(DelegateExecution delegateExecution ) throws Exception {

		 try {
			 
			 	String phoneno = (String)delegateExecution.getVariable("phoneno");
	        	String firstname = (String)delegateExecution.getVariable("firstname");
	        	String lastname = (String)delegateExecution.getVariable("lastname");
	        	String title = (String)delegateExecution.getVariable("title");
	        	String department = (String)delegateExecution.getVariable("department");
	        	
	        	
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
				sessionId=ev.getId();
				System.out.println(mn + "Sessionid:"+sessionId);
				
				com.ithrdevbuild.bpmdemo.crm.Name_value[] nvArr = ev.getName_value_list();
				for (int x = 0; x < nvArr.length; x++) {
					System.out.println(nvArr[x].getName() + " = " + nvArr[x].getValue());
				}

				Link_name_to_fields_array[] arr = new Link_name_to_fields_array[1];
				arr[0] = new Link_name_to_fields_array();
				arr[0].setName("phone_work");
				arr[0].setValue(new String[] { "(315)428-9947" });

				/*
				 * get_entries(java.lang.String session, java.lang.String module_name, java.lang.String[] ids, java.lang.String[] select_fields, com.sugarcrm.www.sugarcrm.Link_name_to_fields_array[] link_name_to_fields_array, boolean track_view) throws java.rmi.RemoteException;
				 */
				// call the update logic
				
				com.ithrdevbuild.bpmdemo.crm.Get_entry_result_version2 getRes = pt.get_entries(sessionId, "contacts", null, null,arr, true);

				com.ithrdevbuild.bpmdemo.crm.Entry_value[] evArr = getRes.getEntry_list();

				if (evArr != null) {
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
			
		
	}

}
