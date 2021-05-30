/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.elements;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class ToggleImageButton extends ToggleButton {
    
    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
    private final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";
    
    public ToggleImageButton(String imageurl) {
//        MAHDI: next line is to remove "file: resources/" from the beginning of the url, needs to be fixed.
        imageurl = imageurl.substring(15);
        setGraphic(new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(imageurl))));
        setStyle(STYLE_NORMAL);
        
        setOnMousePressed((MouseEvent event) -> {
            setStyle(STYLE_PRESSED);            
        });
        
        setOnMouseReleased((MouseEvent event) -> {
            setStyle(STYLE_NORMAL);            
        });
    }    
    
//    public ToggleImageButton(Image pImage) {
//        System.out.println("MAHDI: test -> Image");
//
//        setGraphic(new ImageView(pImage));
//        setStyle(STYLE_NORMAL);
//        
//        setOnMousePressed((MouseEvent event) -> {
//            setStyle(STYLE_PRESSED);            
//        });
//        
//        setOnMouseReleased((MouseEvent event) -> {
//            setStyle(STYLE_NORMAL);            
//        });
//    } 
    
//    public ToggleImageButton(ImageView pImageView) {
//        System.out.println("MAHDI: test -> ImageView");
//
//        setGraphic(pImageView);
////        MAHDI: all next lines were comments, I uncommented them to see the difference.
//        setStyle(STYLE_NORMAL);
//        
//        setOnMousePressed((MouseEvent event) -> {
//            setStyle(STYLE_PRESSED);            
//        });
//        
//        setOnMouseReleased((MouseEvent event) -> {
//            setStyle(STYLE_NORMAL);            
//        });
//    } 
}