/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import ca.aicml.meerkat.api.GraphAPI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *  Class Name      : AppearEvent
 *  Created Date    : 2017-03-13
 *  Description     : Contains methods to construct the appear event.
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2017-04-13      @sankalp         N/A
*/

public class AppearEvent {
    final static String cssDefault = "-fx-border-color: black";
    
    /**
     *  Method Name     : constructAppearEvent()
     *  Created Date    : 2017-03-25
     *  Description     : constructs the appear event.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public static void constructAppearEvent(Map<String , Map<Integer, String>> attrNameValuesMap,
            Map<String, ArrayList<String>> appearEventMap, Map<String, Color> colorMap, 
            Map<Integer, Double[]> mapVertexPositions, Set<String> userAttributes){
        
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        
        TabPane tabpRoot = new TabPane();
        tabpRoot.setStyle(cssDefault);

        Scene scene = new Scene(tabpRoot, 800, 600);

        primaryStage.setTitle("Appear");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Map<Integer, UIVertexEvent> allvertexMap = new HashMap<>();
        final Group allvertexGroup = new Group();
        List<Integer> listSelectedVerticesIds = new ArrayList<>();
        Circle circle;
        EventAnalyzerDialog eventObject = new EventAnalyzerDialog();
        
        // map of : 'Community' -> 'List of Vertices'
        Map<String, List<Integer>> commMap = new HashMap<>();
        List<Integer> tempList;
        
        
        // construct a map of communities to their corresponding vertices.
        for(Map.Entry<String, ArrayList<String>> checkentry : appearEventMap.entrySet()){
            for(String comm : checkentry.getValue()){
                if(commMap.containsKey(comm))
                    commMap.get(comm).add(Integer.parseInt(checkentry.getKey()));
                else{
                    tempList = new ArrayList<>();
                    tempList.add(Integer.parseInt(checkentry.getKey()));
                    commMap.put(comm, tempList);
                } 
            }
        }
        
        // generating a map of vertex IDs and Vertex Object 'UIVertexEvent'.
        for(Map.Entry<String, ArrayList<String>> entry : appearEventMap.entrySet()){
            Map<String , String> attributeMap = new HashMap<>();
            for(String comm : entry.getValue()){
                int vertexID = Integer.parseInt(entry.getKey());
                
                // generate a map of attribute names and values for the vertex.
                for (String strAttributeName : attrNameValuesMap.keySet()) {
                    if(!strAttributeName.equals(GraphAPI.getMeerkatSystemXAttribute()) && !strAttributeName.equals(GraphAPI.getMeerkatSystemYAttribute()))
                        attributeMap.put(strAttributeName, attrNameValuesMap.get(strAttributeName).get(vertexID));
                }
                
                //TODO : Issue when one vertex matches multiple communities
                allvertexMap.put(vertexID, new UIVertexEvent(vertexID, attributeMap,mapVertexPositions.get(vertexID)[0], 
                        mapVertexPositions.get(vertexID)[1],colorMap.get(comm)));

                circle = allvertexMap.get(vertexID).getVertexShape();
                allvertexGroup.getChildren().add(circle);
                listSelectedVerticesIds.add(vertexID);
            }
        }
        
        //contruct a table for all the vertices.
        TableView tblVertexInformationall = EventUtility.generateTableView(attrNameValuesMap,userAttributes,allvertexMap);
        tblVertexInformationall.setEditable(true);
        
        //set the table reference for each vertex.
        for(Integer vertexID : listSelectedVerticesIds){
                allvertexMap.get(vertexID).setVertexTable(tblVertexInformationall);
                allvertexMap.get(vertexID).setIndividualEventType();
        }
        
        VBox vb= new VBox();
        Parent zoomPane = eventObject.createZoomPane(allvertexGroup);
        vb.getChildren().addAll(zoomPane,tblVertexInformationall);
        
        // 'all' tab to show all appear vertices belonging to different communities.
        Tab all = new Tab("All");
        all.setContent(vb);
        tabpRoot.getTabs().add(all);
        
        //create a tab for each community with their respective appear vertices.
        for(Map.Entry<String, List<Integer>> commEntry : commMap.entrySet()){
            
            Map<String , String> attributeMap = new HashMap<>();
            Map<Integer, UIVertexEvent> vertexMap = new HashMap<>();
            final Group vertexGroup = new Group();
            Tab source = new Tab(commEntry.getKey());
            List<Integer> listofVertices = new ArrayList<>();
            
            for(Integer vertexID : commEntry.getValue()){
                
                // generate a map of attribute names and values for the vertex.
                for (String strAttributeName : attrNameValuesMap.keySet()) {
                    if(!strAttributeName.equals(GraphAPI.getMeerkatSystemXAttribute()) && !strAttributeName.equals(GraphAPI.getMeerkatSystemYAttribute()))
                        attributeMap.put(strAttributeName, attrNameValuesMap.get(strAttributeName).get(vertexID));
                }
                
                vertexMap.put(vertexID, new UIVertexEvent(vertexID, attributeMap,mapVertexPositions.get(vertexID)[0], 
                        mapVertexPositions.get(vertexID)[1],colorMap.get(commEntry.getKey())));
                
                circle = vertexMap.get(vertexID).getVertexShape();
                vertexGroup.getChildren().add(circle);
                listofVertices.add(vertexID);
            }
            
            //construct a table for all vertices in the community.
            TableView tblVertexInformation = EventUtility.generateTableView(attrNameValuesMap,userAttributes,vertexMap);
            tblVertexInformation.setEditable(true);
            
            //set the table reference for each vertex.
            for(Integer vertexID : listofVertices){
                vertexMap.get(vertexID).setVertexTable(tblVertexInformation);
                vertexMap.get(vertexID).setIndividualEventType();
            }
            
            VBox vb1= new VBox();
            Parent zoomPaneComm = eventObject.createZoomPane(vertexGroup);
            vb1.getChildren().addAll(zoomPaneComm,tblVertexInformation);
            source.setContent(vb1);
            tabpRoot.getTabs().add(source);
        }
        
    }
    
}
