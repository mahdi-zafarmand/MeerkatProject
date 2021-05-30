/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate.UIComponents;


import graphelements.UIEdge;
import graphelements.VertexHolder;
import javafx.scene.control.ScrollPane;

/**
 *
 * @author AICML Administrator
 */
public interface GraphCanvasMode {
    
    public void activateGraphCanvasMode(ScrollPane scrlCanvas, SceneGestures4 sceneGestures, ModeInformation modeInfo);
    
    public void primaryMouseReleased();
    
    public void primaryMousePressed();
    
    public void primaryMouseReleasedOnVertex(VertexHolder vertexHolder, ModeInformation modeInfo);
    
    public void primaryMouseReleasedOnEdge(UIEdge uiEdge);
    
}
