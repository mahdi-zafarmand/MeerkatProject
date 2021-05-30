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
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import ui.dialogwindow.VertexDeleteConfirmationDialog;

/**
 *
 * @author AICML Administrator
 */
public class DeleteVertexMode implements GraphCanvasMode{
    
    ImageCursor c1 ;

    @Override
    public void activateGraphCanvasMode(ScrollPane scrlCanvas, SceneGestures4 sceneGestures, ModeInformation modeInfo) {
        scrlCanvas.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scrlCanvas.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scrlCanvas.addEventFilter( MouseEvent.MOUSE_RELEASED, sceneGestures.getOnMouseReleasedEventHandler());
        scrlCanvas.addEventFilter( MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
        scrlCanvas.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        
        sceneGestures.setCurrentGraphCanvasMode(this);
        modeInfo.updateModeInformation(ModeConfig.ModeTypes.VERTEXDELETE, ModeConfig.VERTEXDELETE_MODE, ModeInformationConfig.SELECTVERTEX_DELETE);
        
        System.out.println("-=-----------------===================------------ In DELETEVERTEXMODE mode ACTIVATED ");

        // Changing the cursor of the application
//        c1 = new ImageCursor(new Image(GraphEditingToolsConfig.getDeleteVertexImageURL()));
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
        System.out.println("-=-----------------===================------------ In DeleteVERTEXMode mode MOUSERELEASED");
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        VertexDeleteConfirmationDialog.Display(UIInstance.getController());
        //EdgeDeleteConfirmationDialog.Display(UIInstance.getController());
    }

    @Override
    public void primaryMousePressed() {
        
    }

    @Override
    public void primaryMouseReleasedOnVertex(VertexHolder vertexHolder, ModeInformation modeInfo) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        VertexDeleteConfirmationDialog.Display(UIInstance.getController());
        
    }

    @Override
    public void primaryMouseReleasedOnEdge(UIEdge uiEdge) {
        MeerkatUI UIInstance = MeerkatUI.getUIInstance();
        //EdgeDeleteConfirmationDialog.Display(UIInstance.getController());
    }
    
}
