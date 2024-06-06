package com.finalProject.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalProject.repo.CibilRepo;
import com.finalProject.service.CibilService;

@Service
public class CibilServiceImpl implements CibilService{

	@Autowired
	CibilRepo cr;
}
