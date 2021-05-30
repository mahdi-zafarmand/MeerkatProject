/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import cern.colt.Arrays;
import config.SceneConfig;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *  Class Name      : UIVertexEvent
 *  Created Date    : 2017-03-24
 *  Description     : this is the class for all the event vertices.
 *  Version         : 1.0
 *  @author         : sankalp
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 * 
*/
public final class UIVertexEvent {
    
    private int vertexID;
    private double centerX;
    private double centerY;
    private Circle circle;
    private Color color;
    private double radius = 5.0;
    private Map<String, String> attributeMap;
    private TableView vertexTable;
    private Boolean IndividualEventType = false;
    
    
    public UIVertexEvent(int vertexID, Map<String, String> attributeMap, 
            double centerX, double centerY, Color color) {
        this.vertexID = vertexID;
        this.centerX = centerX;
        this.centerY = centerY;
        this.color = color;
        this.createShape();  
        this.MouseClickProperty();
        this.setAttributeMap(attributeMap);
        this.createTooltip(circle);
    }
   
    
    //Setter & Getter
    public int getVertexID() {
        return this.vertexID;
    }
    public Circle getVertexShape() {
        return this.circle;
    }
    
     public void setColor(Color color) {
        this.circle.setFill(color);
    }
    
    public double getVertexCenterX() {
        return this.centerX;
    } 
    
    public double getVertexCenterY() {
        return this.centerY;
    } 
    
    public void setAttributeMap(Map<String, String> attributeMap){
        this.attributeMap=attributeMap;
    }
    
    public Map<String, String> getAttributeMap(){
        return this.attributeMap;
    }
    
    public void createShape(){
        this.circle = new Circle(centerX, centerY, radius);
        this.circle.setFill(color);   
    }
    
    public void setVertexTable(TableView tabel){
        vertexTable=tabel;
    }
    
    public TableView getVertexTable(){
        return vertexTable;
    }
    
    public void setIndividualEventType(){
        this.IndividualEventType=true;
    }
    
    /**
     *  Method Name     : createTooltip()
     *  Created Date    : 2017-03-26
     *  Description     : creates the event vertex tool tip
     *  Version         : 1.0
     *  @author         : sankalp 
     * 
    */
    public void createTooltip(Circle circle){
        Integer vrtxID = this.vertexID;
        Tooltip t = new Tooltip(vrtxID.toString()+ " "+Arrays.toString(attributeMap.entrySet().toArray()));
        Tooltip.install(circle, t);
    }
    
    /**
     *  Method Name     : MouseClickProperty()
     *  Created Date    : 2017-03-26
     *  Description     : listener for mouse events on event vertices.
     *  Version         : 1.0
     *  @author         : sankalp 
     * 
    */
    public void MouseClickProperty(){
        
        this.circle.setOnMousePressed((MouseEvent mouseEvent) -> {       
            
            // RIGHT CLICK OF THE MOUSE
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {               
                // Add the nodes to the List of selected vertices
                    UIVertexEventProperty.clearSelectedVertex();
                    this.selectVertex();
                    TableView table = this.getVertexTable();
                    
                    //focus the table row when corresponding vertex is clicked.
                    for (int i = 0; i < table.getItems().size(); i++) {
                        Object[] row = (Object[]) table.getItems().get(i);              
                        if(Integer.valueOf((String) row[0])==this.vertexID){
                            EventUtility.focusTableRow(table, i);        
                        }
                    }
                    //adding logic to show the vertex timeline
                    if(this.IndividualEventType){
                        UIVertextEventContextMenu vcmInstance = UIVertextEventContextMenu.getInstance(this);
                        vcmInstance.Show(this.circle, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                    }
            }
            else if(mouseEvent.getButton() ==MouseButton.PRIMARY){
                this.clearSelectVertex();
            }
        });
    }
    
    /**
     *  Method Name     : selectVertex()
     *  Created Date    : 2017-03-26
     *  Description     : selects the vertex initiated by a mouse click.
     *  Version         : 1.0
     *  @author         : sankalp
     * 
    */
    public void selectVertex () {
        try {
            // Change the color of the vertex
            this.setColor(Color.valueOf(SceneConfig.VERTEX_COLOR_SELECTED));
            UIVertexEventProperty.addSelectedVertex(this);     
        } catch (Exception ex) {
            System.out.println("VertexHolder.selectVertex(): EXCEPTION");
            ex.printStackTrace();
        }
    }
    
        
    public void clearSelectVertex() {
        this.setColor(color);
    }
    
//    public void Show (Node pNode, double pdblX, double pdblY) {
//        this.show(pNode, pdblX, pdblY);
//    }
}
