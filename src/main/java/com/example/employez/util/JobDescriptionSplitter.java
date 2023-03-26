package com.example.employez.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobDescriptionSplitter {
    private static List<String> cuttingString = new ArrayList<>();
    public static Map<String,String> getSplittedPart(String jobDescription) {
        if (cuttingString == null) {
            cuttingString.add("Position:");
            cuttingString.add("Location:");
            cuttingString.add("Compensation:");
            cuttingString.add("Job Description:");
            cuttingString.add("Qualifications:");
            cuttingString.add("EDUCATION AND EXPERIENCE REQUIREMENTS");
            cuttingString.add("SKILLS AND EXPERIENCE");
            cuttingString.add("Required Skills:");
            cuttingString.add("Contact:");
            cuttingString.add("Position Type:");
            cuttingString.add("Education:");
        }
        for (String s : cuttingString) {
            if (jobDescription.contains(s)) {

            }
        }


        Map<String,String> result = new HashMap<>();
        return result;
    }
}
