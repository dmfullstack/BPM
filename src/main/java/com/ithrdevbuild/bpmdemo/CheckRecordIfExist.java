package com.ithrdevbuild.bpmdemo;

import com.sugaronrest.*;
import com.sugaronrest.modules.Contacts;
import com.ithrdevbuild.bpmdemo.helpers.ContactsModule;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.*;


@SuppressWarnings("unused")
public class CheckRecordIfExist implements JavaDelegate {
	public static void main(String args[]) {
		String phoneno = "123123";
		String firstname = "Bharat";
		String lastname = "Vhanmane";
		String title = "SSE";
		String department = "IT";

		Contacts contact = new Contacts();
		contact.setFirstName(firstname);
		contact.setLastName(lastname);
		contact.setTitle(title);
		contact.setDepartment(department);
		contact.setPhoneWork(phoneno);
		// Login and get client
		SugarRestClient client = new SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);

		CheckRecordIfExist cr = new CheckRecordIfExist();

		String contactId = cr.createRecord(client, contact);
		// Contacts contact1 = cr.getRecord(client, contactId);
		// Contacts contact1 = cr.getRecordByPhone(client, contact.getPhoneWork());

		Contacts contact1 = new Contacts();
		contact1.setPhoneWork("55555");
		contact1.setFirstName("RRR");
		contact1.setLastName("SSSS");

		// cr.updateRecord(client, contact1);
		// cr.deleteRecord(client, contact1.getId());

		List<Contacts> bulkCcontacts = new ArrayList<Contacts>();

		bulkCcontacts.add(0, contact1);
		Contacts contact2 = new Contacts();
		contact2.setPhoneWork("323232");
		contact2.setFirstName("TTTT");
		contact2.setLastName("UUUU");
		bulkCcontacts.add(1, contact2);
		// cr.bulkCreate(client, bulkCcontacts);

		List<String> phoneNumbers = new ArrayList<String>();
		phoneNumbers.add(0, contact2.getPhoneWork());
		phoneNumbers.add(1, contact.getPhoneWork());
		int maxcount=100;
		List<Contacts> readContacts=cr.bulkReadByPhone(client, contact.getPhoneWork(),maxcount);
		
		for (Contacts contact11 : readContacts) {
			 
			System.out.println("Bulk Read ID:" + contact.getId() + contact.getFirstName() + contact.getPhoneWork());
			contact11.setFirstName(contact.getFirstName());
			contact11.setLastName(contact.getLastName());
			contact11.setTitle(contact.getTitle());
			contact11.setDepartment(contact.getDepartment());
				
		}
		cr.bulkUpdate(client, readContacts,contact.getPhoneWork());
		
	}

	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		String phoneno = (String) delegateExecution.getVariable("phoneno");
		String firstname = (String) delegateExecution.getVariable("firstname");
		String lastname = (String) delegateExecution.getVariable("lastname");
		String title = (String) delegateExecution.getVariable("title");
		String department = (String) delegateExecution.getVariable("department");

		Contacts contact = new Contacts();
		contact.setFirstName(firstname);
		contact.setLastName(lastname);
		contact.setTitle(title);
		contact.setDepartment(department);
		contact.setPhoneWork(phoneno);

		// Login and get client
		SugarRestClient client = new SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);
		//String contactId = createRecord(client, contact);
		int maxcount=100;
		List<Contacts> readContacts=bulkReadByPhone(client, contact.getPhoneWork(),maxcount);
		if (readContacts.size() >0){
			delegateExecution.setVariable("foundRecord", true);
		} else {
			String contactId = createRecord(client, contact);
			delegateExecution.setVariable("foundRecord", false);
			
		}
//		for (Contacts contact11 : readContacts) {
//			 
//			System.out.println("Bulk Read ID:" + contact.getId() + contact.getFirstName() + contact.getPhoneWork());
//			contact11.setFirstName(contact.getFirstName());
//			contact11.setLastName(contact.getLastName());
//			contact11.setTitle(contact.getTitle());
//			contact11.setDepartment(contact.getDepartment());
//				
//		}
//		cr.bulkUpdate(client, readContacts,contact.getPhoneWork());
//		
		// String sessionId= login();
		// boolean isExistingContact = checkExistingContact(sessionId, phoneno,
		// firstname, lastname, title, department);
		/*
		 * if (isExistingContact) { delegateExecution.setVariable("foundRecord", true);
		 * } else { delegateExecution.setVariable("foundRecord", false); }
		 */

	}

	public String createRecord(SugarRestClient client, Contacts contact) {

		// -------------------Create Contact-------------------
		SugarRestResponse response = ContactsModule.createContact(client, contact);

		if (response.getStatusCode() == HttpStatus.SC_OK) {
			System.out.println("Contact created successfully");
		}

		String insertId = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();
		System.out.println("Contact ID:" + insertId);
		return insertId;
		// assertNotNull(insertId);
		// assertNotSame(insertId, StringUtils.EMPTY);
		// -------------------End Create Contact-------------------
	}

	public Contacts getRecord(SugarRestClient client, String contactId) {

		// -------------------Read Contact-------------------
		SugarRestResponse response = ContactsModule.readContact(client, contactId);

		// assertNotNull(response);
		// assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

		Contacts readOnCreateContact = (Contacts) response.getData();

		// assertNotNull(readOnCreateContact);

		System.out.println(readOnCreateContact.getPhoneWork() + readOnCreateContact.getFirstName()
				+ readOnCreateContact.getLastName());
		// -------------------End Read Contact-------------------
		return readOnCreateContact;
	}

	public Contacts getRecordByPhone(SugarRestClient client, String phone) {

		// -------------------Read Contact-------------------
		SugarRestResponse response = ContactsModule.readContactByPhone(client, phone);

		// assertNotNull(response);
		// assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

		Contacts readOnCreateContact = (Contacts) response.getData();

		// assertNotNull(readOnCreateContact);

		System.out.println(readOnCreateContact.getPhoneWork() + readOnCreateContact.getFirstName()
				+ readOnCreateContact.getLastName());
		// -------------------End Read Contact-------------------
		return readOnCreateContact;
	}

	public String updateRecord(SugarRestClient client, Contacts contact) {

		// -------------------Update Contact-------------------
		SugarRestResponse response = ContactsModule.updateContact(client, contact);

		String updateId = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();
		System.out.println("Contact updated:" + updateId);
		// -------------------End Update Contact-------------------
		return updateId;
	}

	public void deleteRecord(SugarRestClient client, String contactId) {

		// -------------------Delete Contact-------------------
		SugarRestResponse response = ContactsModule.deleteContact(client, contactId);

//		assertNotNull(response);
//		assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

		String deleteId = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();

		// assertNotNull(deleteId);
		System.out.println("Deleted:" + deleteId);
		// -------------------End Delete Contact-------------------
	}

	public List<String> bulkCreate(SugarRestClient client, List<Contacts> bulkContacts) {

		// -------------------Create Bulk Contact-------------------
		SugarRestResponse response = ContactsModule.bulkCreateContact(client, bulkContacts);

		List<String> insertIds = (response.getData() == null) ? null : (List<String>) response.getData();

		for (String id : insertIds) {
			System.out.println("Bulk Create ID:" + id);
		}
		// -------------------End Bulk Create Contact-------------------
		return insertIds;
	}

	public void bulkRead(SugarRestClient client, List<String> insertIds) {
		// -------------------Bulk Read Contact-------------------
		List<Contacts> readOnCreateContacts = ContactsModule.bulkReadContact2(client, insertIds);

		for (Contacts contact : readOnCreateContacts) {
			System.out.println("Bulk Read ID:" + contact.getId() + contact.getFirstName() + contact.getPhoneWork());
		}
		// -------------------End Bulk Read Contact-------------------
	}

	public List<Contacts> bulkReadByPhone(SugarRestClient client, String phoneNumber, int maxcount) {

		SugarRestResponse response = ContactsModule.bulkReadContactByPhoneNumber(client, phoneNumber, maxcount);
		List<Contacts> readContacts = (List<Contacts>) (response.getData());
		for (Contacts contact : readContacts) {
			System.out.println("Bulk Read ID:" + contact.getId() + contact.getFirstName() + contact.getPhoneWork());
		}
		return readContacts;
	}

	public void bulkUpdate(SugarRestClient client, List<Contacts> bulkContacts, String phoneNumber) {
		// -------------------Bulk Update Contact-------------------

	/*	Map<String, String> contactAddressMap = new HashMap<String, String>();

		for (Contacts contact : bulkContacts) {
			contactAddressMap.put(contact.getId(), contact.getPhoneWork());
		}*/

		SugarRestResponse response = ContactsModule.bulkUpdateContact(client, bulkContacts, phoneNumber);

		List<String> updateIds = (response.getData() == null) ? null : (List<String>) response.getData();

		for (String id : updateIds) {
			System.out.println("Bulk Update ID:" + id);
		}
		// -------------------End Bulk Update Contact-------------------

		// -------------------Bulk Read Contact-------------------
//		List<Contacts> readOnUpdateContacts = ContactsModule.bulkReadContact2(client, updateIds);
//
//		for (Map.Entry<String, String> entry : contactAddressMap.entrySet()) {
//			String key = entry.getKey();
//			System.out.println("Bulk Update IDS:" + key);
//		}
		// -------------------End Bulk Read Contact-------------------

	}

	public void bulkDelete(SugarRestClient client, List<String> updateIds) {

		// -------------------Bulk Delete Contact-------------------
		List<String> deleteIds = ContactsModule.bulkDeleteContact(client, updateIds);

		for (String id : updateIds) {
			System.out.println("Deleted:" + deleteIds.contains(id));
		}
		// -------------------End Bulk Delete Contact-------------------
	}

	/*
	 * @Test public void crudByTypeTest() { SugarRestClient client = new
	 * SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);
	 * 
	 * Contacts insertContact = ContactsModule.getTestContact();
	 * 
	 * // -------------------Create Contact------------------- SugarRestResponse
	 * response = ContactsModule.createContactByType(client, insertContact);
	 * 
	 * assertNotNull(response); assertEquals(response.getStatusCode(),
	 * HttpStatus.SC_OK);
	 * 
	 * String insertId = (response.getData() == null) ? StringUtils.EMPTY :
	 * response.getData().toString();
	 * 
	 * assertNotNull(insertId); assertNotSame(insertId, StringUtils.EMPTY); //
	 * -------------------End Create Contact-------------------
	 * 
	 * // -------------------Read Contact------------------- response =
	 * ContactsModule.readContactByType(client, insertId);
	 * 
	 * assertNotNull(response); assertEquals(response.getStatusCode(),
	 * HttpStatus.SC_OK);
	 * 
	 * Contacts readOnCreateContact = (Contacts) response.getData();
	 * 
	 * assertNotNull(readOnCreateContact);
	 * assertEquals(insertContact.getFirstName(),
	 * readOnCreateContact.getFirstName());
	 * assertEquals(insertContact.getLastName(), readOnCreateContact.getLastName());
	 * assertEquals(insertContact.getTitle(), readOnCreateContact.getTitle());
	 * assertEquals(insertContact.getPrimaryAddressPostalcode(),
	 * readOnCreateContact.getPrimaryAddressPostalcode()); // -------------------End
	 * Read Contact-------------------
	 * 
	 * // -------------------Update Contact------------------- // response =
	 * ContactsModule.updateContact(client, readOnCreateContact.getId());
	 * 
	 * assertNotNull(response); assertEquals(response.getStatusCode(),
	 * HttpStatus.SC_OK);
	 * 
	 * String updateId = (response.getData() == null) ? StringUtils.EMPTY :
	 * response.getData().toString();
	 * 
	 * assertNotNull(updateId); assertNotSame(updateId, StringUtils.EMPTY); //
	 * -------------------End Update Contact-------------------
	 * 
	 * // -------------------Read Contact------------------- response =
	 * ContactsModule.readContact(client, updateId);
	 * 
	 * assertNotNull(response); assertEquals(response.getStatusCode(),
	 * HttpStatus.SC_OK);
	 * 
	 * Contacts readOnUpdateContact = (Contacts) response.getData();
	 * 
	 * assertNotNull(readOnUpdateContact.getTitle());
	 * assertNotSame(readOnUpdateContact.getTitle(), StringUtils.EMPTY);
	 * assertEquals(updateId, updateId);
	 * assertNotSame(readOnCreateContact.getTitle(),
	 * readOnUpdateContact.getTitle()); // -------------------End Read
	 * Contact-------------------
	 * 
	 * // -------------------Delete Contact------------------- response =
	 * ContactsModule.deleteContactByType(client, updateId);
	 * 
	 * assertNotNull(response); assertEquals(response.getStatusCode(),
	 * HttpStatus.SC_OK); ;
	 * 
	 * String deleteId = (response.getData() == null) ? StringUtils.EMPTY :
	 * response.getData().toString();
	 * 
	 * assertNotNull(deleteId); assertNotSame(deleteId, StringUtils.EMPTY);
	 * assertEquals(insertId, deleteId); // -------------------End Delete
	 * Contact------------------- }
	 */

	/*
	 * @Test public void readBulkTest() { SugarRestClient client = new
	 * SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);
	 * 
	 * // -------------------Bulk Read Contact------------------- int count = 10;
	 * SugarRestResponse response = ContactsModule.bulkReadContact(client, count);
	 * 
	 * assertNotNull(response); assertEquals(response.getStatusCode(),
	 * HttpStatus.SC_OK);
	 * 
	 * List<Contacts> readContacts = (List<Contacts>) (response.getData());
	 * assertNotNull(readContacts); assertTrue(readContacts.size() <= count); //
	 * -------------------End Bulk Read Contact------------------- }
	 */
}
