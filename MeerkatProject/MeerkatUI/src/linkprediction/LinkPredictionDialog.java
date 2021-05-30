/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkprediction;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.analysis.LinkPredictionAPI;
import config.StatusMsgsConfig;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author Talat
 * @since 2018-04-07
 */
public class LinkPredictionDialog {
    
    private Button stopAlgoButton;
    private Task<Void> runPredictionAndUpdateTask;
    
    public void runAlgorithm(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, String pstrMappingId, String[] arrstrParameters, AnalysisController pController){
        ProgressIndicator progressIndicator = new ProgressIndicator();
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
        GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
        // #REPHRASELANG
        stopAlgoButton = new Button("Stop Link Prediction");
        
        stopAlgoButton.setOnMouseClicked(e ->{
            //cancel the task - check if its even needed or not since the call to ComMining thread will return
            runPredictionAndUpdateTask.cancel();
            //stop the algorithm
            LinkPredictionAPI.stopAlgorithm(pintProjectId, pintGraphId, pintTimeFrameIndex, pstrMappingId);
        });
        
        currentGraph.addProgressIndicatorAndDisableSlider(progressIndicator, stopAlgoButton);
        
        //CommunityMiningAPI.runCMAlgorithm(pintProjectID, pintGraphID, pintTimeFrameIndex, pstrMappingID, arrstrParameters) ;
        
        runPredictionAndUpdateTask = new Task<Void>() {
                    
            @Override
            public Void call() throws Exception {
                LinkPredictionAPI.runLinkPredictionAlgorithm(pintProjectId, pintGraphId, pintTimeFrameIndex, pstrMappingId, arrstrParameters) ;
                System.out.println("\t\t\t\t $$$$$$$$$$ Link Prediction Completed");

                Platform.runLater(new Runnable() {
                    @Override
                        public void run() {
                            currentGraph.updateAfterLinkPrediction(pintProjectId, pintGraphId, pintTimeFrameIndex);
                            currentGraph.updateLinkPredictionStatus();
                            
                            ProjectStatusTracker.updateProjectModifiedStatus(pintProjectId, ProjectStatusTracker.eventLinkPredictionDone);
                            pController.updateStatusBar(false, StatusMsgsConfig.LINKPREDICTION_RESULTSCOMPUTED);
                        }
                });

                System.out.println("\t\t\t\t $$$$$$$$$$ UI Update Completed");
                return null;
            }
        };
        
        runPredictionAndUpdateTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event){
                System.out.println("\t\t\t\t $$$$$$ Link Prediction algo exit because it was Successfull");
                currentGraph.removeProgressIndicatorAndDisableSlider();
            }
        });

        runPredictionAndUpdateTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event){
                System.out.println("\t\t\t\t $$$$$$ Link Prediction algo exit because it was Cancelled");
                currentGraph.removeProgressIndicatorAndDisableSlider();
            }
        });

        runPredictionAndUpdateTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event){
                currentGraph.removeProgressIndicatorAndDisableSlider();
            }
        });

        Thread runLinkPrediction = new Thread(runPredictionAndUpdateTask);
        runLinkPrediction.setDaemon(true);
        runLinkPrediction.start();
    }    
}
