/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import accordiontab.AccordionTabContents;
import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import config.LangConfig;
import config.SceneConfig;
import config.StatusMsgsConfig;
import globalstate.GraphCanvas;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import graphelements.UIVertex;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.elements.SizeToolBox;
import ui.utilities.KeyPressFunctionality;

/**
 *
 * @author AICML Administrator
 */
public class AddVertexDialog {
    
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strTitle;
    private static String strMessage ;  
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */    
    private static String getTitle() {
        return strTitle;
    }
    private static String getMessage() {
        return strMessage;
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 
     *  Description     : Sets the parameters of the Vertex Add Confirmation Dialog Box
     *  Version         : 1.0
     *  @author         : 
     * 
     *  @param pstrTitle : String
     *  @param pstrMessage : String
     *  @param pstrIconPath : String    
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters(String pstrTitle, String pstrMessage, String pstrIconPath) {
        strTitle = pstrTitle;
        strMessage = pstrMessage;
    }
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 
     *  Description     : Closes the Vertex Add Dialog Box
     *  Version         : 1.0
     *  @author         : 
     * 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * @param pController
     * 
    */

    public static void Display(AnalysisController pController) {
    
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int projectId = UIInstance.getActiveProjectTab().getProjectID();
        GraphTab graphTab = UIInstance.getActiveProjectTab().getActiveGraphTab();
        int graphId = graphTab.getGraphID();
        int timeframeIndex = graphTab.getTimeFrameIndex();

        GraphCanvas graphCanvas = graphTab.getGraphCanvas();
        String verteXLabelAttr = graphTab.getVertexLabelAttr();
        String vertexTooltipAttr = graphTab.getVertexTooltipAttr();
        //get mouse click position relative to PanAndZoomPane
        Double x = graphTab.getZoomPaneClickX();
        Double y = graphTab.getZoomPaneClickY();
        Double[] arrPostionVertexInLogic = GraphAPI.getConvertedPositionFromUIToLogic(x, y, SceneConfig.GRAPHCANVAS_WIDTH, 
                        SceneConfig.GRAPHCANVAS_HEIGHT);
        //check if x and y is in range (0,1) else put them = 0.5
        System.out.println("AddVertexDialog : x,y = " + x + ", " + y + "  convertedInLogic X,y = " + arrPostionVertexInLogic[0] + ", " + arrPostionVertexInLogic[1]);
        if((arrPostionVertexInLogic[0] >=0 && arrPostionVertexInLogic[0]<1) && (arrPostionVertexInLogic[1] >=0 && arrPostionVertexInLogic[1]<1)){
            
            //Add new vertex in logic - get its id
            int intNewVertexId = GraphAPI.addVertex(projectId, graphId, timeframeIndex, arrPostionVertexInLogic[0], arrPostionVertexInLogic[1]);
        
            graphCanvas.updateCanvasAfterAddingVertex(intNewVertexId, projectId, graphId, timeframeIndex, verteXLabelAttr, vertexTooltipAttr);
            //System.out.println(" AddVertexDialog If Case: x,y");
        }else{
            //if vertex is being added outside the bounds of the graph i.e. its coordinate is -ve in any axis relative to panAndZoomPane
            //then add it at the same location in UI but in logic store it at (0.5,0.5)
            arrPostionVertexInLogic[0] = 0.5;
            arrPostionVertexInLogic[1] = 0.5;
            //Add new vertex in logic - get its id
            int intNewVertexId = GraphAPI.addVertex(projectId, graphId, timeframeIndex, arrPostionVertexInLogic[0], arrPostionVertexInLogic[1]);
            //System.out.println("AddVertexDialog Else Case: x,y");
            graphCanvas.updateCanvasAfterAddingVertexOutsideGraphBoundary(intNewVertexId, projectId, graphId, timeframeIndex, x, y, verteXLabelAttr, vertexTooltipAttr);
        
        }
        
        //update Accordion Tabs
        //update accordion tab values
                graphTab.updateAccordionValues();
                
                //update the status of the project after vertex is added
                ProjectStatusTracker.updateProjectModifiedStatus(UIInstance.getActiveProjectID(), ProjectStatusTracker.eventVertexAdded);
                
                //update UI after vertices/edges have been removed.
                UIInstance.UpdateUI();
                
                //update SizeToolBox
                SizeToolBox.getInstance().disableSizeToolbox();      

    }
    
    
    public static void DisplayMultipleTimeFrameOption(AnalysisController pController) {
    
        
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        int projectId = UIInstance.getActiveProjectTab().getProjectID();
        GraphTab graphTab = UIInstance.getActiveProjectTab().getActiveGraphTab();
        int graphId = graphTab.getGraphID();
        int timeframeIndex = graphTab.getTimeFrameIndex();

            Stage stgAddVertexMultipleTimeFramesDialog = new Stage();
            pController.updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);
        
            Label lblMessage = new Label("Add vertex in multiple timefames");
            
            //loop over all time frames and add check box for each of them
            VBox vboxTimeFrames = new VBox();
            for(String timeFrameName : graphTab.getTimeFrames()){
                CheckBox tfCheckBox = new CheckBox();
                tfCheckBox.setText(timeFrameName);
                tfCheckBox.setSelected(false);
                
                vboxTimeFrames.getChildren().add(tfCheckBox);
            }
            
            
            Button btnOk = new Button(LangConfig.GENERAL_OK);
            
            btnOk.setOnAction(e -> {

                    //if no time frame selected show a message in new stage 
                    //that user has to slelect at least one time frame
                    List<String> listTimeFrames = new ArrayList<>();
                    for(Node checkBoxNode : vboxTimeFrames.getChildren()){
                        
                        CheckBox cbox = (CheckBox)checkBoxNode;
                        if(cbox.isSelected()){
                            listTimeFrames.add(cbox.getText());
                        }
                        
                    }
                    
                    if(!listTimeFrames.isEmpty()){
                        addVertexInMultipleTimeFrames(projectId, graphId, timeframeIndex,listTimeFrames);
                    }else{
                        //show a message in new stage 
                        //that user has to slelect at least one time frame

                        Stage alertStage = new Stage();
                        alertStage.setTitle("Please select at least one vertex");

                        VBox vbox = new VBox();
                        HBox hbox = new HBox();

                        Label label = new Label("Please select at least one time frame");
                        Button btnnYes = new Button(LangConfig.GENERAL_OK);
                        btnnYes.setOnAction(m -> {

                            alertStage.close();
                        });

                        hbox.getChildren().addAll(btnnYes);
                        hbox.setSpacing(20);
                        hbox.setPadding(new Insets(5,5,5,5));
                        hbox.setAlignment(Pos.CENTER);
                        vbox.getChildren().addAll(label,hbox);
                        vbox.setSpacing(20);
                        vbox.setPadding(new Insets(10,10,10,10));

                        Scene alertScene = new Scene(vbox);
                        alertStage.setScene(alertScene);
                        alertStage.initOwner(stgAddVertexMultipleTimeFramesDialog);
                        alertStage.initModality(Modality.WINDOW_MODAL);
                        alertStage.show();

                        //stgAddVertexMultipleTimeFramesDialog.close();

                    }
                stgAddVertexMultipleTimeFramesDialog.close();    
            });

            btnOk.setCancelButton(false);        
            btnOk.setAlignment(Pos.CENTER);

            Button btnCancel = new Button(LangConfig.GENERAL_CANCEL);
            btnCancel.setOnAction(e -> {   
                stgAddVertexMultipleTimeFramesDialog.close();
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            });
            btnCancel.setCancelButton(true);        
            btnCancel.setAlignment(Pos.CENTER);


            HBox hbox = new HBox(btnOk, btnCancel);
            //hbox.setPadding(new Insets(10, 10, 10, 10));
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(20);
            VBox vbox = new VBox(lblMessage, vboxTimeFrames, hbox);                
            vbox.setPadding(new Insets(10, 10, 10, 10));
            vbox.setSpacing(20);

            Scene scnAddVertexMultipleTimeFrames = new Scene(vbox);
            scnAddVertexMultipleTimeFrames.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                if (evt.getCode().equals(KeyCode.ESCAPE)) {
                    stgAddVertexMultipleTimeFramesDialog.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
                }
            });

            stgAddVertexMultipleTimeFramesDialog.initModality(Modality.APPLICATION_MODAL);
            stgAddVertexMultipleTimeFramesDialog.setTitle("Add Vertex"); 
            stgAddVertexMultipleTimeFramesDialog.setResizable(false);

            stgAddVertexMultipleTimeFramesDialog.setScene(scnAddVertexMultipleTimeFrames);
            stgAddVertexMultipleTimeFramesDialog.show();
            stgAddVertexMultipleTimeFramesDialog.setAlwaysOnTop(true);
            stgAddVertexMultipleTimeFramesDialog.setOnCloseRequest(event -> {
                event.consume();
                stgAddVertexMultipleTimeFramesDialog.close();
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            });

            // Adds the funcitonality to close the window on pressing the specific key
            KeyPressFunctionality.CloseWindowOnKeyPress(stgAddVertexMultipleTimeFramesDialog, scnAddVertexMultipleTimeFrames, KeyCode.ESCAPE);
        
        
    }
    
    private static void addVertexInMultipleTimeFrames(int projectId, int graphId, int currentTimeFrameIndex,List<String> listTimeFrames){

        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        GraphTab graphTab = UIInstance.getProject(projectId).getGraphTab(graphId);
        //GraphCanvas graphCanvas = graphTab.getGraphCanvas();
        String verteXLabelAttr = graphTab.getVertexLabelAttr();
        String vertexTooltipAttr = graphTab.getVertexTooltipAttr();
        //get mouse click position relative to PanAndZoomPane
        Double x = graphTab.getZoomPaneClickX();
        Double y = graphTab.getZoomPaneClickY();
        Double[] arrPostionVertexInLogic = GraphAPI.getConvertedPositionFromUIToLogic(x, y, SceneConfig.GRAPHCANVAS_WIDTH, 
                        SceneConfig.GRAPHCANVAS_HEIGHT);
        //check if x and y is in range (0,1) else put them = 0.5
        
        //get time frame indices(integer)from the listTimeFrames
        
        List<Integer> listTimeFramesIndicesSelected = new ArrayList<Integer>();
        
        for(int i = 0; i < graphTab.getTotalTimeFrames(); i++){
            
            String timeFrameName = graphTab.getTimeFrames()[i];
            
            for(String nameTimeFrameSelected : listTimeFrames){
                if(timeFrameName.equals(nameTimeFrameSelected)){
                    //it is a selected time frame index
                    listTimeFramesIndicesSelected.add(i);
                    break;
                }
            }
            
        }
        
        
        
        System.out.println("AddVertexDialog.addVertexInMultipleTimeFrames() : x,y = " + x + ", " + y + "  convertedInLogic X,y = " + arrPostionVertexInLogic[0] + ", " + arrPostionVertexInLogic[1] + " :: listTimeFramesIndicesSelected = " + listTimeFramesIndicesSelected);
        if((arrPostionVertexInLogic[0] >=0 && arrPostionVertexInLogic[0]<1) && (arrPostionVertexInLogic[1] >=0 && arrPostionVertexInLogic[1]<1)){
            
            //Add new vertex in logic - get its id
            int intNewVertexId = GraphAPI.addVertexMultipleTimeFrames(projectId, graphId, currentTimeFrameIndex, listTimeFramesIndicesSelected,arrPostionVertexInLogic[0], arrPostionVertexInLogic[1]);

            //update graphCanvas, do it from graphtab

            for(int timeFrameIndex : listTimeFramesIndicesSelected){
            
                graphTab.getGraphCanvas(timeFrameIndex).updateCanvasAfterAddingVertex(intNewVertexId, projectId, graphId, timeFrameIndex, verteXLabelAttr, vertexTooltipAttr);
            
            }

        }else{
            
            //if vertex is being added outside the bounds of the graph i.e. its coordinate is -ve in any axis relative to panAndZoomPane
            //then add it at the same location in UI but in logic store it at (0.5,0.5)
            arrPostionVertexInLogic[0] = 0.5;
            arrPostionVertexInLogic[1] = 0.5;
            
            //Add new vertex in logic - get its id
            int intNewVertexId = GraphAPI.addVertexMultipleTimeFrames(projectId, graphId, currentTimeFrameIndex, listTimeFramesIndicesSelected,arrPostionVertexInLogic[0], arrPostionVertexInLogic[1]);
            //System.out.println("AddVertexDialog Else Case: x,y");

            for(int timeFrameIndex : listTimeFramesIndicesSelected){
            
                graphTab.getGraphCanvas(timeFrameIndex).updateCanvasAfterAddingVertexOutsideGraphBoundary(intNewVertexId, projectId, graphId, currentTimeFrameIndex,x, y, verteXLabelAttr, vertexTooltipAttr);
            
            }
    
        }
        
        
        //update accordion tab values for the selected time frames only
                for(int timeFrameIndex : listTimeFramesIndicesSelected){
            
                    graphTab.updateAccordionValues(timeFrameIndex);
            
                }
                
                
                //update the status of the project after vertex is added
                ProjectStatusTracker.updateProjectModifiedStatus(UIInstance.getActiveProjectID(), ProjectStatusTracker.eventVertexAdded);
                
                //update UI after vertices/edges have been removed.
                UIInstance.UpdateUI();
                
                //update SizeToolBox
                SizeToolBox.getInstance().disableSizeToolbox();
    
    }
    
}
