/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.metric.writer;

import java.io.FileWriter;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *  Class Name      : MetricWriter
 *  Created Date    : 2016-07-25
 *  Description     : Writes the metrics values to the file
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class MetricWriter {
    
    /**
     *  Method Name     : Write()
     *  Created Date    : 2016-07-25
     *  Description     : Writes all the metric values to the file on disk
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param mapMetrics: Map<String, Map<Integer, Double>>
     *  @param pstrFilePath : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2017-07-13      @sankalp        added write logic in the method below
     * 
    */
    public static int Write(Map<String, Map<Integer, Double>> mapMetrics, String pstrFilePath) {
        
        int intError= 0;
        try {
            
            if(mapMetrics.size()>0){
                
                JSONObject pobj = new JSONObject();
                JSONObject metricName  = new JSONObject();
                pobj.put("metrics", metricName);
                
                for(Map.Entry<String, Map<Integer, Double>> entry : mapMetrics.entrySet()){
                    JSONArray jMetricEntry = new JSONArray();

                    
                        for(Map.Entry<Integer, Double> entryMetric : entry.getValue().entrySet()){
                            JSONObject jentry = new JSONObject();
                            jentry.put("id", entryMetric.getKey());
                            jentry.put("value",entryMetric.getValue());
                            jMetricEntry.add(jentry);
                        }
                        
                        
                    metricName.put(entry.getKey(), jMetricEntry);
                }
                            
                String strCommunityGraphTitle = pstrFilePath;

                System.out.println("Writing metrics to path :"+ strCommunityGraphTitle);

                FileWriter file = new FileWriter(strCommunityGraphTitle);
                file.write(pobj.toJSONString());
                file.flush();
                file.close();
                
            }else{
                System.out.println("MetricWriter Write() : No metrics to be written");
            }

        } catch (Exception ex) {
            intError=-102;
            System.out.println("MetricWriter Write(): EXCEPTION") ;
            ex.printStackTrace();
            return intError;
        }
        
        return intError;
    }
    

}
