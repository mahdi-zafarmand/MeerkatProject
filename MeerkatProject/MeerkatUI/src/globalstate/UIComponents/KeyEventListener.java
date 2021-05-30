/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate.UIComponents;

import analysisscreen.AnalysisController;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author sankalp
 */
public class KeyEventListener {
    
    
    public KeyEventListener(AnalysisController pController){
        pController.getPrimaryStage().getScene().addEventFilter(KeyEvent.KEY_RELEASED, onKeyReleasedEventHandler);
    }
            
    /**
     * KeyReleasedEvent listener on the application
     * 
    */
    
    private EventHandler<KeyEvent> onKeyReleasedEventHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if(event.isControlDown()){
                if(event.getCode()==KeyCode.EQUALS)
                    ZoomCanvas.zoomCanvas(1.0);
                else if(event.getCode()==KeyCode.MINUS)
                    ZoomCanvas.zoomCanvas(-1.0);
            }
        }
    };
        
            
}
