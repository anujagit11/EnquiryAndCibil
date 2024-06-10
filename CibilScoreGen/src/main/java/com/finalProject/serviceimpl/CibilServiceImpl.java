package com.finalProject.serviceimpl;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.finalProject.model.CibilDetails;
import com.finalProject.service.CibilService;

@Service
public class CibilServiceImpl implements CibilService {

	@Override
	public CibilDetails getCibilData() {

		CibilDetails cibilDetails = new CibilDetails();

		Random r = new Random();
		int score = r.nextInt(300, 900);

		cibilDetails.setCibilId(id());
		cibilDetails.setCibilScore(score);
		cibilDetails.setRemark(getRemarkForScore(score));
		cibilDetails.setIsApplicable(applicable(score));
		return cibilDetails;
	}

	private static String u = "CJC";

	public static String id() {
		Random r = new Random();
		int ii = r.nextInt(100, 200);
		String id1 = u + ii;
		
		return id1;
	}

	private String getRemarkForScore(int score) {
		if (score >= 750 && score <= 900) {
			return "Excellent Credit Score";
		} else if (score >= 700 && score <= 749) {
			return "Good Credit Score";
		} else if (score >= 621 && score <= 699) {
			return "Fair Credit Score";
		} else if (score >= 551 && score <= 620) {
			return "Low Credit Score";
		} else {
			return "Very Low Credit Score";
		}
	}

	public static String applicable(int score) {
		String eligibilityMsg = "";
		if (score >= 600) { // CIBIL score of 750 and above is considered as ideal cibil/credit score
			eligibilityMsg = "Congratulations! You are eligible for a loan with your current CIBIL score.";
		} else {
			eligibilityMsg = "Sorry, you are not eligible for a loan with your current CIBIL score.";
		}
		return eligibilityMsg;

	}

	@Override
	public CibilDetails updateCibil(String cid) {

		CibilDetails cibilDetails = new CibilDetails();
		Random r = new Random();
		int score = r.nextInt(300, 900);
		cibilDetails.setCibilId(cid);

		cibilDetails.setCibilScore(score);
		cibilDetails.setRemark(getRemarkForScore(score));
		cibilDetails.setIsApplicable(applicable(score));
		return cibilDetails;

	}

}
