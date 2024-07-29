package com.finalProject.service;

import java.util.List;

import com.finalProject.model.Enquiry;

public interface Enquiry_And_CbilServiceI {

	Enquiry saveEnquiry(Enquiry e);

	Enquiry getSingleEnquiry(String enquiryid);
	
	void deleteRecord(String enquiryid);

	void deleteAll();

	List<Enquiry> getAllEnquiryData();

	Enquiry UpdateRecord(Enquiry e, String enquiryid);

	Enquiry getEnquiry(String panCardNo);

	List<Enquiry> getEnqByStatus(String status);

	

	void updateStatus(String id, String status);

	void updateCibilScore(String enqid);

}
