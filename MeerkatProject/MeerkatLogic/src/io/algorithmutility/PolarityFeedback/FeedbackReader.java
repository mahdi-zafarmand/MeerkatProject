/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.algorithmutility.PolarityFeedback;

import static io.graph.reader.GraphReader.BUFFER_SIZE;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aabnar
 */
public class FeedbackReader {
    
    String fileName;

    /**
     *
     * @param pstrPathToFeedbackFile
     */
    public FeedbackReader(String pstrPathToFeedbackFile) {
        this.fileName = pstrPathToFeedbackFile;
    }
    
    /**
     *
     * @return
     */
    public Map<String,Double> read() {
        InputStreamReader reader = null;
        Map<String,Double> mapFeedbackScores = new HashMap<>();
        try {
            reader = new InputStreamReader(
                    new FileInputStream(fileName));
            //reader = new InputStreamReader(FeedbackReader.class.getClassLoader().getResourceAsStream(fileName));
            BufferedReader br = new BufferedReader(reader, BUFFER_SIZE);
            
            String strLine = br.readLine();
            while (strLine != null) {
                if (!strLine.isEmpty()) {
                    String score = 
                            strLine.substring(0, strLine.indexOf(":") -1).trim();
                    String sentence = 
                            strLine.substring(strLine.indexOf(":") + 1).trim();
                    mapFeedbackScores.put(sentence, Double.parseDouble(score));
                }
                strLine = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FeedbackReader.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FeedbackReader.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(FeedbackReader.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        
        return mapFeedbackScores;
    }
}
