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
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import graphelements.UIEdge;
import graphelements.UIVertex;
import graphelements.VertexHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class VertexDeleteConfirmationDialog {
    
    
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
     *  Description     : Sets the parameters of the Vertex Delete Confirmation Dialog Box
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
     *  Description     : Closes the Vertex Delete Confirmation Dialog Box
     *  Version         : 1.0
     *  @author         : Talat
     *  @param pController
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2017-07-16      @sankalp        changed the double pop-up each on vertex delete confirmation 
     *                                  and community results changed to a single confirmation pop-up.
     * 
    */

    public static void Display(AnalysisController pController) {
                      
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int tf = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();

        GraphCanvas graphCanvas = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas();
        
        if(graphCanvas.getSelectedVertices().size()>0){
            
            Stage stgVertexDeleteConfirmationDialog = new Stage();
            pController.updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);
        
            Label lblMessage;
            String commLabel = "This action will change the community mining results.\nDo you want to proceed?";
            if(UIInstance.getActiveProjectTab().getActiveGraphTab().getCommMiningStatus(tf))
                lblMessage = new Label(LangConfig.GENERAL_AREYOUSURE+"\n\n"+commLabel);
            else
                lblMessage = new Label(LangConfig.GENERAL_AREYOUSURE);
            
            
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
//                        removeVertices(pController);
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
                    removeVertices(pController);
                //}     
                stgVertexDeleteConfirmationDialog.close();
                pController.updateStatusBar(false, StatusMsgsConfig.VERTEX_DELETED);
            });

            btnYes.setCancelButton(false);        
            btnYes.setAlignment(Pos.CENTER);

            Button btnNo = new Button(LangConfig.GENERAL_NO);
            btnNo.setOnAction(e -> {   
                stgVertexDeleteConfirmationDialog.close();
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

            Scene scnVertexDeleteConfirmation = new Scene(vbox);
            scnVertexDeleteConfirmation.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                if (evt.getCode().equals(KeyCode.ESCAPE)) {
                    stgVertexDeleteConfirmationDialog.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
                }
            });

            stgVertexDeleteConfirmationDialog.initModality(Modality.APPLICATION_MODAL);
            stgVertexDeleteConfirmationDialog.setTitle("Remove Vertex"); 
            stgVertexDeleteConfirmationDialog.setResizable(false);

            stgVertexDeleteConfirmationDialog.setScene(scnVertexDeleteConfirmation);
            stgVertexDeleteConfirmationDialog.show();
            stgVertexDeleteConfirmationDialog.setAlwaysOnTop(true);
            stgVertexDeleteConfirmationDialog.setOnCloseRequest(event -> {
                event.consume();
                stgVertexDeleteConfirmationDialog.close();
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            });

            // Adds the funcitonality to close the window on pressing the specific key
            KeyPressFunctionality.CloseWindowOnKeyPress(stgVertexDeleteConfirmationDialog, scnVertexDeleteConfirmation, KeyCode.ESCAPE);
        }
//        else{
//            InfoDialog.Display(LangConfig.INFO_SELECTVERTEX, 
//                    SceneConfig.INFO_TIMEOUT);
//        }
    }

    private static void removeVertices(AnalysisController pController) {
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        int projectID = UIInstance.getActiveProjectTab().getProjectID();
        int graphID = UIInstance.getActiveProjectTab().getActiveGraphID();
        int tf = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        
        GraphCanvas graphCanvas = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas();
         
        pController.updateStatusBar(true, StatusMsgsConfig.VERTEX_DELETING);      

                Map<UIVertex, Set<UIEdge>> vertexEdgesMap = graphCanvas.getVertexEdgesMap();
                List<Integer> vertexIDs = new ArrayList<>();
                List<Integer> edgeIDs = new ArrayList<>();

                //deleting from UI
                System.out.println("VertexDeleteConfirmationDialog removeVertices() -> Total Selected Vertices for Removal: "+ graphCanvas.getSelectedVertices().size());
                for(UIVertex uiVertex : graphCanvas.getSelectedVertices()){

                    vertexIDs.add(uiVertex.getVertexHolder().getID());

                    graphCanvas.getChildren().remove(uiVertex.getVertexHolder().getNode());
                    graphCanvas.getChildren().remove(uiVertex.getVertexHolder().getLabelHolder());
                    
                    if(vertexEdgesMap.containsKey(uiVertex)){
                        for(UIEdge edge  : vertexEdgesMap.get(uiVertex)){
                            if(edge!=null){
                                edgeIDs.add(edge.getID());
                                graphCanvas.getChildren().remove(edge.getEdgeShape().getShapeNode());
                            }
                        }
                    }
                }
                
                //update the UI data structures after vertex/edge removal
                graphCanvas.updateCanvasAfterVertexRemove(vertexIDs, edgeIDs);                
                
                //deleting edges corresponding to selected vertices from logic
                GraphAPI.removeEdges(projectID, graphID, tf, vertexIDs);
                //deleting selected vertices from logic
                GraphAPI.removeVertices(projectID, graphID, tf, vertexIDs);

                //update accordion tab values
                UIInstance.getActiveProjectTab().getActiveGraphTab().updateAccordionValues();
                
                //update minimap
                UIInstance.getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().updateMinimap();
                
                //update the status of the project
                ProjectStatusTracker.updateProjectModifiedStatus(projectID, ProjectStatusTracker.eventVertexRemoved);
                
                //update UI after vertices/edges have been removed.
                UIInstance.UpdateUI();
                
                //update SizeToolBox
                SizeToolBox.getInstance().disableSizeToolbox();
    }
}
