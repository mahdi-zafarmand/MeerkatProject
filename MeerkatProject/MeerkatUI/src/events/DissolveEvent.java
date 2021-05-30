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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *  Class Name      : DissolveEvent
 *  Created Date    : 2017-03-23
 *  Description     : Contains methods to construct the dissolve event.
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2017-04-13      @sankalp         N/A
*/
public class DissolveEvent {
    final static String cssDefault = "-fx-border-color: black";
  
    /**
     *  Method Name     : constructDissolveEvent()
     *  Created Date    : 2017-03-23
     *  Description     : Displays Dissolved Communities in current Time frame 
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public static void constructDissolveEvent(int pintProjectID, int pintGraphID, int intTimeFrame, 
            Map<String, Integer> mapEvents, ArrayList<Map<String, List<Integer>>> arrlstCommunities, 
            List<String> lstAttributesNames, Map<Integer, Double[]> mapVertexPositions, Map<String, Color> colorMap,
            Set<String> userAttributes){
        
        
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        
        TabPane tabpRoot = new TabPane();
        tabpRoot.setStyle(cssDefault);      

        Scene scene = new Scene(tabpRoot, 800, 600);

        primaryStage.setTitle("Dissolved");
        primaryStage.setScene(scene);
        primaryStage.show();

        List<Integer> listSelectedVerticesIds = new ArrayList<>();
        EventAnalyzerDialog eventObject = new EventAnalyzerDialog();
        // AttributeName --> Map<VertexIDs, Attributevalue>
        Map<String , Map<Integer, String>> mapResults = new HashMap<>();

        Map<String , String> atrributeMap = new HashMap<>();
        Circle circle;
        
        for (Map.Entry<String, Integer> entry : mapEvents.entrySet()) {
            
            if(entry.getValue()==2){

            listSelectedVerticesIds = arrlstCommunities.get(intTimeFrame).get(entry.getKey());
            Map<Integer, UIVertexEvent> vertexMap = new HashMap<>();
            for (String strAttributeName : lstAttributesNames) {
                  Map<Integer, String> tempmap = GraphAPI.getVertexAttributeValues(pintProjectID, pintGraphID, intTimeFrame, strAttributeName, listSelectedVerticesIds) ;
                  mapResults.put(strAttributeName, tempmap);
            }

            final Group vertexGroup = new Group();
            ScrollPane vertexPane = new ScrollPane();

            for(Integer vertexID : listSelectedVerticesIds){
              for (String strAttributeName : lstAttributesNames) {
                  if(!strAttributeName.equals(GraphAPI.getMeerkatSystemXAttribute()) && !strAttributeName.equals(GraphAPI.getMeerkatSystemYAttribute()))
                      atrributeMap.put(strAttributeName, mapResults.get(strAttributeName).get(vertexID));
              } 

                vertexMap.put(vertexID, new UIVertexEvent(vertexID, atrributeMap, mapVertexPositions.get(vertexID)[0], mapVertexPositions.get(vertexID)[1],
                        colorMap.get(entry.getKey())));        

                circle = vertexMap.get(vertexID).getVertexShape();
                vertexGroup.getChildren().add(circle);
                atrributeMap.clear();
            }

            vertexPane.setContent(vertexGroup);

            Tab tabEvent = new Tab(entry.getKey());

            //creating a table with attribute names and values of all vertices.
            TableView tblVertexInformation = EventUtility.generateTableView(mapResults,userAttributes,vertexMap);
            tblVertexInformation.setEditable(true);
              
            for(Integer vertexID : listSelectedVerticesIds){
                vertexMap.get(vertexID).setVertexTable(tblVertexInformation);
            }

              VBox vb= new VBox();

              Parent zoomPane = eventObject.createZoomPane(vertexGroup);
              vb.getChildren().addAll(zoomPane,tblVertexInformation);

              tabEvent.setContent(vb);
              tabpRoot.getTabs().add(tabEvent);

            
        }
        }
        
    }
    
}
