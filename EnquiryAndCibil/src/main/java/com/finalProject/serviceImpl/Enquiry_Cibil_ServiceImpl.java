package com.finalProject.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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
    private String from_email;

	private static final String PREFIX = "enq";
    private static final AtomicInteger generatedId = new AtomicInteger(100);
    private static final Set<String> generatedIds = new HashSet<>();
    
    private static String generateUniqueId() {
        String id;
        do {
            id = PREFIX + generatedId.incrementAndGet();
            System.out.println("Generated ID attempt: " + id);
        } while (!generatedIds.add(id)); // Ensure the ID is unique
        System.out.println("Unique ID generated: " + id);
        return id;
    }

	@Override
	public Enquiry saveEnquiry(Enquiry e) {
		//CibilDetails cd = rt.getForObject("http://localhost:8082/getCibilData", CibilDetails.class);
		
		//e.getCibil().setCibilScore(0);;
		
//		e.setCibil(cd);
//		e.setEnquiryid(id());
//		e.setEnquiryStatus("Register");
		CibilDetails cd = rt.getForObject("http://localhost:8082/getCibilData", CibilDetails.class);
		
		e.setCibil(cd);
		e.setEnquiryid(generateUniqueId());
		System.out.println(e.getEnquiryid());
		e.setEnquiryStatus("Register");
		er.save(e);

		

//		

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

	@Override
	public List<Enquiry> getEnqByStatus(String status) {
		
		return er.findByEnquiryStatus(status);
	}

	@Override
	public void updateStatus(String id,String status) {
		
		System.out.println(id);
		System.out.println(status);
		Optional<Enquiry> op=er.findById(id);
		if(op.isPresent())
		{
			Enquiry en=op.get();
			en.setEnquiryStatus(status);
			er.save(en);
		}
		if(status=="verification Completed"|| status=="Enquiry rejected")
		{
			if(op.isPresent())
			{
				Enquiry e=op.get();
				
			
			SimpleMailMessage sm = new SimpleMailMessage();
			sm.setTo(e.getApplicantEmail());
			sm.setFrom(from_email);
			sm.setSubject("Great News! Regarding Your Car Loan Enquiry with CJC");
			sm.setText("Dear " + e.getFullName() + ",\n\n"
					+ "We're excited to help you get your dream car! Thank you for choosing ABC Bank for your car financing needs!\n"
					+ "Here's a quick update on your enquiry:\n\n"
					+ "Enquiry ID : " + e.getEnquiryid() + "\n"
					+ "CIBIL Score : " + e.getCibil().getCibilScore() + "\n"
					+ "Remark For Score : " + e.getCibil().getCbilScoreRemark() + "\n"
					+ "Eligibility Status : " + e.getCibil().getIsApplicable() + "\n\n"
					+ "We're thrilled to have you on board and are working hard to process your request.." + "\n" 
					+"If you have any questions or need further assistance, please don't hesitate to reach out to us. Your satisfaction is our top priority!\n\n"
					+ "Stay tuned for more updates.\n\n"
					+ "Best regards,\n"
					+ "The CJC Financing Team");
	
			sender.send(sm);
		}
		}
		
	}

	@Override
	public void updateCibilScore(String enqid) {
		//CibilDetails cd = rt.getForObject("http://localhost:8082/getCibilData", CibilDetails.class);
		
		Optional<Enquiry> op=er.findById(enqid);
		if(op.isPresent())
		{
			Enquiry e=op.get();
			String cbilId=e.getCibil().getCibilId();
			CibilDetails cd = rt.getForObject("http://localhost:8082/update/"+cbilId, CibilDetails.class);
			//cd.setCibilScore(en.get)
			//e.getCibil().setCibilScore(cd.getCibilScore());
			e.setCibil(cd);
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
				+ "Remark For Score : " + e.getCibil().getCbilScoreRemark() + "\n"
				+ "Eligibility Status : " + e.getCibil().getIsApplicable() + "\n\n"
				+ "We're thrilled to have you on board and are working hard to process your request.." + "\n" 
				+"If you have any questions or need further assistance, please don't hesitate to reach out to us. Your satisfaction is our top priority!\n\n"
				+ "Stay tuned for more updates.\n\n"
				+ "Best regards,\n"
				+ "The CJC Financing Team");

		sender.send(sm);

		}
	}

}
