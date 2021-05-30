/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate.UIComponents;

import config.AppConfig;
import config.ModeConfig;
import config.ModeConfig.ModeTypes;
import config.ModeInformationConfig;
import config.SceneConfig;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 *
 * @author talat
 */
public class ModeInformation {
    private StackPane stkModeInformation ; // Holder for the Mode Information
    private Label lblModeInformation ; // Label that displays the information
    private ModeTypes modeType ;
    
    
    public ModeInformation(){
        modeType = ModeTypes.SELECT;
        String strModeInformation = ModeConfig.SELECT_MODE + AppConfig.DELIMITER_SEMICOLON 
                + " " + ModeInformationConfig.SELECTMODEINFO;
        stkModeInformation = addModeInformation(strModeInformation);
    }
    
    public StackPane getStackModeInformation() {
        return this.stkModeInformation;
    }
    
    public void updateModeInformation(ModeTypes pmodeType, String pstrMode, String pstrNewInformation){
        this.modeType = pmodeType;
        lblModeInformation.setText(pstrMode + ": " + pstrNewInformation);
    }
    
    public StackPane addModeInformation(String pstrModeInformation) {
        
        StackPane stkpaneMiniMap_Return = new StackPane();
        stkpaneMiniMap_Return.setMinWidth(SceneConfig.MODEINFO_WIDTH) ;
        stkpaneMiniMap_Return.setMinHeight(SceneConfig.MODEINFO_HEIGHT) ;

        stkpaneMiniMap_Return.setMaxWidth(SceneConfig.MODEINFO_WIDTH) ;
        stkpaneMiniMap_Return.setMaxHeight(SceneConfig.MODEINFO_HEIGHT) ;

        lblModeInformation = new Label(pstrModeInformation);
        // lblModeInformation.setContentDisplay(ContentDisplay.LEFT);
        // lblModeInformation.setAlignment(Pos.BASELINE_LEFT);
        stkpaneMiniMap_Return.getChildren().add(lblModeInformation);
        StackPane.setAlignment(lblModeInformation, Pos.BOTTOM_LEFT);    
        StackPane.setMargin(lblModeInformation, new Insets(0, 0, 0, 0));
        
        return stkpaneMiniMap_Return;
    }
    
    public ModeTypes getModeType() {
        return this.modeType;
    }
}
