package com.finalProject.service;

import com.finalProject.model.Enquiry;

public interface Enquiry_And_CbilServiceI {

	Enquiry saveEnquiry(Enquiry e);

	Enquiry getSingleEnquiry(String enquiryid);
	
	void deleteRecord(String enquiryid);

	void deleteAll();

}
