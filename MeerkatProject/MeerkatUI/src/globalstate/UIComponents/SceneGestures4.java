/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate.UIComponents;

import config.SceneConfig;
import globalstate.GraphCanvas;
import graphelements.CanvasContextMenu;
import graphelements.UIEdge;
import graphelements.UIVertex;
import java.util.Map;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import ui.elements.ColorToolBox;
import ui.elements.IconToolBox;
import ui.elements.SizeToolBox;

/**
 *
 * @author AICML Administrator
 */
public class SceneGestures4 {
    
    private static final double MAX_SCALE = 8.0d;
    private static final double MIN_SCALE = .125d;
    
    private DragContext sceneDragContext = new DragContext();
    private Map<Integer, GraphCanvas> graphCanvasMap;
    private SimpleIntegerProperty intCurrentTimeFrameIndexProperty;
    private Rectangle rectDragMask;
    private MinimapDelegator minimapDeligator;
    private PanAndZoomPane[] ArrPanAndZoomPane;
    private GraphCanvasMode currentGraphCanvasMode;
    //private GraphCanvas pGraphElements;
    private boolean ignoreFirstScroll;

        public SceneGestures4( PanAndZoomPane[] pArrpanAndZoomPane, Map<Integer, GraphCanvas> graphCanvasMap, SimpleIntegerProperty intCurrentTimeFrameIndexProperty, MinimapDelegator minimapDeligator) {
            this.ArrPanAndZoomPane = pArrpanAndZoomPane;
            this.graphCanvasMap = graphCanvasMap;
            this.intCurrentTimeFrameIndexProperty = intCurrentTimeFrameIndexProperty;
            this.minimapDeligator = minimapDeligator;
            //this.pGraphElements = pGraphElements;
            this.ignoreFirstScroll = true;
            
            setUpSelectionRectangle();
            //TODO - check the piece of code below - what it does and is it required
            /*
            final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<>();
                grpScrollContent.setOnMousePressed((MouseEvent event) -> {
                lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
                });
        
            grpScrollContent.setOnMouseClicked((MouseEvent event) -> {
                EditingToolBox editTool = EditingToolBox.getInstance() ;
                    if (event.isPrimaryButtonDown()) {
                        if (editTool.IsAddVertex()) {
                            GraphAPI.addVertex(intProjectID, intGraphID, getMousePointerPosition().getX(), getMousePointerPosition().getY());
                    }
                }
            });
        */
        }
        public void setCurrentGraphCanvasMode(GraphCanvasMode currentGraphCanvasMode){
            this.currentGraphCanvasMode = currentGraphCanvasMode;
        }
        public Double getXRectDragMask(){
            return rectDragMask.getX();
        }
        
        public Double getYRectDragMask(){
            return rectDragMask.getY();
        }
        
        private void setUpSelectionRectangle(){
                rectDragMask = new Rectangle(0,0,0,0);
                rectDragMask.setStroke(Color.valueOf(SceneConfig.GRAPHCANVAS_DRAGRECTANGLE_BORDERCOLOR));
                rectDragMask.setStrokeWidth(1);
                rectDragMask.setStrokeLineCap(StrokeLineCap.ROUND);
                rectDragMask.setFill(Color.valueOf(SceneConfig.GRAPHCANVAS_DRAGRECTANGLE_FILLCOLOR).deriveColor(
                  SceneConfig.GRAPHCANVAS_DRAGRECT_HUESHIFT
                , SceneConfig.GRAPHCANVAS_DRAGRECT_SATURATION
                , SceneConfig.GRAPHCANVAS_DRAGRECT_BRIGHTNESS
                , SceneConfig.GRAPHCANVAS_DRAGRECT_OPACITY));
        }

        public EventHandler<MouseEvent> getOnMouseClickedEventHandler() {
            return onMouseClickedEventHandler;
        }

        public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
            return onMousePressedEventHandler;
        }

        public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
            return onMouseDraggedEventHandler;
        }

        public EventHandler<ScrollEvent> getOnScrollEventHandler() {
            return onScrollEventHandler;
        }
        
        public EventHandler<MouseEvent> getOnMouseReleasedEventHandler() {
            return onMouseReleasedEventHandler;
        }

        private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {

                sceneDragContext.mouseAnchorX = event.getX();
                sceneDragContext.mouseAnchorY = event.getY();

                sceneDragContext.translateAnchorX = getPanAndZoomPane().getTranslateX();
                sceneDragContext.translateAnchorY = getPanAndZoomPane().getTranslateY();
                
                double panAndZoomPaneParentX = getPanAndZoomPane().getBoundsInParent().getMinX();
                double panAndZoomPaneParentY = getPanAndZoomPane().getBoundsInParent().getMinY();
                double panAndZoomPaneLocalMinX = getPanAndZoomPane().getBoundsInLocal().getMinX();
                double panAndZoomPaneLocalMinY = getPanAndZoomPane().getBoundsInLocal().getMinY();
                // System.out.println("SceneGestures4.mouseClick: panAndZoomPane MinX,MinY :: MaxX,MaxY = ( " + panAndZoomPaneParentX + ", " + panAndZoomPaneParentY + " ) :: ( " + getPanAndZoomPane().getBoundsInParent().getMaxX() + ", " + getPanAndZoomPane().getBoundsInParent().getMaxY() + ", ... " + getPanAndZoomPane().getWidth() + ", " + getPanAndZoomPane().getHeight());
                //scale and transform the co-ordinates recieved from scrollpane
                //add the localMin coordinates because it may not be zero, there may be a vertex at (-ve, -ve).
                double locationXRectDragMask = panAndZoomPaneLocalMinX + (sceneDragContext.mouseAnchorX - panAndZoomPaneParentX)/getPanAndZoomPane().getScale();
                double locationYRectDragMask = panAndZoomPaneLocalMinY + (sceneDragContext.mouseAnchorY - panAndZoomPaneParentY)/getPanAndZoomPane().getScale();
                
                                
                    //rectDragMask.setX((sceneDragContext.mouseAnchorX - panAndZoomPaneOffsetX)/panAndZoomPane.getScale());
                    //rectDragMask.setY((sceneDragContext.mouseAnchorY - panAndZoomPaneOffsetY)/panAndZoomPane.getScale());
                    
                    //set X,Y co-ordinates of mouseClick of PanAndZoomPane
                    getPanAndZoomPane().setMouseClickX(locationXRectDragMask);
                    getPanAndZoomPane().setMouseClickY(locationYRectDragMask);
                    
                    rectDragMask.setX(locationXRectDragMask);
                    rectDragMask.setY(locationYRectDragMask);
                    rectDragMask.setWidth(0);
                    rectDragMask.setHeight(0);
                    //System.out.println("SceneGestures2.mouseClick: Click on Canvas : mouseAnchorX, mouseAnchorY = " + sceneDragContext.mouseAnchorX + ", " + sceneDragContext.mouseAnchorY);
                    // System.out.println("SceneGestures4.mouseClick: Click on Canvas : rextDragmask" + rectDragMask.toString());
                    //removing the add rectDragMask and adding it to onMouseDragged listener.
//                    if(!getPanAndZoomPane().getChildren().contains(rectDragMask))
//                        getPanAndZoomPane().getChildren().add(rectDragMask);
                    //pGraphElements.getChildren().add(rectDragMask);
                    // Create an instance of the Canvas Context Menu. Hide them by default (will hide it if we click on the canvas)
                    // and then recreate a context menu on right clicking the canvas
                    CanvasContextMenu ccmInstance = CanvasContextMenu.getInstance();
                    ccmInstance.Hide();
                    graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).setDragged(false);
                    
                    currentGraphCanvasMode.primaryMousePressed();
            
            }

        };

        private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                
                setMouseX(mouseEvent.getX());
                setMouseY(mouseEvent.getY());
            
                if (mouseEvent.isPrimaryButtonDown()) {
                    
                    if(!getPanAndZoomPane().getChildren().contains(rectDragMask))
                        getPanAndZoomPane().getChildren().add(rectDragMask);

                        if (!graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).getIsVertexClicked()) {
                            double offsetX = (mouseEvent.getX() - sceneDragContext.mouseAnchorX)/getPanAndZoomPane().getScale();
                            double offsetY = (mouseEvent.getY() - sceneDragContext.mouseAnchorY)/getPanAndZoomPane().getScale();
                            double panAndZoomPaneParentMinX = getPanAndZoomPane().getBoundsInParent().getMinX();
                            double panAndZoomPaneParentMinY = getPanAndZoomPane().getBoundsInParent().getMinY();
                            
                            double panAndZoomPaneLocalMinX = getPanAndZoomPane().getBoundsInLocal().getMinX();
                            double panAndZoomPaneLocalMinY = getPanAndZoomPane().getBoundsInLocal().getMinY();
                            
                            /*
                            System.out.println("GraphTab.mouseClick: Click on Canvas :"
                                    + " rextDragmask" + rectDragMask.toString());
                            System.out.println("GraphTab.createZoomPane(): MOUSE DRAGGED");
                            */
                            
                            if(offsetX > 0) {
//                                     System.out.println("ScdeneGestures2.onMouseDragged offsetX > 0): " + rectDragMask.toString());
                                    rectDragMask.setWidth(offsetX);
                            } else {
                                //rectDragMask.setX(mouseEvent.getX());
                                    //scale and transform the co-ordinates recieved from scrollpane
                                    //add the localMin coordinates because it may not be zero, there may be a vertex at (-ve, -ve).
                                    // System.out.println("ScdeneGestures2.onMouseDragged offsetX << 0): " + rectDragMask.toString());
                                    double newXCoordinateRectDragMask = panAndZoomPaneLocalMinX + (mouseEvent.getX() - panAndZoomPaneParentMinX)/getPanAndZoomPane().getScale();
                                
                                    rectDragMask.setX(newXCoordinateRectDragMask);
                                    //rectDragMask.setWidth(sceneDragContext.mouseAnchorX - rectDragMask.getX());
                                    rectDragMask.setWidth(-1*offsetX);
                            }

                            if(offsetY > 0) {
                                rectDragMask.setHeight(offsetY);                                
                            } else {
                                //rectDragMask.setY(mouseEvent.getY());
                                    double newYCoordinateRectDragMask = panAndZoomPaneLocalMinY + (mouseEvent.getY() - panAndZoomPaneParentMinY)/getPanAndZoomPane().getScale();
                                
                                    rectDragMask.setY(newYCoordinateRectDragMask);
                                    //rectDragMask.setHeight(sceneDragContext.mouseAnchorY - rectDragMask.getY());
                                    rectDragMask.setHeight(-1*offsetY);                                
                            }
                        }                    
                }   



                //----------------------------------------//
                // right mouse button => panning
                if( mouseEvent.isSecondaryButtonDown()){
                    graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).setDragged(true);
                            
                    CanvasContextMenu ccmInstance = CanvasContextMenu.getInstance();
                    ccmInstance.Hide();

                    getPanAndZoomPane().setTranslateX(sceneDragContext.translateAnchorX + mouseEvent.getX() - sceneDragContext.mouseAnchorX);
                    getPanAndZoomPane().setTranslateY(sceneDragContext.translateAnchorY + mouseEvent.getY() - sceneDragContext.mouseAnchorY);

                    
                    minimapDeligator.changeMinimapMasking();
                    mouseEvent.consume();
                }
            }
        };

        /**
         * Mouse wheel handler: zoom to pivot point
         */
        private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {
//            MAHDI:
//            There was a problem that this function has been called twice when running the jar file 
//            (the first one was the extra, always with scrollup event). To avoid that problem I added 
//            the ignoreFirstScroll attribute to SceneGestures4 class to always ignore the first mouse scroll event. 
//            There should be a better way to solve this.
            
            @Override
            public void handle(ScrollEvent event) {
                if(ignoreFirstScroll == true){
                    ignoreFirstScroll = false;
                    return;
                }
               double delta = PanAndZoomPane.DEFAULT_DELTA;

                double scale = getPanAndZoomPane().getScale(); // currently we only use Y, same value is used for X
                double oldScale = scale;

                getPanAndZoomPane().setDeltaY(event.getDeltaY()); 
                if (getPanAndZoomPane().deltaY.get() < 0) {
                    scale /= delta;
                } else {
                    scale *= delta;
                }
                scale = clamp( scale, MIN_SCALE, MAX_SCALE);
                double f = (scale / oldScale)-1;
                //double f = (scale - oldScale);

                double dx = (event.getX() - (getPanAndZoomPane().getBoundsInParent().getWidth()/2 + getPanAndZoomPane().getBoundsInParent().getMinX()));
                double dy = (event.getY() - (getPanAndZoomPane().getBoundsInParent().getHeight()/2 + getPanAndZoomPane().getBoundsInParent().getMinY()));

                getPanAndZoomPane().setPivot(f*dx, f*dy, scale);
                
                minimapDeligator.changeMinimapMasking();
                
                ignoreFirstScroll = true;
                event.consume();
            }
        };

        /**
         * Mouse click handler
         */
        private EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        getPanAndZoomPane().resetZoom();
                        //minimapDeligator.updateMinimap();
                        minimapDeligator.changeMinimapMasking();
                    }
                }
                //if (event.getButton().equals(MouseButton.SECONDARY)) {
                //    if (event.getClickCount() == 2) {
                //        panAndZoomPane.fitWidth();
                //    }
                //}
            }
        };
        
        private EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>(){
        public void handle(MouseEvent mouseEvent){
        
            // ON MOUSE BUTTON RELEASE

            setMouseX(mouseEvent.getX());
            setMouseY(mouseEvent.getY());
                        
            // Check if there was any signal that was sent from any of the vertices
            if(!graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).getIsVertexClicked() &&
                    !graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).getIsEdgeClicked()) {            
                // Do this only for a right click
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    double dblStartX = rectDragMask.getX() ;
                    double dblStartY = rectDragMask.getY() ;
                    double dblEndX = dblStartX + rectDragMask.getWidth() ;
                    double dblEndY = dblStartY + rectDragMask.getHeight() ;
                    //System.out.println("GraphTab.mouseClick: Release Click on Canvas : rextDragmask" + rectDragMask.toString());
                    //System.out.println("Rectangle X: "+dblStartX+"->"+dblEndX+"\tY: "+dblStartY+"->"+dblEndY);

                    // VERTICES
                    // The deselection of the vertices takes place when they dont fall within the boundaries of the rectangular mask
                    // In current time frame graphcanvas
                    for (UIVertex currentVertex : graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).getVertices().values()){
                        double dblVertexCentreX = currentVertex.getVertexHolder().getXCentreProperty().get() ;
                        double dblVertexCentreY = currentVertex.getVertexHolder().getYCentreProperty().get() ;

                        //System.out.println("Vertex: X: "+dblVertexCentreX+"\tY: "+dblVertexCentreY);

                        if (dblVertexCentreX >= dblStartX 
                                && dblVertexCentreX <= dblEndX 
                                && dblVertexCentreY >= dblStartY
                                && dblVertexCentreY <= dblEndY) {
                            //System.out.println("RubberbandSelection: Selected Item id : "+currentVertex.getVertexHolder().getID());
                            currentVertex.getVertexHolder().selectVertex();
                        } else {
                            currentVertex.getVertexHolder().deselectVertex();
                        }
                    }
                    graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).setIsVertexClicked(false);
                    
                    // EDGES
                    // The selection of Edges take place when both the vertices of an edge are already selected
                    
                    // Check if the edges are available if both the vertices are selected
                    for (UIEdge currentEdge : graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).getEdges().values()){
                        
                        UIVertex vtxSource = graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).getVertices().get(currentEdge.getSourceVertexID()) ;
                        UIVertex vtxDestination = graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).getVertices().get(currentEdge.getDestinationVertexID()) ;
                        //System.err.println("GraphTab: Selected EdgeID : vtxSource : " + vtxSource + " , " + vtxSource.getVertexHolder() + " , " + vtxSource.getVertexHolder().getID());
                        
                        if (graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).getSelectedVertices()
                                .contains(vtxSource) && 
                                graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).getSelectedVertices()
                                        .contains(vtxDestination)) {
                            currentEdge.selectEdge();
                            //System.out.println("GraphTab: Selected EdgeID : "+currentEdge.getID()+" with Vertices: "+vtxSource.getVertexHolder().getID()+" & "+vtxDestination.getVertexHolder().getID());
                        } else {
                            currentEdge.deselectEdge();
                        }
                        
                    }
                    
                    currentGraphCanvasMode.primaryMouseReleased();
                    
                }
                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                    // System.out.println("SceneGestures4 mouseClick: Right Mouse Pressed on Canvas");
                
                    if (!graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).getIsVertexClicked() &&
                            !graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).getIsEdgeClicked() &&
                            !graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).getDragged()) {
                        // Display the Context Menu on right click mouse release
                        CanvasContextMenu ccmInstance = CanvasContextMenu.getInstance();
                        //ccmInstance.Show(canvas, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                        ccmInstance.Show(getPanAndZoomPane(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
                    }
                } 
            }            
            rectDragMask.setX(0);
            rectDragMask.setY(0);
            rectDragMask.setWidth(0);
            rectDragMask.setHeight(0);
            getPanAndZoomPane().getChildren().remove(rectDragMask);
            //pGraphElements.getChildren().remove(rectDragMask);
            graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).setIsVertexClicked(false);
            //TODO use this varible in next line : blnShowEdgeLabel
            //graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).setIsEdgeClicked(blnShowEdgeLabel);
            graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).setIsEdgeClicked(false);
            // System.out.println("SceneGestures4.OnMouseReleased(): MOUSE RELEASED");
            //MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            //UIInstance.getActiveProjectTab().getActiveGraphTab().updateMiniMap();
            minimapDeligator.updateMinimap();
            
            //update vertex/edge size tools on deselection.
            if(graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).getSelectedVertices().size()<=0){
                SizeToolBox.getInstance().disableVertexSizeToolbox();
                ColorToolBox.getInstance().disableVertexColorToolbox();
                IconToolBox.getInstance().disableIconToolbox();
            }
            if(graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).getSelectedEdges().size()<=0){
                SizeToolBox.getInstance().disableEdgeSizeToolbox();
                ColorToolBox.getInstance().disableEdgeColorToolbox();
            }
        }

    };    
    private double clamp( double value, double min, double max) {

        if( Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;

        return value;
    }    
    public void setMouseX(double pdblValue) {
        //mouseContext.mouseX = pdblValue ;
        graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).setMouseX(pdblValue);
    }
    public void setMouseY(double pdblValue) {
        //mouseContext.mouseY = pdblValue ;
        graphCanvasMap.get(intCurrentTimeFrameIndexProperty.getValue()).setMouseY(pdblValue);
    }
    private PanAndZoomPane getPanAndZoomPane(){
        return ArrPanAndZoomPane[intCurrentTimeFrameIndexProperty.getValue()];
    }
}

