/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import globalstate.GraphTab;
import globalstate.MeerkatUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *  Class Name      : VertexTimeline
 *  Created Date    : 2017-04-27
 *  Description     : Contains logic to generate time line window for event vertex.
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/
public class VertexTimeline {
    private static double bigRadius = 0;
    
    /**
     *  Method Name     : createVertexTimeline()
     *  Created Date    : 2017-04-27
     *  Description     : creates the vertex time line for UIEventVertex.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    public static void createVertexTimeline(UIVertexEvent uiVertex){
        
        String[] strTimeFrames = MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getTimeFrames();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Vertex Timeline");
        
        Pane pane = new Pane();
        Label  lblTimeFrameLabel;
        pane.setPrefSize(800, 300);
        GraphTab currentGraph = MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab();
        Circle vertexCircle = new Circle(uiVertex.getVertexShape().getRadius());
        
        //get Community Info
        ArrayList<Map<String, List<Integer>>> comInfo = EventAnalyzerDialog.getCommInfo();
        ArrayList<Map<String, String>> communityEvent = EventAnalyzerDialog.getCommEventMap();
        
        //add vertex ID and animation checkbox
        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(10));
        hbox1.setSpacing(20);
        Label vertexID = new Label("Vertex ID: "+ String.valueOf(uiVertex.getVertexID()));
        CheckBox chk = new CheckBox("Enable Animation");
        chk.setSelected(true);

        hbox1.getChildren().add(vertexID);
        hbox1.getChildren().add(chk);
        
        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(20);
        CheckBox labelchk = new CheckBox("Show Community Label");
        CheckBox eventchk = new CheckBox("Show Community Event");
        labelchk.setSelected(true);
        eventchk.setSelected(true);

        hbox2.getChildren().add(labelchk);
        hbox2.getChildren().add(eventchk);
        
        ArrayList<Label> commLabels = new ArrayList<>();
        ArrayList<Label> eventLabels = new ArrayList<>();
        
        // x,y drawing positions for the circles.
        double x_pane_divider = pane.getPrefWidth()/strTimeFrames.length;
        double x_draw_position = x_pane_divider/2;
        double y_pane_divider = pane.getPrefHeight()/6;
        double y_draw_position = y_pane_divider/2;
        double path_start_x_pos = 0, path_start_y_pos = 0,path_end_x_pos = 0,path_end_y_pos = 0;
        
        for (int tf =0; tf<strTimeFrames.length;tf++){
            lblTimeFrameLabel = new Label(strTimeFrames[tf]);            
            Line line = new Line(x_pane_divider + tf*x_pane_divider, y_pane_divider+y_draw_position, x_pane_divider + tf*x_pane_divider, pane.getPrefHeight()-y_pane_divider);
            line.getStrokeDashArray().addAll(2d);
            
            Circle comCircle = null;
            Label comID = null;
            Color color;
            Label eventName = null;
            
            for(Map.Entry<String, List<Integer>> entry : comInfo.get(tf).entrySet()){
                if(entry.getValue().contains(uiVertex.getVertexID())){
                    comCircle = new Circle(entry.getValue().size());
                    if(bigRadius<comCircle.getRadius())
                        bigRadius = comCircle.getRadius();
                    comID = new Label(entry.getKey());
                    //TODO add the scaling paramters to handle very large radius circles.
                    comCircle.relocate(x_draw_position + tf*x_pane_divider, 3*y_pane_divider);
                    
                    comID.setLayoutX(comCircle.getLayoutX()-comCircle.getRadius()-20);
                    comID.setLayoutY(comCircle.getLayoutY()-comCircle.getRadius() -30);
                    comID.setVisible(true);
                    commLabels.add(comID);
                    
                    color = currentGraph.getAccordionTabValues(tf).getCommunitiesValues().getCommunityColors().get(entry.getKey());
                    comCircle.setFill(color);
                    
                    eventName = new Label(communityEvent.get(tf).get(entry.getKey()));
                    eventName.setLayoutX(comCircle.getLayoutX()-comCircle.getRadius()-20);
                    eventName.setLayoutY(comCircle.getLayoutY()+comCircle.getRadius() +20);
                    eventName.setVisible(true);
                    eventLabels.add(eventName);
                    
                    if(tf==0){
                        path_start_x_pos = comCircle.getLayoutX();
                        path_start_y_pos = comCircle.getLayoutY();
                    }else if(tf == strTimeFrames.length-1){
                        path_end_x_pos = comCircle.getLayoutX();
                        path_end_y_pos = comCircle.getLayoutY();
                    }     
                }   
            }
            lblTimeFrameLabel.relocate(x_draw_position + tf*x_pane_divider - 2*bigRadius, y_pane_divider);
            pane.getChildren().add(lblTimeFrameLabel);
            pane.getChildren().add(line);
            pane.getChildren().add(comCircle);
            pane.getChildren().add(comID);
            pane.getChildren().add(eventName);
        }
        
        pane.getChildren().add(hbox1);
        Line path = new Line(path_start_x_pos, path_start_y_pos, path_end_x_pos, path_end_y_pos);
        path.getStrokeDashArray().addAll(2d, 21d);
        pane.getChildren().add(path);
        pane.getChildren().add(vertexCircle);
        hbox2.relocate(0, pane.getPrefHeight()-y_pane_divider);
        pane.getChildren().add(hbox2);
        path.toBack();
        
        //create a transition path for the vertex to follow along multiple timeframes
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setPath(path);
        pathTransition.setNode(vertexCircle);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        //pathTransition.setAutoReverse(true);
        pathTransition.play();
        
        //listener to enable/disable animation
        chk.selectedProperty().addListener( new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue)
                    pathTransition.stop();
                else
                    pathTransition.play();
            }
            
        });
        
        //listener to show/not-show community labels.
        labelchk.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    for(Label label : commLabels)
                        label.setVisible(false);
                }else{
                    for(Label label : commLabels)
                        label.setVisible(true);
                }
            }         
        });
        
        //listener to show/not-show event labels.
        eventchk.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    for(Label label : eventLabels)
                        label.setVisible(false);
                }else{
                    for(Label label : eventLabels)
                        label.setVisible(true);
                }
            }         
        });
        
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
