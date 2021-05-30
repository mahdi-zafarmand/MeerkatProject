/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import ca.aicml.meerkat.api.GraphAPI;
import cern.colt.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
 *  Class Name      : SplitEvent
 *  Created Date    : 2017-03-23
 *  Description     : Contains methods to construct the split event.
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2017-04-13      @sankalp         N/A
*/
public class SplitEvent {
    final static String cssDefault = "-fx-border-color: black";
    
    /**
     *  Method Name     : constructSplitEvent()
     *  Created Date    : 2017-03-23
     *  Description     : Displays Split Communities in current Time frame 
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    
    public static void constructSplitEvent(int pintProjectID, int pintGraphID, int intTimeFrame, 
            Map<Pair<Integer, Integer>, Map<Pair<String, String>,String>>  newMap, ArrayList<Map<String, List<Integer>>> arrlstCommunities, 
            Map<Integer, Double[]> mapVertexPositions, Map<Integer, Double[]> mapVertexPositionsResultTF, Map<String, Color> colorMap,
            Map<String, Color> colorMapResult,Set<String> userAttributes){
        
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        
        TabPane tabpRoot = new TabPane();
        tabpRoot.setStyle(cssDefault);

        Scene scene = new Scene(tabpRoot, 800, 600);

        primaryStage.setTitle("Split");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        List<Integer> listSelectedVerticesIds = new ArrayList<>();
        Set<Integer> listResultVerticesIds;
        EventAnalyzerDialog eventObject = new EventAnalyzerDialog();
        List<String> lstAttributesNamesSourceTF =  GraphAPI.getAllAttributesNames_Sorted(pintProjectID, pintGraphID, intTimeFrame);
        List<String> lstAttributesNamesResultTF =  GraphAPI.getAllAttributesNames_Sorted(pintProjectID, pintGraphID, intTimeFrame+1);
        Map<String , Map<Integer, String>> mapResults = new HashMap<>();
        Map<String , String> attributeMap = new HashMap<>();
        //allMapResults for 'all tab
        Map<String , Map<Integer, String>> allmapResults = new HashMap<>();

        Circle circle;
        
        //SourceComm -->ResultComms (List<String>)
        Map<String, List<String>> comMap = new HashMap<>();
        
        for(Map.Entry<Pair<Integer, Integer>, Map<Pair<String, String>,String>> 
                entry : newMap.entrySet()){
            
            Pair<Integer,Integer> Tfpair = entry.getKey();
            Map<Pair<String, String>,String> comEventPair = entry.getValue();
                 
            
            for(Map.Entry<Pair<String, String>,String> comPair : comEventPair.entrySet()){
                
                if(!Objects.equals(Tfpair.getKey(), Tfpair.getValue()) && 
                        "SPLIT".equals(comPair.getValue()) &&
                        Tfpair.getKey()==intTimeFrame){
                    Pair<String,String> comValues = comPair.getKey();
                    
                    if(comMap.containsKey(comValues.getKey())){
                        comMap.get(comValues.getKey()).add(comValues.getValue());
                    }else{
                        List<String> templist = new ArrayList<>();
                        templist.add(comValues.getValue());
                        comMap.put(comValues.getKey(), templist);
                    }
                }
            }
        }
        
        
        for(Map.Entry<String, List<String>> comEntry : comMap.entrySet()){
            Map<Integer, UIVertexEvent> vertexMap = new HashMap<>();
            Map<Integer, UIVertexEvent> allvertexMap = new HashMap<>();
            listResultVerticesIds = new HashSet<>();
                    
            Tab tabEvent = new Tab(comEntry.getKey() + " -> " + Arrays.toString(comEntry.getValue().toArray()));
            SplitPane spltpEvent = new SplitPane();
            spltpEvent.setOrientation(Orientation.HORIZONTAL);
            spltpEvent.setDividerPositions(0.5);

            listSelectedVerticesIds = arrlstCommunities.get(intTimeFrame).get(comEntry.getKey());
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
                  colorMap.get(comEntry.getKey())));        

                circle = vertexMap.get(vertexID).getVertexShape();
                vertexGroup.getChildren().add(circle);
                attributeMap.clear();
            }

            vertexPane.setContent(vertexGroup);

            //creating a table with attribute names and values of all vertices.
            TableView tblVertexInformation = EventUtility.generateTableView(mapResults,userAttributes,vertexMap);
            tblVertexInformation.setEditable(true);
            
            for(Integer vertexID : listSelectedVerticesIds){
                vertexMap.get(vertexID).setVertexTable(tblVertexInformation);
            }
            
            VBox vb= new VBox();

            Parent zoomPane = eventObject.createZoomPane(vertexGroup);
            vb.getChildren().addAll(zoomPane,tblVertexInformation);

            Tab source = new Tab(comEntry.getKey());
            source.setContent(vb);

            mapResults.clear();

            TabPane tabpResult = new TabPane();
            
            Tab all = new Tab("All");
            final Group vertexGroupall = new Group();
            tabpResult.getTabs().add(all);
            
            for(String resultCom : comEntry.getValue()){
                Map<Integer, UIVertexEvent> resultvertexMap = new HashMap<>();

                listSelectedVerticesIds = arrlstCommunities.get(intTimeFrame+1).get(resultCom);

                for (String strAttributeName : lstAttributesNamesResultTF) {
                    Map<Integer, String> tempmap2 = GraphAPI.getVertexAttributeValues(pintProjectID, pintGraphID, intTimeFrame+1, strAttributeName, listSelectedVerticesIds) ;
                    mapResults.put(strAttributeName, tempmap2);
                }

                final Group vertexGroup2 = new Group();

                for(Integer vertexID : listSelectedVerticesIds){
                    
                    for (String strAttributeName : lstAttributesNamesResultTF) {
                        if(!strAttributeName.equals(GraphAPI.getMeerkatSystemXAttribute()) && !strAttributeName.equals(GraphAPI.getMeerkatSystemYAttribute()))
                            attributeMap.put(strAttributeName, mapResults.get(strAttributeName).get(vertexID));
                    } 
                    
                    resultvertexMap.put(vertexID, new UIVertexEvent(vertexID, attributeMap, mapVertexPositionsResultTF.get(vertexID)[0], mapVertexPositionsResultTF.get(vertexID)[1],
                      colorMapResult.get(resultCom)));        

                    circle = resultvertexMap.get(vertexID).getVertexShape();
                    vertexGroup2.getChildren().add(circle);
                    
                    // create another circle object to be added to 'all' tab
                    allvertexMap.put(vertexID, new UIVertexEvent(vertexID, attributeMap, mapVertexPositionsResultTF.get(vertexID)[0], mapVertexPositionsResultTF.get(vertexID)[1],
                      colorMapResult.get(resultCom)));     
                    circle = allvertexMap.get(vertexID).getVertexShape(); 
                    vertexGroupall.getChildren().add(circle);
                    
                    listResultVerticesIds.add(vertexID);
                    attributeMap.clear();
                }

                //creating a table with attribute names and values of all vertices.
                TableView tblVertexInformation2 = EventUtility.generateTableView(mapResults,userAttributes,resultvertexMap);
                tblVertexInformation2.setEditable(true);
                
                for(Integer vertexID : listSelectedVerticesIds){
                    resultvertexMap.get(vertexID).setVertexTable(tblVertexInformation2);
                }
                
                VBox vb2 = new VBox();

                Parent zoomPane2 = eventObject.createZoomPane(vertexGroup2);
                vb2.getChildren().addAll(zoomPane2,tblVertexInformation2);

                Tab result = new Tab(resultCom);
                result.setContent(vb2);
                tabpResult.getTabs().add(result);
                
                mapResults.clear();

            }
            
            for (String strAttributeName : lstAttributesNamesResultTF) {
                    Map<Integer, String> tempmap2 = GraphAPI.getVertexAttributeValues(pintProjectID, pintGraphID, intTimeFrame+1, strAttributeName, new ArrayList<>(listResultVerticesIds)) ;
                    allmapResults.put(strAttributeName, tempmap2);
                }
            
            
            //zoompane for all vertices in the 'all' tab
            Parent zoomPane2 = eventObject.createZoomPane(vertexGroupall);
            all.setContent(zoomPane2);
            ArrayList<Integer> lstResultVerticesIds = new ArrayList<>(listResultVerticesIds);
            
            TableView tblVertexInformationall = EventUtility.generateTableView(allmapResults,userAttributes,allvertexMap);
            tblVertexInformationall.setEditable(true);
            
            for(Integer vertexID : lstResultVerticesIds){
                allvertexMap.get(vertexID).setVertexTable(tblVertexInformationall);
            }
                
            
            VBox vb3 = new VBox();
            vb3.getChildren().addAll(zoomPane2,tblVertexInformationall);
            all.setContent(vb3);
            
            TabPane tabpSource = new TabPane();
            tabpSource.getTabs().add(source);
            spltpEvent.getItems().addAll(tabpSource, tabpResult);
            tabEvent.setContent(spltpEvent);
            tabpRoot.getTabs().add(tabEvent);
                
        }
                 
    }
    
}
