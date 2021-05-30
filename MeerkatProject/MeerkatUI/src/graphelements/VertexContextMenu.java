/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphelements;

import ca.aicml.meerkat.api.GraphAPI;
import config.LangConfig;
import config.VertexContextConfig;
import config.SceneConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import java.util.ArrayList;
import java.util.Set;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import ui.dialogwindow.NeighborhoodDegreeDialog;
import ui.dialogwindow.VertexDeleteConfirmationDialog;
import ui.dialogwindow.VertexInformationDialog;

/**
 *  Class Name      : NodeContextMenu
 *  Created Date    : 2015-10-28
 *  Description     : The Menu that is to be displayed on right click of a Vertex (Node)
 *                      and their corresponding functionalities
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  2016-04-06      Talat           Added fields (menuShapeSquare, sqrMenuItem, lblSquare, hboxSquare) and the options in the Menu for shape
 * 
*/

public class VertexContextMenu {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    private static ContextMenu cmNode;
    private static VertexHolder vtxHolder ;
    
    // Contents of the content menu - primary
    private MenuItem menuNodeInfo ;
    private MenuItem menuNodeNeighbour ;
    private Menu menuStyle ;
    private MenuItem menuDeleteNode ;
    private MenuItem menuPinUnpinNode ;
    private MenuItem menuExtractGraphs ;
                    
    // Menu Items within Style
    private Menu menuNodeShape ;
    private Menu menuNodeSize ;  
    private Menu menuNodeLabelSize ;
    private Menu menuNodeColor ;
    
    // Size
    private CustomMenuItem cusmenuSize ;
    private Slider sliderVertexSize ;
    
    // Label Size
    private CustomMenuItem cusmenuLabelSize ;
    private Slider sliderVertexLabelSize ;
        
    // Color
    private MenuItem cusmenuColorPicker ;
    private final ColorPicker clrPicker ;
    
        
    // Shapes of the Vertex
    private CustomMenuItem menuShapeCircle ;        
    private Circle crcMenuItem ;
    private Label lblCircle ;
    private HBox hboxCircle ;
    
    private CustomMenuItem menuShapeEllipse ;
    private Ellipse elpMenuItem ;
    private Label lblEllipse ;
    private HBox hboxEllipse ;

    private CustomMenuItem menuShapeRectangle ;
    private Rectangle recMenuItem ;
    private Label lblRectangle ;
    private HBox hboxRectangle ;
    
    private CustomMenuItem menuShapeSquare ;
    private Rectangle sqrMenuItem ;
    private Label lblSquare ;
    private HBox hboxSquare ;
    
    // Extract the selected vertices as graph

    
    private static VertexContextMenu ncmInstance = null; // To use singleton pattern
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    /**
     *  Constructor Name: NodeContextMenu()
     *  Created Date    : 2015-10-29
     *  Description     : Constructors that initializes the context menu on the right click of a vertex (Node)
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pvtxHolder : VertexHolder
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2015-11-30      Talat           For a singleton pattern, the very first instance should always be created without binding it 
     *                                  to a Vertex. Passing vtxHolder as the parameter will always cause the changes to be reflected
     *                                  on the bound vertex. Therefore 
     *  2018-01-19      Talat           Updating the UI color to the Data Structure in MeerkatLogic
    */
    private VertexContextMenu(VertexHolder pvtxHolder) {
        this.vtxHolder = pvtxHolder ;
        
        cmNode = new ContextMenu();
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        
        // List<UIVertex> lstSelectedVertices = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedVertices();
        //System.out.println("VertexContextMenu.vertexContextMenu() : activeTimeFrame = " + intTimeFrameIndex);
        
        /* ADDING THE CONTEXT MENU ITEM */
        menuNodeInfo = new MenuItem(VertexContextConfig.getVertexInfoText());
        menuNodeInfo.setOnAction(event -> {
            event.consume();
            Set<UIVertex> setSelectedVertices = UIInstance.getActiveProjectTab().getActiveGraphTab().getGraphCanvas().getSelectedVertices();
            
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
            int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex() ;
            
            System.out.println("VertexContextMenu.vertexContextMenu() : activeTimeFramee = " + (intTimeFrameIndex));
            VertexInformationDialog.Display(UIInstance.getController(), setSelectedVertices, intProjectID, intGraphID, intTimeFrameIndex);
        });
        VertexInformationDialog.setParameters(VertexContextConfig.getVertexInfoText());
        
        menuNodeNeighbour = new MenuItem(VertexContextConfig.getVertexNeighborText());
        menuNodeNeighbour.setOnAction(event -> {
            event.consume();
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
            int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex() ;
            NeighborhoodDegreeDialog.Display(UIInstance.getController(), intProjectID, intGraphID, intTimeFrameIndex);
        });
        
        menuDeleteNode = new MenuItem(VertexContextConfig.getVertexDeleteText());
        menuDeleteNode.setOnAction(event -> {
            event.consume();
            VertexDeleteConfirmationDialog.Display(UIInstance.getController());
        });
        VertexDeleteConfirmationDialog.setParameters(VertexContextConfig.getVertexDeleteText(), "", null);
        
        if (this.vtxHolder.IsVertexPinned()) {
            menuPinUnpinNode = new MenuItem(VertexContextConfig.getVertexUnpinText());
        } else {
            menuPinUnpinNode = new MenuItem(VertexContextConfig.getVertexPinText());
        }
                
        menuPinUnpinNode.setOnAction(event -> {
            event.consume();
            if (this.vtxHolder.IsVertexPinned()) {
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.VERTEXPINNING_DISABLING);
                this.vtxHolder.setIsVertexPinned(false);
                this.menuPinUnpinNode.setText(VertexContextConfig.getVertexUnpinText());
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.VERTEXPINNING_DISABLED);
            } else {
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.VERTEXPINNING_DISABLING);
                this.vtxHolder.setIsVertexPinned(true);
                this.menuPinUnpinNode.setText(VertexContextConfig.getVertexPinText());
                UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.VERTEXPINNING_ENABLED);
            }
        });
        
        menuExtractGraphs = new MenuItem(VertexContextConfig.getVertexExtractText());
        menuExtractGraphs.setOnAction((event) -> {
            event.consume();
            
            // Get the list of all the Vertex IDs
            
            // Get the list of all the selected Edge IDs
            
            // Get the Project ID and other stuff
        });
                
        // Size
        // Create the slider
        this.sliderVertexSize = createVertexSizeSlider();
        cusmenuSize = new CustomMenuItem(sliderVertexSize);
        menuNodeSize = new Menu(VertexContextConfig.getVertexSizeText()) ;
        menuNodeSize.getItems().add(cusmenuSize);
        
        // Vertex Label Slider
        // Create the slider
        this.sliderVertexLabelSize = createVertexLabelSizeSlider();
        cusmenuLabelSize = new CustomMenuItem(sliderVertexLabelSize);
        menuNodeLabelSize = new Menu(VertexContextConfig.getVertexLabelSizeText()) ;
        menuNodeLabelSize.getItems().add(cusmenuLabelSize);
        
        // Color
        clrPicker = new ColorPicker();
        clrPicker.setValue(this.vtxHolder.getColor());
        
        cusmenuColorPicker = new MenuItem(null, clrPicker);
        cusmenuColorPicker.setOnAction((ActionEvent t) -> {
            System.out.println("NodeContextMenu.NodeContextMenu(): Change of color invoked ");
            
            int intProjectID = UIInstance.getActiveProjectTab().getProjectID() ;
            int intGraphID = UIInstance.getActiveProjectTab().getActiveGraphID() ;
            int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex() ;
            
            Color selectedColor = clrPicker.getValue();            
            this.vtxHolder.setColor(selectedColor);
            GraphAPI.updateVertexColor(intProjectID, intGraphID, intTimeFrameIndex, 
                    selectedColor.toString(), new ArrayList<Integer>() {{add(vtxHolder.getID());}});
            UIInstance.getActiveProjectTab().setProjectModifiedStatus(Boolean.TRUE);
        });        
        menuNodeColor = new Menu(VertexContextConfig.getVertexColorText());
        menuNodeColor.getItems().add(cusmenuColorPicker);
        
        
        // Shape of the Vertex
        menuShapeCircle = new CustomMenuItem();        
        crcMenuItem = new Circle(5);
        lblCircle = new Label(LangConfig.VERTEXSHAPES_CIRCLE);
        lblCircle.setTextFill(Color.web(SceneConfig.COLORS_FONT_NODECONTEXTMENU));
        hboxCircle = new HBox(crcMenuItem, lblCircle);
        hboxCircle.setAlignment(Pos.CENTER);
        hboxCircle.setSpacing(3);
        
        menuShapeCircle.setContent(hboxCircle);
        menuShapeCircle.setOnAction(event -> {
            event.consume();
            this.vtxHolder.addCircleShape();
        });

        menuShapeEllipse = new CustomMenuItem();
        elpMenuItem = new Ellipse(5,4);
        lblEllipse = new Label(LangConfig.VERTEXSHAPES_ELLIPSE);
        lblEllipse.setTextFill(Color.web(SceneConfig.COLORS_FONT_NODECONTEXTMENU));
        hboxEllipse = new HBox(elpMenuItem, lblEllipse);
        hboxEllipse.setAlignment(Pos.CENTER);
        hboxEllipse.setSpacing(3);
        
        menuShapeEllipse.setContent(hboxEllipse);
        menuShapeEllipse.setOnAction(event -> {
            event.consume();
            this.vtxHolder.addEllipseShape();
        });
        
        menuShapeRectangle = new CustomMenuItem();
        recMenuItem = new Rectangle(8,6);
        lblRectangle = new Label(LangConfig.VERTEXSHAPES_RECTANGLE);
        lblRectangle.setTextFill(Color.web(SceneConfig.COLORS_FONT_NODECONTEXTMENU));
        hboxRectangle = new HBox(recMenuItem, lblRectangle);
        hboxRectangle.setAlignment(Pos.CENTER);
        hboxRectangle.setSpacing(3);
        menuShapeRectangle.setContent(hboxRectangle);
        menuShapeRectangle.setOnAction(event -> {
            event.consume();          
            this.vtxHolder.addRectangleShape();
        });
        
        menuShapeSquare = new CustomMenuItem();
        sqrMenuItem = new Rectangle(8,6);
        lblSquare = new Label(LangConfig.VERTEXSHAPES_SQUARE);
        lblSquare.setTextFill(Color.web(SceneConfig.COLORS_FONT_NODECONTEXTMENU));
        hboxSquare = new HBox(sqrMenuItem, lblSquare);
        hboxSquare.setAlignment(Pos.CENTER);
        hboxSquare.setSpacing(3);
        menuShapeSquare.setContent(hboxSquare);
        menuShapeSquare.setOnAction(event -> {
            event.consume();           
            this.vtxHolder.addSquareShape();
        });
        
        menuNodeShape = new Menu(VertexContextConfig.getVertexShapeText());
        menuNodeShape.getItems().addAll(menuShapeCircle, menuShapeEllipse, menuShapeRectangle, menuShapeSquare);
        
        // Style
        menuStyle = new Menu(VertexContextConfig.getVertexStyleText());
        menuStyle.getItems().add(menuNodeShape);
        menuStyle.getItems().add(menuNodeColor);
        menuStyle.getItems().add(menuNodeSize);
        menuStyle.getItems().add(menuNodeLabelSize);

        cmNode.getItems().addAll(menuNodeInfo, menuNodeNeighbour, menuStyle, menuDeleteNode, menuPinUnpinNode);
        
        // Disable the Vertex Label Slider based on the visibility
        if (vtxHolder.getLabelHolder().getIsLabelVisible()) {
            sliderVertexLabelSize.setDisable(false);
        } else {
            sliderVertexLabelSize.setDisable(true);
        }
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
     * 
     *  @param pvtxHolder: VertexHolder
     *  @return NodeContextMenu
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public static VertexContextMenu getInstance(VertexHolder pvtxHolder) {
        vtxHolder = pvtxHolder;
        // System.out.println("NodeContextMenu.getInstance(): Vertex Invoked is "+vtxHolder.getID());
        if (ncmInstance == null) {
            ncmInstance = new VertexContextMenu(vtxHolder);
//            System.out.println("NodeContextMenu.getInstance(): XLength = "+vtxHolder.getXLengthProperty().get()
//                +"\tSlider Value = "+vtxHolder.getXLengthProperty().get() / AppConfig.DEFAULT_VERTEX_SIZE);
        } else {
            System.out.println("VertexContextMenu.getInstance(): Vertex Instance is UPDATED");
            updateInstance(pvtxHolder);
        }
        return ncmInstance;
    }
    
    
    /**
     *  Method Name     : updateInstance()
     *  Created Date    : 2015-10-29
     *  Description     : Updates the single instance of the Vertex Context Menu
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pvtxHolder : VertexHolder
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    private static void updateInstance(VertexHolder pvtxHolder) {
        ncmInstance.vtxHolder = pvtxHolder;
        
        ncmInstance.sliderVertexSize.setValue(ncmInstance.vtxHolder.getXLengthProperty().get() / SceneConfig.VERTEX_SIZE_DEFAULT);
        ncmInstance.sliderVertexLabelSize.setValue((int)(ncmInstance.vtxHolder.getLabelHolder().getFontSize() / SceneConfig.VERTEX_LABEL_FONTSIZE_DEFAULT * 2));
        
        if (ncmInstance.vtxHolder.IsVertexPinned()) {
            ncmInstance.menuPinUnpinNode.setText(VertexContextConfig.getVertexUnpinText());
        } else {
            ncmInstance.menuPinUnpinNode.setText(VertexContextConfig.getVertexPinText());
        }
        
        if (ncmInstance.vtxHolder.getLabelHolder().getIsLabelVisible()) {
            ncmInstance.sliderVertexLabelSize.setDisable(false);
            System.out.println("VertexContectMenu.updateInstance(): Vertex Label is VISIBLE");
        } else {
            ncmInstance.sliderVertexLabelSize.setDisable(true);
            System.out.println("VertexContectMenu.updateInstance(): Vertex Label is NOT VISIBLE");
        }
        
    }

    
    /**
     *  Method Name     : Show()
     *  Created Date    : 2015-10-29
     *  Description     : Shows the context menu for the node
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pNode : Node
     *  @param pdblX : double
     *  @param pdblY : double
     * 
     *  EDIT HISTORY (most recent at the top)20
     *  Date            Author          Description
     *  
     * 
    */
    public void Show (Node pNode, double pdblX, double pdblY) {
        this.cmNode.show(pNode, pdblX, pdblY);
    }
    
    
    /**
     *  Method Name     : createVertexSizeSlider()
     *  Created Date    : 2015-10-29
     *  Description     : Creates a Slider for the Vertex Size
     *  Version         : 1.0
     *  @author         : Talat
     *
     *  @return Slider
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public Slider createVertexSizeSlider() {
        Slider sliderLocal = new Slider();
        sliderLocal.setMin(1);
        sliderLocal.setMax(10);
        sliderLocal.setBlockIncrement(1);
        sliderLocal.setShowTickMarks(true);
        
        // DoubleProperty dblRatio = new SimpleDoubleProperty(vtxHolder.getXLengthProperty().get() / SceneConfig.VERTEX_SIZE_DEFAULT);
        sliderLocal.setValue(vtxHolder.getXLengthProperty().get() / SceneConfig.VERTEX_SIZE_DEFAULT);
        
        sliderLocal.setTooltip(new Tooltip(String.valueOf(sliderLocal.getValue())));
        sliderLocal.setShowTickMarks(true);          
        
        sliderLocal.valueProperty().addListener((ObservableValue<? extends Number> ov, Number numOldValue, Number numNewValue) -> {
            // System.out.println("NodeContextMenu.createVertexSizeSlider(): Values are "+numOldValue+" to "+numNewValue + " & Slider value "+sliderVertexSize.getValue());
            vtxHolder.changeHolderSize(sliderLocal.getValue());
        });        
        
        return sliderLocal;
    }
    
    /**
     *  Method Name     : createVertexLabelSizeSlider()
     *  Created Date    : 2015-10-29
     *  Description     : Creates a Slider for the Vertex Size
     *  Version         : 1.0
     *  @author         : Talat
     *
     *  @return Slider
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public Slider createVertexLabelSizeSlider() {
        Slider sliderLocal = new Slider();
        sliderLocal.setMin(1);
        sliderLocal.setMax(10);
        sliderLocal.setBlockIncrement(1);
        sliderLocal.setShowTickMarks(true);
        sliderLocal.setShowTickLabels(true);
        
        // DoubleProperty dblRatio = new SimpleDoubleProperty(vtxHolder.getLabelHolder().getFontSize() / SceneConfig.VERTEX_LABEL_FONTSIZE * 2);
        sliderLocal.setValue(vtxHolder.getLabelHolder().getFontSize() / SceneConfig.VERTEX_LABEL_FONTSIZE_DEFAULT * 2);
        
        sliderLocal.setTooltip(new Tooltip(String.valueOf(sliderLocal.getValue())));
        sliderLocal.setShowTickMarks(true);          
        
        sliderLocal.valueProperty().addListener((ObservableValue<? extends Number> ov, Number numOldValue, Number numNewValue) -> {
            System.out.println("NodeContextMenu.createVertexSizeSlider(): Values are "+numOldValue+" to "+numNewValue + " & Slider value "+sliderVertexSize.getValue());
            // vtxHolder.changeHolderSize(sliderVertexSize.getValue());
            sliderLocal.setValue(numNewValue.intValue());
            vtxHolder.getLabelHolder().changeLabelSize((int)(SceneConfig.VERTEX_LABEL_FONTSIZE_DEFAULT * numNewValue.intValue() / 2.0)) ;
        });
        
        return sliderLocal;
    }
            
}
