/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import ca.aicml.meerkat.api.GraphAPI;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *  Class Name      : CommunityExtraction
 *  Created Date    : 2017-03-11
 *  Description     : Class for extracting all communities in different time frames.
 *  Version         : 1.0
 *  @author         : sankalp
 * 
*/

public class CommunityExtraction {
    
    // <TimeFrame -> <Community ID -> Circle>>
    private static HashMap<Integer, HashMap<String, Circle>> hmCommunities;
    private static Map<String,Color> colorMap;
    private static GraphTab currentGraph;
    
    /**
     *  Method Name     : extractCommunities()
     *  Created Date    : 2017-03-11
     *  Description     : Method for extracting all communities in different time frames.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     * 
    */
    public static HashMap<Integer, HashMap<String, Circle>> extractCommunities(
            int pintProjectID, int pintGraphID, String[] strTimeFrames) {

        System.out.println("CommunityExtraction : extractCommunities()");
        int intNumberOfTimeFrames = strTimeFrames.length;

        // Map of Community ID -> No. of vertices
        HashMap<String, Integer> hmCommunitySize;
        
        //Map of Community -> Circle
        HashMap<String, Circle> hmCommunityCircle;

        Circle crclTemporary;

        hmCommunities = new HashMap<>();
        currentGraph = MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab();
        
        for (int tf = 0; tf < intNumberOfTimeFrames; tf++) {
            
            ArrayList<String> newstrExtractedCommunities = new ArrayList<>();
            
            String[] strExtractedCommunities = GraphAPI.getVertexAttributeValues(pintProjectID,
                    pintGraphID, tf, GraphAPI.getMeerkatSystemCommunityAttribute());
            
            colorMap = new HashMap<>();
            colorMap = currentGraph.getAccordionTabValues(tf).getCommunitiesValues().getCommunityColors();
            
            String strDelimiter = "[,]";
            
            // a vertex can be part of multiple communities, hence seperating it out.
            for(int i=0;i<strExtractedCommunities.length;i++){
                String[] temp = null;
                if(strExtractedCommunities[i].contains(",")){       
                    temp = strExtractedCommunities[i].split(strDelimiter);
                    newstrExtractedCommunities.addAll(Arrays.asList(temp)); 
                }
                else
                    newstrExtractedCommunities.add(strExtractedCommunities[i]);                
            }

            hmCommunitySize = new HashMap<>();
            
            for (String commID : newstrExtractedCommunities) {
                hmCommunitySize.putIfAbsent(commID, 0);
                hmCommunitySize.compute(commID, (k, v) -> v + 1);
            }

            hmCommunityCircle = new HashMap<>();
            
            for (HashMap.Entry<String, Integer> entryCommunity
                    : hmCommunitySize.entrySet()) {
                
                crclTemporary = new Circle(entryCommunity.getValue());
                
                Color clrCommunityColor = colorMap.get(entryCommunity.getKey());

                crclTemporary.setFill(clrCommunityColor);          

                hmCommunityCircle.put(entryCommunity.getKey(), crclTemporary);
                
                crclTemporary = null;
            }
           
            hmCommunities.put(tf, new HashMap<>(hmCommunityCircle));

            hmCommunitySize = null;
            hmCommunityCircle = null;
        }
        
        return hmCommunities;
    }

    
}
