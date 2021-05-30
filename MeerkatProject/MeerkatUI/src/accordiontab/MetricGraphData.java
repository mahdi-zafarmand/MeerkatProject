/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aicml_adm
 */
public class MetricGraphData {
    
    private Integer intVertexID ;
    private Map<String, Double> mapValues ;
    
    public String getVertexID() {
        return String.valueOf(this.intVertexID);
    }
    
    public String getMetric(String pstrMetricKey) {
        return String.valueOf(mapValues.get(pstrMetricKey));
    }
    
    public MetricGraphData(int pintVertexID, String pstrMetricText, Map<String, Double> pmapValues) {        
        mapValues = new HashMap<>();        
        mapValues.putAll(pmapValues);
        this.intVertexID = pintVertexID ;
    }
}
