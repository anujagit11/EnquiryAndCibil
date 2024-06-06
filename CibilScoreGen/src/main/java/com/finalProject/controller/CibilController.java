package com.finalProject.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.finalProject.model.CibilDetails;
import com.finalProject.service.CibilService;

@RestController
public class CibilController {
	
	@Autowired
	CibilService cs;
	
	@GetMapping("/getCibilData")
	public ResponseEntity<CibilDetails> getSingleEnquiryData()
	{
		CibilDetails user=cs.getCibilData();
		
		return new ResponseEntity<CibilDetails>(user,HttpStatus.OK);
	}
	

	}

