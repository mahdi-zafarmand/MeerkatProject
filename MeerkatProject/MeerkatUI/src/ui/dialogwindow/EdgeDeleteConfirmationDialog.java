/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import config.LangConfig;
import config.SceneConfig;
import config.StatusMsgsConfig;
import globalstate.GraphCanvas;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import graphelements.UIEdge;
import java.util.HashSet;
import java.util.Set;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.elements.SizeToolBox;
import ui.utilities.KeyPressFunctionality;

/**
 *
 * @author Talat-AICML
 */
public class EdgeDeleteConfirmationDialog {
    
    
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
     *  Created Date    : 2016-05-10
     *  Description     : Sets the parameters of the Edge Delete Confirmation Dialog Box
     *  Version         : 1.0
     *  @author         : Talat
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
     *  Created Date    : 2016-05-10
     *  Description     : Closes the Edge Delete Confirmation Dialog Box
     *  Version         : 1.0
     *  @author         : Talat
     *  @param pController
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 2017-07-16      @sankalp         changed the double pop-up each on edge delete confirmation 
     *                                  and community results changed to a single confirmation pop-up.
     * 
    */

    public static void Display(AnalysisController pController) {
                      
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int tf = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();

        GraphCanvas graphCanvas = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas();
        
        if(graphCanvas.getSelectedEdges().size()>0){
            
            Stage stgEdgeDeleteConfirmationDialog = new Stage();
            pController.updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);
            
            String commLabel = "This action will change the community mining results.\nDo you want to proceed?";
            Label lblMessage;
            
            if(UIInstance.getActiveProjectTab().getActiveGraphTab().getCommMiningStatus(tf))   
                lblMessage = new Label(LangConfig.GENERAL_AREYOUSURE_EDGE+"\n\n"+commLabel);
            else
                lblMessage = new Label(LangConfig.GENERAL_AREYOUSURE_EDGE);
            
            Button btnYes = new Button(LangConfig.GENERAL_YES);
            btnYes.setOnAction(e -> {

//                if(UIInstance.getActiveProjectTab().getActiveGraphTab().getCommMiningStatus(tf)){
//                    Stage stage = new Stage();
//                    stage.setTitle("Community Mining Results");
//
//                    VBox vbox = new VBox();
//                    HBox hbox = new HBox();
//
//                    Label label = new Label("This action will change the community mining results.\nDo you want to proceed?");
//                    Button btnnYes = new Button(LangConfig.GENERAL_YES_TAG);
//                    btnnYes.setOnAction(m -> {
//                        removeEdges(pController);
//                        stage.close();
//                    });
//                    Button btnNo = new Button(LangConfig.GENERAL_NO_TAG);
//                    btnNo.setOnAction(k -> {
//                        stage.close();
//                    });
//                    hbox.getChildren().addAll(btnnYes, btnNo);
//                    hbox.setSpacing(20);
//                    hbox.setPadding(new Insets(5,5,5,5));
//                    hbox.setAlignment(Pos.CENTER);
//                    vbox.getChildren().addAll(label,hbox);
//                    vbox.setSpacing(20);
//                    vbox.setPadding(new Insets(10,10,10,10));
//
//                    Scene loadScene = new Scene(vbox);
//                    stage.setScene(loadScene);
//                    stage.initModality(Modality.APPLICATION_MODAL);
//                    stage.show();
//                }
                //else{
                    removeEdges(pController);
                //}     
                stgEdgeDeleteConfirmationDialog.close();
                pController.updateStatusBar(false, StatusMsgsConfig.Edge_DELETE);
            });

            btnYes.setCancelButton(false);        
            btnYes.setAlignment(Pos.CENTER);

            Button btnNo = new Button(LangConfig.GENERAL_NO);
            btnNo.setOnAction(e -> {   
                stgEdgeDeleteConfirmationDialog.close();
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            });
            btnNo.setCancelButton(true);        
            btnNo.setAlignment(Pos.CENTER);


            HBox hbox = new HBox(btnYes, btnNo);
            //hbox.setPadding(new Insets(10, 10, 10, 10));
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(20);
            VBox vbox = new VBox(lblMessage, hbox);                
            vbox.setPadding(new Insets(10, 10, 10, 10));
            vbox.setSpacing(20);

            Scene scnEdgeDeleteConfirmation = new Scene(vbox);
            scnEdgeDeleteConfirmation.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                if (evt.getCode().equals(KeyCode.ESCAPE)) {
                    stgEdgeDeleteConfirmationDialog.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
                }
            });

            stgEdgeDeleteConfirmationDialog.initModality(Modality.APPLICATION_MODAL);
            stgEdgeDeleteConfirmationDialog.setTitle("Remove Edge"); 
            stgEdgeDeleteConfirmationDialog.setResizable(false);

            stgEdgeDeleteConfirmationDialog.setScene(scnEdgeDeleteConfirmation);
            stgEdgeDeleteConfirmationDialog.show();
            stgEdgeDeleteConfirmationDialog.setAlwaysOnTop(true);
            stgEdgeDeleteConfirmationDialog.setOnCloseRequest(event -> {
                event.consume();
                stgEdgeDeleteConfirmationDialog.close();
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            });

            // Adds the funcitonality to close the window on pressing the specific key
            KeyPressFunctionality.CloseWindowOnKeyPress(stgEdgeDeleteConfirmationDialog, scnEdgeDeleteConfirmation, KeyCode.ESCAPE);
        }
//        else{
//            InfoDialog.Display(LangConfig.INFO_SELECTEDGE, 
//                    SceneConfig.INFO_TIMEOUT);
//        }
    }

    private static void removeEdges(AnalysisController pController) {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int projectID = UIInstance.getActiveProjectTab().getProjectID();
        int graphID = UIInstance.getActiveProjectTab().getActiveGraphID();
        int tf = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        
        GraphCanvas graphCanvas = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas();
         
        pController.updateStatusBar(true, StatusMsgsConfig.Edge_DELETE);      

                Set<Integer> EdgeIDs = new HashSet<>();
                Set<Integer> VertexIDs = new HashSet<>();

                //deleting from UI
                System.out.println("EdgeDeleteConfirmationDialog removeEdges() -> Total Selected Edges for Removal: "+ graphCanvas.getSelectedEdges().size());
                for(UIEdge uiEdge : graphCanvas.getSelectedEdges()){
                    
                    EdgeIDs.add(uiEdge.getID());
                    VertexIDs.add(uiEdge.getDestinationVertexID());
                    VertexIDs.add(uiEdge.getSourceVertexID());
                    graphCanvas.getChildren().remove(uiEdge.getEdgeShape().getShapeNode());
                }
                
                //update UIEdge data structures after edge deletion
                graphCanvas.updateCanvasAfterEdgeRemove(VertexIDs, EdgeIDs);

                //deleting edges from logic
                GraphAPI.removeEdges(projectID, graphID, tf, EdgeIDs);

                //update accordion tab values
                UIInstance.getActiveProjectTab().getActiveGraphTab().updateAccordionValues();
                
                //update minimap
                UIInstance.getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().updateMinimap();
                
                //update the status of the project
                ProjectStatusTracker.updateProjectModifiedStatus(projectID, ProjectStatusTracker.eventEdgeRemoved);
                
                //update UI after vertices/edges have been removed.
                UIInstance.UpdateUI();
                
                //update SizeToolBox
                SizeToolBox.getInstance().disableEdgeSizeToolbox();
    }
}
