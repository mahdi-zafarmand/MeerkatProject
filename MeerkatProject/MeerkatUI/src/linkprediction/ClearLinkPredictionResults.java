/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkprediction;

import analysisscreen.AnalysisController;
import config.LangConfig;
import config.SceneConfig;
import config.StatusMsgsConfig;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.dialogwindow.InfoDialog;

public class ClearLinkPredictionResults {
    
    private static boolean blnIsToClearPredictedLinks = true;

    public static void Display (AnalysisController pController, String pstrMappingID) {
        try {
            pController.updateStatusBar(true, 
                    StatusMsgsConfig.LINKPREDICTION_RESULTSCLEARING);
            
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
            int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            
            if (currentGraph.getLinkPredictionStatus(intTimeFrameIndex)){
                // Display the UI dialog box
                Stage clearStage = new Stage();
                Label label = new Label("This action will remove existing predicted edges.\n"
                        + "Do you want to proceed?");
                Button okButton = new Button(LangConfig.GENERAL_YES);
                okButton.setOnAction(e  -> {
                    clearStage.close();
                    UIInstance.getActiveProjectTab().getActiveGraphTab().clearLinkPrediction();
                    currentGraph.setLinkPredictionStatus(false, intTimeFrameIndex);
                    UIInstance.UpdateUI();
                });

                Button noButton = new Button(LangConfig.GENERAL_NO);
                noButton.setOnAction(e -> {
                    clearStage.close();
                });

                HBox hbox = new HBox();
                hbox.getChildren().addAll(okButton, noButton);
                hbox.setSpacing(20);
                hbox.setPadding(new Insets(5,5,5,5));
                hbox.setAlignment(Pos.CENTER);
                VBox vbox = new VBox();
                vbox.getChildren().addAll(label, hbox);
                vbox.setSpacing(20);
                vbox.setPadding(new Insets(10,10,10,10));
                Scene scene = new Scene(vbox);
                clearStage.setScene(scene);
                clearStage.initModality(Modality.APPLICATION_MODAL);
                clearStage.showAndWait();
            }
            
            //Update Project Status
            ProjectStatusTracker.updateProjectModifiedStatus(MeerkatUI.getUIInstance().getActiveProjectID(), ProjectStatusTracker.eventLinkPredictionDone);
            MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().updateMinimap();
            
            InfoDialog.Display(LangConfig.INFO_LINKPREDICTIONRESULTSCLEARED, SceneConfig.INFO_TIMEOUT);
                
            pController.updateStatusBar(false, 
                    StatusMsgsConfig.MINING_RESULTSCLEARED);
        } catch (Exception ex) {
            System.out.println("ClearMiningResults.Display(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
        
    public static boolean checkLinkPredictionStatus() {

        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
        int inttimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
        if (currentGraph.getLinkPredictionStatus(inttimeFrameIndex)){
            // Display the UI dialog box
            Stage clearStage = new Stage();
            Label label = new Label("This action will remove existing predicted edges.\n"
                    + "Do you want to proceed?");
            Button okButton = new Button(LangConfig.GENERAL_YES);
            okButton.setOnAction(e  -> {
                clearStage.close();
                UIInstance.getActiveProjectTab().getActiveGraphTab()
                        .getGraphCanvas().clearLinkPredictionResultsUI(inttimeFrameIndex);
                currentGraph.setLinkPredictionStatus(false, inttimeFrameIndex);
            });

            Button noButton = new Button(LangConfig.GENERAL_NO);
            noButton.setOnAction(e -> {
                clearStage.close();
                blnIsToClearPredictedLinks = false ;
            });

            HBox hbox = new HBox();
            hbox.getChildren().addAll(okButton, noButton);
            hbox.setSpacing(20);
            hbox.setPadding(new Insets(5,5,5,5));
            hbox.setAlignment(Pos.CENTER);
            VBox vbox = new VBox();
            vbox.getChildren().addAll(label, hbox);
            vbox.setSpacing(20);
            vbox.setPadding(new Insets(10,10,10,10));
            Scene scene = new Scene(vbox);
            clearStage.setScene(scene);
            clearStage.initModality(Modality.APPLICATION_MODAL);
            clearStage.showAndWait();
        } 

        return blnIsToClearPredictedLinks;
    }
}
