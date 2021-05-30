/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import config.GraphConfig;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import graphelements.UIVertex;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import meerkat.Utilities;

/**
 *  Class Name      : NeighborhoodDegreeDialog
 *  Created Date    : 2016-05-11
 *  Description     : The Dialog box requesting the user to select the number of neighborhood 
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class NeighborhoodDegreeDialog {
        
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static String strNeighborDegreeTitle ;
    private static String strPrompt; 
    private static String strErrorLabel;
    private static int intValue  ;
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2015-08-04
     *  Description     : Sets the parameters of the New Project Wizard
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrNeighborDegreeTitle : String
     *  @param pstrPrompt : String
     *  @param pstrErrorLabel : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters (String pstrNeighborDegreeTitle, String pstrPrompt, String pstrErrorLabel) {
        strNeighborDegreeTitle = pstrNeighborDegreeTitle;
        strPrompt = pstrPrompt;
        strErrorLabel = pstrErrorLabel;
    }
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-05-11
     *  Description     : Displays the Window to select parameters for algorithm RosvallInfomod
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Display (AnalysisController pController, int pintProjectID, int pintGraphID, int pintTimeFrameIndex) {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
        Set<UIVertex> setSelectedVertices = UIInstance.getProject(pintProjectID).getGraphTab(pintGraphID).getGraphCanvas().getSelectedVertices();
        if(!setSelectedVertices.isEmpty()){
            try {
                Stage stgNeighborhoodDegree = new Stage();


                // Grid to show all the User Input controls
                GridPane gridUserControls = new GridPane();
                gridUserControls.setPadding(new Insets(5));
                gridUserControls.setHgap(5);
                gridUserControls.setVgap(5);

                // Column constraints to define the two columns
                ColumnConstraints column1 = new ColumnConstraints();
                //column1.setPercentWidth(70);
                ColumnConstraints column2 = new ColumnConstraints();
                //column2.setPercentWidth(30);
                column2.setHgrow(Priority.ALWAYS);
                gridUserControls.getColumnConstraints().addAll(column1, column2);

                // ROW 1 - ATTEMPTS COUNT
                Label lblAttemptsCount = new Label(strPrompt) ;
                final Spinner<Integer> spnrNeighborDegree = new Spinner();
                spnrNeighborDegree.setValueFactory(
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(
                                  GraphConfig.NEIGHBORHOOD_DEGREE_MIN
                                , GraphConfig.NEIGHBORHOOD_DEGREE_MAX
                                , GraphConfig.NEIGHBORHOOD_DEGREE_DEFAULT
                                , GraphConfig.NEIGHBORHOOD_DEGREE_STEP
                        ));
                intValue = spnrNeighborDegree.getValue();
                spnrNeighborDegree.valueProperty().addListener((obv, oldValue, newValue) -> {
                    System.out.println("NeighborhoodDegreeDialog(): OldValue: "+oldValue+"\tNewValue: "+newValue);
                    SpinnerValueFactory<Integer> valueFactory = spnrNeighborDegree.getValueFactory();
                    if (Utilities.tryParseInteger(String.valueOf(newValue))) {                
                        if (newValue <= GraphConfig.NEIGHBORHOOD_DEGREE_MAX && newValue >= GraphConfig.NEIGHBORHOOD_DEGREE_MIN) {
                            valueFactory.setValue(newValue);                    
                            intValue = spnrNeighborDegree.getValue();
                        }
                    } else {
                        valueFactory.setValue(oldValue);
                    }
                });
                spnrNeighborDegree.setEditable(true);
                spnrNeighborDegree.setMinWidth(60);
                spnrNeighborDegree.setMaxWidth(60);

                GridPane.setHalignment(lblAttemptsCount, HPos.LEFT);
                gridUserControls.add(lblAttemptsCount, 0, 0);

                GridPane.setHalignment(spnrNeighborDegree, HPos.LEFT);
                gridUserControls.add(spnrNeighborDegree, 1, 0);

                // Error Label 
                Label lblError = new Label(strErrorLabel + " " + GraphConfig.NEIGHBORHOOD_DEGREE_MIN + LangConfig.GENERAL_AND + GraphConfig.NEIGHBORHOOD_DEGREE_MAX) ;
                lblError.setVisible(false);

                // Adding the OK and Cancel Buttons at the end        
                Button btnOK = new Button (LangConfig.GENERAL_OK);
                btnOK.setOnAction(e -> {

                    if (intValue < GraphConfig.NEIGHBORHOOD_DEGREE_MIN 
                            || intValue > GraphConfig.NEIGHBORHOOD_DEGREE_MAX 
                            || !Utilities.tryParseInteger(String.valueOf(spnrNeighborDegree.getValue()))) {
                        lblError.setVisible(true);
                        return ;
                    }

                    //MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
                    //Set<UIVertex> setSelectedVertices = UIInstance.getProject(pintProjectID).getGraphTab(pintGraphID).getGraphCanvas().getSelectedVertices();
                    List<Integer> lstintSelectedVertexIDs = new ArrayList<>();        
                    for (UIVertex uiVertex : setSelectedVertices) {
                        lstintSelectedVertexIDs.add(uiVertex.getVertexHolder().getID()) ;
                    }

                    Set<Integer> setNeighbourhoodIDs = GraphAPI.getVertexNeighborhood(pintProjectID, pintGraphID, pintTimeFrameIndex, lstintSelectedVertexIDs, intValue) ;
                    System.out.println("VertexContextMenu(): Set of Neighbourhoods - "+setNeighbourhoodIDs.size()+"\t"+setNeighbourhoodIDs);
                    UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().selectVertex(setNeighbourhoodIDs);

                    // Close the Stage 
                    stgNeighborhoodDegree.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
                });
                Button btnCancel = new Button (LangConfig.GENERAL_CANCEL);
                btnCancel.setOnAction(e -> {                
                    stgNeighborhoodDegree.close();
                    pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
                });
                HBox hboxButtons = new HBox(btnOK, btnCancel);
                hboxButtons.setSpacing(10);
                hboxButtons.setPadding(new Insets(5,10,5,10));
                hboxButtons.setAlignment(Pos.CENTER);

                // The scene consists of the following items
                // 1) Grid for Input Values
                // 2) HBox - buttons
                // 3) Label - to Display Error Msg
                VBox vboxPanel = new VBox(gridUserControls, lblError, hboxButtons) ;


                Scene scnNeighborhoodDegree = new Scene(vboxPanel);
                scnNeighborhoodDegree.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                    if (evt.getCode().equals(KeyCode.ESCAPE)) {
                        stgNeighborhoodDegree.close();
                        pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
                    }
                });

                stgNeighborhoodDegree.initModality(Modality.APPLICATION_MODAL);
                stgNeighborhoodDegree.setTitle(strNeighborDegreeTitle);
                stgNeighborhoodDegree.setResizable(false);
                stgNeighborhoodDegree.setOnCloseRequest(event -> {
                    pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
                });

                stgNeighborhoodDegree.setScene(scnNeighborhoodDegree);
                stgNeighborhoodDegree.show();

                pController.updateStatusBar(true, StatusMsgsConfig.WAITING_USERINPUT);
            } catch (Exception ex) {
                System.out.println("NeighborhoodDegreeDialog.Display(): EXCEPTION");
                ex.printStackTrace();
            }
        }else{
            InfoDialog.Display(LangConfig.INFO_NODENEIGHBORHOOD_WARNING, -1);
        } 
    }
}
