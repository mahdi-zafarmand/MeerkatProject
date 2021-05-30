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
import ui.dialogwindow.AddEdgeDialog;

/**
 *
 * @author AICML Administrator
 */
public class AddEdgeMode implements GraphCanvasMode{
    
    
    private Boolean blnOneVertexSelected = false;
    private VertexHolder firstVertexInVertexPair = null;
    
    ImageCursor c1 ;
    
    @Override
    public void activateGraphCanvasMode(ScrollPane scrlCanvas, SceneGestures4 sceneGestures, ModeInformation modeInfo) {
        //scrlCanvas.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        //scrlCanvas.addEventFilter( MouseEvent.MOUSE_RELEASED, sceneGestures.getOnMouseReleasedEventHandler());
        //scrlCanvas.addEventFilter( MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
        scrlCanvas.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        
        sceneGestures.setCurrentGraphCanvasMode(this);
        modeInfo.updateModeInformation(ModeConfig.ModeTypes.EDGEADD, ModeConfig.EDGEADD_MODE, ModeInformationConfig.SELECTVERTEX1INFO);
        
        System.out.println("-=-----------------===================------------ In ADDEDGEMODE ");
        
        // Changing the cursor of the application
        c1 = new ImageCursor(new Image(GraphEditingToolsConfig.getAddEdgeImageURL()));
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
        System.out.println("-=-----------------===================------------ In ADDEDGEMODE MOUSERELEASED");
        // toggle this blnOneVertexSelected every time a vertex is clicked
        
            if(blnOneVertexSelected==true){
                blnOneVertexSelected = false;
                
                modeInfo.updateModeInformation(ModeConfig.ModeTypes.EDGEADD, ModeConfig.EDGEADD_MODE, ModeInformationConfig.SELECTVERTEX1INFO);
            } else {
                firstVertexInVertexPair = vertexHolder;
                blnOneVertexSelected = true;
                
                modeInfo.updateModeInformation(ModeConfig.ModeTypes.EDGEADD, ModeConfig.EDGEADD_MODE, ModeInformationConfig.SELECTVERTEX2INFO);
            }
            
            MeerkatUI UIInstance = MeerkatUI.getUIInstance();
            if(!blnOneVertexSelected){
                AddEdgeDialog.Display(firstVertexInVertexPair, vertexHolder, UIInstance.getController());
                vertexHolder.deselectVertex();
            }
    }

    @Override
    public void primaryMouseReleasedOnEdge(UIEdge uiEdge) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
