/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate.UIComponents;

import config.SceneConfig;
import globalstate.GraphTab;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import static javafx.scene.layout.HBox.setHgrow;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;

/**
 *
 * @author AICML Administrator
 */
public class DCSlider extends Slider{
    
    //private Slider sldrTimeFrame ;
    private SimpleIntegerProperty intCurrentTimeFrameIndexProperty;
    private String [] arrstrTimeFrames;
    private GraphTab graphTab;
    
    public DCSlider(SimpleIntegerProperty intCurrentTimeFrameIndexProperty, String [] arrstrTimeFrames, GraphTab graphTab){
        super();
        //this.sldrTimeFrame = new Slider();
        this.intCurrentTimeFrameIndexProperty = intCurrentTimeFrameIndexProperty;
        this.arrstrTimeFrames = arrstrTimeFrames;
        this.graphTab = graphTab;
    }
    
        /**
     *  Method Name     : updateDCSlider()
     *  Created Date    : 2016-01-25
     *  Description     : Updates the Slider with the current time 
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    

    public void updateDCSlider() {        
        this.setMin(0);
        this.setMax(arrstrTimeFrames.length-1);
        this.setValue(intCurrentTimeFrameIndexProperty.getValue());
        this.setMinorTickCount(0);
        this.setMajorTickUnit(1);
        this.setSnapToTicks(true);
        this.setShowTickMarks(true);
        this.setShowTickLabels(true);
        this.setOrientation(Orientation.HORIZONTAL);
        setHgrow(this, Priority.ALWAYS);
        
        this.setPadding(new Insets(10, 5, 10, 5));
        
        
        this.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double dblSliderValue) {
                return arrstrTimeFrames[dblSliderValue.intValue()];
            }

            @Override
            public Double fromString(String pstrValue) {
                
                for (int intIndex=0; intIndex < arrstrTimeFrames.length; intIndex++) {
                    if (pstrValue.equals(arrstrTimeFrames[intIndex])) {
                        return new Double(intIndex);
                    }
                }
                return new Double(arrstrTimeFrames.length - 1);
            }
        });
        
        this.valueProperty().addListener((
                ObservableValue<? extends Number> ov, 
                Number numOldValue, 
                Number numNewValue) -> {
            // save new x and y before going to a different timeframe
            //@TODO 
            
            // changing to a different timeframe
            this.intCurrentTimeFrameIndexProperty.setValue((int)Math.round(this.getValue())); 
                    //(int)Math.round(sldrTimeFrame.getValue());
            System.out.println("GraphTab.slider: "+intCurrentTimeFrameIndexProperty.getValue());
            
            //changeGraphCanvas(intProjectID, intGraphID, intCurrentTimeFrameIndexProperty.getValue());
            graphTab.changeGraphCanvas(graphTab.getProjectID(), graphTab.getGraphID(), intCurrentTimeFrameIndexProperty.getValue());
        });  

        this.setMinWidth(SceneConfig.DCCOMMUNITY_SLIDER_WIDTH);
    }
    
    /**
     *  Method Name     : moveToNextTimeFrame()
     *  Created Date    : 2016-07-04
     *  Description     : Moves to the Next Time Frame of the current graph
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void moveToNextTimeFrame () {
        System.out.println("GraphTab.slider: moveToNextTimeFrame() : " +
                    intCurrentTimeFrameIndexProperty.getValue());
        if (intCurrentTimeFrameIndexProperty.getValue()+1 < arrstrTimeFrames.length) {
            this.setValue(this.getValue()+1);
            System.out.println("GraphTab.slider: moveToNextTimeFrame() : " +
                    intCurrentTimeFrameIndexProperty.getValue());
        } else {
            System.out.println("NO FORWARD TIMEFRAME");
        }
    }
    
    /**
     *  Method Name     : moveToPreviousTimeFrame()
     *  Created Date    : 2016-07-04
     *  Description     : Moves to the Previous Time Frame of the current graph
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  
     * 
    */
    public void moveToPreviousTimeFrame() {
        System.out.println("GraphTab.moveToPreviousTimeFrame() : " +
                    intCurrentTimeFrameIndexProperty.getValue());
        if (intCurrentTimeFrameIndexProperty.getValue() > 0) {
            this.setValue(this.getValue()-1);
            System.out.println("GraphTab.moveToPreviousTimeFrame() : " +
                    intCurrentTimeFrameIndexProperty.getValue());
        } else {
            System.out.println("NO PREVIOUS TIMEFRAME");
        }
             
    }
    
}
