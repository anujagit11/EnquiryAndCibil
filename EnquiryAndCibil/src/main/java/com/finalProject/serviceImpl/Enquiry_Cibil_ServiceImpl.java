package com.finalProject.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.finalProject.customException.EnquiryIdNotFoundException;
import com.finalProject.customException.RecordNotFoundException;
import com.finalProject.model.CibilDetails;
import com.finalProject.model.Enquiry;
import com.finalProject.repo.EnquiryRepo;
import com.finalProject.service.Enquiry_And_CbilServiceI;

@Service
public class Enquiry_Cibil_ServiceImpl implements Enquiry_And_CbilServiceI {

	@Autowired
	EnquiryRepo er;
	
	@Autowired
	RestTemplate rt;
	
	@Autowired
	private JavaMailSender sender;
	
	@Value("${spring.mail.username}")
	private static  String  from_email;

	@Override
	public Enquiry saveEnquiry(Enquiry e) {
		
		CibilDetails cd =rt.getForObject("http://localhost:8082/getCibilData", CibilDetails.class);
		e.setCibil(cd);
	
		 er.save(e);
		 
		SimpleMailMessage sm=new SimpleMailMessage();
		sm.setTo(e.getApplicantEmail());
		sm.setFrom(from_email);
		sm.setSubject("Regarding car loan enquiry");
		sm.setText("Subject: Inquiry Response: Car Loan Information.. "+"\n"+
				"Dear "+e.getFullName()+", \n"+
				"Thank you for your interest in car financing with CJC."+"\n"+
				"Your enquiryId is "+e.getEnquiryid()+" and Cibil Score is : "+e.getCibil().getCibilScore()+"\n"+
				"You are "+e.getCibil().getIsApplicable());
			
		sender.send(sm);
		
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
	@Override
	public void deleteAll() {

		er.deleteAll();
		
	}
	@Override
	public List<Enquiry> getAllEnquiryData() {
		
		return er.findAll();
	}

	@Override
	public Enquiry UpdateRecord(Enquiry e, String enquiryid) {
		
		Optional<Enquiry> opEnquiry = er.findById(enquiryid);
		if (opEnquiry.isPresent()) {
			Enquiry enquiry = opEnquiry.get();
			return er.save(e);
		} else {
			throw new EnquiryIdNotFoundException("No Record Found For ID Update Data:- " + enquiryid);
		}
		
	}
	
	
	

}
