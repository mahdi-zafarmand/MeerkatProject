/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.UtilityAPI;
import config.ErrorMsgsConfig;
import config.GenerateGraphConfig;
import config.GraphConfig;
import config.ImportFileFilters;
import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *  Class Name      : GenerateGraph
 *  Created Date    : 2017-03-13
 *  Description     : Contains method used in generating graphs
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/

public class GenerateGraph {
    
    // graph generation parameters.
    private static String beta, alpha, gamma;
    private static String nodes, edges, community;
    private static String epsilon, r, t, phi, q;
    private static Label errorLabel;
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2017-03-13
     *  Description     : method to generate graph.
     *  Version         : 1.0
     *  @author         : sankalp
     *  @param pController
     *  @param pstrTitle
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
    */
    
     public static void Display(AnalysisController pController, String pstrTitle) {
         
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Parameters for graph generation");
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 50, 10, 50));
        grid.setVgap(8.0);
        grid.setHgap(10.0);

        //generate text field and labels to capture graph paramteres.
        final TextField text1 = new TextField();
        Label label1 = new Label(GenerateGraphConfig.nodes);

        GridPane.setConstraints(text1, 1, 1);
        GridPane.setConstraints(label1, 0, 1);
        grid.getChildren().addAll(label1,text1);
        
        final TextField text2 = new TextField();
        Label label2 = new Label(GenerateGraphConfig.edges);

        GridPane.setConstraints(text2, 1, 2);
        GridPane.setConstraints(label2, 0, 2);
        grid.getChildren().addAll(label2,text2);
        
        final TextField text3 = new TextField();
        Label label3 = new Label(GenerateGraphConfig.communities);

        GridPane.setConstraints(text3, 1, 3);
        GridPane.setConstraints(label3, 0, 3);
        grid.getChildren().addAll(label3,text3);
        
        final TextField text4 = new TextField();
        text4.setPromptText("default: 0.8");
        Label label4 = new Label(GenerateGraphConfig.beta_label);
        GridPane.setConstraints(text4, 1, 4);
        GridPane.setConstraints(label4, 0, 4);
        grid.getChildren().addAll(label4,text4);

        final TextField text5 = new TextField();
        text5.setPromptText("default: 0.5");
        Label label5 = new Label(GenerateGraphConfig.alpha_label);
        GridPane.setConstraints(text5, 1, 5);
        GridPane.setConstraints(label5, 0, 5);
        grid.getChildren().addAll(label5,text5);

        final TextField text6 = new TextField();
        text6.setPromptText("default: 0.5");
        Label label6 = new Label(GenerateGraphConfig.gamma_label);
        GridPane.setConstraints(text6, 1, 6);
        GridPane.setConstraints(label6, 0, 6);
        grid.getChildren().addAll(label6,text6);
        
        final TextField text12 = new TextField();
        text12.setPromptText("default: current directory");
        Label label12 = new Label(GenerateGraphConfig.file_location);
        GridPane.setConstraints(text12, 1, 0);
        GridPane.setConstraints(label12, 0, 0);
        grid.getChildren().addAll(label12,text12);
        
        Button browse = new Button("Browse...");
        GridPane.setConstraints(browse, 2, 0);
        grid.getChildren().addAll(browse);
        browse.setOnAction( e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Folder location");
            directoryChooser.setInitialDirectory((new File(System.getProperty("user.dir"))));
            File dir = directoryChooser.showDialog(dialog);
            if(dir != null){
                text12.setText(dir.getAbsolutePath());
            }else{
                text12.setText(null);
            }   
        });
        
        errorLabel = new Label(GenerateGraphConfig.error_label);
        errorLabel.setTextFill(Color.RED);
//        errorLabel.setMaxWidth(200);
//        errorLabel.setWrapText(true);
        grid.getChildren().add(errorLabel);
        errorLabel.setVisible(false);
        GridPane.setConstraints(errorLabel, 0, 8);

        HBox hboxButtons = new HBox();
        
        final TextField text7 = new TextField();
        final TextField text8 = new TextField();
        final TextField text9 = new TextField();
        final TextField text10 = new TextField();
        final TextField text11 = new TextField();
        
        final Label label7 = new Label(GenerateGraphConfig.epsilon_label);
        final Label label8 = new Label(GenerateGraphConfig.r_label);
        final Label label9 = new Label(GenerateGraphConfig.q_label);
        final Label label10 = new Label(GenerateGraphConfig.phi_label);
        final Label label11 = new Label(GenerateGraphConfig.t_label);
        
        r = GenerateGraphConfig.r;
        t = GenerateGraphConfig.t;
        q = GenerateGraphConfig.q;
        epsilon = GenerateGraphConfig.epsilon;
        phi = GenerateGraphConfig.phi;
        
        Button advnVariables = new Button("Advance Parameters...");
        advnVariables.setOnAction((event) -> {
            
            event.consume();
            Stage subdialog = new Stage();
            subdialog.setTitle("Advance paramters");
            subdialog.initOwner(dialog);
            subdialog.initModality(Modality.WINDOW_MODAL);
            
            GridPane subgrid = new GridPane();
            subgrid.setPadding(new Insets(10, 50, 10, 50));
            subgrid.setVgap(8.0);
            subgrid.setHgap(10.0);
            
            //generating text fields and labels for capturing advance parameters.
            text7.setPromptText("default: 0.0000001");
            label7.setMaxWidth(400);
            GridPane.setConstraints(text7, 1, 3);
            GridPane.setConstraints(label7, 0, 3);
            subgrid.getChildren().addAll(label7,text7);
            
            
            text8.setPromptText("default: 1");
            label8.setMaxWidth(400);
            GridPane.setConstraints(text8, 1, 0);
            GridPane.setConstraints(label8, 0, 0);
            subgrid.getChildren().addAll(label8,text8);
            
            text9.setPromptText("default: 0.5");
            label9.setMaxWidth(400);
            GridPane.setConstraints(text9, 1, 1);
            GridPane.setConstraints(label9, 0, 1);
            subgrid.getChildren().addAll(label9,text9);
            
            text10.setPromptText("default: 1");
            label10.setMaxWidth(400);
            GridPane.setConstraints(text10, 1, 2);
            GridPane.setConstraints(label10, 0, 2);
            subgrid.getChildren().addAll(label10,text10);
            
            text11.setPromptText("default: 0");
            label11.setWrapText(true);
            label11.setMaxWidth(400);
            GridPane.setConstraints(text11, 1, 4);
            GridPane.setConstraints(label11, 0, 4);
            subgrid.getChildren().addAll(label11,text11);
            
            HBox subhboxButtons = new HBox();
            Button subsubmit = new Button(LangConfig.GENERAL_OK);
            subsubmit.setOnAction((newEvent) -> {
                newEvent.consume();
                
                if(!text7.getText().isEmpty()){
                    epsilon = text7.getText();
                }else{
                    epsilon = GenerateGraphConfig.epsilon;
                }
                if(!text8.getText().isEmpty()){
                    r = text8.getText();
                }else{
                    r = GenerateGraphConfig.r;
                }
                if(!text9.getText().isEmpty()){
                    q = text9.getText();
                }else{
                    q = GenerateGraphConfig.q;
                }
                if(!text10.getText().isEmpty()){
                    phi = text10.getText();
                }else{
                    phi = GenerateGraphConfig.phi;
                }
                if(!text11.getText().isEmpty()){
                    t = text11.getText();
                }else{
                    t = GenerateGraphConfig.t;
                }
            
                subdialog.close();
            });
            
            Button subclear = new Button("Clear");
            subclear.setOnAction((event1) -> {
                 event1.consume();
                 text7.clear();
                 text8.clear();
                 text9.clear();
                 text10.clear();
                 text11.clear();
             });

             Button subbtnCancel = new Button(LangConfig.GENERAL_CANCEL);
             subbtnCancel.setOnAction((event2) -> {
                 event2.consume();
                 subdialog.close();
             });
            
            subhboxButtons.getChildren().addAll(subsubmit, subclear,subbtnCancel);
            subhboxButtons.setPadding(new Insets(10));
            subhboxButtons.setSpacing(20);
            subhboxButtons.setAlignment(Pos.CENTER_RIGHT);
            GridPane.setConstraints(subhboxButtons, 1, 6);
            subgrid.getChildren().add(subhboxButtons);
            
            Scene subscene = new Scene(subgrid);
            subdialog.setScene(subscene);
            subdialog.show();
            
        });
        
        Button submit = new Button(LangConfig.GENERAL_OK);
        submit.setOnAction((event) -> {
            event.consume();
            nodes = text1.getText();
            edges = text2.getText();
            community = text3.getText();

            if(!text4.getText().isEmpty()){
                beta = text4.getText();
            }else{
                beta = GenerateGraphConfig.beta;
            }
            if(!text5.getText().isEmpty()){
                alpha = text5.getText();
            }else{
                alpha = GenerateGraphConfig.alpha;
            }
            if(!text6.getText().isEmpty()){
                gamma = text6.getText();
            }else{
                gamma = GenerateGraphConfig.gamma;
            }
            
            if(!nodes.isEmpty() && !edges.isEmpty() && !community.isEmpty()){
                pController.updateStatusBar(true, StatusMsgsConfig.EXPORTING_GRAPH);
                dialog.close();
                try {
                    UtilityAPI.generateGraph(nodes, edges, community, beta, alpha, gamma, r, q, phi, epsilon, t);
                    File file = new File(System.getProperty("user.dir")+"/network.gml");
                    if(!text12.getText().isEmpty()){
                        File newFile = new File(text12.getText() + "/network.gml");
                        file.renameTo(newFile);
                        System.out.println("Generated File Name: "+ file.getName() + " Location: "+ newFile.getAbsolutePath());
                        loadgraph(newFile);
                    }else{
                        System.out.println("Generated File Name: "+ file.getName() + " Location: "+ file.getAbsolutePath());
                        loadgraph(file);
                    }

                }catch (InterruptedException ex) {
                    System.out.println("EXCEPTION GenerateGraph Display() :");
                    Logger.getLogger(GenerateGraph.class.getName()).log(Level.SEVERE, null, ex);
                }

                pController.updateStatusBar(false, StatusMsgsConfig.GRAPH_EXPORTED);      
            }else{
                errorLabel.setVisible(true);
            }  
        });

       Button clear = new Button("Clear");
       clear.setOnAction((event) -> {
            event.consume();
            text1.clear();
            text2.clear();
            text3.clear();
            text4.clear();
            pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLEARED);
        });
        
        Button btnCancel = new Button(LangConfig.GENERAL_CANCEL);
        btnCancel.setOnAction((event) -> {
            event.consume();
            dialog.close();
            pController.updateStatusBar(false, StatusMsgsConfig.EXPORT_CANCELLED);
        });

        GridPane.setConstraints(advnVariables, 1, 12);
        grid.getChildren().add(advnVariables);
        
        hboxButtons.getChildren().addAll(submit, clear, btnCancel);
        //hboxButtons.setPadding(new Insets(10));
        hboxButtons.setSpacing(20);
        hboxButtons.setAlignment(Pos.CENTER_RIGHT);
        GridPane.setConstraints(hboxButtons, 1, 9);
        grid.getChildren().add(hboxButtons);
      
        Scene scene = new Scene(grid);
        dialog.setScene(scene);
        dialog.show();
         
     }  

    private static void loadgraph(File file) {
        
        Stage loadStage = new Stage();
        loadStage.setTitle("Load Generated Graph");
        
        VBox vbox = new VBox();
        HBox hbox = new HBox();
        
        Label label = new Label(GenerateGraphConfig.load_label);
        Button btnYes = new Button(LangConfig.GENERAL_YES_TAG);
        Button btnNo = new Button(LangConfig.GENERAL_NO_TAG);
        hbox.getChildren().addAll(btnYes, btnNo);
        hbox.setSpacing(20);
        hbox.setPadding(new Insets(5,5,5,5));
        hbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label,hbox);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10,10,10,10));
        
        Scene loadScene = new Scene(vbox);
        loadStage.setScene(loadScene);
        loadStage.initModality(Modality.APPLICATION_MODAL);
        loadStage.show();
        
        btnYes.setOnAction(e -> {
            
            try {
                MeerkatUI UIInstance = MeerkatUI.getUIInstance();
                if (UIInstance.getActiveProjectTab() == null) {
                    System.out.println("GenerateGraph.loadgraph(): There is no active project yet");
                    ErrorDialog.Display(ErrorMsgsConfig.ERROR_ATLEASTONEPROJECT);
                }else{

                    int intProjectID = UIInstance.getActiveProjectTab().getProjectID();

                    System.out.println("GenerateGraph.loadgraph(): ProjectID : "+intProjectID);
                    
                    System.out.println("Loading the Generated Graph File : "+ file.getAbsolutePath());
                    //load the generated graph file
                    if(file!=null){
                        String strReaderID = ImportFileFilters.getReaderID(file.getAbsolutePath());
                        GraphConfig.GraphType graphType = ImportFileFilters.getGraphType(file.getName());
                        UIInstance.getActiveProjectTab().addFileToProject(file.getAbsolutePath(), strReaderID, graphType);
                    }

                    //Update Project Status
                    ProjectStatusTracker.updateProjectModifiedStatus(UIInstance.getActiveProjectTab().getProjectID(), ProjectStatusTracker.eventNewGraphLoaded);

                    /* Update the UI components in the Project Tab */
                    UIInstance.getController().updateProjectUI(UIInstance.getActiveProjectTab());
                } 
            }catch (Exception ex) {
                System.out.println("GenerateGraph.loadgraph(): EXCEPTION") ;
                ex.printStackTrace();
            }
            
            loadStage.close();
        
        });
        
        btnNo.setOnAction(e -> {
        
            loadStage.close();
        
        });
        
    }
    
}
