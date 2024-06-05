package com.finalProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.finalProject.model.Enquiry;
import com.finalProject.service.Enquiry_And_CbilServiceI;


@RestController
public class EnquiryController {
	
	@Autowired
	Enquiry_And_CbilServiceI es;
	
	@PostMapping("/addenquiry")
	public ResponseEntity<Enquiry> addEnquiry(@RequestBody Enquiry e)
	{
		Enquiry ee=es.saveEnquiry(e);
		return new ResponseEntity<Enquiry>(ee, HttpStatus.OK);
	}
	
	@GetMapping("/getSingleEnquiry/{enquiryid}")
	public ResponseEntity<Enquiry> getSingleEnquiryData(@PathVariable("enquiryid") String enquiryid)
	{
		Enquiry user=es.getSingleEnquiry(enquiryid);
		return new ResponseEntity<Enquiry>(user,HttpStatus.OK);
	}

	@DeleteMapping("/deleteEnquiry/{enquiryid}")
	public ResponseEntity<String> deleteEnquiry(@PathVariable String enquiryid)
	{
		es.deleteRecord(enquiryid);
		
		return new ResponseEntity<String>("record is deleted", HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Enquiry>> getAllEnquiryData() {
		List<Enquiry> list = es.getAllEnquiryData();
		return new ResponseEntity<List<Enquiry>>(list, HttpStatus.OK);
	}
	@PatchMapping("/update/{enquiryid}")
	public ResponseEntity<Enquiry> updateEnquiry(@RequestBody Enquiry e, @PathVariable String enquiryid) {
		Enquiry data=   es.UpdateRecord(e, enquiryid);

		return new ResponseEntity<Enquiry>(data,HttpStatus.OK);
	}

}
