/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.communities.writer;

import java.io.FileWriter;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *  Class Name      : CommunityWriter
 *  Created Date    : 2016-07-25
 *  Description     : Writes the Communities to the file
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class CommunityWriter {
    
    /**
     *  Method Name     : Write()
     *  Created Date    : 2016-07-25
     *  Description     : Writes the communities to the file on disk
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param intGraphID : int
     *  @param communities : Set<String>
     *  @param pstrProjectDirectory : String
     *  @param lstintSelectedVertexIDs : List<Integer>
     *  @param edgeSourceAndTarget : List<Integer[]>
     *  @param filePath : String
     *  @return intErrorCode : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static int Write(int intGraphID, Set<String> communities, String pstrProjectDirectory, List<Integer> lstintSelectedVertexIDs, 
            List<Integer[]> edgeSourceAndTarget, String filePath) {
        
        int intError= 0;
                
        try {
            if(communities.size()>0){
                JSONObject pobj = new JSONObject();
                JSONObject communityName  = new JSONObject();
                pobj.put("communities", communityName);

                for(String CommunityID : communities){
                    
                    JSONObject obj = new JSONObject();
                    //nodes/vertex array
                    JSONArray nodes = new JSONArray();
                    for(int vertexID : lstintSelectedVertexIDs){
                        JSONObject jnode = new JSONObject();
                        jnode.put("id",vertexID);
                        nodes.add(jnode);
                    }

                    obj.put("nodes", nodes);

                    //edges array
                    JSONArray edges  = new JSONArray();
                    for(Integer[] edgeSourceTarget : edgeSourceAndTarget){
                        JSONObject jedge = new JSONObject();
                        jedge.put("source", edgeSourceTarget[0]);
                        jedge.put("target", edgeSourceTarget[1]);
                        edges.add(jedge);
                    }

                    obj.put("links", edges);
                    
                    communityName.put(CommunityID, obj);
                }

                String strCommunityGraphTitle = filePath;

                System.out.println("Writing community to path :"+ strCommunityGraphTitle);

                FileWriter file = new FileWriter(strCommunityGraphTitle);
                file.write(pobj.toJSONString());
                file.flush();
                file.close();
            }else{
                System.out.println("CommunityWriter Write() : No Communities to be written");
            }
            
        } catch (Exception ex) {
            intError = -101;
            System.out.println("CommunityWriter Write(): EXCEPTION") ;
            ex.printStackTrace();
            return intError;
        }
        
        return intError;
    }
}
