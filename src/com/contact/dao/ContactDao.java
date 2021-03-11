package com.contact.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.contact.bean.Contact;
import com.contact.util.DbUtil;

public class ContactDao {
	private Connection con = DbUtil.getConnection();
	
	public List<Contact> populateFromDb() {
		String query = "select * from contact_tbl";
		
		List<Contact> contacts = new ArrayList<Contact>();
		
		try (PreparedStatement pst = con.prepareStatement(query);
				ResultSet rs = pst.executeQuery()) {
			while(rs.next()) {
				Contact c = new Contact();
				c.setContactId(rs.getInt(1));
				c.setContactName(rs.getString(2));
				c.setEmailAddress(rs.getString(3));
				c.setContactNumber(Arrays.asList(rs.getString(4).split(",")));
				contacts.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contacts;
	}
}
