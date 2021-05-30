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
import java.util.Objects;
import java.util.Set;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 *  Class Name      : SurviveEvent
 *  Created Date    : 2017-03-23
 *  Description     : Contains methods to construct the survive event.
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2017-04-13      @sankalp         N/A
*/
public class SurviveEvent {
    
    final static String cssDefault = "-fx-border-color: black";
            //+ "-fx-border-insets: 5";
    
    /**
     *  Method Name     : constructSurviveEvent()
     *  Created Date    : 2017-03-23
     *  Description     : Displays Survived Communities in current Time frame 
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public static void constructSurviveEvent(int pintProjectID, int pintGraphID, int intTimeFrame, 
            Map<Pair<Integer, Integer>, Map<Pair<String, String>,String>>  newMap, ArrayList<Map<String, List<Integer>>> arrlstCommunities, 
            Map<Integer, Double[]> mapVertexPositions, Map<String, Color> colorMap, Set<String> userAttributes){
        
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        
        TabPane tabpRoot = new TabPane();
        tabpRoot.setStyle(cssDefault);

        Scene scene = new Scene(tabpRoot, 800, 600);

        primaryStage.setTitle("Survived");
        primaryStage.setScene(scene);
        primaryStage.show();
     
        List<Integer> listSelectedVerticesIds = new ArrayList<>();
        Map<String , Map<Integer, String>> mapResults = new HashMap<>();
        Map<String , String> attributeMap = new HashMap<>();
        Circle circle;
        EventAnalyzerDialog eventObject = new EventAnalyzerDialog();
        List<String> lstAttributesNamesSourceTF =  GraphAPI.getAllAttributesNames_Sorted(pintProjectID, pintGraphID, intTimeFrame);
        List<String> lstAttributesNamesResultTF =  GraphAPI.getAllAttributesNames_Sorted(pintProjectID, pintGraphID, intTimeFrame+1);
        
        for(Map.Entry<Pair<Integer, Integer>, Map<Pair<String, String>,String>> 
                entry : newMap.entrySet()){
            
            Pair<Integer,Integer> Tfpair = entry.getKey();
            Map<Pair<String, String>,String> comEventPair = entry.getValue();
            
            
            for(Map.Entry<Pair<String, String>,String> comPair : comEventPair.entrySet()){
                Map<Integer, UIVertexEvent> vertexMap = new HashMap<>();
                Map<Integer, UIVertexEvent> resultVertexMap = new HashMap<>();
                
                if(!Objects.equals(Tfpair.getKey(), Tfpair.getValue()) && 
                        "SURVIVE".equals(comPair.getValue()) &&
                        Tfpair.getKey()==intTimeFrame){
                    
                    Pair<String,String> comValues = comPair.getKey();
                    Tab tabEvent = new Tab(comValues.getKey() + " -> " + comValues.getValue());
                    SplitPane spltpEvent = new SplitPane();
                    spltpEvent.setOrientation(Orientation.HORIZONTAL);
                    spltpEvent.setDividerPositions(0.5);

                    listSelectedVerticesIds = arrlstCommunities.get(intTimeFrame).get(comValues.getKey());      
                    
                    for (String strAttributeName : lstAttributesNamesSourceTF) {
                        Map<Integer, String> tempmap = GraphAPI.getVertexAttributeValues(pintProjectID, pintGraphID, intTimeFrame, strAttributeName, listSelectedVerticesIds);
                        mapResults.put(strAttributeName, tempmap);
                    }
                    
                    final Group vertexGroup = new Group();
                    ScrollPane vertexPane = new ScrollPane();

                    for(Integer vertexID : listSelectedVerticesIds){
                        
                        for (String strAttributeName : lstAttributesNamesSourceTF) {
                            if(!strAttributeName.equals(GraphAPI.getMeerkatSystemXAttribute()) && !strAttributeName.equals(GraphAPI.getMeerkatSystemYAttribute()))
                                attributeMap.put(strAttributeName, mapResults.get(strAttributeName).get(vertexID));
                        } 
                        vertexMap.put(vertexID, new UIVertexEvent(vertexID, attributeMap, mapVertexPositions.get(vertexID)[0], mapVertexPositions.get(vertexID)[1],
                          colorMap.get(comValues.getKey())));        

                        circle = vertexMap.get(vertexID).getVertexShape();
                        vertexGroup.getChildren().add(circle);
                    }

                    vertexPane.setContent(vertexGroup);

                    //creating a table with attribute names and values of all vertices.
                    TableView tblVertexInformation = EventUtility.generateTableView(mapResults,userAttributes, vertexMap);
                    tblVertexInformation.setEditable(true);
                    
                    for(Integer vertexID : listSelectedVerticesIds){
                        vertexMap.get(vertexID).setVertexTable(tblVertexInformation);
                    }
                    
                    VBox vb= new VBox();

                    Parent zoomPane = eventObject.createZoomPane(vertexGroup);
                    vb.getChildren().addAll(zoomPane,tblVertexInformation);

                    Tab source = new Tab(comValues.getKey());
                    source.setContent(vb);
                    
                    mapResults.clear();                   
   
                    listSelectedVerticesIds = arrlstCommunities.get(intTimeFrame+1).get(comValues.getValue());

                    for (String strAttributeName : lstAttributesNamesResultTF) {
                        Map<Integer, String> tempmap2 = GraphAPI.getVertexAttributeValues(pintProjectID, pintGraphID, intTimeFrame+1, strAttributeName, listSelectedVerticesIds) ;
                        mapResults.put(strAttributeName, tempmap2);
                    }
                    
                    final Group vertexGroup2 = new Group();
                    ScrollPane vertexPane2 = new ScrollPane();

                    for(Integer vertexID : listSelectedVerticesIds){
                        
                        for (String strAttributeName : lstAttributesNamesResultTF) {
                            if(!strAttributeName.equals(GraphAPI.getMeerkatSystemXAttribute()) && !strAttributeName.equals(GraphAPI.getMeerkatSystemYAttribute()))
                                attributeMap.put(strAttributeName, mapResults.get(strAttributeName).get(vertexID));
                        } 
                        resultVertexMap.put(vertexID, new UIVertexEvent(vertexID, attributeMap, mapVertexPositions.get(vertexID)[0], mapVertexPositions.get(vertexID)[1],
                          colorMap.get(comValues.getKey())));        

                        circle = resultVertexMap.get(vertexID).getVertexShape();
                        vertexGroup2.getChildren().add(circle);
                    }

                    vertexPane2.setContent(vertexGroup2);

                    //creating a table with attribute names and values of all vertices.
                    TableView tblVertexInformation2 = EventUtility.generateTableView(mapResults,userAttributes,resultVertexMap);
                    tblVertexInformation2.setEditable(true);
                    
                    for(Integer vertexID : listSelectedVerticesIds){
                        resultVertexMap.get(vertexID).setVertexTable(tblVertexInformation2);
                    }
                    
                    VBox vb2 = new VBox();

                    Parent zoomPane2 = eventObject.createZoomPane(vertexGroup2);
                    vb2.getChildren().addAll(zoomPane2,tblVertexInformation2);
                    
                    Tab result = new Tab(comValues.getValue());
                    result.setContent(vb2);

                    
                    TabPane tabpSource = new TabPane();
                    TabPane tabpResult = new TabPane();
                    
                    tabpSource.getTabs().add(source);
                    tabpResult.getTabs().add(result);
                    
                    spltpEvent.getItems().addAll(tabpSource, tabpResult);
                    tabEvent.setContent(spltpEvent);
                    tabpRoot.getTabs().add(tabEvent);
                   
                
                }    
            }
        }    
        
    }
    
}
