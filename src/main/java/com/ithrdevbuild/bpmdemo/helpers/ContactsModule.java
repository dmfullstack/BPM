package com.ithrdevbuild.bpmdemo.helpers;

import com.sugaronrest.*;
import com.sugaronrest.modules.Accounts;
import com.sugaronrest.modules.Contacts;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContactsModule {
	public static SugarRestResponse createContact(SugarRestClient client, Contacts contact) {
		SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.Create);
		request.setParameter(contact);
		request.getOptions().setSelectFields(getSelectedField());

		return client.execute(request);
	}

	public static SugarRestResponse createContactByType(SugarRestClient client, Contacts contact) {
		SugarRestRequest request = new SugarRestRequest(RequestType.Create);
		request.setModuleType(Contacts.class);
		request.setParameter(contact);
		request.getOptions().setSelectFields(getSelectedField());

		return client.execute(request);
	}

	public static SugarRestResponse bulkCreateContact(SugarRestClient client, List<Contacts> contacts) {
		SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.BulkCreate);
		request.setParameter(contacts);
		request.getOptions().setSelectFields(getSelectedField());

		return client.execute(request);
	}

	public static SugarRestResponse readContact(SugarRestClient client, String contactId) {
		SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.ReadById);
		request.setParameter(contactId);
		request.getOptions().setSelectFields(getSelectedField());
		request.getOptions().getSelectFields().add(NameOf.Contacts.Id);

		return client.execute(request);
	}

	public static SugarRestResponse readContactByType(SugarRestClient client, String contactId) {
		SugarRestRequest request = new SugarRestRequest(Contacts.class, RequestType.ReadById);
		request.setParameter(contactId);
		request.getOptions().setSelectFields(getSelectedField());
		request.getOptions().getSelectFields().add(NameOf.Contacts.Id);

		return client.execute(request);
	}

	public static SugarRestResponse readContactByPhone(SugarRestClient client, String phone) {
		SugarRestRequest request = new SugarRestRequest(Contacts.class, RequestType.BulkRead);
		request.setParameter(phone);
		request.getOptions().setSelectFields(getSelectedField());
		request.getOptions().getSelectFields().add(NameOf.Contacts.PhoneWork);

		return client.execute(request);
	}

	public static SugarRestResponse bulkReadContactByPhoneNumber(SugarRestClient client, String phoneNumber,
			int maxcount) {
		SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.BulkRead);
		request.getOptions().setSelectFields(getSelectedField());
		request.getOptions().getSelectFields().add(NameOf.Contacts.Id);
		request.getOptions().setMaxResult(maxcount);
		List<QueryPredicate> queryPredicates = new ArrayList<QueryPredicate>();

		queryPredicates.add(new QueryPredicate(NameOf.Contacts.PhoneWork, QueryOperator.Equal, phoneNumber));
		request.getOptions().setQueryPredicates(queryPredicates);
		return client.execute(request);
	}

	public static SugarRestResponse bulkReadContact(SugarRestClient client, int count) {
		SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.BulkRead);
		request.getOptions().setSelectFields(getSelectedField());
		request.getOptions().getSelectFields().add(NameOf.Contacts.Id);

		// request.getOptions().getSelectFields().add(NameOf.Contacts.PhoneWork );

		request.getOptions().setMaxResult(count);

		// String query = "Contacts.phone_work = '222222'";
		// request.getOptions().setQuery(query);

		List<QueryPredicate> queryPredicates = new ArrayList<QueryPredicate>();

		queryPredicates.add(new QueryPredicate(NameOf.Contacts.PhoneWork, QueryOperator.Equal, "88991122"));
		// queryPredicates.add(new QueryPredicate("status", Equal, "Open_New"));

//        String fromDate = "2016-06-01 00:00:00";
//        String toDate = "2017-01-20 00:00:00";
//
//        // Since query was set, the predicates will be ignored.
//        queryPredicates.add(new QueryPredicate(NameOf.Cases.DateEntered, QueryOperator.Between, null, fromDate, toDate));

		request.getOptions().setQueryPredicates(queryPredicates);

		return client.execute(request);
	}

	public static SugarRestResponse bulkReadContact1(SugarRestClient client, int count) {
		SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.BulkRead);
		request.getOptions().setSelectFields(getSelectedField());
		// request.getOptions().getSelectFields().add(NameOf.Contacts.Id);
		// request.getOptions().getSelectFields().add(NameOf.Contacts.PhoneWork);
		Contacts contact = new Contacts();
		contact.setPhoneWork("222222");
		// request.setParameter("222222");
		// request.setParameter(contact);
		request.getOptions().setMaxResult(count);
		request.setModuleType(Contacts.class);
		request.setParameter(contact);

		String query = "Contacts.phone_work = '222222'";
		request.getOptions().setQuery(query);
		/*
		 * List<QueryPredicate> queryPredicates = new ArrayList<QueryPredicate>();
		 * 
		 * queryPredicates.add(new QueryPredicate(NameOf.Cases.Name, Equal,
		 * "System not responding")); queryPredicates.add(new QueryPredicate("status",
		 * Equal, "Open_New"));
		 * 
		 * String fromDate = "2016-06-01 00:00:00"; String toDate =
		 * "2017-01-20 00:00:00";
		 * 
		 * // Since query was set, the predicates will be ignored.
		 * queryPredicates.add(new QueryPredicate(NameOf.Cases.DateEntered,
		 * QueryOperator.Between, null, fromDate, toDate));
		 * 
		 * request.getOptions().setQueryPredicates(queryPredicates);
		 */

		return client.execute(request);
	}

	public static List<Contacts> bulkReadContact2(SugarRestClient client, List<String> contactIds) {
		SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.ReadById);
		request.getOptions().setSelectFields(getSelectedField());
		request.getOptions().getSelectFields().add(NameOf.Contacts.Id);

		List<Contacts> contacts = new ArrayList<Contacts>();

		for (String id : contactIds) {

			request.setParameter(id);
			SugarRestResponse response = client.execute(request);

			contacts.add((Contacts) response.getData());
		}

		return contacts;
	}

	public static List<Contacts> bulkReadContact2ByPhone(SugarRestClient client, List<String> phoneNumbers) {
		SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.BulkRead);

		request.getOptions().setSelectFields(getSelectedField());
		request.getOptions().getSelectFields().add(NameOf.Contacts.PhoneWork);

		List<Contacts> contacts = new ArrayList<Contacts>();

		for (String phonenumber : phoneNumbers) {
			request.getOptions().setMaxResult(1);
			request.setParameter(phonenumber);
			SugarRestResponse response = client.execute(request);

			contacts.add((Contacts) response.getData());
		}

		return contacts;
	}

	public static SugarRestResponse updateContact(SugarRestClient client, Contacts contact) {
		SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.Update);
		request.setParameter(contact);

		request.getOptions().setSelectFields(new ArrayList<String>());
		// request.getOptions().getSelectFields().add(NameOf.Contacts.Title);

		return client.execute(request);
	}

	public static SugarRestResponse bulkUpdateContact(SugarRestClient client, List<Contacts> contacts, String phoneNumber) {
		/*
		 * Random random = new Random(); for (Contacts contact : contacts) {
		 * contact.setPhoneWork(10000 + random.nextInt(1000) + ""); }
		 */

		SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.BulkUpdate);
		request.setParameter(contacts);

		request.getOptions().setSelectFields(new ArrayList<String>());
		// request.getOptions().getSelectFields().add(NameOf.Contacts.PrimaryAddressPostalcode);

		List<QueryPredicate> queryPredicates = new ArrayList<QueryPredicate>();

		queryPredicates.add(new QueryPredicate(NameOf.Contacts.PhoneWork, QueryOperator.Equal, phoneNumber));
		request.getOptions().setQueryPredicates(queryPredicates);
		
		return client.execute(request);
	}

	public static SugarRestResponse deleteContact(SugarRestClient client, String contactId) {
		SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.Delete);
		request.setParameter(contactId);

		return client.execute(request);
	}

	public static SugarRestResponse deleteContactByType(SugarRestClient client, String contactId) {
		SugarRestRequest request = new SugarRestRequest(Contacts.class, RequestType.Delete);
		request.setParameter(contactId);

		return client.execute(request);
	}

	public static List<String> bulkDeleteContact(SugarRestClient client, List<String> contactIds) {
		SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.Delete);

		List<String> listId = new ArrayList<String>();
		for (String id : contactIds) {
			request.setParameter(id);
			SugarRestResponse response = client.execute(request);
			String identifier = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();
			listId.add(identifier);
		}

		return listId;
	}

	public static List<String> getSelectedField() {
		List<String> selectedFields = new ArrayList<String>();

		selectedFields.add(NameOf.Contacts.FirstName);
		selectedFields.add(NameOf.Contacts.LastName);
		selectedFields.add(NameOf.Contacts.Title);
		selectedFields.add(NameOf.Contacts.Department);
		selectedFields.add(NameOf.Contacts.PhoneWork);

		return selectedFields;
	}

	public static List<String> getJsonSelectedField() {
		List<String> selectedFields = new ArrayList<String>();

		selectedFields.add("id");
		selectedFields.add("first_name");
		selectedFields.add("last_name");
		selectedFields.add("title");
		selectedFields.add("department");
		selectedFields.add("phone_work");

		return selectedFields;
	}

	public static Contacts getTestContact() {
		Contacts contact = new Contacts();
		contact.setFirstName("Carolyn");
		contact.setLastName("Smith");
		contact.setTitle("Director of Programming");
		contact.setDepartment("IT");
		contact.setPhoneWork("66666666");

		return contact;
	}

	public static List<Contacts> getTestBulkContact() {
		Random random = new Random();

		List<Contacts> contacts = new ArrayList<Contacts>();

		for (int i = 0; i < 5; i++) {
			Contacts contact = new Contacts();
			int uniqueNumber = 10000 + random.nextInt(1000);
			contact.setFirstName("FirstName_" + uniqueNumber);
			contact.setLastName("LastName_" + uniqueNumber);
			contact.setTitle("Title_" + uniqueNumber);
			contact.setPhoneWork(uniqueNumber + "");

			contacts.add(contact);
		}

		return contacts;
	}
}
