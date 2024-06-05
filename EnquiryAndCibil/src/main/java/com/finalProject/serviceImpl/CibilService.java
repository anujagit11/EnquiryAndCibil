package com.finalProject.serviceImpl;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.finalProject.model.CibilDetails;

@Service
public class CibilService {

	private static String u = "cjc";

	public static String id() {
		Random r = new Random();
		int ii = r.nextInt(100, 200);
		String id1 = u + ii;
		return id1;
	}

	public static String applicable(int score) {
		if (score <= 600) {
			return "No";
		} else {
			return "Yes";
		}
	}

	public CibilDetails generateRandomCibilDetails() {
		CibilDetails cibilDetails = new CibilDetails();

		Random r = new Random();
		int score = r.nextInt(300, 900);
		cibilDetails.setCibilId(id());

		cibilDetails.setCibilScore(score);
		cibilDetails.setRemark(getRemarkForScore(score));
		cibilDetails.setIsApplicable(applicable(score));

		return cibilDetails;
	}

	private String getRemarkForScore(int score) {
		if (score >= 750) {
			return "Good";
		} else if (score >= 600) {
			return "Average";
		} else {
			return "Bad";
		}
	}
}
