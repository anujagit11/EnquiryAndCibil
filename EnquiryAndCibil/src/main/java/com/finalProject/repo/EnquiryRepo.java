package com.finalProject.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalProject.model.Enquiry;

@Repository
public interface EnquiryRepo extends JpaRepository<Enquiry, String>{




}
