/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.dialogwindow;

import accordiontab.CommunitiesValues;
import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.analysis.CommunityMiningAPI;
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

/**
 *  Class Name      : ClearMiningResults
 *  Created Date    : 2016-05-17
 *  Description     : Clears the Mining Results
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class ClearMiningResults {

    private static boolean miningStatus;
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-05-17
     *  Description     : Just invoked to clear the mining results
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController : AnalysisController
     *  @param pstrMappingID : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2017-05-18      sankalp         added to logic to check if we need to clear dynamic community
     *                                  or any other time frame specific mining community result.
     * 
    */
    public static void Display (AnalysisController pController, String pstrMappingID) {
        try {
            pController.updateStatusBar(true, 
                    StatusMsgsConfig.MINING_RESULTSCLEARING);
            
            
            
            if(MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getDCommMiningStatus())
                clearDynamicCommunityAssignment();
            else
                clearCommunityAssignment();
            
            //Update Project Status
            ProjectStatusTracker.updateProjectModifiedStatus(MeerkatUI.getUIInstance().getActiveProjectID(), ProjectStatusTracker.eventClearedCommunityMining);
            MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab().getMinimapDelegator().updateMinimap();
            
            
            InfoDialog.Display(LangConfig.INFO_MININGRESULTSCLEARED, 
                    SceneConfig.INFO_TIMEOUT);
            
            
                
            pController.updateStatusBar(false, 
                    StatusMsgsConfig.MINING_RESULTSCLEARED);
        } catch (Exception ex) {
            System.out.println("ClearMiningResults.Display(): EXCEPTION");
            ex.printStackTrace();
        } 
    }
    
    /**
     *  Method Name     : clearCommunityAssignment()
     *  Created Date    : N/A
     *  Description     : clears the mining results for non-dynamic CM algorithm.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     * 
    */
    public static void clearCommunityAssignment() {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
        if(currentGraph.getDCommMiningStatus()){
            clearDynamicCommunityAssignment();
        }else{
            if(currentGraph.getCommMiningStatus(currentGraph.getTimeFrameIndex()))
                currentGraph.setComMiningStatus(Boolean.FALSE, currentGraph.getTimeFrameIndex());
                
            CommunityMiningAPI.clearCommunities(UIInstance.getActiveProjectID(),
                    UIInstance.getActiveProjectTab().getActiveGraphID(),
                    UIInstance.getActiveProjectTab().getActiveGraphTab()
                            .getTimeFrameIndex());
            
            ///removing the sys color attribute, setting vertices to default color
            GraphAPI.removeSysColorVerticesAttribute(UIInstance.getActiveProjectID(),
                    UIInstance.getActiveProjectTab().getActiveGraphID(),
                    UIInstance.getActiveProjectTab().getActiveGraphTab()
                            .getTimeFrameIndex());
            
            ///removing the sys color attribute, setting edges to default color
            GraphAPI.removeSysColorEdgesAttribute(UIInstance.getActiveProjectID(),
                    UIInstance.getActiveProjectTab().getActiveGraphID(),
                    UIInstance.getActiveProjectTab().getActiveGraphTab()
                            .getTimeFrameIndex());
        }
        clearCommunityMiningUI();
        
        if(UIInstance.getActiveProjectTab().getActiveGraphTab().getDCommMiningStatus()){
            CommunitiesValues.resetGlobalColors(UIInstance.getActiveProjectID(),
                    UIInstance.getActiveProjectTab().getActiveGraphID());
        }
    }
    
    /**
     *  Method Name     : clearDynamicCommunityAssignment()
     *  Created Date    : N/A
     *  Description     : clears the mining results for dynamic CM algorithm.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     * 
    */
    public static void clearDynamicCommunityAssignment() {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
        
        for(int tf = 0; tf<currentGraph.getTotalTimeFrames();tf++){
                currentGraph.setComMiningStatus(Boolean.FALSE, tf);
                
                //removing the sys color attribute, setting vertices to default color
                GraphAPI.removeSysColorVerticesAttribute(UIInstance.getActiveProjectID(),
                    UIInstance.getActiveProjectTab().getActiveGraphID(),
                    tf);
                
                ///removing the sys color attribute, setting edges to default color
                GraphAPI.removeSysColorEdgesAttribute(UIInstance.getActiveProjectID(),
                    UIInstance.getActiveProjectTab().getActiveGraphID(),
                    UIInstance.getActiveProjectTab().getActiveGraphTab()
                            .getTimeFrameIndex());
        }
        
        CommunityMiningAPI.clearDynamicCommunities(UIInstance.getActiveProjectID(),
                UIInstance.getActiveProjectTab().getActiveGraphID());
        
        clearCommunityMiningUI();
        
        CommunitiesValues.resetGlobalColors(UIInstance.getActiveProjectID(),
                    UIInstance.getActiveProjectTab().getActiveGraphID());
    }
    
    /**
     *  Method Name     : clearCommunityMiningUI()
     *  Created Date    : N/A
     *  Description     : clears the mining results from the UI elements.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     * 
    */
    public static void clearCommunityMiningUI(){
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        GraphTab currentGraph = 
                UIInstance.getActiveProjectTab().getActiveGraphTab();
        
        boolean DCMiningStatus = currentGraph.getDCommMiningStatus();
        int currentTFIndex = currentGraph.getTimeFrameIndex();
        if(DCMiningStatus){
            for (int i=0 ; i < currentGraph.getTimeFrames().length; i++) {
                currentGraph.setTimeFrameIndex(i);
                // decoloring vertices
                UIInstance.getActiveProjectTab().getActiveGraphTab()
                        .getGraphCanvas().clearMiningResults();

                // updating community table and tree in the communities tab
                currentGraph.updateAfterCommunityMining();
            }
        }else{
            // decoloring vertices
            UIInstance.getActiveProjectTab().getActiveGraphTab()
                    .getGraphCanvas().clearMiningResults();

            // updating community table and tree in the communities tab
            currentGraph.updateAfterCommunityMining();
        }
        
        currentGraph.setDCommMiningStatus(Boolean.FALSE);
        currentGraph.setTimeFrameIndex(currentTFIndex);
        currentGraph.updateDCPanelAfterCommunityMining();
             
    }
        
    public static boolean checkExistingCommMining(boolean isDCMining) {
        miningStatus=true;
        //below flag is helpful to check an existing communuity mining before DCMining is initiated.
        boolean DCMiningFlag=false;
        
        GraphTab currentGraph = MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab();
        System.out.println("ClearMiningResults checkExistingCommMining() : Mining Result Status : "+currentGraph.getCommMiningStatus(currentGraph.getTimeFrameIndex()));
        System.out.println("Current TimeFrame Index :"+ currentGraph.getTimeFrameIndex());
        
        if(isDCMining){
            for(int tf=0; tf<currentGraph.getTotalTimeFrames();tf++){
                if(currentGraph.getCommMiningStatus(tf)){
                    DCMiningFlag=true;
                    break;
                }
            }
        }
        
        
        if(currentGraph.getCommMiningStatus(currentGraph.getTimeFrameIndex()) || DCMiningFlag){
            Stage clearStage = new Stage();
            Label label = new Label("This action will clear the existing community\n"
                    + "mining results. Do you want to proceed?");
            Button okButton = new Button(LangConfig.GENERAL_YES);
            okButton.setOnAction(e  -> {
                clearStage.close();
            });
            
            Button noButton = new Button(LangConfig.GENERAL_NO);
            noButton.setOnAction( e ->{
                miningStatus=false;
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
        return miningStatus;
    }
}
