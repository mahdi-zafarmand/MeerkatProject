/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import ca.aicml.meerkat.api.GraphAPI;
import cern.colt.Arrays;
import globalstate.MeerkatUI;
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
import javafx.scene.control.Label;
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
 *  Class Name      : MergeEvent
 *  Created Date    : 2017-03-23
 *  Description     : Contains methods to construct the merge event.
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2017-04-13      @sankalp         N/A
*/
public class MergeEvent {
    final static String cssDefault = "-fx-border-color: black";
    
    /**
     *  Method Name     : constructMergeEvent()
     *  Created Date    : 2017-03-24
     *  Description     : Displays Merged Communities in current Time frame 
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public static void constructMergeEvent(int pintProjectID, int pintGraphID, int intTimeFrame, 
            Map<Pair<Integer, Integer>, Map<Pair<String, String>,String>>  newMap, ArrayList<Map<String, List<Integer>>> arrlstCommunities, 
            Map<Integer, Double[]> mapVertexPositions, Map<Integer, Double[]> mapVertexPositionsResultTF, Map<String, Color> colorMap,
            Map<String, Color> colorMapResult,Set<String> userAttributes){
        
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        
        TabPane tabpRoot = new TabPane();
        tabpRoot.setStyle(cssDefault);
        
        String sourceTF = MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getTimeFrames()[intTimeFrame];
        String resultTF = MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getTimeFrames()[intTimeFrame+1];
        
        Label sourceTFLabel = new Label(sourceTF);
        Label resultTFLabel = new Label(resultTF);  

        Scene scene = new Scene(tabpRoot, 800, 600);

        primaryStage.setTitle("Merge");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        List<Integer> listSelectedVerticesIds = new ArrayList<>();
        EventAnalyzerDialog eventObject = new EventAnalyzerDialog();
        List<String> lstAttributesNamesSourceTF =  GraphAPI.getAllAttributesNames_Sorted(pintProjectID, pintGraphID, intTimeFrame);
        List<String> lstAttributesNamesResultTF =  GraphAPI.getAllAttributesNames_Sorted(pintProjectID, pintGraphID, intTimeFrame+1);
        Map<String , Map<Integer, String>> mapResults = new HashMap<>();
        Map<String , String> attributeMap = new HashMap<>();

        Set<Integer> listResultVerticesIds = new HashSet<>();
        Circle circle;
        //allMapResults for 'all tab
        Map<String , Map<Integer, String>> allmapResults = new HashMap<>();
        
        //SourceComm -->ResultComms (List<String>)
        Map<String, List<String>> comMap = new HashMap<>();
        
        for(Map.Entry<Pair<Integer, Integer>, Map<Pair<String, String>,String>> 
                entry : newMap.entrySet()){
            
            Pair<Integer,Integer> Tfpair = entry.getKey();
            Map<Pair<String, String>,String> comEventPair = entry.getValue();
                 
            
            for(Map.Entry<Pair<String, String>,String> comPair : comEventPair.entrySet()){
                
                if(!Objects.equals(Tfpair.getKey(), Tfpair.getValue()) && 
                        "MERGE".equals(comPair.getValue()) &&
                        Tfpair.getKey()==intTimeFrame){
                    Pair<String,String> comValues = comPair.getKey();
                    
                    if(comMap.containsKey(comValues.getValue())){
                        comMap.get(comValues.getValue()).add(comValues.getKey());
                    }else{
                        List<String> templist = new ArrayList<>();
                        templist.add(comValues.getKey());
                        comMap.put(comValues.getValue(), templist);
                    }
                }
            }
        }
               
        
        for(Map.Entry<String, List<String>> comEntry : comMap.entrySet()){
            
            Map<Integer, UIVertexEvent> vertexMap = new HashMap<>();
            Map<Integer, UIVertexEvent> allvertexMap = new HashMap<>();
                    
            Tab tabEvent = new Tab(Arrays.toString(comEntry.getValue().toArray()) + " -> " + comEntry.getKey());
            SplitPane spltpEvent = new SplitPane();
            spltpEvent.setOrientation(Orientation.HORIZONTAL);
            spltpEvent.setDividerPositions(0.5);

            listSelectedVerticesIds = arrlstCommunities.get(intTimeFrame+1).get(comEntry.getKey());
            for (String strAttributeName : lstAttributesNamesResultTF) {
                Map<Integer, String> tempmap = GraphAPI.getVertexAttributeValues(pintProjectID, pintGraphID, intTimeFrame+1, strAttributeName, listSelectedVerticesIds);
                mapResults.put(strAttributeName, tempmap);
            }

            final Group vertexGroup = new Group();
            ScrollPane vertexPane = new ScrollPane();

            for(Integer vertexID : listSelectedVerticesIds){
                
                for (String strAttributeName : lstAttributesNamesResultTF) {
                    if(!strAttributeName.equals(GraphAPI.getMeerkatSystemXAttribute()) && !strAttributeName.equals(GraphAPI.getMeerkatSystemYAttribute()))
                        attributeMap.put(strAttributeName, mapResults.get(strAttributeName).get(vertexID));
                } 
                vertexMap.put(vertexID, new UIVertexEvent(vertexID, attributeMap,mapVertexPositionsResultTF.get(vertexID)[0], mapVertexPositionsResultTF.get(vertexID)[1],
                  colorMapResult.get(comEntry.getKey())));

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
                
                Map<Integer, UIVertexEvent> resultVertexMap = new HashMap<>();

                listSelectedVerticesIds = arrlstCommunities.get(intTimeFrame).get(resultCom);

                for (String strAttributeName : lstAttributesNamesSourceTF) {
                    Map<Integer, String> tempmap2 = GraphAPI.getVertexAttributeValues(pintProjectID, pintGraphID, intTimeFrame, strAttributeName, listSelectedVerticesIds) ;
                    mapResults.put(strAttributeName, tempmap2);
                }

                final Group vertexGroup2 = new Group();
                ScrollPane vertexPane2 = new ScrollPane();

                for(Integer vertexID : listSelectedVerticesIds){
                    for (String strAttributeName : lstAttributesNamesSourceTF) {
                        if(!strAttributeName.equals(GraphAPI.getMeerkatSystemXAttribute()) && !strAttributeName.equals(GraphAPI.getMeerkatSystemYAttribute()))
                            attributeMap.put(strAttributeName, mapResults.get(strAttributeName).get(vertexID));
                    } 
                    resultVertexMap.put(vertexID, new UIVertexEvent(vertexID, attributeMap, mapVertexPositions.get(vertexID)[0], mapVertexPositions.get(vertexID)[1],
                      colorMap.get(resultCom)));        

                    circle = resultVertexMap.get(vertexID).getVertexShape();
                    vertexGroup2.getChildren().add(circle);
                    
                    // create another circle object to be added to 'all' tab
                    allvertexMap.put(vertexID, new UIVertexEvent(vertexID, attributeMap, mapVertexPositions.get(vertexID)[0], mapVertexPositions.get(vertexID)[1],
                      colorMap.get(resultCom)));     
                    circle = allvertexMap.get(vertexID).getVertexShape(); 
                    vertexGroupall.getChildren().add(circle);
                    
                    listResultVerticesIds.add(vertexID);
                    attributeMap.clear();
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

                Tab result = new Tab(resultCom);
                result.setContent(vb2);
                tabpResult.getTabs().add(result);
                
                mapResults.clear();

            }
            
            for (String strAttributeName : lstAttributesNamesSourceTF) {
                    Map<Integer, String> tempmap2 = GraphAPI.getVertexAttributeValues(pintProjectID, pintGraphID, intTimeFrame, strAttributeName, new ArrayList<Integer>(listResultVerticesIds)) ;
                    allmapResults.put(strAttributeName, tempmap2);
                }
            
            
            //zoompane for all vertices in the 'all' tab
            Parent zoomPane2 = eventObject.createZoomPane(vertexGroupall);
            all.setContent(zoomPane2);
            TableView tblVertexInformationall = EventUtility.generateTableView(allmapResults,userAttributes,allvertexMap);
            tblVertexInformationall.setEditable(true);
            
            for(Integer vertexID : listResultVerticesIds){
                allvertexMap.get(vertexID).setVertexTable(tblVertexInformationall);
            }
            
            VBox vb3 = new VBox();
            vb3.getChildren().addAll(zoomPane2,tblVertexInformationall);
            all.setContent(vb3);
            
            TabPane tabpSource = new TabPane();
            tabpSource.getTabs().add(source);
            spltpEvent.getItems().addAll(tabpResult,tabpSource);
            tabEvent.setContent(spltpEvent);
            tabpRoot.getTabs().add(tabEvent);
                
        }
                 
    }
    
}
