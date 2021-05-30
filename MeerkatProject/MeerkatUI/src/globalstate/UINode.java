/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package globalstate;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author aicml_adm
 */
public class UINode {
    
    private int intID ;
    private Shape shapeNode ;
    private Point2D point2D ;
    private Point3D point3D ;
    private Color clrBorder ;
    private Color clrFill ;
    private Text txtLabel ;

    /**
     * @return the intID
     */
    public int getIntID() {
        return intID;
    }

    /**
     * @param intID the intID to set
     */
    public void setIntID(int intID) {
        this.intID = intID;
    }

    /**
     * @return the shapeNode
     */
    public Shape getShapeNode() {
        return shapeNode;
    }

    /**
     * @param shapeNode the shapeNode to set
     */
    public void setShapeNode(Shape shapeNode) {
        this.shapeNode = shapeNode;
    }

    /**
     * @return the point2D
     */
    public Point2D getPoint2D() {
        return point2D;
    }

    /**
     * @param point2D the point2D to set
     */
    public void setPoint2D(Point2D point2D) {
        this.point2D = point2D;
    }

    /**
     * @return the point3D
     */
    public Point3D getPoint3D() {
        return point3D;
    }

    /**
     * @param point3D the point3D to set
     */
    public void setPoint3D(Point3D point3D) {
        this.point3D = point3D;
    }

    /**
     * @return the clrBorder
     */
    public Color getClrBorder() {
        return clrBorder;
    }

    /**
     * @param clrBorder the clrBorder to set
     */
    public void setClrBorder(Color clrBorder) {
        this.clrBorder = clrBorder;
    }

    /**
     * @return the clrFill
     */
    public Color getClrFill() {
        return clrFill;
    }

    /**
     * @param clrFill the clrFill to set
     */
    public void setClrFill(Color clrFill) {
        this.clrFill = clrFill;
    }

    /**
     * @return the txtLabel
     */
    public Text getTxtLabel() {
        return txtLabel;
    }

    /**
     * @param txtLabel the txtLabel to set
     */
    public void setTxtLabel(Text txtLabel) {
        this.txtLabel = txtLabel;
    }
    
    
    
}

