/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.features;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.analysis.ShortestPathAPI;
import config.StatusMsgsConfig;
import globalstate.GraphTab;
import globalstate.MeerkatUI;
import java.util.List;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author talat
 */
public class ShortestPath {

    private Button stopAlgoButton;
    private Task<Void> runShortestPathAlgorithm;
    
    /**
     * 
     * @param pintProjectId
     * @param pintGraphId
     * @param pintTimeFrameIndex
     * @param pstrMappingID
     * @param pintSourceVertexId
     * @param pintDestinationVertexId
     * @param pController 
     */
    public void runAlgorithm(int pintProjectId, int pintGraphId, int pintTimeFrameIndex, 
            String pstrMappingID, int pintSourceVertexId, int pintDestinationVertexId, AnalysisController pController){
        
        ProgressIndicator progressIndicator = new ProgressIndicator();
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
        GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
        // #REPHRASELANG
        stopAlgoButton = new Button("Stop Computing Shortest Path");
        
        stopAlgoButton.setOnMouseClicked(e ->{
            //cancel the task - check if its even needed or not since the call to ComMining thread will return
            runShortestPathAlgorithm.cancel();
            //stop the algorithm
            ShortestPathAPI.stopAlgorithm(pintProjectId, pintGraphId, pintTimeFrameIndex, pstrMappingID);
        });
        
        currentGraph.addProgressIndicatorAndDisableSlider(progressIndicator, stopAlgoButton);
        
        runShortestPathAlgorithm = new Task<Void>() {
                    
            @Override
            public Void call() throws Exception {
                // Call the Community corresponding Mining API                
                ShortestPathAPI.runShortestPathAlgorithm(pintProjectId, pintGraphId, pintTimeFrameIndex, pstrMappingID, pintSourceVertexId, pintDestinationVertexId) ;
                System.out.println("\t\t\t\t $$$$ Shortest Path Algorithm Completed");

                Platform.runLater(new Runnable() {
                    @Override
                        public void run() {
                            List<Integer> lstEdgeIds = ShortestPathAPI.getShortestPathResults_LastRun(pintProjectId, pintGraphId, pintTimeFrameIndex);
                            currentGraph.updateAfterShortestPath(pintProjectId, pintGraphId, pintTimeFrameIndex, lstEdgeIds);
                            pController.updateStatusBar(false, StatusMsgsConfig.SHORTESTPATH_COMPUTED);
                        }
                });

                System.out.println("\t\t\t\t $$$$$$$$$$ UI Update Completed");
                return null;
            }
        };
        
        runShortestPathAlgorithm.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event){
                System.out.println("\t\t\t\t $$$$$$ Shortest Path algo exit because it was Successfull");
                currentGraph.removeProgressIndicatorAndDisableSlider();
            }
        });

        runShortestPathAlgorithm.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event){
                System.out.println("\t\t\t\t $$$$$$ Shortest Path algo exit because it was Cancelled");
                currentGraph.removeProgressIndicatorAndDisableSlider();
            }
        });

        runShortestPathAlgorithm.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event){
                currentGraph.removeProgressIndicatorAndDisableSlider();
            }
        });

        Thread runShortestPath = new Thread(runShortestPathAlgorithm);
        runShortestPath.setDaemon(true);
        runShortestPath.start();    
    }
}
