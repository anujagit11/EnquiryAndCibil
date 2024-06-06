package com.finalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.finalProject.service.CibilService;

@RestController
public class CibilController {
	
	@Autowired
	CibilService cs;

}
