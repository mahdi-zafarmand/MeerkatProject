/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import io.algorithmutility.PolarityFeedback.FeedbackCleaner;
import io.algorithmutility.PolarityFeedback.FeedbackReader;
import io.algorithmutility.PolarityFeedback.FeedbackWriter;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aabnar
 */
public class FeedbackFileTest {
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Testing reader/writer/cleaner "
                + "for the feedback file");

        String sampleFileName = "test.feedback";
        
        FeedbackWriter fwriter = new FeedbackWriter(sampleFileName);
        FeedbackReader freader = new FeedbackReader(sampleFileName);
        FeedbackCleaner fcleaner = new FeedbackCleaner(sampleFileName);
        
        HashMap<String, Double> scores = new HashMap<>();
        scores.put("sentence1", 1.0);
        scores.put("sentence2", 1.5);
        scores.put("sentence3", 1.75);
        
        fwriter.writeFeedBack(scores);
        
        scores = new HashMap<>();
        scores.put("sentence4", 2.5);
        scores.put("sentence5", 2.75);
        scores.put("sentence6", 2.3);
        
        fwriter.writeFeedBack(scores);
        
        Map<String,Double> results = freader.read();
        
        for (String str : results.keySet()) {
            System.out.println(str + " : " + results.get(str));
        }
        
        File file = new File(sampleFileName);
        System.out.println("File Size BEFORE cleaning : " + file.length());
        fcleaner.cleanFile();
        System.out.println("File Size AFTER cleaning : " + file.length());
        
    }
    
}
