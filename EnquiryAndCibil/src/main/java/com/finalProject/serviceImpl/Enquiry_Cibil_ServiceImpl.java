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
	private static String from_email;

	private static String u = "CJC";
	private static int generatedId = 100;

	public static String id() {
		generatedId++;
        String newId = u + generatedId;
        return newId;
	}

	@Override
	public Enquiry saveEnquiry(Enquiry e) {

		CibilDetails cd = rt.getForObject("http://localhost:8082/getCibilData", CibilDetails.class);
			
		e.setCibil(cd);
		e.setEnquiryid(id());
		
		er.save(e);

		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(e.getApplicantEmail());
		sm.setFrom(from_email);
		sm.setSubject("Great News! Regarding Your Car Loan Enquiry with CJC");
		sm.setText("Dear " + e.getFullName() + ",\n\n"
				+ "We're excited to help you get your dream car! Thank you for choosing CJC for your car financing needs!\n"
				+ "Here's a quick update on your enquiry:\n\n"
				+ "Enquiry ID : " + e.getEnquiryid() + "\n"
				+ "CIBIL Score : " + e.getCibil().getCibilScore() + "\n"
				+ "Remark For Score : " + e.getCibil().getRemark() + "\n"
				+ "Eligibility Status : " + e.getCibil().getIsApplicable() + "\n\n"
				+ "We're thrilled to have you on board and are working hard to process your request.." + "\n" 
				+"If you have any questions or need further assistance, please don't hesitate to reach out to us. Your satisfaction is our top priority!\n\n"
				+ "Stay tuned for more updates.\n\n"
				+ "Best regards,\n"
				+ "The CJC Financing Team");

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
			String cid = enquiry.getCibil().getCibilId();
			String url = "http://localhost:8082/update/" + cid;

			CibilDetails cd = rt.getForObject(url, CibilDetails.class);
			e.setCibil(cd);

			return er.save(e);
		} else {
			throw new EnquiryIdNotFoundException("No Record Found For ID Update Data:- " + enquiryid);
		}

	}

	@Override
	public Enquiry getEnquiry(String panCardNo) {
		Enquiry ee   =er.findByPanCardNo(panCardNo);
		return ee;

	}

}
