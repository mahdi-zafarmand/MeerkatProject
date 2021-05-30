/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import ca.aicml.meerkat.api.GraphAPI;
import config.EdgeContextConfig;
import globalstate.MeerkatUI;
import java.util.ArrayList;
import java.util.Set;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import ui.dialogwindow.EdgeDeleteConfirmationDialog;
import ui.dialogwindow.EdgeInformationDialog;
import ui.edgeshapes.EdgeLine;

/**
 *  Class Name      : EdgeContextMenu
 *  Created Date    : 2015-10-28
 *  Description     : The Menu that is to be displayed on right click of an Edge
 *                      and their corresponding functionalities
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2018-01-19      Talat           Updating the UI color to the Data Structure in MeerkatLogic
 * 
*/

public class EdgeContextMenu {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private ContextMenu cmEdge;
    private static EdgeLine edgeLine ;
    
    private MenuItem menuEdgeInfo;
    private MenuItem menuEdgeDelete;
    private Slider sliderVertexSize;
    private CustomMenuItem menuSlider ;
    private Menu menuEdgeWidth;
    private ColorPicker clrPicker;
    private MenuItem menuColorPicker;
    private Menu menuEdgeColor;
    private Menu menuStyle;
    
    
    private static EdgeContextMenu ecmInstance = null; // To use singleton pattern
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    /**
     *  Constructor Name: EdgeContextMenu()
     *  Created Date    : 2015-10-29
     *  Description     : Constructors that initializes the context menu on the right click of a Edge
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pEdge : Edge
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 2017-06-14       @sankalp         Added logic for all edge context menu.
     *  
     * 
    */
    private EdgeContextMenu(EdgeLine pNode) {
        this.edgeLine = pNode;
        cmEdge = new ContextMenu();
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        /* ADDING THE CONTEXT MENU ITEM */
        menuEdgeInfo = new MenuItem(EdgeContextConfig.getEdgeInfoText());
        menuEdgeInfo.setOnAction(event -> {
            event.consume();
            Set<UIEdge> setSelectedEdges = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedEdges();
            
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
            int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex() ;
            
            // System.out.println("EdgeContextMenu.EdgeContextMenu() : activeTimeFramee = " + (intTimeFrameIndex));
            EdgeInformationDialog.Display(UIInstance.getController(), setSelectedEdges, intProjectID, intGraphID, intTimeFrameIndex);
        });
        EdgeInformationDialog.setParameters(EdgeContextConfig.getEdgeInfoText()); 
        
        menuEdgeDelete = new MenuItem(EdgeContextConfig.getEdgeDeleteText());
        menuEdgeDelete.setOnAction(event -> {
            event.consume();
            EdgeDeleteConfirmationDialog.Display(UIInstance.getController());
        });
        EdgeDeleteConfirmationDialog.setParameters(EdgeContextConfig.getEdgeDeleteText(), "", null);
                
        // Size
        sliderVertexSize = createEdgeWidthSlider();
        menuSlider = new CustomMenuItem(sliderVertexSize);
        
        menuEdgeWidth = new Menu(EdgeContextConfig.getEdgeWidthText()) ;
        menuEdgeWidth.getItems().add(menuSlider);
        
        // Color
        clrPicker = new ColorPicker();
        clrPicker.setValue(this.edgeLine.getColor());
        menuColorPicker = new MenuItem(null, clrPicker);
        
        clrPicker.setOnAction((ActionEvent t) -> {
            System.out.println("EdgeContextMenu.EdgeContextMenu(): Change of color invoked to color :"+ clrPicker.getValue());
                      
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
            int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex() ;
            
            Color selectedColor = clrPicker.getValue();            
            this.edgeLine.setColor(selectedColor);
            GraphAPI.updateEdgeColor(intProjectID, intGraphID, intTimeFrameIndex, 
                    selectedColor.toString(), new ArrayList<Integer>() {{add(edgeLine.getEdgeParent().getID());}});
            UIInstance.getActiveProjectTab().setProjectModifiedStatus(Boolean.TRUE);
        });        
        menuEdgeColor = new Menu(EdgeContextConfig.getEdgeColorText());
        menuEdgeColor.getItems().add(menuColorPicker);
        
        // Style
        menuStyle = new Menu(EdgeContextConfig.getEdgeStyleText());
        menuStyle.getItems().add(menuEdgeColor);
        menuStyle.getItems().add(menuEdgeWidth);

        cmEdge.getItems().addAll(menuEdgeInfo, menuStyle, menuEdgeDelete);
    }
    
    /* *************************************************************** */
    /* ********************       METHODS     ************************ */
    /* *************************************************************** */
    /**
     *  Method Name     : getInstance()
     *  Created Date    : 2015-10-29
     *  Description     : A static method that returns the only object that is created 
     *                      (or creates if it does not exist)
     *  Version         : 1.0
     *  @author         : Talat
     *  @param edge     : edge 
     * 
     *  @param pEdge: Edge
     *  @return EdgeContextMenu
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static EdgeContextMenu getInstance(EdgeLine edge) {
        edgeLine = edge;
        if (ecmInstance == null) {
            ecmInstance = new EdgeContextMenu(edgeLine);
        }else
            updateInstance(edge);
        return ecmInstance;
    }
    
    /**
     *  Method Name     : updateInstance()
     *  Created Date    : 2017-06-14
     *  Description     : Updates the single instance of the Edge Context Menu
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param edge : EdgeLine
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private static void updateInstance(EdgeLine edge) {
        ecmInstance.edgeLine = edge;
        
        ecmInstance.sliderVertexSize.setValue(ecmInstance.edgeLine.getStrokeWidth());
        ecmInstance.clrPicker.setValue((Color)ecmInstance.edgeLine.getStroke());
    }
    
    /**
     *  Method Name     : Show()
     *  Created Date    : 2015-10-29
     *  Description     : Shows the context menu for the node
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pEdge : Edge
     *  @param pdblX : double
     *  @param pdblY : double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void Show (Node pNode, double pdblX, double pdblY) {
        this.cmEdge.show(pNode, pdblX, pdblY);
    }
    
    /**
     *  Method Name     : createEdgeWidthSlider()
     *  Created Date    : 2017-06-08
     *  Description     : creates a slider for edge width
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param pEdge : Edge
     *  @param pdblX : double
     *  @param pdblY : double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */

    private Slider createEdgeWidthSlider() {
        Slider sliderLocal = new Slider();
        sliderLocal.setMin(1);
        sliderLocal.setMax(10);
        sliderLocal.setBlockIncrement(1);
        sliderLocal.setShowTickMarks(true);
        
        sliderLocal.setValue(edgeLine.getStrokeWidth());
        
        sliderLocal.setTooltip(new Tooltip(String.valueOf(sliderLocal.getValue())));
        sliderLocal.setShowTickMarks(true);          
        
        sliderLocal.valueProperty().addListener((ObservableValue<? extends Number> ov, Number numOldValue, Number numNewValue) -> {
            edgeLine.setStrokeWidth(sliderLocal.getValue());
        });        
        
        return sliderLocal;
    }
            
}
