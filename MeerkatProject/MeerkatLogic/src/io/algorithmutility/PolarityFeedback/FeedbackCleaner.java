/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.algorithmutility.PolarityFeedback;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aabnar
 */
public class FeedbackCleaner {
    
    String fileName;

    /**
     *
     * @param pstrPathToFeedbackFile
     */
    public FeedbackCleaner(String pstrPathToFeedbackFile) {
        this.fileName = pstrPathToFeedbackFile;
    }
    
    /**
     *
     */
    public void cleanFile() {
        try (PrintWriter pw = new PrintWriter(fileName)) {
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FeedbackCleaner.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
}
