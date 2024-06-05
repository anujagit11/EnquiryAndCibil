package com.finalProject.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Enquiry {
	
	@Id
	private String enquiryid;
	private String fullName;
	private String applicantEmail;
	private long contactNo;
	private  long alternativeContactNo;
	private int age;
	private String panCardNo;
	
	@OneToOne(cascade = CascadeType.ALL)
	private CibilDetails cibil;
	

}
