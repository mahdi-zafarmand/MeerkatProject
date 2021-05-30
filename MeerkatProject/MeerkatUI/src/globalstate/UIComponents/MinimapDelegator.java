/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate.UIComponents;

import config.SceneConfig;
import globalstate.GraphCanvas;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.ScrollPane;

/**
 *
 * @author AICML Administrator
 */
public class MinimapDelegator {
    
    private Minimap minimap;
    
    private ScrollPane scrlCanvas;
    private PanAndZoomPane[] ArrPanAndZoomPane;
    private SimpleIntegerProperty intCurrentTimeFrameIndexProperty;
    
    public MinimapDelegator(Minimap minimap, PanAndZoomPane[] pArrpanAndZoomPane, ScrollPane scrlCanvas, SimpleIntegerProperty intCurrentTimeFrameIndexProperty){
        this.minimap = minimap;
        this.ArrPanAndZoomPane = pArrpanAndZoomPane;
        this.scrlCanvas = scrlCanvas;
        this.intCurrentTimeFrameIndexProperty = intCurrentTimeFrameIndexProperty;
    }
    private PanAndZoomPane getPanAndZoomPane(){
        return ArrPanAndZoomPane[intCurrentTimeFrameIndexProperty.getValue()];
    }
    
    public void updateMinimap(){
        this.minimap.updateMiniMap();
    }
    
    public void changeMinimapMasking(){
        
            double dblMinXTotalContent = getPanAndZoomPane().getBoundsInParent().getMinX(); // Minimum value of X pixel (top left x pixel value)
            double dblMinYTotalContent = getPanAndZoomPane().getBoundsInParent().getMinY(); // Minimum Y Pixel value (top left y pixel value)
            SceneConfig.GRAPHCANVAS_VIEWPORT_WIDTH = scrlCanvas.getViewportBounds().getWidth() ; // Width of the viewport
            SceneConfig.GRAPHCANVAS_VIEWPORT_HEIGHT = scrlCanvas.getViewportBounds().getHeight(); // Height of the viewport
            // double dblStackPaneWidth = stkCanvasMinimapHolder.getWidth() ;
            // double dblStackPaneHeight = stkCanvasMinimapHolder.getHeight();
            double dblTotalContentWidth = getPanAndZoomPane().getBoundsInParent().getWidth();
            double dblTotalContentHeight = getPanAndZoomPane().getBoundsInParent().getHeight(); 
            /*
            System.out.println("MinimapDeligator.changeMinimap: "               
                    +"\tPanAndZoomPane_Size("+dblTotalContentWidth+","+dblTotalContentHeight+")"
                    +"\tMinCordinates_PanAndZoomPane("+dblMinXTotalContent+","+dblMinYTotalContent+")"
                    +"\tViewport_Size ("+SceneConfig.GRAPHCANVAS_VIEWPORT_WIDTH+","+SceneConfig.GRAPHCANVAS_VIEWPORT_HEIGHT+")"            
            );
            */
            
            
            this.minimap.changeMiniMapMasking(
                      
                    dblTotalContentWidth, dblTotalContentHeight
                    , dblMinXTotalContent, dblMinYTotalContent
                    , SceneConfig.GRAPHCANVAS_VIEWPORT_WIDTH, SceneConfig.GRAPHCANVAS_VIEWPORT_HEIGHT
            );
        
    }
    
}
