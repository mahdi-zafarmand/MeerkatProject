/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.analysis.CommunityMiningAPI;
import ca.aicml.meerkat.api.analysis.EventAnalyzerAPI;
import config.SceneConfig;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Pair;

/**
 *  Class Name      : EventAnalyzerDialog
 *  Created Date    : N/A
 *  Description     : this class does all the initializations and method calls
 *                    for event construction.
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/
public class EventAnalyzerDialog {

    // <TimeFrame -> <Community ID -> Circle>>
    private HashMap<Integer, HashMap<String, Circle>> hmCommunities;
    private int circleCount;
    private double bigRadius;
    //min radius added for scaling small circles.
    private int addRadius;
    // set this boolean if we need to add radius values to circles.
    private boolean checkToAddRadius;
    // set this values as true if the radius has been scaled.
    private boolean radiusScaled;
  
    // Pair<TimeFrameIndex, TimeFrameIndex> --> <Pair<CommunityID, CommunityID>, EventName>
    //TODO : maybe use a different data structure.
    private Map<Pair<Integer, Integer>, Map<Pair<String, String>,String>> newMap;  
    
//    Event curves connecting community circles : commented for future use. 
//    private ArrayList<Map<Pair<String, String>, CubicCurve>> arrlstSurvive;
//    private ArrayList<Map<Pair<String, String>, CubicCurve>> arrlstSplit;
//    private ArrayList<Map<Pair<String, String>, CubicCurve>> arrlstMerge;
    
    //Event lines connecting community circles
    private ArrayList<Map<Pair<String, String>, Line>> arrlstSurviveNew;
    private ArrayList<Map<Pair<String, String>, Line>> arrlstSplitNew;
    private ArrayList<Map<Pair<String, String>, Line>> arrlstMergeNew;
    
    //varaibles needed for main event window.
    private ArrayList<Rectangle> arrlRectangle;
    private ArrayList<CheckBox> arrCheckBox;
    private Map<Circle,List<Line>> eventGraph;
    private CheckBox cbCommunityLabelsAsSize;
    private CheckBox cbShowCommunityLabels;
    
    // ArrayList<Map<Community ID, Event ID>> , contains FORM, DISSOLVE events.
    private ArrayList<Map<String, Integer>> arrlstEvents;
    
    // VertexID --> <AttributeName , AttributeValue>
    Map<Integer, Map<String, String>> mapVertex;
    
    // VertexID --> Layout Positions (x,y)
    Map<Integer, Double[]> mapVertexPositions;
    Map<Integer, Double[]> mapVertexPositionsResultTF;
    
    //CommunityID -> Color
    Map<String, Color> colorMap;
    Map<String, Color> colorMapResult;
    
    //vertex attribute names (system and user attributes)
    List<String> lstAttributesNames;
    Set<String> userAttributes;
    
    Map<CheckBox, Color> hideCommCheck;
    Map<Circle,Text> mapCircleLabels;
    Map<Circle,Text> mapCircleLabelsAsSize;
    
    // AttributeName -> VertexID, AttributeValue
    Map<String , Map<Integer, String>> attrNameValuesMap;

    private SplitPane spltpRoot;
    private String[] strTimeFrames;
    private List<Color> commColors;
    
    //All Individual Event Maps:
    Map<String, ArrayList<String>> joinEventMap;
    Map<String, ArrayList<String>> leftEventMap;
    Map<String, ArrayList<String>> appearEventMap;
    Map<String, ArrayList<String>> disappearEventMap;
    
//maintains a map of all valid events for every timeframe.
    private static ArrayList<Map<String, Boolean>> EventStatus;

    // CommunityID --> List of VertexID
    private static ArrayList<Map<String, List<Integer>>> arrlstCommunities;
    //CommunityID -> EventName
    private static ArrayList<Map<String, String>> communityEventMap;
    List<Integer> listSelectedVerticesIds;
    
    // Community Labels
    private ArrayList<Text> CommunityLabels;
    private ArrayList<Text> CommunityLabelsAsSize;
    private ArrayList<Line> CommunityLines;
    
    private Map<Circle,Double> CommunityCircles;
    
    private MeerkatUI UIInstance;
    
    private enum EventName {

        NONE, SURVIVE, DISSOLVE, SPLIT, MERGE, FORM

    }
    
    private enum IndividualEventName {

        NONE, JOIN, LEFT, APPEAR, DISAPPEAR

    }

    /**
     *  Method Name     : Init()
     *  Created Date    : N/A
     *  Description     : Initializes some data members of EventAnalyzerDialog
     *  Version         : 1.0
     *  @author         : sankalp 
     * 
    */
    public void Init(int pintProjectID, int pintGraphID, int intTimeFrame) {
        System.out.println("EventAnalyzerDialog.Init() - Initiating Events Calculation...");
        
        UIInstance = MeerkatUI.getUIInstance();
        int tfIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        
        mapVertex = GraphAPI.getallVertices(pintProjectID, pintGraphID, tfIndex);        
        
        strTimeFrames = GraphAPI.getTimeFrameNames(pintProjectID, pintGraphID);
        
        arrlstEvents = new ArrayList<>(strTimeFrames.length);
        for (int tf = 0; tf < strTimeFrames.length ; tf++){
            arrlstEvents.add(new HashMap<>());
        }
        
        //Initialize variables.
        newMap = new HashMap<>();
        arrlstCommunities = new ArrayList<>();
        CommunityLabels = new ArrayList<>();
        CommunityLabelsAsSize = new ArrayList<>();
        CommunityLines = new ArrayList<>();
        CommunityCircles = new HashMap<>();
        hideCommCheck = new HashMap<>();
        arrlRectangle= new ArrayList<>();
        arrCheckBox = new ArrayList<>();
        eventGraph = new HashMap<>();
        commColors = new ArrayList<>();
        mapCircleLabels = new HashMap<>();
        mapCircleLabelsAsSize = new HashMap<>();
        
        //Initialize all individual event maps
        joinEventMap = new HashMap<>();
        leftEventMap = new HashMap<>();
        appearEventMap = new HashMap<>();
        disappearEventMap = new HashMap<>();
        EventStatus = new ArrayList<>();
        communityEventMap = new ArrayList<>();
        circleCount =0;
        bigRadius =0.0;
        addRadius=4;
        checkToAddRadius = false;
        radiusScaled = false;
        
        for(int i = 0; i < strTimeFrames.length ; i++){
            Map<String, Boolean> tempMap = new HashMap<>();
            EventStatus.add(tempMap);
            Map<String, String> comEventMap = new HashMap<>();
            communityEventMap.add(comEventMap);
        }
        
        for (int tf = 0; tf < strTimeFrames.length ; tf++){
            arrlstCommunities.add(CommunityMiningAPI.getCommunities(pintProjectID, pintGraphID, tf));
        }
        
        mapVertexPositions = 
            GraphAPI.getVertex2DPoistions(pintProjectID, 
            pintGraphID, 
            intTimeFrame, 
            SceneConfig.GRAPHCANVAS_WIDTH/SceneConfig.EVENTCANVAS_WIDTH_SCALE, 
            SceneConfig.GRAPHCANVAS_HEIGHT/SceneConfig.EVENTCANVAS_HEIGHT_SCALE);
        
        if(intTimeFrame<strTimeFrames.length-1){
            mapVertexPositionsResultTF = 
                GraphAPI.getVertex2DPoistions(pintProjectID, 
                pintGraphID, 
                intTimeFrame+1, 
                SceneConfig.GRAPHCANVAS_WIDTH/SceneConfig.EVENTCANVAS_WIDTH_SCALE, 
                SceneConfig.GRAPHCANVAS_HEIGHT/SceneConfig.EVENTCANVAS_HEIGHT_SCALE);
        }
        
        GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
        
        for(int tf=0; tf<strTimeFrames.length;tf++){
            Map<String, Color> tempMap = new HashMap<>();
            tempMap = currentGraph.getAccordionTabValues(tf).getCommunitiesValues().getCommunityColors();
            for(Map.Entry<String, Color> entry : tempMap.entrySet())
                commColors.add(entry.getValue());
        }
        
        colorMap = currentGraph.getAccordionTabValues().
                    getCommunitiesValues().getCommunityColors();
        
        if(intTimeFrame<strTimeFrames.length-1)
            colorMapResult = currentGraph.getAccordionTabValues(intTimeFrame+1).getCommunitiesValues().getCommunityColors();
        
        lstAttributesNames = GraphAPI.getAllAttributesNames_Sorted(pintProjectID, pintGraphID, intTimeFrame);
        userAttributes = GraphAPI.getAllUserAttributesNames(pintProjectID, pintGraphID, intTimeFrame);
        
        extractCommunities(pintProjectID, pintGraphID);
        
        
        Object[] EventArray = EventAnalyzerAPI.runEvolutionAnalyzer(pintProjectID,
                        pintGraphID);
        List<String> lstEvolutionAnalyzerResult = (List<String>)EventArray[0];
        
        parseEventAnalyzerResult(lstEvolutionAnalyzerResult);  
        
        ArrayList<Map<String, Map<String, ArrayList<String>>>> individualEvents
                = (ArrayList<Map<String, Map<String, ArrayList<String>>>>)EventArray[1];
        
        parseIndividualEventAnalyzerResult(individualEvents.get(tfIndex), tfIndex);
        
        System.out.println("\nIndividual Events : "+individualEvents.toString());
        
        System.out.println("\nEvent Status "+EventStatus);
        
        int intNumberOfTimeFrames = strTimeFrames.length;
        
        arrlstSurviveNew = new ArrayList<>(
                intNumberOfTimeFrames);
        arrlstSplitNew = new ArrayList<>(
                intNumberOfTimeFrames);
        arrlstMergeNew = new ArrayList<>(
                intNumberOfTimeFrames);    
    }
    
    /**
     *  Method Name     : getEventStatus()
     *  Created Date    : 2017-04-20
     *  Description     : getter for EventMap
     *  Version         : 1.0
     *  @author         : sankalp
     * 
    */
    public static ArrayList<Map<String, Boolean>> getEventStatus(){
        return EventStatus;
    }
    
    /**
     *  Method Name     : getCommInfo()
     *  Created Date    : 2017-04-27
     *  Description     : get Community Info
     *  Version         : 1.0
     *  @author         : sankalp
     *  @return         : HashMap<Integer, HashMap<String, Circle>>
     * 
    */
    public static ArrayList<Map<String, List<Integer>>> getCommInfo(){
        return arrlstCommunities;
    }
    
    /**
     *  Method Name     : getCommEventMap()
     *  Created Date    : 2017-04-27
     *  Description     : get Community and event Info
     *  Version         : 1.0
     *  @author         : sankalp
     *  @return         : HashMap<Integer, HashMap<String, Circle>>
     * 
    */
    public static ArrayList<Map<String, String>> getCommEventMap(){
        return communityEventMap;
    }
    
    /**
     *  Method Name     : ConstructEvents()
     *  Created Date    : 2017-04-20
     *  Description     : Constructs community events : Form, Dissolve, Merge, Split & Survive. 
     *  Version         : 1.0
     *  @author         : sankalp
     * 
    */
    public void ConstructEvents(int pintProjectID, int pintGraphID, int intTimeFrame, String event){
        System.out.println("Event Type : "+event);
        
        switch(EventName.valueOf(event)) {
            case FORM:
                Map<String, Integer> mapEvents = arrlstEvents.get(intTimeFrame);
                FormEvent.constructFormedEvent(pintProjectID, pintGraphID, intTimeFrame, mapEvents,
                        arrlstCommunities,lstAttributesNames,mapVertexPositions,colorMap,userAttributes);
                break;
            case DISSOLVE:
                Map<String, Integer> mapEvents2 = arrlstEvents.get(intTimeFrame);
                DissolveEvent.constructDissolveEvent(pintProjectID, pintGraphID, intTimeFrame, mapEvents2, 
                        arrlstCommunities, lstAttributesNames, mapVertexPositions, colorMap,userAttributes);
                break;
            case SURVIVE:
                SurviveEvent.constructSurviveEvent(pintProjectID, pintGraphID, intTimeFrame, newMap, 
                        arrlstCommunities, mapVertexPositions, colorMap,userAttributes);
                break;
            case SPLIT:
                SplitEvent.constructSplitEvent(pintProjectID, pintGraphID, intTimeFrame, newMap, 
                        arrlstCommunities, mapVertexPositions, mapVertexPositionsResultTF, colorMap, 
                        colorMapResult,userAttributes);
                break;
            case MERGE:
                MergeEvent.constructMergeEvent(pintProjectID, pintGraphID, intTimeFrame, newMap, 
                        arrlstCommunities, mapVertexPositions, mapVertexPositionsResultTF, colorMap, 
                        colorMapResult,userAttributes);
                break;
            default:
                System.out.println("EventAnalyzerDialog : ConstructEvents() - Incorrect Event Type");
                break;
                
        }   
    }
    
    /**
     *  Method Name     : ConstructIndividualEvents()
     *  Created Date    : 2017-04-20
     *  Description     : Constructs individual community events : Appear, Disappear, Join & Left 
     *  Version         : 1.0
     *  @author         : sankalp
     * 
    */
     public void ConstructIndividualEvents(int pintProjectID, int pintGraphID, int intTimeFrame, String event){
        System.out.println("Individual Event Type : "+ event);
        
        switch(IndividualEventName.valueOf(event)) {
            case APPEAR:
                ConstructParametersForEvent(pintProjectID, pintGraphID, intTimeFrame, appearEventMap);
                AppearEvent.constructAppearEvent(attrNameValuesMap, appearEventMap, colorMap, mapVertexPositions, 
                        userAttributes);
                break;
            case DISAPPEAR:
                ConstructParametersForEvent(pintProjectID, pintGraphID, intTimeFrame, disappearEventMap);
                DisappearEvent.constructDisappearEvent(attrNameValuesMap, disappearEventMap, colorMap, mapVertexPositions, 
                        userAttributes);
                break;
            case JOIN:
                ConstructParametersForEvent(pintProjectID, pintGraphID, intTimeFrame, joinEventMap);
                JoinEvent.constructJoinEvent(attrNameValuesMap, joinEventMap, colorMap, mapVertexPositions, 
                        userAttributes);
                break;
            case LEFT:
                ConstructParametersForEvent(pintProjectID, pintGraphID, intTimeFrame, leftEventMap);
                LeftEvent.constructLeftEvent(attrNameValuesMap, leftEventMap, colorMap, mapVertexPositions, 
                        userAttributes);
                break;
            default:
                System.out.println("EventAnalyzerDialog : ConstructIndividualEvents() - Incorrect Individual Event Type");
                break;
                
        }   
    }
     
    /**
     *  Method Name     : ConstructParametersForEvent()
     *  Created Date    : 2017-04-20
     *  Description     : Initializes the parameters needed for event construction.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
    */
    public void ConstructParametersForEvent(int pintProjectID, int pintGraphID, int intTimeFrame,
            Map<String, ArrayList<String>> individualEventMap){
        
        lstAttributesNames  = GraphAPI.getAllAttributesNames_Sorted(pintProjectID, pintGraphID, intTimeFrame);
        attrNameValuesMap = new HashMap<>();
        listSelectedVerticesIds = new ArrayList<>();
        Map<Integer, String> tempmap;
               
        for(Map.Entry<String, ArrayList<String>> checkentry : individualEventMap.entrySet()){
             listSelectedVerticesIds.add(Integer.parseInt(checkentry.getKey()));
        }
       
        for (String strAttributeName : lstAttributesNames) {
            tempmap = new HashMap<>();
            tempmap = GraphAPI.getVertexAttributeValues(pintProjectID, pintGraphID, intTimeFrame, strAttributeName, listSelectedVerticesIds);
             
            attrNameValuesMap.put(strAttributeName, tempmap);
        }
    }
    
        /**
     *  Method Name     : extractCommunities()
     *  Created Date    : 2017-03-20
     *  Description     : helps to extract communities for every TimeFrame.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
    */
    private void extractCommunities(int pintProjectID, int pintGraphID) {
        
        hmCommunities = CommunityExtraction.extractCommunities(pintProjectID, pintGraphID, strTimeFrames);
        
        for(Map.Entry<Integer, HashMap<String,Circle>> entry : hmCommunities.entrySet()){
            if(circleCount<entry.getValue().size())
                circleCount=entry.getValue().size();
            for(Map.Entry<String,Circle> check : entry.getValue().entrySet()){
                if(bigRadius<check.getValue().getRadius())
                    bigRadius=check.getValue().getRadius();
                if(addRadius>check.getValue().getRadius())
                    checkToAddRadius=true;
            }
        }
    }
    
         
    /**
     *  Method Name     : DisplayEventDialog()
     *  Created Date    : 2016-11-02
     *  Description     : Displays the Event Analyzer Dialog 
     *  Version         : 1.0
     *  @author         : Elahe
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void DisplayEventDialog(int pintProjectID, int pintGraphID, int intTimeFrame) {

        Stage primaryStage = new Stage();

        spltpRoot = new SplitPane();
        Scene scene = new Scene(spltpRoot, 1000, 800);

        primaryStage.setTitle("Event Analyzer");
        primaryStage.setScene(scene);
        primaryStage.show();

        spltpRoot.setPrefSize(1000, 800);
        spltpRoot.setOrientation(Orientation.VERTICAL);
        spltpRoot.setDividerPositions(600);
        
        Pane pane = new Pane();
        ConstructEventCommunities(pane);
        ConstructEventCurves(pane);
        
        Group group = new Group(pane);
        Parent parent = createZoomPane(group);
        spltpRoot.getItems().addAll(parent, EventAnalyzerTools());

    }
    
    /**
     *  Method Name     : ConstructEventCommunities()
     *  Created Date    : 2017-04-20
     *  Description     : Creates the community circles for different events.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void ConstructEventCommunities(Pane pane) {
        
        Label  lblTimeFrameLabel;
        //setting the height of the pane based on number of communities.
        double prefHeight=600;        
        if(circleCount*bigRadius>prefHeight)
            prefHeight=circleCount*bigRadius;        
        pane.setPrefSize(800, prefHeight);

        // x,y drawing positions for the circles.
        double pane_divider = pane.getPrefWidth()/strTimeFrames.length;
        double x_draw_position = pane_divider/2;
        double height = pane.getPrefHeight()/circleCount;
        double y_draw_position = height/2;
        
        int tf = 0;
//        disabling scaling for now, since height has been made flexible.
//        double scale =1.0;
//        if(height<bigRadius)
//            scale = height/bigRadius;
    
        for (HashMap.Entry<Integer, HashMap<String, Circle>> entry : hmCommunities.entrySet()) {
            //draw the timeframe labels. 
            lblTimeFrameLabel = new Label(strTimeFrames[entry.getKey()]);
            lblTimeFrameLabel.relocate(x_draw_position + tf*pane_divider - bigRadius, y_draw_position);
            
            pane.getChildren().add(lblTimeFrameLabel);
            
            Circle comCircle;
            int rowindex=1;
            //draw circles in rows for each timeframe.
            for (HashMap.Entry<String, Circle> circleEntry : entry.getValue().entrySet()) {
                
                comCircle = circleEntry.getValue();
                //minRadius is added so that very small circles (communities with one or two nodes) are visible.
                if(checkToAddRadius && !radiusScaled)
                    comCircle.setRadius((comCircle.getRadius()+addRadius));
                try {
                     pane.getChildren().add(comCircle);
                     comCircle.relocate(x_draw_position + tf*pane_divider, 2*height*rowindex);
                } catch (Exception e) {
                    System.out.println("EXCEPTION in EventAnalyzerDialog : EventAnalyzerTab()");
                    e.printStackTrace();
                }
                rowindex++;
                CommunityCircles.put(comCircle, comCircle.getRadius());
            }
            tf++;
         
            Text txtCaption;
            Text txtCaptionSize;
            Integer CommSize;
            
            // draw the communityID and size as labels.
            for (HashMap.Entry<String, Circle> circleEntry : entry.getValue().entrySet()) {

                    txtCaption = new Text(circleEntry.getKey());
                    txtCaption.setLayoutX(
                            circleEntry.getValue().getLayoutX()-circleEntry.getValue().getRadius()-20);
                    txtCaption.setLayoutY(
                            circleEntry.getValue().getLayoutY()+circleEntry.getValue().getRadius() +20);
                    txtCaption.setVisible(false);
                    
                    CommSize = (int) circleEntry.getValue().getRadius();
                    //removing the minRadius to reflect the Circle's (communities) actual radius (nodes).
                    if(checkToAddRadius)
                        CommSize = (int) circleEntry.getValue().getRadius() - addRadius;

                    txtCaptionSize = new Text(CommSize.toString());
                    txtCaptionSize.setLayoutX(
                            circleEntry.getValue().getLayoutX() -
                            circleEntry.getValue().getRadius()-10);
                    txtCaptionSize.setLayoutY(
                            circleEntry.getValue().getLayoutY()-circleEntry.getValue().getRadius()-10);
                    txtCaptionSize.setVisible(false);

                mapCircleLabels.put(circleEntry.getValue(), txtCaption);
                mapCircleLabelsAsSize.put(circleEntry.getValue(), txtCaptionSize);
                CommunityLabels.add(txtCaption);
                CommunityLabelsAsSize.add(txtCaptionSize);
                pane.getChildren().add(CommunityLabels.get(CommunityLabels.size()-1));
                pane.getChildren().add(CommunityLabelsAsSize.get(CommunityLabelsAsSize.size()-1));
            }   
        }
        radiusScaled=true;
    }
    
    /**
     *  Method Name     : ConstructEventCurves()
     *  Created Date    : 2017-04-20
     *  Description     : Creates event curves between communities.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
    */
    private void ConstructEventCurves(Pane pane) {
        Line line;
        HashMap<Pair<String, String>, Line> hmSurvive;
        HashMap<Pair<String, String>, Line> hmSplit;
        HashMap<Pair<String, String>, Line> hmMerge;
    
        for (HashMap.Entry<Pair<Integer,Integer>, Map<Pair<String,String>,String>> 
                entry : newMap.entrySet()) {
            
            hmSurvive = new HashMap<>();
            hmSplit = new HashMap<>();
            hmMerge = new HashMap<>();
            double x1, x2, y1, y2;
            
            Pair<Integer,Integer> tfPair = entry.getKey();
            
            if(!Objects.equals(tfPair.getKey(), tfPair.getValue())){
                Map<Pair<String, String>, String> comEventMap = entry.getValue();
                
                for(Map.Entry<Pair<String, String>, String> tempEntry : 
                        comEventMap.entrySet()){
                    
                    Pair<String, String> shortPair = tempEntry.getKey();
                    //getting the start and end points of the line.
                    x1 = hmCommunities.get(tfPair.getKey()).get(shortPair.getKey()).getLayoutX();
                    y1 = hmCommunities.get(tfPair.getKey()).get(shortPair.getKey()).getLayoutY();
                    x2 = hmCommunities.get(tfPair.getValue()).get(shortPair.getValue()).getLayoutX();
                    y2 = hmCommunities.get(tfPair.getValue()).get(shortPair.getValue()).getLayoutY();
                    
                    line = new Line(x1, y1, x2, y2);
                    CommunityLines.add(line);
             
                    switch (tempEntry.getValue()) {
                           case "SURVIVE": //SURVIVE
                               line.getStrokeDashArray().addAll();
                               pane.getChildren().add(line);
                               hmSurvive.put(new Pair<>(shortPair.getKey(),shortPair.getValue()), line);
                               break;
                           case "SPLIT": //SPLIT
                               line.getStrokeDashArray().addAll(5d, 4d, 1d, 4d);
                               pane.getChildren().add(line);
                               hmSplit.put(new Pair<>(shortPair.getKey(),shortPair.getValue()), line);
                               break;
                           case "MERGE": //MERGE
                               line.getStrokeDashArray().addAll(5d, 5d);
                               pane.getChildren().add(line);
                               hmMerge.put(new Pair<>(shortPair.getKey(),shortPair.getValue()), line);
                               break;
                    }
                    line.toBack();
                    Circle circle = hmCommunities.get(tfPair.getKey()).get(shortPair.getKey());
                    
                    //creating map of circles and all the lines connected to it.
                    if(eventGraph.containsKey(circle)){
                        eventGraph.get(circle).add(line);
                    }else{
                        List<Line> tempLineList = new ArrayList<>();
                        tempLineList.add(line);
                        eventGraph.put(circle, tempLineList);
                    }
                    
                    Circle circle2 = hmCommunities.get(tfPair.getValue()).get(shortPair.getValue());
                    if(eventGraph.containsKey(circle2)){
                        eventGraph.get(circle2).add(line);
                    }else{
                        List<Line> tempLineList = new ArrayList<>();
                        tempLineList.add(line);
                        eventGraph.put(circle2, tempLineList);
                    }
                    
                }
            }
            arrlstSurviveNew.add(hmSurvive);
            arrlstSplitNew.add(hmSplit);
            arrlstMergeNew.add(hmMerge);

            hmSurvive = null;
            hmSplit = null;
            hmMerge = null;  
        } 
    }

    /**
     *  Method Name     : EventAnalyzerTools()
     *  Created Date    : 2016-11-02
     *  Description     : The bottom pane of Event Analyzer Dialog that includes
     *                    guides and options for customize the events graph
     *  Version         : 1.0
     *  @author         : Elahe
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
        private HBox EventAnalyzerTools() {
        HBox hbOptions = new HBox(5);

        HBox hbEventGuides = EventGuides();
        hbOptions.getChildren().add(hbEventGuides);
        HBox.setMargin(hbEventGuides, new Insets(5, 5, 5, 5));

        VBox vbEventViewer = EventViewer();
        hbOptions.getChildren().add(vbEventViewer);
        HBox.setMargin(vbEventViewer, new Insets(5, 5, 5, 5));
        
//        method below can be used for styling the curves connecting community circles.
//        VBox vbEventStyle = EventStyle();
//        hbOptions.getChildren().add(vbEventStyle);
//        HBox.setMargin(vbEventStyle, new Insets(5, 5, 5, 5));

        VBox vbCustomizeEventAnalyzer = CustomizeEventAnalyzer();
        hbOptions.getChildren().add(vbCustomizeEventAnalyzer);
        HBox.setMargin(vbCustomizeEventAnalyzer, new Insets(5, 5, 5, 5));

        VBox vbHideCommunities = HideCommunities();
        hbOptions.getChildren().add(vbHideCommunities);
        HBox.setMargin(vbHideCommunities, new Insets(5, 5, 5, 5));

        return hbOptions;

    }
    
    /**
     *  Method Name     : EventGuides()
     *  Created Date    : 2016-11-02
     *  Description     : The guide for recognizing different events in graph
     *  Version         : 1.0
     *  @author         : Elahe
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private HBox EventGuides() {
        Label lblSurvive = new Label("Survive");
        Label lblMerge = new Label("Merge");
        Label lblSplit = new Label("Split");
        Label lblDissolve = new Label("Dissolve");
        Label lblForm = new Label("Form");

        Line lnSurvive = new Line(0, 0, 150, 0);
        Line lnMerge = new Line(0, 0, 150, 0);
        Line lnSplit = new Line(0, 0, 150, 0);
        Circle crclForm = new Circle(5);
        Circle crclDissolve = new Circle(5);

        lnSurvive.getStrokeDashArray().addAll();
        lnMerge.getStrokeDashArray().addAll(5d, 5d);
        lnSplit.getStrokeDashArray().addAll(5d, 4d, 1d, 4d);

        crclForm.getStrokeDashArray().addAll();
        crclForm.setStroke(Color.BLACK);
        crclForm.setStrokeWidth(2);
        crclForm.setFill(Color.TRANSPARENT);
        crclDissolve.getStrokeDashArray().addAll(1d, 4d);
        crclDissolve.setStroke(Color.BLACK);
        crclDissolve.setStrokeWidth(2);
        crclDissolve.setFill(Color.TRANSPARENT);

        VBox vbInfoLabels = new VBox(18);
        vbInfoLabels.getChildren().addAll(lblSurvive, lblMerge, lblSplit,
                lblForm, lblDissolve);

        VBox vbInfoLines = new VBox(30);
        vbInfoLines.getChildren().addAll(lnSurvive, lnMerge, lnSplit,
                crclForm, crclDissolve);
        vbInfoLines.setMargin(lnSurvive, new Insets(5, 0, 0, 0));

        HBox hbInfo = new HBox(10);
        hbInfo.getChildren().addAll(vbInfoLabels, vbInfoLines);
        hbInfo.setStyle("-fx-border-color: black");
        hbInfo.setMargin(vbInfoLabels, new Insets(10, 10, 10, 10));
        hbInfo.setMargin(vbInfoLines, new Insets(10, 10, 10, 10));

        return hbInfo;

    }

    /**
     *  Method Name     : EventViewer()
     *  Created Date    : 2016-11-02
     *  Description     : Creates Checkboxex for Show/Hide curves related to events
     *  Version         : 1.0
     *  @author         : Elahe
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */    
    private VBox EventViewer() {
        CheckBox cbSurvive = new CheckBox("Survive");
        CheckBox cbMerge = new CheckBox("Merge");
        CheckBox cbSplit = new CheckBox("Split");
        CheckBox cbForm = new CheckBox("Form");
        CheckBox cbDissolve = new CheckBox("Dissolve");

        cbSurvive.setSelected(true);
        cbMerge.setSelected(true);
        cbSplit.setSelected(true);
        cbForm.setSelected(true);
        cbDissolve.setSelected(true);

        cbSurvive.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> povChange,
                    Boolean pblnOldValue, Boolean pblnNewValue) {

                for (int i = 0; i < arrlstSurviveNew.size(); i++) {
                    for (HashMap.Entry<Pair<String, String>, Line> entry
                            : arrlstSurviveNew.get(i).entrySet()) {
                        entry.getValue().setVisible(pblnNewValue ? true : false);
                    }
                }

            }

        });

        cbMerge.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> povChange,
                    Boolean pblnOldValue, Boolean pblnNewValue) {

                for (int i = 0; i < arrlstMergeNew.size(); i++) {
                    for (HashMap.Entry<Pair<String, String>, Line> entry
                            : arrlstMergeNew.get(i).entrySet()) {
                        entry.getValue().setVisible(pblnNewValue ? true : false);
                    }
                }
            }

        });

        cbSplit.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> povChange,
                    Boolean pblnOldValue, Boolean pblnNewValue) {

                for (int i = 0; i < arrlstSplitNew.size(); i++) {
                    for (HashMap.Entry<Pair<String, String>, Line> entry
                            : arrlstSplitNew.get(i).entrySet()) {
                        entry.getValue().setVisible(pblnNewValue ? true : false);
                    }
                }
            }

        });
        
        
        VBox vbcbEvents = new VBox(10);
        vbcbEvents.setStyle("-fx-border-color: black");
        vbcbEvents.getChildren().addAll(cbSurvive, cbMerge, cbSplit,cbForm,cbDissolve);
        VBox.setMargin(cbSurvive, new Insets(10, 10, 0, 10));
        VBox.setMargin(cbMerge, new Insets(0, 10, 0, 10));
        VBox.setMargin(cbSplit, new Insets(0, 10, 0, 10));
        VBox.setMargin(cbForm   , new Insets(0, 10, 0, 10));   
        VBox.setMargin(cbDissolve, new Insets(0, 10, 10, 10));   
        
        return vbcbEvents;
    }

    /**
     *  Method Name     : EventStyle()
     *  Created Date    : 2016-11-02
     *  Description     : Give option to select the style of curves between communities
     *  Version         : 1.0
     *  @author         : Elahe
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */    
    
    /*
    private VBox EventStyle() {
        final ToggleGroup tgStyle = new ToggleGroup();

        RadioButton rbLinear = new RadioButton("Line");
        rbLinear.setToggleGroup(tgStyle);
        rbLinear.setSelected(true);
        rbLinear.setUserData("Line");

        RadioButton rbCurve = new RadioButton("Curve");
        rbCurve.setToggleGroup(tgStyle);
        rbCurve.setUserData("Curve");
        //TODO
        //Fix the bug about Curve style
        

        tgStyle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                double x1, x2, y1, y2;
                if (tgStyle.getSelectedToggle() != null) {
                    if (tgStyle.getSelectedToggle().getUserData().toString()
                            == "Line") {
                        for (int i = 0; i < arrlstSurvive.size(); i++) {
                            for (HashMap.Entry<Pair<String, String>, CubicCurve> entry : arrlstSurvive.get(i).entrySet()) {
                                x1 = hmCommunities.get(i).get(entry.getKey().getKey()).getLayoutX();
                                y1 = hmCommunities.get(i).get(entry.getKey().getKey()).getLayoutY();
                                x2 = hmCommunities.get(i).get(entry.getKey().getValue()).getLayoutX();
                                y2 = hmCommunities.get(i).get(entry.getKey().getValue()).getLayoutY();

                                entry.getValue().setControlX1((x1 + x2) / 2);
                                entry.getValue().setControlY1((y1 + y2) / 2);
                                entry.getValue().setControlX2((x1 + x2) / 2);
                                entry.getValue().setControlY2((y1 + y2) / 2);

                            }
                        }

                        for (int i = 0; i < arrlstSplit.size(); i++) {
                            for (HashMap.Entry<Pair<String, String>, CubicCurve> entry : arrlstSplit.get(i).entrySet()) {
                                x1 = hmCommunities.get(i).get(entry.getKey().getKey()).getLayoutX();
                                y1 = hmCommunities.get(i).get(entry.getKey().getKey()).getLayoutY();
                                x2 = hmCommunities.get(i).get(entry.getKey().getValue()).getLayoutX();
                                y2 = hmCommunities.get(i).get(entry.getKey().getValue()).getLayoutY();

                                entry.getValue().setControlX1((x1 + x2) / 2);
                                entry.getValue().setControlY1((y1 + y2) / 2);
                                entry.getValue().setControlX2((x1 + x2) / 2);
                                entry.getValue().setControlY2((y1 + y2) / 2);
                            }
                        }

                        for (int i = 0; i < arrlstMerge.size(); i++) {
                            for (HashMap.Entry<Pair<String, String>, CubicCurve> entry : arrlstMerge.get(i).entrySet()) {
                                x1 = hmCommunities.get(i).get(entry.getKey().getKey()).getLayoutX();
                                y1 = hmCommunities.get(i).get(entry.getKey().getKey()).getLayoutY();
                                x2 = hmCommunities.get(i).get(entry.getKey().getValue()).getLayoutX();
                                y2 = hmCommunities.get(i).get(entry.getKey().getValue()).getLayoutY();

                                entry.getValue().setControlX1((x1 + x2) / 2);
                                entry.getValue().setControlY1((y1 + y2) / 2);
                                entry.getValue().setControlX2((x1 + x2) / 2);
                                entry.getValue().setControlY2((y1 + y2) / 2);
                            }
                        }
                    }

                    if (tgStyle.getSelectedToggle().getUserData().toString()
                            == "Curve") {
                        for (int i = 0; i < arrlstSurvive.size(); i++) {
                            for (HashMap.Entry<Pair<String, String>, CubicCurve> entry : arrlstSurvive.get(i).entrySet()) {
                                x1 = hmCommunities.get(i).get(entry.getKey().getKey()).getLayoutX();
                                y1 = hmCommunities.get(i).get(entry.getKey().getKey()).getLayoutY();
                                x2 = hmCommunities.get(i).get(entry.getKey().getValue()).getLayoutX();
                                y2 = hmCommunities.get(i).get(entry.getKey().getValue()).getLayoutY();

                                entry.getValue().setControlX1(x1 + 20);
                                entry.getValue().setControlY1(y1 - 20);
                                entry.getValue().setControlX2(x2 - 20);
                                entry.getValue().setControlY2(y2 + 20);

                            }
                        }

                        for (int i = 0; i < arrlstMerge.size(); i++) {
                            for (HashMap.Entry<Pair<String, String>, CubicCurve> entry : arrlstMerge.get(i).entrySet()) {
                                x1 = hmCommunities.get(i).get(entry.getKey().getKey()).getLayoutX();
                                y1 = hmCommunities.get(i).get(entry.getKey().getKey()).getLayoutY();
                                x2 = hmCommunities.get(i).get(entry.getKey().getValue()).getLayoutX();
                                y2 = hmCommunities.get(i).get(entry.getKey().getValue()).getLayoutY();

                                entry.getValue().setControlX1((x1 + x2) / 3);
                                entry.getValue().setControlY1(y2 + (y1 + y2) / 3);
                                entry.getValue().setControlX2(2 * (x1 + x2) / 3);
                                entry.getValue().setControlY2(y1 - ((y1 + y2) / 3));

                            }
                        }

                        for (int i = 0; i < arrlstSplit.size(); i++) {
                            for (HashMap.Entry<Pair<String, String>, CubicCurve> entry : arrlstSplit.get(i).entrySet()) {
                                x1 = hmCommunities.get(i).get(entry.getKey().getKey()).getLayoutX();
                                y1 = hmCommunities.get(i).get(entry.getKey().getKey()).getLayoutY();
                                x2 = hmCommunities.get(i).get(entry.getKey().getValue()).getLayoutX();
                                y2 = hmCommunities.get(i).get(entry.getKey().getValue()).getLayoutY();

                                entry.getValue().setControlX1((x1 + x2) / 3);
                                entry.getValue().setControlY1(y2 + (y1 + y2) / 3);
                                entry.getValue().setControlX2(2 * (x1 + x2) / 3);
                                entry.getValue().setControlY2(y1 - ((y1 + y2) / 3));

                            }
                        }
                    }
                }
            }
        });

//        RadioButton rbBent      = new RadioButton("Bent");
//        RadioButton rbCubic     = new RadioButton("Cubic");
        VBox vbrbStyle = new VBox(10);
        vbrbStyle.setStyle("-fx-border-color: black");
        vbrbStyle.getChildren().addAll(rbLinear, rbCurve);//, rbBent, rbCubic

        vbrbStyle.setMargin(rbLinear, new Insets(10, 10, 0, 10));
        vbrbStyle.setMargin(rbCurve, new Insets(0, 10, 0, 10));
//        vbrbStyle.setMargin(rbBent     , new Insets(0, 10, 0, 10)); 
//        vbrbStyle.setMargin(rbCubic    , new Insets(0, 10, 10, 10)); 

        return vbrbStyle;
    }
    */
    
    /**
     *  Method Name     : CustomizeEventAnalyzer()
     *  Created Date    : 2016-11-02
     *  Description     : Some options for customizing the graph
     *  Version         : 1.0
     *  @author         : Elahe
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */    
    private VBox CustomizeEventAnalyzer() {
        cbCommunityLabelsAsSize = new CheckBox("Community Size as Label");
        
        //to display the community size as labels.
        cbCommunityLabelsAsSize.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> povChange,
                    Boolean pblnOldValue, Boolean pblnNewValue) {
                if(cbCommunityLabelsAsSize.isSelected()){
                    for(Text txtCaption : CommunityLabelsAsSize){
                        txtCaption.setVisible(pblnNewValue);
                    }
                }else{
                    for(Text txtCaption : CommunityLabelsAsSize){
                        txtCaption.setVisible(pblnNewValue);
                    }          
                }
            }

        });

        //TO DISPLAY THE COMMUNITY LABELS ON THE EVENTS WINDOW
        cbShowCommunityLabels = new CheckBox("Show Community Labels");
        cbShowCommunityLabels.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> povChange,
                    Boolean pblnOldValue, Boolean pblnNewValue) {
                if(cbShowCommunityLabels.isSelected()){
                    for(Text txtCaption : CommunityLabels)
                    {
                        txtCaption.setVisible(pblnNewValue);
                    }
                }else{
                    for(Text txtCaption : CommunityLabels)
                    {
                        txtCaption.setVisible(pblnNewValue);
                    }
                    
                }   
            }
        });

        Spinner spnSizeMultiplier = new Spinner(1, 10, 1);
        spnSizeMultiplier.setPrefWidth(100);

        spnSizeMultiplier.valueProperty().addListener(new ChangeListener<Integer>() {
            public void changed(ObservableValue<? extends Integer> povChange,
                    Integer pblnOldValue, Integer pblnNewValue) {
                    for(Map.Entry<Circle, Double> entry : CommunityCircles.entrySet())
                    {
                        entry.getKey().setRadius(entry.getValue()*pblnNewValue);
                    }
                    for(Line line : CommunityLines)
                    {
                        line.setStrokeWidth(pblnNewValue);
                    }
            }
        });


        GridPane grdSizeMultiplier = new GridPane();
        grdSizeMultiplier.setHgap(2);
        grdSizeMultiplier.setVgap(2);
        grdSizeMultiplier.add(new Label("Size Multiplier"), 0, 0);
        grdSizeMultiplier.add(spnSizeMultiplier, 1, 0);
        VBox vbcbCustomize = new VBox(10);
        vbcbCustomize.setStyle("-fx-border-color: black");
        vbcbCustomize.getChildren().addAll(cbCommunityLabelsAsSize,
                cbShowCommunityLabels,
                grdSizeMultiplier);
        vbcbCustomize.setMargin(cbCommunityLabelsAsSize, new Insets(10, 10, 0, 10));
        vbcbCustomize.setMargin(cbShowCommunityLabels, new Insets(0, 10, 0, 10));
        vbcbCustomize.setMargin(grdSizeMultiplier, new Insets(0, 10, 10, 10));

        return vbcbCustomize;
    }

    /**
     *  Method Name     : HideCommunities()
     *  Created Date    : 2017-03-22
     *  Description     : Hides arbitrary communities in the graph
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */      
    private VBox HideCommunities() {
        Label lblHideCommunities = new Label("Hide Communities");
        GridPane gpCommunities = new GridPane();
        gpCommunities.setHgap(10);
        gpCommunities.setVgap(10);
        gpCommunities.setPadding(new Insets(2, 5, 2, 2));

        int intMaxSizeOfCommunityMaps = 0;
        for (HashMap.Entry<Integer, HashMap<String, Circle>> entry : hmCommunities.entrySet()) {
            if (entry.getValue().size() > intMaxSizeOfCommunityMaps) {
                intMaxSizeOfCommunityMaps = entry.getValue().size();
            }
        }

        Rectangle rctnglTemporary;
        for (int i = 0; i < commColors.size(); i++) {
            rctnglTemporary = new Rectangle(20, 15);
            Color clrCommunityColor = commColors.get(i);
            rctnglTemporary.setFill(clrCommunityColor);
            arrlRectangle.add(rctnglTemporary);
            arrCheckBox.add(new CheckBox());
            hideCommCheck.put(arrCheckBox.get(i), clrCommunityColor);
            rctnglTemporary = null;
        }
        
        
        for(int i=0;i<arrCheckBox.size();i++){
            final CheckBox check = arrCheckBox.get(i);
            arrCheckBox.get(i).selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue){
                        Color tempcolor = hideCommCheck.get(check);
                        for(Map.Entry<Circle, Double> circleEntry : CommunityCircles.entrySet()){
                            if(tempcolor.equals(circleEntry.getKey().getFill())){
                                circleEntry.getKey().setVisible(false);
                                if(eventGraph.containsKey(circleEntry.getKey())){
                                    List<Line> tempLines = eventGraph.get(circleEntry.getKey());
                                    for(Line line: tempLines)
                                        line.setVisible(false);
                                }
                                if(mapCircleLabels.containsKey(circleEntry.getKey()))
                                    mapCircleLabels.get(circleEntry.getKey()).setVisible(false);
                                if(mapCircleLabelsAsSize.containsKey(circleEntry.getKey()))
                                    mapCircleLabelsAsSize.get(circleEntry.getKey()).setVisible(false);
                            }
                        }   
                        
                    }else{
                        Color tempcolor = hideCommCheck.get(check);
                        for(Map.Entry<Circle, Double> circleEntry : CommunityCircles.entrySet()){
                            if(tempcolor.equals(circleEntry.getKey().getFill())){
                                circleEntry.getKey().setVisible(true);
                                if(eventGraph.containsKey(circleEntry.getKey())){
                                    List<Line> tempLines = eventGraph.get(circleEntry.getKey());
                                    for(Line line: tempLines)
                                        line.setVisible(true);   
                                }
                                if(cbShowCommunityLabels.isSelected()){
                                    if(mapCircleLabels.containsKey(circleEntry.getKey()))
                                        mapCircleLabels.get(circleEntry.getKey()).setVisible(true);
                                }
                                if(cbCommunityLabelsAsSize.isSelected()){
                                    if(mapCircleLabelsAsSize.containsKey(circleEntry.getKey()))
                                        mapCircleLabelsAsSize.get(circleEntry.getKey()).setVisible(true);
                                }
                            }
                        }
                    }
                }
            });
        }
        
        ToggleButton btnSelectAll = new ToggleButton("Select All");
        btnSelectAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(btnSelectAll.isSelected()){
                    for (int i = 0; i < arrCheckBox.size(); i++){
                        arrCheckBox.get(i).setSelected(true);
                    }                   
                }else{
                    for (int i = 0; i < arrCheckBox.size(); i++){
                        arrCheckBox.get(i).setSelected(false);
                    }   
                }
            }
        });

        for (int i = 0; i < arrlRectangle.size(); i++) {
            gpCommunities.add(arrlRectangle.get(i), 0, i);

            gpCommunities.add(arrCheckBox.get(i), 1, i);
        }

        ScrollPane scrlpHideCommunities = new ScrollPane(gpCommunities);

        VBox vbHideCommunities = new VBox(10);
        vbHideCommunities.setStyle("-fx-border-color: black");
        vbHideCommunities.getChildren().addAll(lblHideCommunities, scrlpHideCommunities,
                btnSelectAll);
        VBox.setMargin(lblHideCommunities, new Insets(10, 10, 0, 10));
        VBox.setMargin(scrlpHideCommunities, new Insets(0, 10, 0, 10));
        VBox.setMargin(btnSelectAll, new Insets(0, 10, 10, 10));

        return vbHideCommunities;
    }

    
    /**
     *  Method Name     : createZoomPane()
     *  Created Date    : 2016-11-02
     *  Description     : 
     *  Version         : 1.0
     *  @author         : Elahe
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     * 
     *  NOTE : Reference : http://stackoverflow.com/questions/16680295/javafx-correct-scaling
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */      
    public Parent createZoomPane(final Group group) {
        final double SCALE_DELTA = 1.1;
        final StackPane zoomPane = new StackPane();

        zoomPane.getChildren().add(group);

        final ScrollPane scroller = new ScrollPane();
        final Group scrollContent = new Group(zoomPane);
        scroller.setContent(scrollContent);

        scroller.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable,
                    Bounds oldValue, Bounds newValue) {
                zoomPane.setMinSize(newValue.getWidth(), newValue.getHeight());
            }
        });

        scroller.setPrefViewportWidth(SceneConfig.EVENTCANVAS_WIDTH);
        scroller.setPrefViewportHeight(SceneConfig.EVENTCANVAS_HEIGHT);

        zoomPane.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                event.consume();
                if (event.getDeltaY() == 0) {
                    return;
                }

                double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA
                        : 1 / SCALE_DELTA;

                // amount of scrolling in each direction in scrollContent coordinate
                // units
                Point2D scrollOffset = figureScrollOffset(scrollContent, scroller);

                group.setScaleX(group.getScaleX() * scaleFactor);
                group.setScaleY(group.getScaleY() * scaleFactor);

                // move viewport so that old center remains in the center after the
                // scaling
                repositionScroller(scrollContent, scroller, scaleFactor, scrollOffset);

            }
        });
        final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<Point2D>();
        scrollContent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
            }
        });

        scrollContent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double deltaX = event.getX() - lastMouseCoordinates.get().getX();
                double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
                double deltaH = deltaX * (scroller.getHmax() - scroller.getHmin()) / extraWidth;
                double desiredH = scroller.getHvalue() - deltaH;
                scroller.setHvalue(Math.max(0, Math.min(scroller.getHmax(), desiredH)));

                double deltaY = event.getY() - lastMouseCoordinates.get().getY();
                double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
                double deltaV = deltaY * (scroller.getHmax() - scroller.getHmin()) / extraHeight;
                double desiredV = scroller.getVvalue() - deltaV;
                scroller.setVvalue(Math.max(0, Math.min(scroller.getVmax(), desiredV)));
            }
        });
        
        scroller.setOnMousePressed((MouseEvent mouseEvent) -> {       
            
            // RIGHT CLICK OF THE MOUSE
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                UIVertexEventProperty.clearSelectedVertex();
        });

        return scroller;
    }

    private Point2D figureScrollOffset(Node scrollContent, ScrollPane scroller) {
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        double hScrollProportion = (scroller.getHvalue() - scroller.getHmin()) / (scroller.getHmax() - scroller.getHmin());
        double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        double vScrollProportion = (scroller.getVvalue() - scroller.getVmin()) / (scroller.getVmax() - scroller.getVmin());
        double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);
        return new Point2D(scrollXOffset, scrollYOffset);
    }

    private void repositionScroller(Node scrollContent, ScrollPane scroller, double scaleFactor, Point2D scrollOffset) {
        double scrollXOffset = scrollOffset.getX();
        double scrollYOffset = scrollOffset.getY();
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        if (extraWidth > 0) {
            double halfWidth = scroller.getViewportBounds().getWidth() / 2;
            double newScrollXOffset = (scaleFactor - 1) * halfWidth + scaleFactor * scrollXOffset;
            scroller.setHvalue(scroller.getHmin() + newScrollXOffset * (scroller.getHmax() - scroller.getHmin()) / extraWidth);
        } else {
            scroller.setHvalue(scroller.getHmin());
        }
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        if (extraHeight > 0) {
            double halfHeight = scroller.getViewportBounds().getHeight() / 2;
            double newScrollYOffset = (scaleFactor - 1) * halfHeight + scaleFactor * scrollYOffset;
            scroller.setVvalue(scroller.getVmin() + newScrollYOffset * (scroller.getVmax() - scroller.getVmin()) / extraHeight);
        } else {
            scroller.setHvalue(scroller.getHmin());
        }
    }

    
    private void parseEventAnalyzerResult(List<String> plstEventAnalyzerResult) {
        System.out.println("\nEventAnalyzerDialog parseEventAnalyzerResult() :- Full Event Parse Sring : "+plstEventAnalyzerResult );
        Collections.sort(plstEventAnalyzerResult);
        for (String strCurrentResultString : plstEventAnalyzerResult) {
            parseEventAnalyzerResultString(strCurrentResultString);
        }
    }
    /**
     *  Method Name     : parseEventAnalyzerResultString()
     *  Created Date    : 2016-11-02
     *  Description     : Parses the string which is created by Event Analyzer
     *                    (Logic part) to represent the events between communities as a graph
     *  Version         : 1.0
     *  @author         : Elahe
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pstrEventAnalyzerResult : String
     * 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2017-04-20      @sankalp        added logic to populate the newMap data structure having 
     *                                  communities and events between them.
     * 
    */      
    private void parseEventAnalyzerResultString(String pstrEventAnalyzerResult) {
        
        Map<String, Integer> mapEvents;
        String strDelimiter;
        String[] strEventTokens;
        int intSourceTimeFrame;
        int intResultTimeFrame;
        String[] strTimeFrame;
        String[] strSingleEventToken;
        String[] strSourceCommunities;
        String[] strResultCommunities;
        String strEventName;

        strDelimiter = "[;]";
        strEventTokens = pstrEventAnalyzerResult.split(strDelimiter);
        strDelimiter = "[:]";
        strTimeFrame = strEventTokens[0].split(strDelimiter);

        intSourceTimeFrame = Integer.parseInt(strTimeFrame[0]);
        intResultTimeFrame = Integer.parseInt(strTimeFrame[1]);

        if (intResultTimeFrame - intSourceTimeFrame > 1) {
            return;
        }
        
        mapEvents = new HashMap<>();
        
        Pair<Integer,Integer> pairTf;
        pairTf = new Pair(intSourceTimeFrame,intResultTimeFrame);
        Map<Pair<String, String>, String> comMap;
        comMap = new HashMap<>();

        for (int i = 1; i < strEventTokens.length; i++) {
            strDelimiter = "[:]";
            strSingleEventToken = strEventTokens[i].split(strDelimiter);
            strEventName = strSingleEventToken[0];

            strDelimiter = "[,]";
            strSourceCommunities = strSingleEventToken[1].split(strDelimiter);
            strResultCommunities = strSingleEventToken[2].split(strDelimiter);

            for (String srcCommunity : strSourceCommunities) {
                for (String resCommunity : strResultCommunities) {
                    
                    Pair<String,String> comPair = new Pair(srcCommunity,resCommunity);
                    comMap.put(comPair,strEventName);

                    if (intSourceTimeFrame == intResultTimeFrame
                            && intSourceTimeFrame != 0
                            && intResultTimeFrame != strTimeFrames.length - 1) {
                        switch (EventName.valueOf(strEventName).ordinal()) {
                            case 2: //Dissolve
                                hmCommunities.get(intSourceTimeFrame).get(srcCommunity).
                                        getStrokeDashArray().addAll(1d, 4d);
                                hmCommunities.get(intSourceTimeFrame).get(srcCommunity).setStroke(Color.BLACK);
                                hmCommunities.get(intSourceTimeFrame).get(srcCommunity).setStrokeWidth(2);
                                break;
                            case 5: //Form
                                hmCommunities.get(intSourceTimeFrame).get(srcCommunity).
                                        getStrokeDashArray().addAll();
                                hmCommunities.get(intSourceTimeFrame).get(srcCommunity).setStroke(Color.BLACK);
                                hmCommunities.get(intSourceTimeFrame).get(srcCommunity).setStrokeWidth(3);
                                break;
                        
                        }
                    }
                    
                    // Note: mapEvents will have dissolved communities correctly marked but not the formed
                    // communities since they will be overwritten by dissolved comm, due to the analyzer string format.
                    if(intSourceTimeFrame == intResultTimeFrame){
                        mapEvents.put(srcCommunity, EventName.valueOf(strEventName).ordinal());
                        if(EventStatus.get(intSourceTimeFrame).isEmpty()){
                            EventStatus.get(intSourceTimeFrame).put(strEventName, Boolean.TRUE);
                        }else{
                            EventStatus.get(intSourceTimeFrame).put(strEventName, Boolean.TRUE);
                        }
                        
                    }else{
                        if(EventStatus.get(intSourceTimeFrame).isEmpty()){
                            EventStatus.get(intSourceTimeFrame).put(strEventName, Boolean.TRUE);
                        }else{
                            EventStatus.get(intSourceTimeFrame).put(strEventName, Boolean.TRUE);
                        }
                    }
                }
                
                communityEventMap.get(intSourceTimeFrame).put(srcCommunity, strEventName);
            }
        }
        newMap.put(pairTf, comMap);

        if (intSourceTimeFrame == intResultTimeFrame) {
            arrlstEvents.get(intSourceTimeFrame).putAll(mapEvents);
        }
    }

    
    // TODO
    // Minimizing crossing of edges that represents the events between timeframes
    private HashMap<Integer, Integer> BaryCenterHeuristic(
            int[][] pintAdjacencyMatrix,
            HashMap<Integer, Integer> phmNewRanksOfSourceTimeFrame,
            int pintSource,
            int pintResult) {

        int intSumOfRanks;
        int intNumberOfNeighbours;
        TreeMap<Double, Integer> hmRanks = new TreeMap<Double, Integer>();
        HashMap<Integer, Integer> hmNewRanks = new HashMap<>();
        int intRankCount;

        int intTemporaryRankOfNewCommunities = pintResult;
        for (int i = 0; i < pintResult; i++) {
            intSumOfRanks = 0;
            intNumberOfNeighbours = 0;
            System.err.println(">>>>i: " + i);
            for (int j = 0; j < pintSource; j++) {
//                    System.out.println("events.EventAnalyzerDialog.BaryCenterHeuristic()");

                if (pintAdjacencyMatrix[j][i] > 0) {
//                        for (Map.Entry<Integer, Integer> entry : phmNewRanksOfSourceTimeFrame.entrySet()) {
//                            Integer key = entry.getKey();
//                            Integer value = entry.getValue();
//                            System.out.println(key + " -------------- " + value);
//                            
//                        }
                    try {
                        intSumOfRanks += phmNewRanksOfSourceTimeFrame.get(j);
                    } catch (Exception e) {
                        System.err.println("events.EventAnalyzerDialog.BaryCenterHeuristic()");
                        System.err.println("j : " + j + "i :" + i);
                        for (Map.Entry<Integer, Integer> entry : phmNewRanksOfSourceTimeFrame.entrySet()) {
                            Integer key = entry.getKey();
                            Integer value = entry.getValue();
                            System.err.println("***" + key + " > " + value);

                        }
                    }

                    intNumberOfNeighbours++;
                }

            }
            if (intNumberOfNeighbours > 0) {
                System.err.println("sum of Rank: " + (intSumOfRanks * 1.0) / intNumberOfNeighbours + "i : " + i);

                Object res = hmRanks.putIfAbsent((intSumOfRanks * 1.0) / intNumberOfNeighbours, i);
                if (res == null) {
                    hmRanks.put(((intSumOfRanks * 1.0) / intNumberOfNeighbours) + 0.001, i);
                }
            } else {
                System.err.println(" temporaryRank: " + intTemporaryRankOfNewCommunities + "i : " + i);
                intTemporaryRankOfNewCommunities++;
                hmRanks.put(intTemporaryRankOfNewCommunities * 1.0, i);
            }
        }

        intRankCount = -1;
        System.err.println("##########" + hmRanks.size());
        for (double key : hmRanks.keySet()) {
            intRankCount++;

            System.err.println(key + " > " + hmRanks.get(key)
                    + " > " + intRankCount);

            if (hmNewRanks.containsKey(hmRanks.get(key))) {
                hmNewRanks.put(hmRanks.get(key) + 1, intRankCount);
            } else {
                hmNewRanks.put(hmRanks.get(key), intRankCount);
            }

        }
        System.err.println("##########");
        return hmNewRanks;

    }
    
    /**
     *  Method Name     : parseIndividualEventAnalyzerResult()
     *  Created Date    : 2017-04-20
     *  Description     : parse the individual events map entries.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     * 
    */  
    private void parseIndividualEventAnalyzerResult(Map<String, Map<String, ArrayList<String>>> individualEvents, int timeFrameIndex) {
        for (Map.Entry<String, Map<String, ArrayList<String>>> entry : individualEvents.entrySet()) {
            if(entry.getKey().equals(IndividualEventName.JOIN.toString())){
                joinEventMap = entry.getValue();
                if(joinEventMap.size()>0)
                    EventStatus.get(timeFrameIndex).put(entry.getKey(), Boolean.TRUE);
            }
            else if (entry.getKey().equals(IndividualEventName.LEFT.toString())){
                leftEventMap = entry.getValue();
                if(leftEventMap.size()>0)
                    EventStatus.get(timeFrameIndex).put(entry.getKey(), Boolean.TRUE);
            }
            else if (entry.getKey().equals(IndividualEventName.APPEAR.toString())){
                appearEventMap = entry.getValue();
                if(appearEventMap.size()>0)
                    EventStatus.get(timeFrameIndex).put(entry.getKey(), Boolean.TRUE);
            }
            else if (entry.getKey().equals(IndividualEventName.DISAPPEAR.toString())){
                disappearEventMap = entry.getValue();   
                if(disappearEventMap.size()>0)
                    EventStatus.get(timeFrameIndex).put(entry.getKey(), Boolean.TRUE);
            }
        }
    }   

}