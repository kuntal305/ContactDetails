package com.contact.bean;

import java.util.List;

public class Contact implements Comparable<Contact>{
	private int contactId;
	private String contactName;
	private String emailAddress;
	private List<String> contactNumber;
	
	public int getContactId() {
		return contactId;
	}
	
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public List<String> getContactNumber() {
		return contactNumber;
	}
	
	public void setContactNumber(List<String> contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public int compareTo(Contact c) {
		return this.getContactName().compareTo(c.getContactName());
	}
}
