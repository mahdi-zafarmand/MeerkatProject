/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.dialogwindow;

import analysisscreen.AnalysisController;
import config.LangConfig;
import config.StatusMsgsConfig;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *  Class Name      : AppParameter
 *  Created Date    : 2016-01-18
 *  Description     : The application parameters that would be displayed
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class AppParameterDialog {
    
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    
    private static int intMB = 1024*1024 ;
    
    private static String strOperatingSystem_Label ;
    private static String strProcessors_Label ;
    private static String strJVM_Label ;
    private static String strTotalMemory_Label ;
    private static String strTotalJVMMemory_Label ;
    private static String strUsedJVMMemory_Label ;
    private static String strFreeJVMMemory_Label ;
    
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
    public static String getOperatingSystemLabel () {
        return strOperatingSystem_Label ;
    }
    
    public static String getProcessorsLabel () {
        return strProcessors_Label ;
    }
    
    public static String getJVMLabel () {
        return strJVM_Label ;
    }
    
    public static String getTotalMemoryLabel () {
        return strTotalMemory_Label ;
    }
    
    public static String getTotalJVMMemoryLabel () {
        return strTotalJVMMemory_Label ;
    }
    
    public static String getUsedJVMMemoryLabel () {
        return strUsedJVMMemory_Label ;
    }
    
    public static String getFreeJVMMemoryLabel () {
        return strFreeJVMMemory_Label ;
    }
    
    public static String getOperatingSystem () {
        return System.getProperty("os.name") + " ("+System.getProperty("os.version")+")" ;
    }
    
    public static String getProcessors () {
        return String.valueOf(Runtime.getRuntime().availableProcessors()) ;
    }
    
    public static String getJVM () {
        return System.getProperty("java.version") ;
    }
    
    public static String getTotalMemory () {
        com.sun.management.OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean)java.lang.management.ManagementFactory.getOperatingSystemMXBean();
        return String.valueOf(bean.getTotalPhysicalMemorySize()/intMB);
    }
    
    public static String getTotalJVMMemory(){
        Runtime runtime = Runtime.getRuntime();
        return String.valueOf(runtime.totalMemory()/ intMB) ;
    }
    
    public static String getUsedJVMMemory () {
        Runtime runtime = Runtime.getRuntime();
        return String.valueOf((runtime.totalMemory()-runtime.freeMemory())/ intMB) ;
    }
    
    public static String getFreeJVMMemory () {
        Runtime runtime = Runtime.getRuntime();
        return String.valueOf(runtime.freeMemory() / intMB) ;
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    
    /**
     *  Method Name     : setParameters()
     *  Created Date    : 2016-01-18
     *  Description     : Sets the parameters of the Application Parameters that are to be displayed in the dialog box on clicking the menu option
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pstrOperatingSystem_Label : String
     *  @param pstrProcessors_Label : String
     *  @param pstrTotalMemory_Label : String
     *  @param pstrJVM_Label : String
     *  @param pstrTotalJVMMemory_Label : String
     *  @param pstrUsedJVMMemory_Label : String
     *  @param pstrFreeJVMMemory_Label : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void setParameters (
          String pstrOperatingSystem_Label 
        , String pstrProcessors_Label 
        , String pstrTotalMemory_Label 
        , String pstrJVM_Label         
        , String pstrTotalJVMMemory_Label 
        , String pstrUsedJVMMemory_Label 
        , String pstrFreeJVMMemory_Label 
    ) {
        strOperatingSystem_Label = pstrOperatingSystem_Label ;
        strProcessors_Label = pstrProcessors_Label ;
        strJVM_Label = pstrJVM_Label ;
        strTotalMemory_Label = pstrTotalMemory_Label ;
        strTotalJVMMemory_Label = pstrTotalJVMMemory_Label ;
        strUsedJVMMemory_Label = pstrUsedJVMMemory_Label ;
        strFreeJVMMemory_Label = pstrFreeJVMMemory_Label ;       
    }
    
    
    /**
     *  Method Name     : Display()
     *  Created Date    : 2016-01-18
     *  Description     : The Dialog Box to be showed when the option is clicked
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pController ; AnalysisController
     *  @param pstrTitle : String
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static void Display(AnalysisController pController, String pstrTitle) {
        
        Stage stgApplicationParameterDialog = new Stage();
        stgApplicationParameterDialog.initModality(Modality.APPLICATION_MODAL);
        String pstrDelimiter = ": " ;
        
        // Gridpane will store all the labels and their results
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        
        
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(50);
        column3.setHgrow(Priority.ALWAYS);
        
        gridpane.getColumnConstraints().addAll(column1, column3);
        
        
        // Assigning the Labels
        Label lblOSLabel = new Label(getOperatingSystemLabel());
        Label lblOS = new Label (pstrDelimiter+getOperatingSystem());
        
        Label lblProcessorsLabel = new Label(getProcessorsLabel());
        Label lblProcessors = new Label(pstrDelimiter+getProcessors());
        
        Label lblTotalMemoryLabel = new Label(getTotalMemoryLabel());
        Label lblTotalMemory = new Label (pstrDelimiter+getTotalMemory());
        
        Label lblJVMVersionLabel = new Label(getJVMLabel());
        Label lblJVMVersion = new Label (pstrDelimiter+getJVM()) ;
        
        Label lblJVMTotalMemoryLabel = new Label (getTotalJVMMemoryLabel());
        Label lblJVMTotalMemory = new Label (pstrDelimiter+getTotalJVMMemory());
        
        Label lblJVMUsedMemoryLabel = new Label (getUsedJVMMemoryLabel()) ;
        Label lblJVMUsedMemory = new Label (pstrDelimiter+getUsedJVMMemory());
        
        Label lblJVMFreeMemoryLabel = new Label (getFreeJVMMemoryLabel());
        Label lblJVMFreeMemory = new Label (pstrDelimiter+getFreeJVMMemory());
        
        // Assigning values to the grid
        // Attribute Names
        GridPane.setHalignment(lblOSLabel, HPos.RIGHT);
        gridpane.add(lblOSLabel, 0, 0);        
        GridPane.setHalignment(lblOS, HPos.LEFT);
        gridpane.add(lblOS, 1, 0);
        
        GridPane.setHalignment(lblProcessorsLabel, HPos.RIGHT);
        gridpane.add(lblProcessorsLabel, 0, 1);        
        GridPane.setHalignment(lblProcessors, HPos.LEFT);
        gridpane.add(lblProcessors, 1, 1);
                
        GridPane.setHalignment(lblTotalMemoryLabel, HPos.RIGHT);
        gridpane.add(lblTotalMemoryLabel, 0, 2);        
        GridPane.setHalignment(lblTotalMemory, HPos.LEFT);
        gridpane.add(lblTotalMemory, 1, 2);        
        
        GridPane.setHalignment(lblJVMVersionLabel, HPos.RIGHT);
        gridpane.add(lblJVMVersionLabel, 0, 3);        
        GridPane.setHalignment(lblJVMVersion, HPos.LEFT);
        gridpane.add(lblJVMVersion, 1, 3);
        
        GridPane.setHalignment(lblJVMTotalMemoryLabel, HPos.RIGHT);
        gridpane.add(lblJVMTotalMemoryLabel, 0, 4);        
        GridPane.setHalignment(lblJVMTotalMemory, HPos.LEFT);
        gridpane.add(lblJVMTotalMemory, 1, 4);
                
        GridPane.setHalignment(lblJVMUsedMemoryLabel, HPos.RIGHT);
        gridpane.add(lblJVMUsedMemoryLabel, 0, 5);        
        GridPane.setHalignment(lblJVMUsedMemory, HPos.LEFT);
        gridpane.add(lblJVMUsedMemory, 1, 5);
        
        GridPane.setHalignment(lblJVMFreeMemoryLabel, HPos.RIGHT);
        gridpane.add(lblJVMFreeMemoryLabel, 0, 6);        
        GridPane.setHalignment(lblJVMFreeMemory, HPos.LEFT);
        gridpane.add(lblJVMFreeMemory, 1, 6);
        
        
        Button btnOK = new Button(LangConfig.GENERAL_OK);
        
      
        // Build a VBox
        VBox vboxRows = new VBox(5);
        vboxRows.setPadding(new Insets(5,10,5,10));        
        vboxRows.getChildren().addAll(gridpane, btnOK);
        vboxRows.setAlignment(Pos.CENTER);
        
        Scene scnApplicationParameterDialog = new Scene(vboxRows);
        scnApplicationParameterDialog.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
            if (evt.getCode().equals(KeyCode.ESCAPE)) {
                stgApplicationParameterDialog.close();
                pController.updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
            }
        });
        
        //stgApplicationParameterDialog.initModality(Modality.WINDOW_MODAL);
        stgApplicationParameterDialog.setTitle(pstrTitle);        
        stgApplicationParameterDialog.setResizable(false);
        
        
        // Events 
        btnOK.setOnAction((ActionEvent e) -> {
            // Close the dialog box
            stgApplicationParameterDialog.close();
            
            // Update the Status bar
            pController.updateStatusBar(false, StatusMsgsConfig.APPPARAMETERS_CLOSED);
        });
        
        stgApplicationParameterDialog.setScene(scnApplicationParameterDialog);
        stgApplicationParameterDialog.show();
        
        // Update the status bar
        pController.updateStatusBar(true, StatusMsgsConfig.APPPARAMETERS_SHOW);
    }
}
