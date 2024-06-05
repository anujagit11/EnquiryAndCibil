package com.finalProject.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalProject.customException.EnquiryIdNotFoundException;
import com.finalProject.customException.RecordNotFoundException;
import com.finalProject.model.Enquiry;
import com.finalProject.repo.EnquiryRepo;
import com.finalProject.service.Enquiry_And_CbilServiceI;

@Service
public class Enquiry_Cibil_ServiceImpl implements Enquiry_And_CbilServiceI {

	@Autowired
	EnquiryRepo er;

	@Override
	public Enquiry saveEnquiry(Enquiry e) {
		er.save(e);
		return e;
	}

	@Override
	public Enquiry getSingleEnquiry(String enquiryid) {
		Optional<Enquiry> opEnquiry = er.findById(enquiryid);
		if (opEnquiry.isPresent()) {
			Enquiry enquiry = opEnquiry.get();
			return enquiry;
		} else {
			throw new EnquiryIdNotFoundException("No Record Found For ID:- " + enquiryid);
		}
	}

	@Override
	public void deleteRecord(String enquiryid) {

		Optional<Enquiry> op = er.findById(enquiryid);
		if (op.isPresent()) {
			er.deleteById(enquiryid);
		} else {
			throw new RecordNotFoundException("Record not present");
		}

	}

}
