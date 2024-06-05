package com.finalProject.ExceptionHandlermethod;


import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.finalProject.customException.EnquiryIdNotFoundException;
import com.finalProject.customException.RecordNotFoundException;
import com.finalProject.dto.EnquiryDto;



@RestControllerAdvice
public class ExceptionHandlerClass {

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<EnquiryDto> RecordNotFoundException(RecordNotFoundException r)
	{
		EnquiryDto ed=new EnquiryDto();
		ed.setMsg(r.getMessage());
		ed.setTimestamp(new Date());
		
		return new ResponseEntity<EnquiryDto>(ed, HttpStatus.OK);
	}

	@ExceptionHandler(EnquiryIdNotFoundException.class)
	public ResponseEntity<EnquiryDto> handleuser(EnquiryIdNotFoundException ee)
	{
		EnquiryDto respo=new EnquiryDto();
		respo.setMsg(ee.getMessage());
		respo.setTimestamp(new Date());
		return new ResponseEntity<EnquiryDto>(respo,HttpStatus.NOT_FOUND);
	}
}
