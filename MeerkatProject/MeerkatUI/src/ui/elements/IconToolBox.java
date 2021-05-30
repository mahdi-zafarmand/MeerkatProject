/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.elements;

import analysismenubar.MenuBarUtilities;
import analysisscreen.AnalysisController;
import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.UtilityAPI;
import globalstate.MeerkatUI;
import globalstate.ProjectStatusTracker;
import graphelements.UIVertex;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 *
 * @author talat
 */
public class IconToolBox {
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    // ComboBox<HBox> cmbIcons = new ComboBox<>() ;
    ComboBox<String> cmbIcons = new ComboBox<>() ;
    
    private static IconToolBox instance;
    private static final String removeIconURL = "file:resources/icons/Delete-16.png";
    
    private static AnalysisController pAnalysisController ;
    private final HBox hboxIconTool ;
    
    private final String style = 
                          "-fx-padding: 0;"
                        + "-fx-border-style: solid outside;"
                        + "-fx-border-width: 1;"
                        + "-fx-border-insets: 0;"
                        + "-fx-border-radius: 0;"
                        + "-fx-border-style: dashed;"
                        + "-fx-border-color: #AAAAAA;";
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    
     public HBox getIconTools() {
        return hboxIconTool;
    }   
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */

    /**
     *  Constructor Name: IconToolBox()
     *  Created Date    : 2017-06-15
     *  Description     : Constructor for IconToolBox
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */

    private IconToolBox() {
        hboxIconTool = new HBox();
        hboxIconTool.setAlignment(Pos.CENTER);
        hboxIconTool.setSpacing(2);
        hboxIconTool.setPadding(new Insets(0, 0, 0, 0));
        hboxIconTool.setStyle(style);        
        
        // ObservableList<HBox> options = FXCollections.observableArrayList();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        
        observableList.add(removeIconURL);
        List<String> lstIconPaths = UtilityAPI.getAllIcons();
        
        for(String iconPath : lstIconPaths){
            String fullIconPath = "file:"+iconPath;   
            if(!fullIconPath.equals(removeIconURL))            
                observableList.add(fullIconPath);
        }
        
        // final ComboBox<HBox> comboBox = new ComboBox(options);        
        cmbIcons = new ComboBox<>();
        cmbIcons.setPrefWidth(150);
        cmbIcons.setTooltip(new Tooltip("Apply icons on selected vertices"));
        cmbIcons.setItems(observableList);
        cmbIcons.setCellFactory(param -> new VertexIconCell());
        cmbIcons.setButtonCell(new VertexIconCell());
        
        hboxIconTool.getChildren().add(cmbIcons);
    }
    
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
   
    /**
     *  Method Name     : getInstance()
     *  Created Date    : 2017-06-15
     *  Description     : Returns the only instance of IconToolBox Object
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @return EditingToolBox
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static IconToolBox getInstance() {
        if (instance == null) {
            instance = new IconToolBox();
        }
        return instance;
    }
    
    
    /**
     *  Method Name     : getInstance()
     *  Created Date    : 2017-06-15
     *  Description     : Returns the only instance of IconToolBox Object
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pController : AnalysisController
     *  @return EditingToolBox
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static IconToolBox getInstance(AnalysisController pController) {
        pAnalysisController = pController;
        if (instance == null) {
            instance = new IconToolBox();
        }
        return instance;
    }
    
    /**
     *  Method Name     : enableIconToolbox()
     *  Created Date    : 2017-06-15
     *  Description     : enables the size tool slider for edges.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void enableIconToolbox(){
        cmbIcons.setDisable(false);
        enableIconListeners();
    }
    
    /**
     *  Method Name     : disableIconToolbox()
     *  Created Date    : 2017-06-15
     *  Description     : disables the size tool slider for edges.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void disableIconToolbox(){
        cmbIcons.setDisable(true);
        cmbIcons.setValue("Select Icons");
    }
    
    
    
     /**
     *  Method Name     : enableIconListeners()
     *  Created Date    : 2017-06-15
     *  Description     : enables the size tool sliders listeners on graph load.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private void enableIconListeners() {       
        
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        cmbIcons.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            Set<UIVertex> selectedVertices = UIInstance.getActiveProjectTab().
                    getActiveGraphTab().getGraphCanvas().getSelectedVertices();
            List<Integer> lstVertexIDs = new ArrayList<>();
            int intProjectId = UIInstance.getActiveProjectTab().getProjectID();
            int intGraphId = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphID();
            int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
                        
            for(UIVertex currentVertex : selectedVertices){
                lstVertexIDs.add(currentVertex.getID());
                if(!newValue.equals(removeIconURL)){                
                    currentVertex.updateIcon(newValue);
                    ProjectStatusTracker.updateProjectModifiedStatus(intProjectId, ProjectStatusTracker.IconAddedRemoved);
                }
                else{
                    currentVertex.updateIcon(null);
                    ProjectStatusTracker.updateProjectModifiedStatus(intProjectId, ProjectStatusTracker.IconAddedRemoved);
                }
            }
            
            if(newValue!=null){
                if(!newValue.equals(removeIconURL))
                    GraphAPI.updateVertexIconURLs(intProjectId, intGraphId, intTimeFrameIndex, newValue, lstVertexIDs);
                else
                    GraphAPI.updateVertexIconURLs(intProjectId, intGraphId, intTimeFrameIndex, "", lstVertexIDs);
            }
            
        }); 
    }   
    
    class VertexIconCell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty)
                setGraphic(null);
            else {                
                String url = item;
                MeerkatVertexImage image = new MeerkatVertexImage(url);
                ImageView imageView = new ImageView(image);
                Label lblItem = new Label();
                
                if(!url.equals(removeIconURL)){
                    String fileName = UtilityAPI.getFileNameWithoutExtension(item);
                    lblItem.setText(fileName);
                    lblItem.setTextFill(Color.valueOf("#000000"));
                }else{
                    lblItem.setText("Remove Selected Icons");
                    lblItem.setTextFill(Color.valueOf("#ff0000"));
                }
                        
                HBox hboxItems = new HBox(5);
                hboxItems.setSpacing(10);
                hboxItems.getChildren().addAll(imageView, lblItem);

                setGraphic(hboxItems);
            }
            setText("");
        }
    }
}
