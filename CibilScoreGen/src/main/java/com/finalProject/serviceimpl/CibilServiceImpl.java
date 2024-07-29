package com.finalProject.serviceimpl;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.springframework.stereotype.Service;
import com.finalProject.model.CibilDetails;
import com.finalProject.service.CibilService;

@Service
public class CibilServiceImpl implements CibilService {

    private static final String PREFIX = "CJC";
    private static final Set<String> generatedIds = new HashSet<>();
    private static final Random random = new Random();
    int initialScore=0;

    @Override
    public CibilDetails getCibilData() {
        CibilDetails cibilDetails = new CibilDetails();
        String id = generateUniqueId();
       // int score = random.nextInt(300, 900);

        cibilDetails.setCibilId(id);
        cibilDetails.setCibilScore(initialScore);
        cibilDetails.setCbilScoreRemark(getRemarkForScore(initialScore));
        cibilDetails.setIsApplicable(applicable(initialScore));
        return cibilDetails;
    }

    private String generateUniqueId() {
        String id;
        do {
            id = PREFIX + random.nextInt(100, 200);
        } while (generatedIds.contains(id));
        generatedIds.add(id);
        return id;
    }

    private String getRemarkForScore(int score) {
    	System.out.println(score);
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

    private String applicable(int score) {
    	System.out.println(score);
        if (score >= 600) {
            return "Congratulations! You are eligible for a loan with your current CIBIL score.";
        } else {
            return "Sorry, you are not eligible for a loan with your current CIBIL score.";
        }
    }

    @Override
    public CibilDetails updateCibil(String cid) {
    	System.out.println("hellllllllo");
        CibilDetails cibilDetails = new CibilDetails();
        Random r = new Random();
        int score = r.nextInt(300, 900);
        System.out.println(score);
        cibilDetails.setCibilId(cid);

        cibilDetails.setCibilScore(score);
        cibilDetails.setCbilScoreRemark(getRemarkForScore(score));
        cibilDetails.setIsApplicable(applicable(score));
        return cibilDetails;
    }
}
