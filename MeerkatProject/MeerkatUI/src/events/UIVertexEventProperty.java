/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.scene.control.TableView;

/**
 *  Class Name      : UIVertexEventProperty
 *  Created Date    : 2017-04-21
 *  Description     : supports certain properties for the UIVertexEvent class
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/
public class UIVertexEventProperty {
    
    private static Set<UIVertexEvent> allVertex = new HashSet<>();
    private static Set<UIVertexEvent> selectedVertex = new HashSet();
    private static Map<UIVertexEvent, TableView> vertexTableViewMap = new HashMap<>();
    
    public static void addAllVertex(UIVertexEvent uiVertexEvent){
        allVertex.add(uiVertexEvent);       
    }
    
    public static void setVertexTable(Map<UIVertexEvent, TableView> vertexTableMap){
        vertexTableViewMap=vertexTableMap;
    }
    
    public static Map<UIVertexEvent, TableView> getVertexTable(){
        return vertexTableViewMap;
    }
    
    public static void clearALLVertex(){
        allVertex.clear();      
    }
    
    public static void addSelectedVertex(UIVertexEvent uiVertexEvent){
        selectedVertex.add(uiVertexEvent);       
    }
    
    public static void clearSelectedVertex(){
        for(UIVertexEvent uiv : selectedVertex){
            uiv.clearSelectVertex();
        }
        selectedVertex.clear();
    }
    
    public static Set<UIVertexEvent> getSelectedVertex(){
        return selectedVertex;
    }
}
