/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.algorithmutility.PolarityFeedback;

import static io.graph.writer.MeerkatWriter.strExtension;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aabnar
 */
public class FeedbackWriter {
    
    String fileName;

    /**
     *
     * @param pstrPathToFeedbackFile
     */
    public FeedbackWriter(String pstrPathToFeedbackFile) {
        this.fileName = pstrPathToFeedbackFile;
    }
    
    /**
     *
     * @param pmapFeedbackScores
     */
    public void writeFeedBack( Map<String,Double> pmapFeedbackScores) {
        try {
            BufferedWriter out =
                new BufferedWriter(new FileWriter(fileName,true));
            
            for (String sentence : pmapFeedbackScores.keySet()) {
                out.write(pmapFeedbackScores.get(sentence) + " : " + sentence);
                out.write("\n");
            }
            
            out.close();
            
        } catch (IOException ex) {
            Logger.getLogger(FeedbackWriter.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }
}
