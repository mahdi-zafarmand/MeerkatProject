/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import config.VertexShapeConfig.VertexShapeType;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *  Class Name      : SceneConfig
 *  Created Date    : 2016-02-24
 *  Description     : Consists all the UI settings related to the display of the panels and their dimensions
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class SceneConfig {
    
    // Time Intervals
    public static int INFO_TIMEOUT = 2;
    
    // GRAPH CANVAS
    public static double GRAPHCANVAS_WIDTH = 0 ; // GraphCanvas Width - Corresponds to the width of the UITab in GraphTab
    public static double GRAPHCANVAS_HEIGHT = 0 ; // GraphCanvas Height - Corresponds to the height of the UITab in GraphTab 
    public static int GRAPHCANVAS_TABTITLE_HEIGHT = 25 ; // The height that is taken by the two rows of tab titles (one for project and one for graph title)
    public static boolean GRAPHCANVAS_DIMENSIONS_ADJUSTED = false ;
    public static String GRAPHCANVAS_DRAGRECTANGLE_BORDERCOLOR = "#0000FF";
    public static String GRAPHCANVAS_DRAGRECTANGLE_FILLCOLOR = "#99CCFF" ;
    public static double GRAPHCANVAS_DRAGRECT_HUESHIFT = 0 ;
    public static double GRAPHCANVAS_DRAGRECT_SATURATION = 1.2 ;
    public static double GRAPHCANVAS_DRAGRECT_BRIGHTNESS = 1 ;
    public static double GRAPHCANVAS_DRAGRECT_OPACITY = 0.5;
    
    public static double GRAPHCANVAS_VIEWPORT_WIDTH = 0 ; // GraphCanvas Width - Corresponds to the width of the UITab in GraphTab
    public static double GRAPHCANVAS_VIEWPORT_HEIGHT = 0 ; // GraphCanvas Height - Corresponds to the height of the UITab in GraphTab 
    
    //Minimap
    public static String MINIMAP_BACKGROUND_COLOR_DEFAULT = "-fx-background-color: white";
    public static String MINIMAP_MASKING_COLOR = "-fx-fill: rgba(127,127,127,0.6)";
    // CONFIG FOR VERTEX
    public static String VERTEX_COLOR_DEFAULT = "#BE4BDB" ;
    public static String VERTEX_COLOR_SELECTED = "#000000" ;
    public static String VERTEX_TITLECOLOR_FILL = "#00FF00" ;
    public static int VERTEX_SHAPE_DEFAULT_INT = -1 ;
    public static VertexShapeType VERTEX_SHAPE_DEFAULT = VertexShapeType.CIRCLE;
    
    public static double VERTEX_SIZE_DEFAULT = 5;
    public static double VERTEX_OPACITY = 1.0 ;
    public static double VERTEX_STROKEWIDTH = 1.0 ;
    // public static boolean VERTEX_LABEL_VISIBLE ;
    public static boolean VERTEX_LABEL_VISIBLE_DEFAULT = false ;
    public static boolean VERTEX_TOOLTIP_VISIBLE_DEFAULT = true ;
    public static String VERTEX_LABEL_SELECTED_ATTR = "" ;
    public static int VERTEX_LABEL_FONTSIZE_DEFAULT = 12 ;
    public static double VERTEXLABEL_SCALINGFACTOR = 1.5 ;
    public static double VERTEX_PINNING_SIZE = 2 ;
    // Throbbing of vertex
    public static int VERTEX_THROB_COUNT = 5 ;
    public static int VERTEX_TIMEPERTHROB_MILLIS = 300 ;
    public static int VERTEX_THROB_SCALE = 3 ;
    public static int VERTEX_TIMESCALE_MILLIS = 300 ;
    
    // CONFIG FOR EDGE
    public static String EDGE_COLOR_PRIMARYCOLOR = "#999999" ;
    public static String EDGE_COLOR_SECONDARYCOLOR = "#FF0000" ;
    public static String EDGE_COLOR_TERTIARYCOLOR = "#0000FF" ;
    
    public static String EDGE_MOUSEHOVER_COLOR = "#000000" ;
    public static String EDGE_SELECTED_COLOR = "#000000" ;
    
    public static boolean EDGE_PREDICTED_DEFAULT = false ;
    public static boolean EDGE_DIRECTED_DEAFULT = false ;
    
    public static double EDGE_STROKEWIDTH = 1.0 ;
    public static double EDGE_OPACITY = 0.5 ;
    public static double EDGE_HUESHIFT = 1.0;
    public static double EDGE_SATURATIONFACTOR = 1.0;
    public static double EDGE_BRIGHTNESSFACTOR = 1.0;
    public static double EDGE_OPACITYFACTOR = 0.5;    
    public static double EDGE_DASHARRAYFILLLENGTH = 10.0;
    public static double EDGE_DASHARRAYEMPTYLENGTH = 5.0;
    public static double EDGE_SHORTESTPATH_THICKNESS = 5.0;
    
    // COLOR OF THE COMMUNITIES
    public static String COLORS_COMMUNITYTITLE = "#ff0000";
    public static List<String> COLORS_COMMUNITIES = 
            Arrays.asList(
                  "#FFB300" // Vivid Yellow
                , "#803E75" // Strong Purple
                , "#FF6800" // Vivid Orange
                , "#A6BDD7" // Very Light Blue
                , "#C10020" // Vivid Red
                , "#CEA262" // Grayish Yellow
                , "#817066" // Medium Gray
                // The following don't work well for people with defective color vision
                , "#007D34" // Vivid Green
                , "#F6768E" // Strong Purplish Pink
                , "#00538A" // Strong Blue
                , "#FF7A5C" // Strong Yellowish Pink
                , "#53377A" // Strong Violet
                , "#FF8E00" // Vivid Orange Yellow
                , "#B32851" // Strong Purplish Red
                , "#F4C800" // Vivid Greenish Yellow
                , "#7F180D" // Strong Reddish Brown
                , "#93AA00" // Vivid Yellowish Green
                , "#593315" // Deep Yellowish Brown
                , "#F13A13" // Vivid Reddish Orange
                , "#232C16" // Dark Olive Green
            );
    
    public static double COMMUNITYRECT_WIDTH = 20 ;
    public static double COMMUNITYRECT_HEIGHT = 10 ;
    
    public static String COLORS_FONT_NODECONTEXTMENU = "#000000" ;
    
    public static double DCCOMMUNITY_SLIDER_WIDTH = 400 ;
    
    
    // public static boolean PINVERTICESTOCANVAS =  false ;
    
    // CANVAS
    public static String CANVAS_BACKGROUND_COLOR = "#ffffff";
    public static String CANVAS_BACKGROUND_TRANSPARENT = "transparent" ;
    public static String CANVAS_DRAGRECT_COLOR = "#0000FF" ;
    public static double CANVAS_DRAGRECT_OPACITY = 0.5 ;
    public static double CANVAS_SCROLL_SCALEFACTOR = 2;
    
    // DC PANEL
    public static Integer DCPANEL_SLIDER_MAXHEIGHT = 60;
    public static Integer DCPANEL_BUTTONS_MAXHEIGHT = 40;
    public static Integer STATUSBAR_MAXHEIGHT = 20;
    
    public static IntegerProperty DCPANEL_SLIDER_HEIGHT = new SimpleIntegerProperty(DCPANEL_SLIDER_MAXHEIGHT);
    public static IntegerProperty DCPANEL_BUTTONS_HEIGHT = new SimpleIntegerProperty(DCPANEL_BUTTONS_MAXHEIGHT);
    public static IntegerProperty STATUSBAR_HEIGHT = new SimpleIntegerProperty(STATUSBAR_MAXHEIGHT);
    
    public static IntegerProperty LOWERPANEL_HEIGHT = new SimpleIntegerProperty(DCPANEL_SLIDER_MAXHEIGHT + DCPANEL_BUTTONS_MAXHEIGHT + STATUSBAR_MAXHEIGHT);
    
    //CONSTANTS FOR EVENT ANALYSIS
    public static double EVENTCANVAS_WIDTH = 1000;
    public static double EVENTCANVAS_HEIGHT = 600;
    public static double EVENTCANVAS_WIDTH_SCALE = 2;
    public static double EVENTCANVAS_HEIGHT_SCALE = 2;
    
    
    public static int MINIMAP_WIDTH = 100 ;
    public static int MINIMAP_HEIGHT = 100 ;
    
    public static double TABPANE_WIDTH = 100 ;
    public static double TABPANE_HEIGHT = 100 ;
    
    public static int MODEINFO_WIDTH = 600 ;
    public static int MODEINFO_HEIGHT = 20 ;
    
    public static int VERTEX_LABEL_PADDING = 5 ;
        
    public static void initialize(){
        LOWERPANEL_HEIGHT.bind(DCPANEL_BUTTONS_HEIGHT.add(DCPANEL_SLIDER_HEIGHT));
    }
    
        
    public void AssignShape(int pintShape) {
        switch (pintShape) {
            case 0 :
                VERTEX_SHAPE_DEFAULT = VertexShapeType.CIRCLE ;
                break ;
            case 1 :
                VERTEX_SHAPE_DEFAULT = VertexShapeType.ELLIPSE ;
                break ;
            case 2 :
                VERTEX_SHAPE_DEFAULT = VertexShapeType.RECTANGLE ;
                break ;
            case 3 :
                VERTEX_SHAPE_DEFAULT = VertexShapeType.SQUARE ;
                break ;
                
        }
    }
    
    // RANDOM ITEMS THAT ARE TO BE DELETED
    public static int RANDOMPARAMETER = 3 ;
}
