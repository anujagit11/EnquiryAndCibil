package com.finalProject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalProject.model.CibilDetails;

@Repository
public interface CibilRepo extends JpaRepository<CibilDetails,String> {
	

}
