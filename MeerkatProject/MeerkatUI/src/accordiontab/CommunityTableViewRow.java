/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import config.SceneConfig;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *  Class Name      : CommunityTableViewRow
 *  Created Date    : 2016-03-16
 *  Description     : A Single row represented in the table view of the communities pane (values)
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/

public class CommunityTableViewRow {
    
    /* *************************************************************** */
    /* *******************       VARIABLES     *********************** */
    /* *************************************************************** */
    public StringProperty Community = new SimpleStringProperty();
    public ObjectProperty Vertex = new SimpleObjectProperty();
    private int intVertexID ;
    
    /* *************************************************************** */
    /* ****************       GETTERS & SETTERS     ****************** */
    /* *************************************************************** */
    public Object getVertex() {
        return Vertex.get() ;
    }
    public String getCommunity() {
        return Community.get() ;
    }
    public int getVertexID() {
        return this.intVertexID ;
    }
    
    /* *************************************************************** */
    /* *****************       CONSTRUCTORS     ********************** */
    /* *************************************************************** */
    public CommunityTableViewRow(String pstrVertexID, Color pclrColor, String pstrCommunity) {
        
        try {
            Rectangle rect = new Rectangle(SceneConfig.COMMUNITYRECT_WIDTH, SceneConfig.COMMUNITYRECT_HEIGHT);
            rect.setFill(pclrColor);
            Group grpShape = new Group(rect);

            this.intVertexID = Integer.parseInt(pstrVertexID);
            Text txtParent = new Text(pstrVertexID);
            HBox hboxVertexID = new HBox(grpShape, txtParent) ;
            hboxVertexID.setSpacing(5);
            hboxVertexID.setPadding(new Insets(2,2,2,2));
            hboxVertexID.setAlignment(Pos.CENTER_LEFT);

            this.Community = new SimpleStringProperty(pstrCommunity);
            this.Vertex = new SimpleObjectProperty(hboxVertexID);
        } catch (Exception ex){
            System.out.println("CommunityTableViewRow.CommunityTableViewRow(): EXCEPTION");
            ex.printStackTrace();
        }
    }

    void setCommunityColor(Color newColor) {
        HBox hboxVertexID = (HBox)this.Vertex.get();
        Group grpShape = (Group)hboxVertexID.getChildren().get(0);
        Rectangle rect = (Rectangle)grpShape.getChildren().get(0);
        //TODO make this rectangle as a field in this class and then do this change
        rect.setFill(newColor);
    }
}
