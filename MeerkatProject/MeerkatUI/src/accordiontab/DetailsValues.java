/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accordiontab;

import ca.aicml.meerkat.api.GraphAPI;
import ca.aicml.meerkat.api.analysis.GraphDetails;
import config.LangConfig;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *  Class Name      : DetailsValues
 *  Created Date    : 2015-08-26
 *  Description     : Graph specific values to be displayed on the details page
 *  Version         : 1.0
 *  @author         : Talat
 * 
 *  EDIT HISTORY (most recent at the top)
 *  Date            Author          Description
 *  
 * 
*/
public class DetailsValues {
    
    // VARIABLES TO STORE THE VALUES
    private int intEdgeCount ;
    private int intVertexCount ;
    private double dblDensity ;
    private double dblAvgConnections ;
    private double dblAvgCoefficient ;
    private double dblAvgAssortavity ;
    private double dblAvgShortestPath;
    
    private Button btnAvgConnections ;
    private Button btnAvgCoefficient ;
    private Button btnAvgAssortavity ;
    private Button btnAvgShortestPath;
    
    private Label lblAvgConnections ;
    private Label lblAvgCoefficient ;
    private Label lblAvgAssortavity ;
    private Label lblAvgShortestPath;
    
    // GETTERS AND SETTERS
    public void setEdgeCount(int pintEdgeCount){
        this.intEdgeCount = pintEdgeCount;
    }
    public int getEdgeCount(){
        return this.intEdgeCount;
    }
    
    public void setVertexCount(int pintVertexCount){
        this.intVertexCount = pintVertexCount;
    }
    public int getVertexCount(){
        return this.intVertexCount;
    }
    
    public void setDensity(double pdblDensity){
        this.dblDensity = pdblDensity;
    }
    public double getDensity(){
        return this.dblDensity ;
    }
    
    public void setAvgConnections(double pdblAvgConnections){
        this.dblAvgConnections = pdblAvgConnections;
    }
    public double getAvgConnections(){
        return this.dblAvgConnections ;
    }
    
    public void setAvgCoefficient(double pdblAvgCoefficient){
        this.dblAvgCoefficient = pdblAvgCoefficient;
    }
    public double getAvgCoefficient(){
        return this.dblAvgCoefficient;
    }
    
    public void setAvgAssortavity(double pdblAvgAssortavity){
        this.dblAvgAssortavity = pdblAvgAssortavity;
    }
    public double getAvgAssortavity(){
        return this.dblAvgAssortavity;
    }
    
    public void setAvgShortestPath(double pdblAvgShortestPath){
        this.dblAvgShortestPath = pdblAvgShortestPath;
    }
    public double getAvgShortestPath(){
        return this.dblAvgShortestPath;
    }    
    
    
    
    /***********    CONSTRUCTORS    **************/
    /**
     *  Constructor Name: DetailsValues
     *  Created Date    : 2015-07-17
     *  Description     : Initializes the LayoutContent Tab in the Accordion Pane with the layouts retrieved the input configuration file
     *  Version         : 1.0
     *  @author         : Talat
     * 
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     * 
     * 
    */   
    public DetailsValues(){
        
        btnAvgConnections = new Button(LangConfig.GENERAL_COMPUTE);
        btnAvgConnections.setOnAction(e -> {
            
        });
        
        btnAvgCoefficient = new Button(LangConfig.GENERAL_COMPUTE);
        btnAvgCoefficient.setOnAction(e -> {
            
        });
        
        btnAvgAssortavity = new Button(LangConfig.GENERAL_COMPUTE);
        btnAvgAssortavity.setOnAction(e -> {
            
        });
        
        btnAvgShortestPath = new Button(LangConfig.GENERAL_COMPUTE);        
        btnAvgShortestPath.setOnAction(e -> {
            
        });
        
    }
    
    
    /**
     *  Method Name     : updateDetailsValues
     *  Created Date    : 2015-08-24
     *  Description     : Updates the values to be displayed on the Values
     *  Version         : 1.0
     *  @author         : Talat
     * 
     *  @param pintProjectID : int
     *  @param pintGraphID : int
     *  @param pintTimeFrameIndex : int
     * 
     *  EDIT HISTORY (most recent at the top)
     *  Date            Author          Description
     *  2016-02-25      Talat           Added a parameter to send the TimeFrameIndex
     * 
    */
    public void updateDetailsValues(int pintProjectID, int pintGraphID, int pintTimeFrameIndex) {

        setVertexCount(GraphAPI.getVertexCount(pintProjectID, pintGraphID, pintTimeFrameIndex));
        setEdgeCount(GraphAPI.getEdgeCount(pintProjectID, pintGraphID, pintTimeFrameIndex));
        setDensity(-1);
        setAvgConnections(-1);
        setAvgCoefficient(-1);
        setAvgAssortavity(-1);
        //run time of getAvgShortestPath = O(V.(V+E)+V*V)
        setAvgShortestPath(-1);

        
        /* Don't calculate these values while loading the graph. Calculate them on demand
        
        
        setVertexCount(GraphAPI.getVertexCount(pintProjectID, pintGraphID, pintTimeFrameIndex));
        setEdgeCount(GraphAPI.getEdgeCount(pintProjectID, pintGraphID, pintTimeFrameIndex));
        setDensity(GraphDetails.getDensity(pintProjectID, pintGraphID, pintTimeFrameIndex));
        setAvgConnections(GraphDetails.getAvgNumberOfConnections(pintProjectID, pintGraphID, pintTimeFrameIndex));
        setAvgCoefficient(GraphDetails.getAvgClusteringCoefficient(pintProjectID, pintGraphID, pintTimeFrameIndex));
        setAvgAssortavity(GraphDetails.getAverageAssortativity(pintProjectID, pintGraphID, pintTimeFrameIndex));
        //run time of getAvgShortestPath = O(V.(V+E)+V*V)
        setAvgShortestPath(GraphDetails.getAvgShortestPath(pintProjectID, pintGraphID, pintTimeFrameIndex));
        */
    }
}
