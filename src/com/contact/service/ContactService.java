package com.contact.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.contact.bean.Contact;
import com.contact.dao.ContactDao;
import com.contact.exception.ContactNotFoundException;

public class ContactService {
	
	public void addContact(Contact contact, List<Contact> contacts) {
		contacts.add(contact);
	}
	
	public void removeContact(Contact contact, List<Contact> contacts) throws ContactNotFoundException {
		Iterator<Contact> itr = contacts.iterator();
		boolean flag = true;
		while(itr.hasNext()) {
			if(itr.next().equals(contact)) {
				itr.remove();
				flag = false;
			}
		}
		if(flag) {
			throw new ContactNotFoundException("Contact with contact Id: " + contact.getContactId() + " could not be found");
		}
	}
	
	public Contact searchContactByName(String name, List<Contact> contacts) throws ContactNotFoundException {
		for(Contact c : contacts) {
			if(c.getContactName().equals(name)) {
				return c;
			}
		}
		throw new ContactNotFoundException("Contact with name: " + name + " could not be found!");
	}
	
	public List<Contact> searchContactByNumber(String number, List<Contact> contacts) throws ContactNotFoundException {
		List<Contact> filteredContacts = new ArrayList<>();
		for(Contact c : contacts) {
			for(String num : c.getContactNumber()) {
				if(num.contains(number)) {
					filteredContacts.add(c);
					break;
				}
			}
		}
		if(filteredContacts.size() == 0) {
			throw new ContactNotFoundException("Contact with contact number: " + number + " could not be found!");
		}
		return filteredContacts;
	}
	
	public void addContactNumber(int contactId, String contactNo, List<Contact> contacts) {
		for(Contact c : contacts) {
			if(c.getContactId() == contactId) {
				c.getContactNumber().add(contactNo);
			}
		}
	}
	
	public void sortContactsByName(List<Contact> contacts) {
		contacts.sort(null);
	}
	
	public void readContactsFromFile(List<Contact> contacts, String fileName) throws IOException {
		Path path = Paths.get(fileName);
		List<String> lines = Files.readAllLines(path);
		for(String line : lines) {
			String[] values = line.split(",");
			Contact c = new Contact();
			c.setContactId(Integer.parseInt(values[0]));
			c.setContactName(values[1]);
			c.setEmailAddress(values[2]);
			List<String> numbers = new ArrayList<String>();
			for(int i=3; i<values.length; i++) {
				numbers.add(values[i]);
			}
			c.setContactNumber(numbers);
			contacts.add(c);
		}
	}
	
	public void serializeContactDetails(List<Contact> contacts, String fileName) throws IOException {
		File f = new File(fileName);
		if(!f.exists()) {
			f.createNewFile();
		}
		FileOutputStream file = new FileOutputStream(f);
		ObjectOutputStream out = new ObjectOutputStream(file);
		out.writeObject(contacts);
		out.close();
		file.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Contact> deserializeContact(String fileName) throws IOException, ClassNotFoundException {
		File f = new File(fileName);
		if(!f.exists()) {
			throw new FileNotFoundException();
		}
		FileInputStream file = new FileInputStream(f);
		ObjectInputStream in = new ObjectInputStream(file);
		List<Contact> contacts = (List<Contact>) in.readObject();
		in.close();
		file.close();
		return contacts;
	}
	
	public Set<Contact> populateContactFromDb() {
		ContactDao dao = new ContactDao();
		List<Contact> contacts = dao.populateFromDb();
		Set<Contact> contactSet = new HashSet<Contact>(contacts);
		return contactSet;
	}
	
	public boolean addContacts(List<Contact> existingContacts, Set<Contact> newContacts) {
		if(newContacts.size() == 0) {
			return false;
		}
		existingContacts.addAll(newContacts);
		return true;
	}
}

