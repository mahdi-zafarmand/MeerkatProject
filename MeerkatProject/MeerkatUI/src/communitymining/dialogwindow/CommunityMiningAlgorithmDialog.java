/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communitymining.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.analysis.CommunityMiningAPI;
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
 * @author AICML Administrator
 */
public class CommunityMiningAlgorithmDialog {
    
    private Button stopAlgoButton;
    private Task<Void> runMiningAndUpdateTask;
    
    public void runAlgorithm(int pintProjectID, int pintGraphID, int pintTimeFrameIndex, String pstrMappingID, String[] arrstrParameters, AnalysisController pController){
        ProgressIndicator progressIndicator = new ProgressIndicator();
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
        GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
        stopAlgoButton = new Button("Stop Mining");
        
        stopAlgoButton.setOnMouseClicked(e ->{
            //cancel the task - check if its even needed or not since the call to ComMining thread will return
            runMiningAndUpdateTask.cancel();
            //stop the algorithm
            CommunityMiningAPI.stopAlgorithm(pintProjectID, pintGraphID, pintTimeFrameIndex, pstrMappingID);
            //remove progress bar vBox from GraphTab
        
        });
        
        currentGraph.addProgressIndicatorAndDisableSlider(progressIndicator, stopAlgoButton);
        
        //CommunityMiningAPI.runCMAlgorithm(pintProjectID, pintGraphID, pintTimeFrameIndex, pstrMappingID, arrstrParameters) ;
        
        runMiningAndUpdateTask = new Task<Void>() {
                    
            @Override
            public Void call() throws Exception {
                // Call the Community corresponding Mining API                
                CommunityMiningAPI.runCommunityMiningAlgorithm(pintProjectID, pintGraphID, pintTimeFrameIndex, pstrMappingID, arrstrParameters) ;
                System.out.println("\t\t\t\t $$$$$$$$$$ Community Mining Completed");

                // Waiting for the Community Mining API to run
                //while(!CommunityMiningAPI.isDone(pintProjectID, pintProjectID, pintTimeFrameIndex)) {
                //    System.out.println("FM(): Is The Algorithm Done ? "+CommunityMiningAPI.isDone(pintProjectID, pintGraphID, pintTimeFrameIndex)) ;
                //}
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        currentGraph.updateAfterCommunityMining();
                        currentGraph.updateComMiningStatus();
                        //Update Project Status
                        ProjectStatusTracker.updateProjectModifiedStatus(pintProjectID, ProjectStatusTracker.eventCommunityMiningDone);

                        pController.updateStatusBar(false, StatusMsgsConfig.MINING_RESULTSCOMPUTED);
                    }
                });
                System.out.println("\t\t\t\t $$$$$$$$$$ UI Update Completed");
                return null;
            }
        };
        
        runMiningAndUpdateTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event){
                System.out.println("\t\t\t\t $$$$$$ CommunityMining algo exit because it was Successfull");
                //remove progress bar, stop button
                currentGraph.removeProgressIndicatorAndDisableSlider();
            }
        });
        
        runMiningAndUpdateTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event){
                System.out.println("\t\t\t\t $$$$$$ CommunityMining algo exit because it was Cancelled");
                //remove progress bar, stop button
                currentGraph.removeProgressIndicatorAndDisableSlider();
            }
        });
        
        runMiningAndUpdateTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event){
                /*
                System.out.println("\n\n  ==================     ");
                System.out.println(event.getSource().getException());
                System.out.println(event.getSource().getMessage());
                System.out.println(event.getSource().getState());
                System.out.println(event.getSource().getTitle());
                System.out.println("\t\t\t\t ------ $$$$$$------- CommunityMining algo exit because it Failed");
                System.out.println("\n\n  ==================     ");
                */
                //remove progress bar, stop button
                currentGraph.removeProgressIndicatorAndDisableSlider();
            }
        });

        Thread runComMining = new Thread(runMiningAndUpdateTask);
        runComMining.setDaemon(true);
        runComMining.start();
        
        // Waiting for the Community Mining API to run
        //while(!CommunityMiningAPI.isDone(pintProjectID, pintGraphID, pintTimeFrameIndex)) {
        //    System.out.println("KMeans(): Is The Algorithm Done ? "+CommunityMiningAPI.isDone(pintProjectID, pintGraphID, pintTimeFrameIndex)) ;
        //}

    }
    
    public void runDynamicMiningAlgorithm(int pintProjectID, int pintGraphID, int pintCurrentTimeFrameIndex,String pstrMappingID, String[] arrstrParameters, AnalysisController pController){
    
        ProgressIndicator progressIndicator = new ProgressIndicator();
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance() ;
        GraphTab currentGraph = UIInstance.getActiveProjectTab().getActiveGraphTab();
        stopAlgoButton = new Button("Stop Mining");
        
        stopAlgoButton.setOnMouseClicked(e ->{
            //cancel the task - check if its even needed or not since the call to ComMining thread will return
            runMiningAndUpdateTask.cancel();
            //stop the algorithm
            CommunityMiningAPI.stopAlgorithm(pintProjectID, pintGraphID, pstrMappingID);
            //remove progress bar vBox from GraphTab
        
        });
        
        currentGraph.addProgressIndicatorAndDisableSlider(progressIndicator, stopAlgoButton);
        
        //CommunityMiningAPI.runCMAlgorithm(pintProjectID, pintGraphID, pintTimeFrameIndex, pstrMappingID, arrstrParameters) ;
        
        runMiningAndUpdateTask = new Task<Void>() {
                    
            @Override
            public Void call() throws Exception {
                // Call the Community corresponding Mining API                
                CommunityMiningAPI.runCMAlgorithm(pintProjectID, pintGraphID, pstrMappingID, arrstrParameters) ;
                System.out.println("\t\t\t\t $$$$$$$$$$ Community Mining Completed");

                // Waiting for the Community Mining API to run
                //while(!CommunityMiningAPI.isDone(pintProjectID, pintProjectID, pintTimeFrameIndex)) {
                //    System.out.println("FM(): Is The Algorithm Done ? "+CommunityMiningAPI.isDone(pintProjectID, pintGraphID, pintTimeFrameIndex)) ;
                //}
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(!runMiningAndUpdateTask.isCancelled()){

                            currentGraph.setDCommMiningStatus(Boolean.TRUE);
                            int intNUMTF = 
                                    GraphAPI.getTimeFrameNames(pintProjectID, pintGraphID)
                                    .length;
                            for (int i = 0 ; i < intNUMTF ; i++) {
                                currentGraph.setTimeFrameIndex(i);
                                currentGraph.updateAfterCommunityMining();
                                currentGraph.updateComMiningStatus();
                            }
                            currentGraph.setTimeFrameIndex(pintCurrentTimeFrameIndex);
                            currentGraph.updateAfterCommunityMining();
                            currentGraph.updateComMiningStatus();
                            currentGraph.updateDCPanelAfterCommunityMining();

                            //Update Project Status
                            ProjectStatusTracker.updateProjectModifiedStatus(pintProjectID, ProjectStatusTracker.eventCommunityMiningDone);

                            System.out.println("DynamicCommunityMining.Display() : "
                                    + "timeframeindex is " 
                                    + currentGraph.getTimeFrameIndex());
                            pController.updateStatusBar(false, 
                                    StatusMsgsConfig.MINING_RESULTSCOMPUTED);

                        }
                    }

                });
                System.out.println("\t\t\t\t $$$$$$$$$$ UI Update Completed");
                return null;
            }
        };
        
        runMiningAndUpdateTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event){
                System.out.println("\t\t\t\t $$$$$$ CommunityMining algo exit because it was Successfull");
                //remove progress bar, stop button
                currentGraph.removeProgressIndicatorAndDisableSlider();
            }
        });

        runMiningAndUpdateTask.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event){
                System.out.println("\t\t\t\t $$$$$$ CommunityMining algo exit because it was Cancelled");
                //remove progress bar, stop button
                currentGraph.removeProgressIndicatorAndDisableSlider();
            }
        });

        runMiningAndUpdateTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event){
                System.out.println("\t\t\t\t ------ $$$$$$------- CommunityMining algo exit because it Failed");
                //remove progress bar, stop button
                currentGraph.removeProgressIndicatorAndDisableSlider();
            }
        });

        Thread runComMining = new Thread(runMiningAndUpdateTask);
        runComMining.setDaemon(true);
        runComMining.start();
        
        
        }
    
}
