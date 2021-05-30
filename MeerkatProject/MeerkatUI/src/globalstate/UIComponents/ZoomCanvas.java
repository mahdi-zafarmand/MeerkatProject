/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate.UIComponents;

import config.SceneConfig;
import globalstate.GraphTab;
import globalstate.MeerkatUI;

/**
 *
 * @author sankalp
 */
public class ZoomCanvas {
    
    private static final double MAX_SCALE = 8.0d;
    private static final double MIN_SCALE = .125d;
    
    /**
     *  Method Name     : zoomCanvas
     *  Created Date    : 2017-07-24
     *  Description     : executes Zoom In/Out functionality
     *  Version         : 1.0
     *  @author         : sankalp
     * 
     *  @param deltaY : double
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author      Description
     * 
    */
    public static void zoomCanvas(double deltaY) {
        
        GraphTab graphTab = MeerkatUI.getUIInstance().getActiveProjectTab().getActiveGraphTab();
        
        double delta = PanAndZoomPane.DEFAULT_DELTA;

        double scale = graphTab.getZoomPane().getScale(); // currently we only use Y, same value is used for X
        double oldScale = scale;

        graphTab.getZoomPane().setDeltaY(deltaY); 
        if (graphTab.getZoomPane().deltaY.get() < 0) {
            scale /= delta;
        } else {
            scale *= delta;
        }
        scale = clamp( scale, MIN_SCALE, MAX_SCALE);
        double f = (scale / oldScale)-1;
        //double f = (scale - oldScale);

        double dx = (SceneConfig.GRAPHCANVAS_WIDTH/2 - (graphTab.getZoomPane().getBoundsInParent().getWidth()/2 + graphTab.getZoomPane().getBoundsInParent().getMinX()));
        double dy = (SceneConfig.GRAPHCANVAS_HEIGHT/2 - (graphTab.getZoomPane().getBoundsInParent().getHeight()/2 + graphTab.getZoomPane().getBoundsInParent().getMinY()));

        graphTab.getZoomPane().setPivot(f*dx, f*dy, scale);

        graphTab.getMinimapDelegator().changeMinimapMasking();
                
        
    }
    
    private static double clamp( double value, double min, double max) {

        if( Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;

        return value;
    }    
    
}
