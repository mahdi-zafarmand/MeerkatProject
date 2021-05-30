/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate.UIComponents;

import config.GraphEditingToolsConfig;
import config.ModeConfig;
import config.ModeInformationConfig;
import globalstate.MeerkatUI;
import graphelements.UIEdge;
import graphelements.VertexHolder;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import ui.features.ShortestPath;

public class ShortestPathMode implements GraphCanvasMode{
    
    ImageCursor c1 ;
    
    private Boolean blnOneVertexSelected = false;
    private VertexHolder firstVertexInVertexPair = null;
    
    @Override
    public void activateGraphCanvasMode(ScrollPane scrlCanvas, SceneGestures4 sceneGestures, ModeInformation modeInfo) {
        //scrlCanvas.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        //scrlCanvas.addEventFilter( MouseEvent.MOUSE_RELEASED, sceneGestures.getOnMouseReleasedEventHandler());
        //scrlCanvas.addEventFilter( MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
        scrlCanvas.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        
        sceneGestures.setCurrentGraphCanvasMode(this);
        modeInfo.updateModeInformation(ModeConfig.ModeTypes.SHORTESTPATH, ModeConfig.SHORTESTPATH_MODE, ModeInformationConfig.SELECTVERTEX1INFO);
        
        System.out.println("-=-----------------===================------------ In SHORTESTPATHMODE ");
        
        // Changing the cursor of the application
//        c1 = new ImageCursor(new Image(GraphEditingToolsConfig.getShortestPathImageURL()));
//        scrlCanvas.setOnMouseEntered(new EventHandler() {
//            @Override
//            public void handle(Event event) {
//                scrlCanvas.setCursor(c1); 
//            }
//        });
//        scrlCanvas.setOnMouseExited(new EventHandler() {
//            @Override
//            public void handle(Event event) {
//                scrlCanvas.setCursor(Cursor.DEFAULT); 
//            }
//        });
    }

    @Override
    public void primaryMouseReleased() {
  
    }

    @Override
    public void primaryMousePressed() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void primaryMouseReleasedOnVertex(VertexHolder vertexHolder, ModeInformation modeInfo) {
        System.out.println("-=-----------------===================------------ In SHORTESTPATHMODE MOUSERELEASED");
                
        if(blnOneVertexSelected==true){
            blnOneVertexSelected = false;
            
            modeInfo.updateModeInformation(ModeConfig.ModeTypes.SHORTESTPATH, ModeConfig.SHORTESTPATH_MODE, ModeInformationConfig.SELECTVERTEX1INFO);
        } else {
            firstVertexInVertexPair = vertexHolder;
            blnOneVertexSelected = true;
            
            modeInfo.updateModeInformation(ModeConfig.ModeTypes.SHORTESTPATH, ModeConfig.SHORTESTPATH_MODE, ModeInformationConfig.SELECTVERTEX2INFO);
        }
        
        if(!blnOneVertexSelected){
            // AddEdgeDialog.Display(firstVertexInVertexPair, vertexHolder, UIInstance.getController());
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            int intProjectId = UIInstance.getActiveProjectID();
            int intGraphId = UIInstance.getActiveProjectTab().getActiveGraphID();
            int intTimeFrameIndex = UIInstance.getActiveProjectTab().getActiveGraphTab().getTimeFrameIndex();
            String strAlgoName = "Dijkstra";
            
            ShortestPath shortestPathAlgo = new ShortestPath();
            
            shortestPathAlgo.runAlgorithm(intProjectId, intGraphId, intTimeFrameIndex, 
                    strAlgoName, firstVertexInVertexPair.getID(), vertexHolder.getID(), 
                    UIInstance.getController());
            vertexHolder.deselectVertex();
            
            modeInfo.updateModeInformation(ModeConfig.ModeTypes.SHORTESTPATH, ModeConfig.SHORTESTPATH_MODE, ModeInformationConfig.SELECTVERTEX1INFO);
        }
    }

    @Override
    public void primaryMouseReleasedOnEdge(UIEdge uiEdge) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}